package com.nineclient.task;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nineclient.cherry.wcc.model.WccCheryArticle;
import com.nineclient.cherry.wcc.model.WccCheryBrands;
import com.nineclient.cherry.wcc.util.Linker;
import com.nineclient.utils.Global;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.RedisCommonHelper;
import com.thoughtworks.xstream.io.json.JsonWriter.Format;


@Component
public class CheryCrawlerTask {
	private static final Logger logger = Logger.getLogger(CheryCrawlerTask.class);
    private static SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    /**
	 * 品牌队列
	 */
	public static ConcurrentLinkedDeque<String> CHERY_BRANDS_LINKER= new ConcurrentLinkedDeque<String>();
	
	@Async
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void articleIntoDB(){
		//遍历品牌
		List<WccCheryBrands> brandList=WccCheryBrands.getBrands();
		if(brandList.size()>0){
			for (WccCheryBrands brand : brandList) {
				getLinkerByBrand(brand);
			}
			
		}
	}
	
	//获取网页内容
	public void getLinkerByBrand(WccCheryBrands brand){
		  int currentPage = 1;
		  int totalPage = 10;
		  StringBuffer cookie=new StringBuffer("");
		  while(currentPage <= totalPage){
			 String str="";
			 String reqUrl;
			try {
				reqUrl = "http://weixin.sogou.com/weixin?type=2&query="+URLEncoder.encode(brand.getBrandName(),"utf-8")+"&ie=utf8&_sug_=n&_sug_type_=&page="+currentPage;
			} catch (UnsupportedEncodingException e) {
				reqUrl = "http://weixin.sogou.com/weixin?type=2&query="+brand.getBrandName()+"&ie=utf8&_sug_=n&_sug_type_=&page="+currentPage;
				e.printStackTrace();
			}
			  Map<String, String> reqHeader = new HashMap<String,String>();
			  reqHeader.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			  reqHeader.put("Accept-Encoding","gzip, deflate");
			  reqHeader.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			  reqHeader.put("Connection","keep-alive");
			  reqHeader.put("Cookie","IPLOC=CN3100; SUV=00FD70C77040BAA256F3A6F44F09F855; sct=2; ABTEST=4|1458808568|v1; SNUID=BCA55F6E1F1B30D848923C921F1D6912; SUID=A2BA4070260C930A0000000056F3A6F8; SUID=A2BA40704FC80D0A0000000056F3A6F9; weixinIndexVisited=1");
			  reqHeader.put("Host","weixin.sogou.com");
			  reqHeader.put("Referer",reqUrl);
			  reqHeader.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
			  Linker linker = new Linker(reqUrl);
		  	 linker.setReqHeader(reqHeader);
			 linker.setEncoding("gbk");
			 linker.setRequestMethod("get");			
			 str=HttpClientUtil.doGetCheryHeader(linker.getUrl(), "GBK", cookie);
				if(!StringUtils.isEmpty(str)){
					parseCheryArticle(str,brand,linker.getUrl(),cookie.toString());
				}
			 currentPage++;		
		}
	}
	
	/**
	 * 解析网页数据
	 * @param str
	 * @param brand
	 * @return
	 */
	public static void  parseCheryArticle(String str,WccCheryBrands brand,String purl,String cookie){
	    List<String> focuseList = new ArrayList<String>();
		String regex="<div class=\"txt-box\">[\\s\\S]*?</div>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while(m.find()){
			String focuse = m.group();
			focuseList.add(focuse);
		}
	
 if(focuseList.size() > 0){
	for(String focuse : focuseList){
	  if(!StringUtils.isEmpty(focuse)){
		         String platformAccount=null;
		         String title=null;
		         String url=null;
		         String articleContent=null;
		         String  createTime=null;
		    	 String regex1 = "<a\\s+[^<>]*\\s+href=\"([^<>\"]*)\"[^<>]*>(.*?)</a>";
				 Pattern p1 = Pattern.compile(regex1);
				 Matcher m1 = p1.matcher(focuse);
				 if(m1.find()){
					 url="http://weixin.sogou.com"+m1.group(1);
					 try {
						Header[] headers=HttpClientUtil.getheaders(url.replace("&amp;", "&"), "GBK", cookie, purl);
						if(headers!=null)
							for (Header header : headers) {
								url=header.getValue();
							}
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					 title=filterHtml(m1.group(2));
				 }
				 String regex2="<p id=\".*\">[\\s\\S]*?</p>";
				 Pattern p2 = Pattern.compile(regex2);
				 Matcher m2 = p2.matcher(focuse);
				 if(m2.find()){
					 articleContent=filterHtml(m2.group());
				 }
				 String regex3="<a\\s+[^<>]*\\s+title=\"([^<>\"]*)\"[^<>]*>";
				 Pattern p3 = Pattern.compile(regex3);
				 Matcher m3 = p3.matcher(focuse);
				 if(m3.find()){
					  platformAccount=m3.group(1);
				 }
				 
				 String createTimereg="<span class=\"time\"><script[^>]*?>.*?\\('(\\d*)'\\)</script>(.*?)</span>";
				 Pattern timmp= Pattern.compile(createTimereg);
				 Matcher timem = timmp.matcher(focuse);
				 if(timem.find()){
					     createTime =timem.group(1);
					     createTime = format.format(new Date(Long.parseLong(createTime)*1000));
			     }
	 //判断redis里面是否存在
				 if(!filterCheryComments(brand,title,platformAccount)){
					 WccCheryArticle article=new WccCheryArticle();
					 article.setBrandName(brand.getBrandName());
					 article.setPlatformAccount(platformAccount);
					 article.setTitle(title);
					 article.setUrl(url);
					 article.setArticleContent(articleContent);
					 try {
						article.setCreateDate(format.parse(createTime));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 article.persist();
				 }
	  
	  }
   }
}
}
	
	
	public static boolean  filterCheryComments(WccCheryBrands brand,String title,String platformAccount){
		String key = "chery_brand_"+brand.getId();
		boolean flag = false;
		Set<String> set = RedisCommonHelper.getInstance().getUniqueCommentsSet(key);
		if(set.contains(platformAccount+"_"+title)){
			flag = true;
		}else{
			set.add(platformAccount+"_"+title);
		}
		return flag;
	}
	
	/**
	 * 过滤html标签
	 * @param str
	 * @return
	 */
	public static String filterHtml(String str) {   
        Pattern pattern = Pattern.compile("<([^>]*)>");   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result1 = matcher.find();   
        while (result1) {   
            matcher.appendReplacement(sb, "");   
            result1 = matcher.find();   
        }   
        matcher.appendTail(sb);   
        return sb.toString();   
    }
	
	public static void main(String[] args) {
//		List<WccCheryBrands> brands=WccCheryBrands.findBrand("艾瑞泽5");
//		List<String> liststr=WccCheryBrands.getBrands();
		
		//品牌队列
//		CHERY_BRANDS_LINKER.addAll(WccCheryBrands.getBrands());
//		System.out.println("初始化品牌个数："+WccCheryBrands.getBrands().size());
		//logger.info("初始化品牌个数："+WccCheryBrands.getBrands().size());
	}

}

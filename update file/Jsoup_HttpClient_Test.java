package cn.shb.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 解析http://www.dsat.gov.mo/dsat/carpark_realtime.aspx
 * 中的标题、日期、来源、内容
 * @author Administrator
 * 
 */
public class Jsoup_HttpClient_Test {
    public static void main(String[] args) throws Exception {
    	while(true){
        //第一步：根据url使用httpclient获取页面信息，方法getHtmlByUrl();
        String html = getHtmlByUrl("http://www.dsat.gov.mo/dsat/carpark_realtime_core.aspx").replace("&nbsp;", "");
        if (html!=null&&!"".equals(html)) {
            //第二步：使用jsoup解析html，获取内容
            Document doc =  Jsoup.parse(html);
            String docStr = doc.toString();  
            Elements linksElements = doc.select("table[class=myTable]>tbody>tr");
            System.out.println("*******************************************************************");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
            int i=1;
      /*      //1.先删除数据
            DBTools.delRecord();*/
            //2.在写入新的数据
            for (Element ele:linksElements) {
            	 i++;
            	 String parkName = ele.select(">td[class=MainContentText style2]>div[align=center]").text();
            	 String carNum = ele.select(">td[class=MainContentText style2]>div[align=left]").text();
            	 String motro = ele.select(">td[width=115]").text();
            	 String updateTime = ele.select(">td[width=171]>div[align=center]").text();
            	 if(!"暫停發佈訊息".contains(updateTime)){
            		 System.out.println(parkName+":"+carNum+":"+motro+":"+sdf.parse(updateTime));
            		 DBTools.addData(parkName, carNum, i);
            	 }
            	 
            }
        }
        Thread.currentThread().sleep(1000*11);
    }
    	
    }
    /**
     * 根据URL获得所有的html信息
     * @param url
     * @return
     */
    public static String getHtmlByUrl(String url){
        String html = null;
        //创建httpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        //以get方式请求该URL
        HttpGet httpget = new HttpGet(url);
        
      
httpget.setHeader("Cookie", "ASP.NET_SessionId=nvu1p5552jwn2xbhn1a5dv45");
        /*httpget.setHeader("Host", "www.dsat.gov.mo");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");*/
        try {
            //得到responce对象
            HttpResponse responce = httpClient.execute(httpget);
            
            //返回码
            int resStatu = responce.getStatusLine().getStatusCode();
            if (resStatu==HttpStatus.SC_OK) {//200正常  其他就不对
               //获得输入流
                // modify by cheally InputStream entity = responce.getEntity().getContent();
            	HttpEntity entity =	responce.getEntity();
                if (entity!=null) {
                    //通过输入流转为字符串获得html源代码  注：可以获得实体，然后通过 EntityUtils.toString方法获得html
                	//但是有可能出现乱码，因此在这里采用了这种方式
               // modify by cheally     html=getStreamString(entity);
                	
                	html = EntityUtils.toString(responce.getEntity(), "utf-8");
                    // System.out.println(html);
                }
            	
            	
            }
        } catch (Exception e) {
            System.out.println("访问【"+url+"】出现异常!");
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return html;
    }

    /**
    * 将一个输入流转化为字符串
    */
    public static String getStreamString(InputStream tInputStream){
        if (tInputStream != null){
        try{
	        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
	        StringBuffer tStringBuffer = new StringBuffer();
	        String sTempOneLine = new String("");
        while ((sTempOneLine = tBufferedReader.readLine()) != null){
                tStringBuffer.append(sTempOneLine+"\n");
        }
            return tStringBuffer.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
       }
         return null;
    }
}
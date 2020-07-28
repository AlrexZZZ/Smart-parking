package com.nineclient.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateJsonValueProcessor;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.DkfEntity;
import com.nineclient.utils.HttpClientUtil;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.RecordData;
import com.nineclient.utils.Recordlist;
import com.nineclient.utils.WxEmotion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/friendRecord")
@Controller
@RooWebScaffold(path = "record", formBackingObject = WccRecordMsg.class)
public class RecordController {
	public static Map<String,String> acctoken = new HashMap<String, String>();
	public static Map<String, String> getAcctoken() {
		return acctoken;
	}

	public static void setAcctoken(Map<String, String> acctoken) {
		RecordController.acctoken = acctoken;
	}

	/**
	 * 粉丝记录查询
	 * 
	 * @param uiModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	
	@ResponseBody
	@RequestMapping(value = "loadRecord", produces = "application/json")
	public void loadRecord(String dtGridPager,HttpServletRequest request,Model model,HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		Pager pager =null;
		Map parm = new HashMap<String, String>();
		String nickName = "";
		String platFormId = "";
		String friendGroup = "";
		String area = "";
		String recordContent = "";
		String beginTime = "";
		String endTime = "";
		Long totalCount = null;
		// 查找当用户的公司
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		
		try {
			 pager = PagerPropertyUtils.copy(JSONObject.fromObject(dtGridPager));
			 parm = pager.getParameters();
				 nickName = (String) parm.get("nickName");
				 platFormId = (String) parm.get("platFormId");
				 if("".equals(platFormId)||platFormId==null){
					 List<WccPlatformUser> platList = WccPlatformUser.findAllWccPlatformUsers(user);
					 if(platList.size()==1){
						 platFormId = platList.get(0).getId().toString();
					 }else if (platList.size()>1){
						 for(WccPlatformUser plat : platList){
							 platFormId += plat.getId()+",";
						 }
					 }
				 }
				 friendGroup = (String) parm.get("friendGroup")==null?"":(String) parm.get("friendGroup");
				 area = (String) parm.get("area");
				 recordContent = (String) parm.get("recordContent");
				 beginTime = (String) parm.get("beginTime");
				 endTime = (String) parm.get("endTime");
				 String  s =  (String) parm.get("restart");
				 if(s.equals("y")){
					 pager.setStartRecord(0);
					 pager.setNowPage(1);
				 }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int firstResult  = pager.getStartRecord() ;
		int maxResults   = pager.getPageSize() ;
		int totalPage    = 0;
     
        	try {
				totalCount = WccRecordMsg.findRcordCountByQuery(nickName, platFormId, area, recordContent, beginTime, endTime, friendGroup,company.getId());
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    		pager.setRecordCount(totalCount);//设置总记录数
    		//根据总记录数和每页的大小计算公有多少页
    		if((totalCount%maxResults) > 0){
    			totalPage = (int)((totalCount/maxResults)+1);
    		}else{
    			totalPage =(int)(totalCount/maxResults);
    		}
    		
        	List  ceshi =null;
		//List  list =	WccRecordMsg.findWccRecordMsgEntries(firstResult, maxResults);
		try {
	//ceshi2 =	WccRecordMsg.findRcordByQuery(firstResult, maxResults, nickName, platFormId, area, recordContent, beginTime, endTime, friendGroup,company.getId());
	ceshi =	WccRecordMsg.findRcordByQuery(firstResult, maxResults, nickName, platFormId, area, recordContent, beginTime, endTime, friendGroup,company.getId());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pager.setExhibitDatas(ceshi);
		pager.setPageCount(totalPage);
		pager.setIsSuccess(true);
		//List<WccRecordMsg> list = WccRecordMsg.findAllWccRecordMsg();
	   try {
		response.setContentType("application/json");//设置返回的数据为json对象
		JsonValueProcessor jsonProcessor = new DateJsonValueProcessor();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, jsonProcessor);
		JSONObject pagerJSON = JSONObject.fromObject(pager);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(pagerJSON.toString());
		//System.out.println(pagerJSON.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	/**
	 * 粉丝记录页面
	 * 
	 * @param 
	 * @return
	 */
	
	@RequestMapping(value = "showRecord", produces = "text/html")
	public String showRecord(HttpServletRequest request,Model uiModel,
			HttpServletResponse response) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		List<WccPlatformUser> platformUser = WccPlatformUser.findAllWccPlatformUsers(user);
		uiModel.addAttribute("platformUser", platformUser);
		Object isUcc = request.getSession().getAttribute("isUcc");
		if (null != isUcc && !"".equals(isUcc)) {
			uiModel.addAttribute("isUcc", true);
		}
	   return "record/recordDt";
	}

	/**
	 * 粉丝多客服记录查询
	 * 
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "dkf", produces = "text/html")
	public String showDkf(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "insertTime", required = false) String insertTime,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "pageSize", required = false) String pageSize
			) throws Exception {
	   WccFriend f =null;
		try {
		 f = WccFriend.findWccFriendByOpenId(openId);
		} catch (Exception e) {
			return "暂无粉丝信息！";
			// TODO: handle exception
		}
		WxMpService wxMpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(f.getPlatformUser().getAppId());
		config.setSecret(f.getPlatformUser().getAppSecret());
		wxMpService.setWxMpConfigStorage(config);
		
		String getPostData = null;                                              
		DkfEntity data = new DkfEntity();
		String start = insertTime+" 00:00:00";
		String end = insertTime+" 23:59:59";
	
		data.setStarttime(String.valueOf(DateUtil.getUnixTime(start)));
		data.setEndtime(String.valueOf(DateUtil.getUnixTime(end)));
		data.setOpenid(openId);
		data.setPageindex(pageIndex);
		data.setPagesize(pageSize);
		JSONObject json = JSONObject.fromObject(data);
	  if(f.getPlatformUser().getPlatformId() != null && acctoken.get(f.getPlatformUser().getPlatformId()) == null){
				//获取accessToken	
	   String accessTok =	wxMpService.getAccessToken();
	   //用map保存accesssToken， key是公众账号，value是accessTok
	   acctoken.put(f.getPlatformUser().getPlatformId(),accessTok);
		   }
			//获取多客服信息
			try {
				 getPostData = HttpClientUtil.doJsonPost("https://api.weixin.qq.com/cgi-bin/customservice/getrecord?access_token="+acctoken.get(f.getPlatformUser().getPlatformId()), json.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				   e.printStackTrace();
			}
			if(getPostData.indexOf(" access_token expired") > 0 || getPostData.indexOf("invalid credential") > 0 || getPostData.indexOf("40001") > 0){//如果过期了
				 String accessTok =wxMpService.getAccessToken();
				 acctoken.put(f.getPlatformUser().getPlatformId(), accessTok);
				 getPostData = HttpClientUtil.doJsonPost("https://api.weixin.qq.com/cgi-bin/customservice/getrecord?access_token="+acctoken.get(f.getPlatformUser().getPlatformId()), json.toString());//再次请求
			}
		       //将json转换为   实体类
		RecordData ls = PagerPropertyUtils.jsonToList(JSONObject.fromObject(getPostData));
		JSONObject jo = null;
		List<Recordlist> voList = new ArrayList<Recordlist>();
     if(ls !=null && ls.getRecordlist() != null &&ls.getRecordlist().size() > 0){
    		for(int i=0;i<ls.getRecordlist().size();i++){
   			 jo =	JSONObject.fromObject(ls.getRecordlist().get(i));
   			 Recordlist r = (Recordlist)JSONObject.toBean(jo, Recordlist.class);
   			 voList.add(r);
   		}
     }

			//写到页面上
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			
			for (Recordlist r:voList) {
				if(r.getOpercode().equals("2001")){
					sb.append("<br style='color: blue;'>");
					sb.append(URLDecoder.decode(f.getNickName(), "utf-8")+" "+DateUtil.getUnixTimeToStr(r.getTime()+"000"));
					sb.append("</br>");
					sb.append(" "+r.getText());
				}
				if(r.getOpercode().equals("1001")){
					sb.append("<br style='color: blue;'>");
					sb.append(r.getWorker()+" "+DateUtil.getUnixTimeToStr(r.getTime()+"000"));
					sb.append("</br>");
					sb.append("客服转接中......");
				}
				if(r.getOpercode().equals("2002")){//客服发送消息
					sb.append("<br style='color: blue;'>");
					sb.append(r.getWorker()+" "+DateUtil.getUnixTimeToStr(r.getTime()+"000"));
					sb.append("</br>");
					sb.append(r.getText());
				}
				if(r.getOpercode().equals("2003")){//客服收到消息
					sb.append("<br style='color: blue;'>");
					sb.append(URLDecoder.decode(f.getNickName(), "utf-8")+" "+DateUtil.getUnixTimeToStr(r.getTime()+"000"));
					sb.append("</br>");
					sb.append(r.getText());
				}
				if(r.getOpercode().equals("1004")){//客服发送消息
					sb.append("<br style='color: blue;'>");
					sb.append(r.getWorker()+" "+DateUtil.getUnixTimeToStr(r.getTime()+"000"));
					sb.append("</br>");
					sb.append("客服关闭会话......");
				}
			}
			
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	   return null;
	}
	
	/**
	 * 粉丝本地记录查询
	 * 
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "showNative", produces = "text/html")
	public String showNative(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "strTime", required = false) String strTime,
			@RequestParam(value = "companyId", required = false) String companyId,
			@RequestParam(value = "platFormUser", required = false) String platFormUser
			) throws Exception {
	
List<WccRecordMsg> msgList = WccRecordMsg.findNativeRcords(openId, strTime, Long.parseLong(companyId));
		


			//写到页面上
		response.setContentType("text/Xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			for (WccRecordMsg r:msgList) {
				String content = r.getToUserRecord();
				String wxContent = r.getRecordContent();
				String img = "<img src=\"url\" width=\"24\" height=\"24\" />";
				if (content != null && !"".equals(content.trim())) {
					Set<String> keySet = WccFriendController.getMap().keySet();
					for (String key : keySet) {
						if (content.contains(key)) {
							content = content.replace(key,
									img.replace("url", WccFriendController.getMap().get(key)));
							r.setToUserRecord(content);
						}
					}
					r.setRecordContent(WxEmotion.replaceEmotion(wxContent));// 粉丝发送表情
				}
				
				sb.append("<br>"+platFormUser+"  "+DateUtil.formateDateToString(r.getToUserInsertTime(), "yyyy-MM-dd hh:mm:ss")+"</br>");
				switch (r.getMsgFrom()) {
				case 0:
					sb.append(r.getToUserRecord()==null?"":r.getToUserRecord());
					break;
				case 1:
					sb.append(r.getToUserRecord()==null?"":"<img src='"+r.getToUserRecord()+"'/>");
					break;
				case 2:
					sb.append(r.getToUserRecord()==null?"":"<img style=\"width: 170px; height: 170px;\" src=\"/ump/resources/images/sound_03.png\" onclick=\"changeState1('"+r.getToUserRecord()+"');\"/>");
					break;
				case 3:
					sb.append(r.getToUserRecord()==null?"":"<video style=\"width: 170px; height: 170px;\" controls=\"controls\" src=\""+r.getToUserRecord()+"\">您的浏览器不支持此种视频格式。</video>");
					break;
				case 4:
					WccMaterials mater = WccMaterials.findWccMaterials(Long.parseLong(r.getToUserRecord()));
					String returnStr = "<div style=\"width: 185px; margin-top: 0px; height: auto; overflow: hidden; background: #fff; display: black;border: 1px solid #e5e5e5;\">"
							+ "<div	style=\"width: 94%; height: auto; margin: 0px auto; clear: both; overflow: hidden; margin-top: 5px; text-align: center\">"
							+ "<img src=\""+mater.getThumbnailUrl()+"\" />"
							+ "</div><div style=\"word-break: break-all; width: 94%; height: auto; margin: 0 auto; clear: both; overflow: hidden; margin-top: 5px; font-family: '微软雅黑'; font- size: 14px;\">"
							+ "<a target=\"_blank\" href=\"/ump/wccmaterialses/showdetail/"+mater.getId()+"\">"+mater.getTitle()+"</a></div>";
							if(mater.getChildren().size()>0){
								for(WccMaterials ma : mater.getChildren()){
									returnStr+="<div class=\"tuwen_dandiv_newlist\">"
									+ "<ul style=\"padding-left: 0px; font-family: Microsoft Yahei;\">"
									+ "<li>"
									+ "<div class=\"tuwen_listw\">"
									+ "<a target=\"_blank\" href=\"/ump/wccmaterialses/showdetail/"+ma.getId()+"\">"+ma.getTitle()+"</a>"
									+ "</div>"
									+"<div class=\"tuwen_listt\">"
									+ "<img src=\""+ma.getThumbnailUrl()+"\" />"
									+ "</div></li></ul></div>";
								}
							}
							returnStr+="</div>";
					
					sb.append(r.getToUserRecord()==null?"":returnStr);
					break;
				case 7:
					sb.append(r.getToUserRecord()==null?"":r.getToUserRecord());
					break;
				}
				sb.append("<br>");	
				if(r.getRecordContent()!=null){
					sb.append("<br>");	
					sb.append(URLDecoder.decode(r.getNickName())+"  "+DateUtil.formateDateToString(r.getInsertTime(), "yyyy-MM-dd hh:mm:ss"));
					sb.append("<br>"+(r.getRecordContent()==null?"":r.getRecordContent())+"</br>");
				}
			}
			
			out.println(sb);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}

	   return null;
	}
}

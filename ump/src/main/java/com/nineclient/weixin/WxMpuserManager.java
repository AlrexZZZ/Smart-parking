package com.nineclient.weixin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.log4j.Logger;

import com.nineclient.utils.HttpClientUtil;



public class WxMpuserManager {
	private static final Logger logger = Logger
			.getLogger(WxMpuserManager.class);
public static WxMpUserInfo getUserinfo(String openid,String accesstoken)
{
	String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";
	String responseStr="";
	WxMpUserInfo wxMpUserInfo=null; 
	try {
		responseStr=HttpClientUtil.doGet(url, Consts.UTF_8.toString());
		wxMpUserInfo=JsonHelper.jsonToBean(responseStr,WxMpUserInfo.class);
	} catch (Exception e) {
		logger.error("获取用户信息失败",e);
	}
	return wxMpUserInfo;
}
public static List<WxMpUserInfo> getBatchUserinfo(List<String> openids,String lang,String accesstoken)
{
	List<WxMpUserInfo> list=null;
	String respnoseString="";
	String Url="https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+accesstoken;
	WxRequestBean wxRequestBean=null;
	Map<String, List<WxRequestBean>> map=null;
	List<WxRequestBean> requestlist=null;
	if(openids!=null&&openids.size()>0)
	{ 
		requestlist=new ArrayList<WxRequestBean>();
		for(int i=0;i<openids.size();i++)
		{
			wxRequestBean=new WxRequestBean();
			wxRequestBean.setOpenid(openids.get(i));
			wxRequestBean.setLang(lang);	
			requestlist.add(wxRequestBean);
		}
		map=new HashMap<String, List<WxRequestBean>>();
		map.put("user_list", requestlist);
		try {
			JSONObject jsonObject=JSONObject.fromObject(map);
			respnoseString=HttpClientUtil.doJsonPost(Url,jsonObject , Consts.UTF_8.toString());
		    JSONObject retjsonJsonObject=JSONObject.fromObject(respnoseString);
		    JSONArray userinflist=JSONArray.fromObject(retjsonJsonObject.get("user_info_list"));
		    if(userinflist!=null&&userinflist.size()>0)
		    {
		    	list=new ArrayList<WxMpUserInfo>();
		    	WxMpUserInfo wxMpUserInfo=null;
		    	for(int k=0;k<userinflist.size();k++)
		    	{
		    		wxMpUserInfo=JsonHelper.jsonToBean(userinflist.getJSONObject(k).toString(), WxMpUserInfo.class);
		    		list.add(wxMpUserInfo);
		    	}
		    }
		} catch (Exception e) {
        logger.error("批量获取粉丝信息失败",e);
		}
		
	}
	return list;
}
public static void main(String[] ags)
{   //o32act2_OOzVwOUu2VYzmqqm2yeg
	//o32act8-Yg6GfFD-RcqgiRIkr02c
	WxMpUserInfo wxMpUserInfo=null;
	//wxMpUserInfo=getUserinfo("o32act9imyb8Twe3CxjR9MHzxByM",WxAccessTokenManager.getInstance().getToken("wx2b80d91b34f42833","913d23024f44db49960961018154c1e2"));
	//System.out.print(wxMpUserInfo.getUnionid());
	List<String> openids=new ArrayList<String>();
	openids.add("o32actzkm0Y8HxwaTVo3e9I2aqTo");
	openids.add("o32act9FmZnYn5N4kfVRZrJGs_hk");
	openids.add("o32act780e-nleVzH_rzFKn2R46s");
	List<WxMpUserInfo> list=null;
	//list=getBatchUserinfo(openids, "zh-CN", WxAccessTokenManager.getInstance().getToken("wx2b80d91b34f42833","913d23024f44db49960961018154c1e2"));
	//list=getBatchUserinfo(openids, "zh-CN", "IdqjrgE-7COcsyJQgT22Z3cZMzk89DrCc_CRfVnAL_bpt-lu2ygR78w82z3Y5T-B7E5-8McOt-b7bXOuiAxqPhplrJ6VC5g9Fu_D7vLj2X0");
}
}

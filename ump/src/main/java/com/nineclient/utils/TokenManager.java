package com.nineclient.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;


import org.apache.log4j.Logger;

import com.nineclient.dto.WXAccessToken;
import com.nineclient.utils.RedisCommonHelper;
import com.nineclient.weixin.JsonHelper;

/**
 * 海航方式获取统一平台TOKEN和微信token
 * @author Administrator
 *
 */

public class TokenManager {
	private static Logger logger = Logger.getLogger(TokenManager.class);
	private static Map<String, Object> WXmap = RedisCommonHelper.getInstance().getNormalMap("Token");
	private static String tokenurl="https://api.weixin.qq.com/cgi-bin/token";
	private static  Map<String, Object> isMap= RedisCommonHelper.getInstance().getNormalMap("isavaila");
	TokenManager() {
//		WXmap = RedisCommonHelper.getInstance().getMap("Token");
	}


	
	/**
	 * 获取微信官方的accesstoken
	 * @return
	 */
	public  synchronized static  String getOfficialToken(String appid,String appsecret,boolean isReload) {
		WXAccessToken token = null;
		if(isMap.get(appid)==null)
		{
		if(isReload)
		{
			token = getOfficialTokenEt(appid,appsecret);
			if (token != null) {
				WXmap.put(appid, token);
				WXmap.put(appsecret, token.getAccess_token());
			}
			isMap.remove(appid);
			
		}
		else if (WXmap.containsKey(appid)) {
			token = (WXAccessToken)WXmap.get(appid);
			long time = System.currentTimeMillis() / 1000 - token.getRefershTime();
			if (time >= token.getExpires_in() - 100) {
				token = getOfficialTokenEt(appid,appsecret);
				if (token != null) {
					WXmap.put(appid, token);
					WXmap.put(appsecret, token.getAccess_token());
				}
				isMap.remove(appid);
			}
		

		} else {
			token = getOfficialTokenEt(appid,appsecret);
			if (token != null) {
				WXmap.put(appid, token);
				WXmap.put(appsecret, token.getAccess_token());
			}
			isMap.remove(appid);
		}
		}
		return token != null ? token.getAccess_token() : "";
	}
	public synchronized static WXAccessToken getOfficialTokenEt(String appid,String appsecrete)
	{   isMap.put(appid, "ing");
		String tempstr="";
		WXAccessToken accessToken=null;		
		Map<String,String> params = new HashMap<String,String>();
		params.put("grant_type", "client_credential");
		params.put("appid", appid);
	    params.put("secret", appsecrete);
	    tempstr=HttpClientUtil.doGet(tokenurl, params, Consts.UTF_8.toString());	
	    accessToken=JsonHelper.jsonToBean(tempstr, WXAccessToken.class);
	    logger.info("getOfficialTokenEt="+tempstr);
	    if(accessToken!=null&&accessToken.getAccess_token()!=null)
	    {
	    	accessToken.setRefershTime(System.currentTimeMillis() / 1000);
	    }
	    else {
	    	  tempstr=HttpClientUtil.doGet(tokenurl, params, Consts.UTF_8.toString());	
	    	  accessToken=JsonHelper.jsonToBean(tempstr, WXAccessToken.class);
	  	     logger.info("getOfficialTokenEt agian="+tempstr);	
	  	   if(accessToken!=null&&accessToken.getAccess_token()!=null)
		    {
		    	accessToken.setRefershTime(System.currentTimeMillis() / 1000);
		    }
		}
		return accessToken;
	}
	public static void main(String arg[])
	{
		 String token ="";
		 token =TokenManager.getOfficialToken("wx2c94749984dbf67d","f53c0f378c473d3b103d0f62dfa86c1e",true);
		 String jsonString="{\"access_token\":\"spjMG7x8QWFr9EPXKnU1xdOUR6qmZVTRwDaYbYrVdbCPdb7t4O51ATHnbWLtqTa6A_74N9ZwIBW8H_ke8gnB-KZjVFja9fsgmXYwiO6mx5cFHUdAJABDQ\",\"expires_in\":\"6300\"}";
		 WXAccessToken accessToken=JsonHelper.jsonToBean(jsonString, WXAccessToken.class);
	}
}

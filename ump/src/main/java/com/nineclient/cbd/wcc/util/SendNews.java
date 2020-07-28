package com.nineclient.cbd.wcc.util;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;

import com.nineclient.model.WccPlatformUser;

public class SendNews {
	
	public static void sendMsgNews(Articles articles,String openId,WccPlatformUser wccPlatformUser){
		
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(wccPlatformUser.getAppId());
	    config.setSecret(wccPlatformUser.getAppSecret());
	    WxMpServiceImpl wxMpServiceImpl=new WxMpServiceImpl();
	    wxMpServiceImpl.setWxMpConfigStorage(config);
	    String url = null;
	    
		if(null != articles && null != openId && wccPlatformUser != null){

			try {
	  String accesstoken = wxMpServiceImpl.getAccessToken();
	    //url = "https://api.weixin.qq.com/cgi-bin/message/send?access_token="+accesstoken;
		url = "https://api.weixin.qq.com/cgi-bin/message/send?access_token="+accesstoken;	    
				String newsStr = "{"
						+ "    \"touser\":\""+openId+"\",\"msgtype\":\"news\","
						+ "\"news\":{\"articles\":"
						+ " [{\"title\":\""+articles.getTitle()+"\","
						+ "   \"description\":\""+articles.getDescription()+"\","
						+ "   \"url\":\""+articles.getUrl()+"\","
						+ "   \"picurl\":\""+articles.getPicurl()+"\"}]}}";	
	    String textNewStr = "{\"touser\":\""+openId+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+articles.getDescription()+"\"}}";
				
				   String result = HttpClientUtils.doPost(url, null, textNewStr, "UTF-8");
		System.out.println("***************************************************");
        System.out.println("--result--:"+result);
        System.out.println("***************************************************");
			
			} catch (WxErrorException e) {
				e.printStackTrace();
			}

		
		}

		
	}
public static void main(String[] args) {
	
}
}

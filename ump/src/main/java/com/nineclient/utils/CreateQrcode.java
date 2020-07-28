package com.nineclient.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.util.FileCopyUtils;

import com.google.gson.JsonObject;
import com.nineclient.cbd.wcc.util.CreateNativeQrCode;
import com.nineclient.cbd.wcc.util.HttpClientUtils;
import com.nineclient.cbd.wcc.util.QrCodeJsonObj;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.tailong.wcc.model.WccSaQrCode;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

public class CreateQrcode {

	/**
	 * 1,--绑定会员，2--绑定门店，3---绑定活动,4--抽奖活动,5--办事处
	 * 
	 */
	public static final String STORE = "2";
	/**
	 * 	图文
	 */
	
	public static final String NEWS = "6";
	
	/**
	 * 生成永久二维码
	 * @throws WxErrorException 
	 */
	public static WccSaQrCode createOrgetCode(Set<WccPlatformUser> platformUsers,HttpServletRequest request,WccSaQrCode wccSaQrCode) throws WxErrorException {
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			
			WxMpServiceImpl wxMpService = null;
			if(platformUsers != null && platformUsers.size() > 0 && wccSaQrCode != null){
	
			Iterator<WccPlatformUser> it = platformUsers.iterator();
			WccPlatformUser platform = null;
			
			while (it.hasNext()) {  
			platform = it.next();
			wxMpService = new WxMpServiceImpl();
				config.setAppId(platform.getAppId());
				config.setSecret(platform.getAppSecret());			
			wxMpService.setWxMpConfigStorage(config);
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+wxMpService.getAccessToken();
            String fName =null;
            String  postJsonStr = null;
            String codeRandomStr = RadomStr.getRandomString(64);
            //永久二维码
			postJsonStr = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+codeRandomStr+"\"}}}";
			wccSaQrCode.setSceneStr(codeRandomStr);
			fName = HttpClientUtils.doPost(url, null, postJsonStr, "UTF-8");
			JSONObject objon = JSONObject.fromObject(fName);
			QrCodeJsonObj obj = (QrCodeJsonObj)JSONObject.toBean(objon,QrCodeJsonObj.class);
			
			// 获得一个在系统临时目录下的文件，是jpg格式的
			String wxCtxPath = request.getSession().getServletContext().getRealPath(Global.IMAGE_QRCOED_PATH);
			String fcode =	CreateNativeQrCode.getQrCodePath(wxCtxPath, obj.getUrl());
            wccSaQrCode.setCodeUrl("/ump/attached/wxQrcode/"+ fcode);
            wccSaQrCode.persist();
		} 
	}
			return wccSaQrCode;

	}


	

}

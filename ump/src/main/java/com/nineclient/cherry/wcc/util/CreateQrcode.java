package com.nineclient.cherry.wcc.util;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;




import net.sf.json.JSONObject;

import com.nineclient.cherry.wcc.model.WccQrCode;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccQrcode;
import com.nineclient.utils.Global;
import com.nineclient.utils.HttpClientUtil;

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
	public static WccQrCode createOrgetCode(Set<WccPlatformUser> platformUsers,HttpServletRequest request,WccQrCode wccQrCode) throws WxErrorException {
			String wxfileName = null;
//			WccQrCode wccQrcode = null;
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			
			WxMpServiceImpl wxMpService = null;
			if(platformUsers != null && platformUsers.size() > 0 && wccQrCode != null){
	
			Iterator<WccPlatformUser> it = platformUsers.iterator();
			WccPlatformUser platform = null;
			
			while (it.hasNext()) {  
			platform = it.next();
			int sceneid  =	GetCodeScenceid.getScenceId(null);
			wxMpService = new WxMpServiceImpl();
				config.setAppId(platform.getAppId());
				config.setSecret(platform.getAppSecret());			
			wxMpService.setWxMpConfigStorage(config);
			WxMpQrCodeTicket ticket = null;
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+wxMpService.getAccessToken();
            String fName =null;
            String  postJsonStr = null;
            String codeRandomStr = RadomStr.getRandomString(64);
			if(wccQrCode.getCodeAttr().equals("永久")){
			postJsonStr = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+codeRandomStr+"\"}}}";
      
				 // ticket = wxMpService.qrCodeCreateLastTicket(sceneid);
			}else{
		postJsonStr = "{\"expire_seconds\": "+wccQrCode.getExpireTime()*24*60*60+", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \""+codeRandomStr+"\"}}}";
	
				// ticket = wxMpService.qrCodeCreateTmpTicket(sceneid, wccQrCode.getExpireTime()*24*60*60);
			}
			fName = HttpClientUtil.doJsonPost(url,new JSONObject().fromObject(postJsonStr),"utf-8");
			JSONObject objon = JSONObject.fromObject(fName);
			QrCodeJsonObj obj = (QrCodeJsonObj)JSONObject.toBean(objon,QrCodeJsonObj.class);
			
			// 获得一个在系统临时目录下的文件，是jpg格式的

/*				File file = wxMpService.qrCodePicture(ticket);
			String wxName = file.getName();// 原文件名
			String wxlastName = wxName.substring(wxName.lastIndexOf(".")); // 后缀名比如.jpg
			wxfileName = System.currentTimeMillis() + wxlastName;*/
			String wxCtxPath = request.getSession().getServletContext().getRealPath("/"+Global.IMAGE_QRCOED_PATH);
			File dirPath = new File(wxCtxPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
String fcode =	CreateNativeQrCode.getQrCodePath(wxCtxPath, obj.getUrl());
			
//				File wxFile = new File(wxCtxPath + "/" + wxfileName);
//				FileCopyUtils.copy(file, wxFile);
//				wccQrCode.setCodeUrl(wxCtxPath + "/" + wxfileName);
			wccQrCode.setCodeUrl("/ump/attached/wxQrcode/"+ fcode);
			//wccQrCode.setCodeName(wxName);
			wccQrCode.setCodeLocalPath(wxCtxPath);
			wccQrCode.setSceneId(sceneid);
			wccQrCode.setSceneStr(codeRandomStr);
			// 保存
			if(null  != wccQrCode.getId() && wccQrCode.getId() > 0){
				wccQrCode.merge();
			}else{
				wccQrCode.setCreateDate(new Date());
				wccQrCode.persist();
			}	
		} 
	}
			return wccQrCode;

	}

	/**
	 * 永久二维码设置可用解绑
	 */
	public static void setIsuseCode(int sceneid) {
		WccQrcode wccQrcode = WccQrcode.findWccQrcodeByScenId(sceneid);
		wccQrcode.setIsUse(false);
		wccQrcode.setUseType("0");
		wccQrcode.merge();
	}
	
	/**
	 * 永久二维码设置可用关联上
	 */
	public static void setIsuseCodeOn(int sceneid) {
		WccQrcode wccQrcode = WccQrcode.findWccQrcodeByScenIdToOn(sceneid);
		if(null != wccQrcode){
			wccQrcode.setIsUse(true);
			wccQrcode.setUseType(CreateQrcode.STORE);
			wccQrcode.merge();
		}
	}
}

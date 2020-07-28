package com.nineclient.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccQrcode;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

public class Qrcode {

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
	public static WccQrcode createOrgetCode(String userType,
			WccPlatformUser platform, String appId, String appSecret,
			HttpServletRequest request) throws WxErrorException {
		WccQrcode wccQrcode = null;
		List<WccQrcode> qrcode = WccQrcode.findWccQrcodeByPlatform(false,platform.getId());
		if (qrcode == null || qrcode.size() == 0) {
			String wxfileName = null;
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			config.setAppId(appId);
			config.setSecret(appSecret);
			WxMpServiceImpl wxMpService = new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(config);
			// 生成sceneid
			int sceneid = 0;
			try {
				sceneid = WccQrcode.getMaxSceneid(2);
			} catch (Exception e) {
				sceneid = 1;
			}

			if (sceneid == 0 || sceneid < 1) {
				sceneid = 1;
			} else {
				sceneid++;
			}
			try {

				WxMpQrCodeTicket ticket = wxMpService
						.qrCodeCreateLastTicket(sceneid);

				// 获得一个在系统临时目录下的文件，是jpg格式的
				File file = wxMpService.qrCodePicture(ticket);
				System.out.println(file.getAbsolutePath());
				String wxName = file.getName();// 原文件名
				String wxlastName = wxName.substring(wxName.lastIndexOf(".")); // 后缀名比如.jpg
				wxfileName = System.currentTimeMillis() + wxlastName;
				String wxCtxPath = request.getSession().getServletContext()
						.getRealPath(Global.IMAGE_QRCOED_PATH);
				File dirPath = new File(wxCtxPath);
				if (!dirPath.exists()) {
					dirPath.mkdirs();
				}
				File wxFile = new File(wxCtxPath + "/" + wxfileName);
				FileCopyUtils.copy(file, wxFile);
				wccQrcode = new WccQrcode();
				wccQrcode.setActionType(2);
				wccQrcode.setCodeUrl(wxfileName);
				wccQrcode.setIsDeleted(true);
				wccQrcode.setIsUse(true);// 是否正在被使用（1未使用，2在使用）
				wccQrcode.setPlatformUser(platform);
				wccQrcode.setCompany(CommonUtils.getCurrentPubOperator(request)
						.getCompany());
				wccQrcode.setSceneId(sceneid);
				wccQrcode.setCreateTime(new Date());
				wccQrcode.setExpireSeconds(ticket.getExpire_seconds());
				wccQrcode.setCodeId(ticket.getUrl());
				wccQrcode.setUseType(userType+"");// userType
											// 1,--绑定会员，2--绑定门店，3---绑定活动,4--抽奖活动,5--办事处,6--图文
				wccQrcode.persist();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (qrcode.size() > 0) {
				wccQrcode = qrcode.get(0);
				wccQrcode.setIsUse(true);
				wccQrcode.merge();
			}
		}

		return wccQrcode;
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
			wccQrcode.setUseType(Qrcode.STORE);
			wccQrcode.merge();
		}
	}

}

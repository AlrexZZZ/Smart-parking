package com.nineclient.thread;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

import com.nineclient.dto.WccUccError;
import com.nineclient.dto.WccUccOut;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.web.WccWxCommController;

public class WCCintoUCC extends Thread{
	
	private String openId;
	
	private WccUccError uccError;
	
	private Map<String,String> openIdMap;
	
	private Map<String,String> timeMap;
	
	private WxMpService wxMpService;
	
	
	public WCCintoUCC(String openId, WccUccError uccError,
			Map<String, String> openIdMap, Map<String, String> timeMap,
			WxMpService wxMpService) {
		super();
		this.openId = openId;
		this.uccError = uccError;
		this.openIdMap = openIdMap;
		this.timeMap = timeMap;
		this.wxMpService = wxMpService;
	}

	public void run() {
		WccUccOut uccOut = WccWxCommController.UccIn(openId);
		System.out.println("uccOut="+uccOut.toString());
		if(!"0".equals(uccError.getErrorCode())){
			openIdMap.remove(openId);
			timeMap.remove(openId);
			try {
				wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(uccError.getErrorMsg()).build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			return;
		}
			try {
				wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(uccOut.getMsg()).build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		System.out.println("ucc====================================");
	}
}

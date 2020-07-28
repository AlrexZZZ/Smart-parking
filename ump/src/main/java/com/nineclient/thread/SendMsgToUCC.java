package com.nineclient.thread;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

import com.nineclient.dto.WccUccError;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.web.WccWxCommController;

public class SendMsgToUCC extends Thread{
	
	private String openId;
	
	private String content;
	
	private WccUccError uccError;
	
	private Map<String,String> openIdMap;
	
	private Map<String,String> timeMap;
	
	private WxMpService wxMpService;
	
	private WccRecordMsg record;
	
	public SendMsgToUCC(String openId, String content, WccUccError uccError,
			Map<String, String> openIdMap, Map<String, String> timeMap,
			WxMpService wxMpService, WccRecordMsg record) {
		super();
		this.openId = openId;
		this.content = content;
		this.uccError = uccError;
		this.openIdMap = openIdMap;
		this.timeMap = timeMap;
		this.wxMpService = wxMpService;
		this.record = record;
	}

	public void run() {
		String msg = WccWxCommController.sendMsgToUcc(openId,content);
		if(!"0".equals(uccError.getErrorCode())){
			openIdMap.remove(openId);
			timeMap.remove(openId);
			try {
				wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(uccError.getErrorMsg()).build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			record.persist();
			return;
		}
		System.out.println("ucc.........msg="+msg);
		System.out.println("UCC接口==================");
	}
}

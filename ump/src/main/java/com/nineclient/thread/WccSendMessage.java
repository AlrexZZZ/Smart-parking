package com.nineclient.thread;

import java.util.Date;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import net.sf.json.JSONObject;

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.web.WccWxController;
import com.nineclient.weixin.imp.WeixinBasicParam;

public class WccSendMessage extends Thread{
	
	private String context;
	
	private Map<String, WxMpService> wxMpServiceMap;
	
	private Map<String,String> uccInfo;
	
	public WccSendMessage(String context,Map<String, WxMpService> wxMpServiceMap,Map<String,String> uccInfo) {
		super();
		this.context = context;
		this.wxMpServiceMap = wxMpServiceMap;
		this.uccInfo = uccInfo;
	}

	public void run() {
		System.out.println("context="+context);
		JSONObject conMsg = JSONObject.fromObject(context);
		String token = conMsg.getString("token");
		String chatID = conMsg.get("chatID").toString();
		String openId = conMsg.getString("userID");
		String requestMsg = conMsg.get("requestMsg").toString();
		JSONObject json = JSONObject.fromObject(requestMsg);
		String type = json.getString("type");
		String message = json.getString("message").replaceAll("<[^<]*>", "");
//		if(!"0".equals(error)){
//			WccWxController.uccToken = null;
//		}
		if("2".equals(type) || "4".equals(type)){
			WeixinBasicParam.openIdMap.remove(openId);
			WeixinBasicParam.chatIdMap.remove(openId);
			WeixinBasicParam.timeMap.remove(openId);
		}
		uccInfo.put(openId, message);
		WxMpService wxMpService = wxMpServiceMap.get(openId);
		if(null != wxMpService){
			try {
				System.out.println("发送的消息====="+message);
				wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(message).build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			WccFriend friend = WccFriend.findWccFriendByOpenId(openId);
			WccRecordMsg record = new WccRecordMsg();
			record.setOpenId(friend.getOpenId());
			record.setNickName(friend.getNickName());
			record.setPlatFormAccount(friend.getPlatformUser()
					.getAccount());
			record.setProvince(friend.getProvince() == null ? ""
					: friend.getProvince());
			record.setPlatFormId(friend.getPlatformUser()
					.getId().toString());
			record.setCompanyId(friend.getPlatformUser()
					.getCompany().getId());
			record.setRecordFriendId(friend.getId());// 标识该消息是属于哪个粉丝的
			record.setFriendGroup(String.valueOf(friend
					.getWgroup().getId()));// 分组Id
			record.setToUserInsertTime(new Date());
			record.setToUserRecord(message);
			record.persist();
		}
	}
}

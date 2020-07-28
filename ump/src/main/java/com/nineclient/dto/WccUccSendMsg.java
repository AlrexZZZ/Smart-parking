package com.nineclient.dto;

public class WccUccSendMsg {

	private String token;
	
	private String chatID;
	
	private String info;
	
	private String busiOperateType;
	
	private String channelType;
	
	private WccUccSendMsgRes requestMsg;
	
	public WccUccSendMsg() {
		super();
	}
	
	public WccUccSendMsg(String token, String chatID, String busiOperateType,
			WccUccSendMsgRes requestMsg) {
		super();
		this.token = token;
		this.chatID = chatID;
		this.busiOperateType = busiOperateType;
		this.requestMsg = requestMsg;
	}
	
	public WccUccSendMsg(String token, String chatID, String busiOperateType,
			String channelType, WccUccSendMsgRes requestMsg) {
		super();
		this.token = token;
		this.chatID = chatID;
		this.busiOperateType = busiOperateType;
		this.channelType = channelType;
		this.requestMsg = requestMsg;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getChatID() {
		return chatID;
	}

	public void setChatID(String chatID) {
		this.chatID = chatID;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getBusiOperateType() {
		return busiOperateType;
	}

	public void setBusiOperateType(String busiOperateType) {
		this.busiOperateType = busiOperateType;
	}

	public WccUccSendMsgRes getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(WccUccSendMsgRes requestMsg) {
		this.requestMsg = requestMsg;
	}

	@Override
	public String toString() {
		return "WccUccSendMsg [token=" + token + ", chatID=" + chatID
				+ ", info=" + info + ", busiOperateType=" + busiOperateType
				+ ", channelType=" + channelType + ", requestMsg=" + requestMsg
				+ "]";
	}
}

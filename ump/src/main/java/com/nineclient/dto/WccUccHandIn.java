package com.nineclient.dto;

public class WccUccHandIn {
	private String token;
	
	private String busiOperateType;
	
	private String userID;
	
	private String nickName;
	
	private String remarks;
	
	private String info;
	
	private String ip;
	
	private String requestMsg;
	
	private String channelType;
	
	public WccUccHandIn(String token, String busiOperateType, String userID,
			String nickName,String channelType) {
		super();
		this.token = token;
		this.busiOperateType = busiOperateType;
		this.userID = userID;
		this.nickName = nickName;
		this.channelType = channelType;
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

	public String getBusiOperateType() {
		return busiOperateType;
	}

	public void setBusiOperateType(String busiOperateType) {
		this.busiOperateType = busiOperateType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	@Override
	public String toString() {
		return "WccUccHandIn [token=" + token + ", busiOperateType="
				+ busiOperateType + ", userID=" + userID + ", userName="
				+ nickName + ", remarks=" + remarks + ", info=" + info
				+ ", ip=" + ip + ", requestMsg=" + requestMsg
				+ ", channelType=" + channelType + "]";
	}
	
}

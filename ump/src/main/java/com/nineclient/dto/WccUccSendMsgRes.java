package com.nineclient.dto;

public class WccUccSendMsgRes {
	private String message;
	
	private String img;

	public WccUccSendMsgRes() {
		super();
	}
	
	public WccUccSendMsgRes(String message, String img) {
		super();
		this.message = message;
		this.img = img;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "WccUccSendMsgRes [message=" + message + ", img=" + img + "]";
	}
}

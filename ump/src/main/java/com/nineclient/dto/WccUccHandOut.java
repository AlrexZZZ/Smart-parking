package com.nineclient.dto;

public class WccUccHandOut {
	private String responseMsg;
	
	private HandOutMessage handOutMessage;

	public WccUccHandOut(String responseMsg, HandOutMessage handOutMessage) {
		super();
		this.responseMsg = responseMsg;
		this.handOutMessage = handOutMessage;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public HandOutMessage getHandOutMessage() {
		return handOutMessage;
	}

	public void setHandOutMessage(HandOutMessage handOutMessage) {
		this.handOutMessage = handOutMessage;
	}

	
	
}

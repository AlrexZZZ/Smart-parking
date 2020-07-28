package com.nineclient.dto;

public class WccUccError {
	private String errorCode;
	
	private String errorMsg;
	
	public WccUccError() {
		super();
	}

	public WccUccError(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "WccUccError [errorCode=" + errorCode + ", errorMsg=" + errorMsg
				+ "]";
	}
}

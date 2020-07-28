package com.nineclient.dto;

public class HandOutMessage {
	private String error;
	
	private String token;
	
	private String info;
	
	public HandOutMessage(String error, String token, String info) {
		super();
		this.error = error;
		this.token = token;
		this.info = info;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}

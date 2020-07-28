package com.nineclient.dto;

import java.io.Serializable;

public class WXAccessToken  implements Serializable {
	private String access_token;
	private Long expires_in;
	private Long refershTime;
	private String errcode;
	private String errmsg;
	
	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getRefershTime() {
		return refershTime;
	}

	public void setRefershTime(Long refershTime) {
		this.refershTime = refershTime;
	}
}

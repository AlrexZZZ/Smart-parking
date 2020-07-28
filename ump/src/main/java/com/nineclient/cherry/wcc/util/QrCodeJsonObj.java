package com.nineclient.cherry.wcc.util;

public class QrCodeJsonObj {
	private String ticket;//	 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	private int expire_seconds;//	 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
	private String url;
	public QrCodeJsonObj() {
	}
	public QrCodeJsonObj(String ticket, int expire_seconds, String url) {
		this.ticket = ticket;
		this.expire_seconds = expire_seconds;
		this.url = url;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(int expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

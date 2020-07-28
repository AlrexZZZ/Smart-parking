package com.nineclient.cherry.wcc.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：url link 配置信息类
 * 作者： sea 2013-3-25
 */
public class Linker {
	
	private Map<String, String> reqHeader = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	private boolean decodeUnicodeFlag = false;	//对下载页的页面源码进行unicode解码 (true 是， false 否)
	private String encoding = "utf-8"; // http 请求是用到的编码，默认utf-8
	private String requestMethod = "get"; // http 的请求方式 (get/post/...)，默认为get方式
    private String url;
    private int tryDownloadNum = 2;  //第一次下载失败后的重试机制
    public Linker() {
	}
    
    public Linker(String url) {
    	this.url = url;
	}
    
	public Map<String, String> getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(Map<String, String> reqHeader) {
		this.reqHeader = reqHeader;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public boolean isDecodeUnicodeFlag() {
		return decodeUnicodeFlag;
	}

	public void setDecodeUnicodeFlag(boolean decodeUnicodeFlag) {
		this.decodeUnicodeFlag = decodeUnicodeFlag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTryDownloadNum() {
		return tryDownloadNum;
	}

	public void setTryDownloadNum(int tryDownloadNum) {
		this.tryDownloadNum = tryDownloadNum;
	}
	
}

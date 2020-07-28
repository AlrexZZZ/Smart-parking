package com.nineclient.cbd.wcc.util;

public class Articles {
	/**
	 * 图文消息条数限制在8条以内，注意，如果图文数超过8，则将会无响应。
	 */
private String title;
private String description;
private String url;
private String picurl;


public Articles() {
	
}

public Articles(String title, String description, String url, String picurl) {
	super();
	this.title = title;
	this.description = description;
	this.url = url;
	this.picurl = picurl;
}

public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getPicurl() {
	return picurl;
}
public void setPicurl(String picurl) {
	this.picurl = picurl;
}

}

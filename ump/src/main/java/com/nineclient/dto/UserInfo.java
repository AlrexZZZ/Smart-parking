package com.nineclient.dto;

import javax.xml.crypto.Data;


public class UserInfo {
private String code;
private Data data; 
private String message;
public UserInfo() {
	super();
	// TODO Auto-generated constructor stub
}
public UserInfo(String code, Data data, String message) {
	super();
	this.code = code;
	this.data = data;
	this.message = message;
}
@Override
public String toString() {
	return "UserInfo [code=" + code + ", data=" + data + ", message=" + message
			+ "]";
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public Data getData() {
	return data;
}
public void setData(Data data) {
	this.data = data;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}

}

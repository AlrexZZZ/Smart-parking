package com.nineclient.entity;

import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;

public class MassConditionEntity {
private WccPlatformUser platformer;//公众平台
private Long gender;//粉丝性别
private String groups;//粉丝分组
private String province;//省份
private String city;//城市
private String massnum;//消息数目
private String fromType;//粉丝来源
private Long massType;//消息类型
private PubOperator pubOperator;//创建用户
public WccPlatformUser getPlatformer() {
	return platformer;
}
public void setPlatformer(WccPlatformUser platformer) {
	this.platformer = platformer;
}
public Long getGender() {
	return gender;
}
public void setGender(Long gender) {
	this.gender = gender;
}
public String getGroups() {
	return groups;
}
public void setGroups(String groups) {
	this.groups = groups;
}
public String getProvince() {
	return province;
}
public void setProvince(String province) {
	this.province = province;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getMassnum() {
	return massnum;
}
public void setMassnum(String massnum) {
	this.massnum = massnum;
}
public String getFromType() {
	return fromType;
}
public void setFromType(String fromType) {
	this.fromType = fromType;
}
public Long getMassType() {
	return massType;
}
public void setMassType(Long massType) {
	this.massType = massType;
}
public PubOperator getPubOperator() {
	return pubOperator;
}
public void setPubOperator(PubOperator pubOperator) {
	this.pubOperator = pubOperator;
}


}

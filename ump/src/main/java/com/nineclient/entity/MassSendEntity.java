package com.nineclient.entity;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;









import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import me.chanjar.weixin.common.api.WxConsts;

import com.nineclient.cherry.wcc.service.MassNewsSend;
import com.nineclient.cherry.wcc.service.MassPicSend;
import com.nineclient.cherry.wcc.service.MassTextSend;
import com.nineclient.cherry.wcc.service.MassVideoSend;
import com.nineclient.cherry.wcc.service.MassVoiceSend;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
/**
 * 群发对象：发送人，发送类型，素材对象，输入的图片
 */
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.CommonUtils;

public class MassSendEntity {
private List<String> list;
private String msgType;
private WccMaterials wccmaterial;
private String content;
private InputStream inputfile;
private WccPlatformUser wccplatforuser;
private String ctxPatch;
private PubOperator pub;
private int state;
private int id;
private Date expectedTime;
private String groups;
private String provice;
private String city;
private int gender;
private PubOrganization pubOrganization;
private Set<WccFriend> friends;
private Set<WccGroup> groupset;


public Set<WccGroup> getGroupset() {
	return groupset;
}
public void setGroupset(Set<WccGroup> groupset) {
	this.groupset = groupset;
}


private HttpServletRequest request;


public HttpServletRequest getRequest() {
	return request;
}
public void setRequest(HttpServletRequest request) {
	this.request = request;
}
public Set<WccFriend> getFriends() {
	return friends;
}
public void setFriends(Set<WccFriend> friends) {
	this.friends = friends;
}
public List<String> getList() {
	return list;
}
public void setList(List<String> list) {
	this.list = list;
}
public String getMsgType() {
	return msgType;
}
public void setMsgType(String msgType) {
	this.msgType = msgType;
}
public WccMaterials getWccmaterial() {
	return wccmaterial;
}
public void setWccmaterial(WccMaterials wccmaterial) {
	this.wccmaterial = wccmaterial;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public InputStream getInputfile() {
	return inputfile;
}
public void setInputfile(InputStream inputfile) {
	this.inputfile = inputfile;
}
public WccPlatformUser getWccplatforuser() {
	return wccplatforuser;
}
public void setWccplatforuser(WccPlatformUser wccplatforuser) {
	this.wccplatforuser = wccplatforuser;
}
public String getCtxPatch() {
	return ctxPatch;
}
public void setCtxPatch(String ctxPatch) {
	this.ctxPatch = ctxPatch;
}
public PubOperator getPub() {
	return pub;
}
public void setPub(PubOperator pub) {
	this.pub = pub;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Date getExpectedTime() {
	return expectedTime;
}
public void setExpectedTime(Date expectedTime) {
	this.expectedTime = expectedTime;
}
public String getGroups() {
	return groups;
}
public void setGroups(String groups) {
	this.groups = groups;
}

public String getProvice() {
	return provice;
}
public void setProvice(String provice) {
	this.provice = provice;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public PubOrganization getPubOrganization() {
	return pubOrganization;
}
public void setPubOrganization(PubOrganization pubOrganization) {
	this.pubOrganization = pubOrganization;
}
public int getGender() {
	return gender;
}
public void setGender(int gender) {
	this.gender = gender;
}


public static String getMassKey(Long messType,WccMaterials material,FileInputStream inputStream,MassSendEntity massSendEntity){	
	String key="";
	//文本
	if(messType == 1){
		massSendEntity.setContent(massSendEntity.getContent());
		massSendEntity.setMsgType(WxConsts.MASS_MSG_TEXT);
		key=new MassTextSend(massSendEntity).sendMessage();
	}
	//图片
	if(messType == 2){
		massSendEntity.setWccmaterial(material);		
		massSendEntity.setMsgType(WxConsts.MASS_MSG_IMAGE);
		massSendEntity.setInputfile(inputStream);
		key=new MassPicSend(massSendEntity).sendMessage();
	}
	//视频
	if(messType == 3){
		massSendEntity.setWccmaterial(material);
		massSendEntity.setMsgType(WxConsts.MASS_MSG_VIDEO);
		massSendEntity.setInputfile(inputStream);
		key=new MassVideoSend(massSendEntity).sendMessage();
	}
	//语音
	if(messType == 4){
		massSendEntity.setWccmaterial(material);
		massSendEntity.setMsgType(WxConsts.MASS_MSG_VOICE);
		massSendEntity.setInputfile(inputStream);
		key=new MassVoiceSend(massSendEntity).sendMessage();
	
	}
	//图文
	if(messType == 5){
		massSendEntity.setWccmaterial(material);
		massSendEntity.setMsgType(WxConsts.MASS_MSG_NEWS);				
		massSendEntity.setInputfile(inputStream);
		key=new MassNewsSend(massSendEntity).sendMessage();
	}
	
	return key;
}

}

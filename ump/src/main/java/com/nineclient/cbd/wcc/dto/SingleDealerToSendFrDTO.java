package com.nineclient.cbd.wcc.dto;

import java.util.Date;

public class SingleDealerToSendFrDTO implements java.io.Serializable{

	private String platformName;//公众号名称
	private String pubOperatorName;//经销商名称
	private String forwardingTimes;//转发次数
	private String offiMateriShareTimes;//总部素材必须共享次数
	private String materialTypeString;//素材类型
	private String sendFr;//转发率
	
	private Date forwardTime;//转发时间
	private  String forwardMaterTitle;//转发素材名称
	private Date offMateriInsertTime;//总部素材入库时间
	
	
	public Date getForwardTime() {
		return forwardTime;
	}
	public void setForwardTime(Date forwardTime) {
		this.forwardTime = forwardTime;
	}
	public String getForwardMaterTitle() {
		return forwardMaterTitle;
	}
	public void setForwardMaterTitle(String forwardMaterTitle) {
		this.forwardMaterTitle = forwardMaterTitle;
	}
	
	public Date getOffMateriInsertTime() {
		return offMateriInsertTime;
	}
	public void setOffMateriInsertTime(Date offMateriInsertTime) {
		this.offMateriInsertTime = offMateriInsertTime;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getPubOperatorName() {
		return pubOperatorName;
	}
	public void setPubOperatorName(String pubOperatorName) {
		this.pubOperatorName = pubOperatorName;
	}
	public String getForwardingTimes() {
		return forwardingTimes;
	}
	public void setForwardingTimes(String forwardingTimes) {
		this.forwardingTimes = forwardingTimes;
	}
	public String getOffiMateriShareTimes() {
		return offiMateriShareTimes;
	}
	public void setOffiMateriShareTimes(String offiMateriShareTimes) {
		this.offiMateriShareTimes = offiMateriShareTimes;
	}
	public String getMaterialTypeString() {
		return materialTypeString;
	}
	public void setMaterialTypeString(String materialTypeString) {
		this.materialTypeString = materialTypeString;
	}
	public String getSendFr() {
		return sendFr;
	}
	public void setSendFr(String sendFr) {
		this.sendFr = sendFr;
	}
	
	
	public SingleDealerToSendFrDTO() {
		super();
	}
	public SingleDealerToSendFrDTO(String platformName, String pubOperatorName,
			String forwardingTimes, String offiMateriShareTimes,
			String materialTypeString, String sendFr) {
		super();
		this.platformName = platformName;
		this.pubOperatorName = pubOperatorName;
		this.forwardingTimes = forwardingTimes;
		this.offiMateriShareTimes = offiMateriShareTimes;
		this.materialTypeString = materialTypeString;
		this.sendFr = sendFr;
	}
	
	

}

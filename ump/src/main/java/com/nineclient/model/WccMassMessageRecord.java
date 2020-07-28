//package com.nineclient.model;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.roo.addon.javabean.RooJavaBean;
//import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
//import org.springframework.roo.addon.tostring.RooToString;
//
//@Entity
//@Configurable
//@RooJavaBean
//@RooToString
//@RooJpaActiveRecord
//public class WccMassMessageRecord {
//	/**
//	 * 添加时间
//	 */
//	private Date insertTime;
//	
//	/**
//	 * 群发信息
//	 */
//	private WccMassMessage wccMassMessage;
//	
//	/**
//	 * 粉丝
//	 */
//	private WccFriend wccFriend;
//
//	public Date getInsertTime() {
//		return insertTime;
//	}
//
//	public void setInsertTime(Date insertTime) {
//		this.insertTime = insertTime;
//	}
//
//	public WccMassMessage getWccMassMessage() {
//		return wccMassMessage;
//	}
//
//	public void setWccMassMessage(WccMassMessage wccMassMessage) {
//		this.wccMassMessage = wccMassMessage;
//	}
//
//	public WccFriend getWccFriend() {
//		return wccFriend;
//	}
//
//	public void setWccFriend(WccFriend wccFriend) {
//		this.wccFriend = wccFriend;
//	}
//}
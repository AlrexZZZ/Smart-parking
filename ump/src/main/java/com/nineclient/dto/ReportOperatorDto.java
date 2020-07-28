package com.nineclient.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.nineclient.model.PubOrganization;
import com.nineclient.model.PubRole;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocAccount;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocComment;
import com.nineclient.model.VocShop;
import com.nineclient.model.WccPlatformUser;

public class ReportOperatorDto  implements java.io.Serializable {


	private String operatorName;//姓名
	private String account;//帐号
	private Boolean active;//启用状态
	private Date createTime;//创建时间
	private String organization;//所属组织
	private String pubRole;//权限组
	private Long loginNum;//登陆次数
	private Date insertTime;// 入驻时间
	private String platformAccount;//公众帐号
	private String platType;//帐号类型
	private Long friendNum;//粉丝数
	
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getPlatformAccount() {
		return platformAccount;
	}
	public void setPlatformAccount(String platformAccount) {
		this.platformAccount = platformAccount;
	}
	public String getPlatType() {
		return platType;
	}
	public void setPlatType(String platType) {
		this.platType = platType;
	}
	public Long getFriendNum() {
		return friendNum;
	}
	public void setFriendNum(Long friendNum) {
		this.friendNum = friendNum;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getPubRole() {
		return pubRole;
	}
	public void setPubRole(String pubRole) {
		this.pubRole = pubRole;
	}
	public Long getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(Long loginNum) {
		this.loginNum = loginNum;
	}
	
	
}

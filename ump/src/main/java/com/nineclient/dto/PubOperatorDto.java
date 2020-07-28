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

public class PubOperatorDto  implements java.io.Serializable {


	private String email;
	private String operatorName;
	private String account;
	private String password;
	private String mobile;
	private Boolean active;
	private Boolean autoAllocate;
	private Boolean isDeleted;
	private Date createTime;
	private Long id;
	private Integer version;
	private PubOrganization organization;
	private UmpCompany company;
	private PubRole pubRole;
	private String platStrings;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getAutoAllocate() {
		return autoAllocate;
	}
	public void setAutoAllocate(Boolean autoAllocate) {
		this.autoAllocate = autoAllocate;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public PubOrganization getOrganization() {
		return organization;
	}
	public void setOrganization(PubOrganization organization) {
		this.organization = organization;
	}
	public UmpCompany getCompany() {
		return company;
	}
	public void setCompany(UmpCompany company) {
		this.company = company;
	}
	public PubRole getPubRole() {
		return pubRole;
	}
	public void setPubRole(PubRole pubRole) {
		this.pubRole = pubRole;
	}
	public String getPlatStrings() {
		return platStrings;
	}
	public void setPlatStrings(String platStrings) {
		this.platStrings = platStrings;
	}

	
}

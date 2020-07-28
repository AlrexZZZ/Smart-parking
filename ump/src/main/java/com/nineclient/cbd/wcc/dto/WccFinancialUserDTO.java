package com.nineclient.cbd.wcc.dto;

import java.util.Date;

import com.nineclient.cbd.wcc.model.WccCredentialType;

public class WccFinancialUserDTO {

	private Long id;

	private String productCode;

	private WccCredentialType credentialType;

	private String credentialNumber;

	private Long money;

	private String userName;
	
	private Boolean isActive;
	private String phone;
	private Date insertTime;
	private String platformUsers;
	public WccFinancialUserDTO() {
		super();
	}

	public WccFinancialUserDTO(Long id, String productCode,
			WccCredentialType credentialType, String credentialNumber,
			Long money, String userName,Boolean isActive,String phone,Date insertTime,String platformUsers) {
		super();
		this.id = id;
		this.productCode = productCode;
		this.credentialType = credentialType;
		this.credentialNumber = credentialNumber;
		this.money = money;
		this.userName = userName;
		this.isActive = isActive;
		this.phone=phone;
		this.insertTime=insertTime;
		this.platformUsers=platformUsers;
	}
	
	
	public String getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(String platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public WccCredentialType getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(WccCredentialType credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}

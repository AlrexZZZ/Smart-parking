package com.nineclient.cbd.wcc.dto;
import java.util.Date;
import java.util.List;

import com.nineclient.model.WccPlatformUser;


public class WccContactUsDTO {
	

    private Long id;
    private Long companyId;	
    private String backImg;
    private String contactUnit;
    private String contactWay;
    private String otherUrl;
    private WccPlatformUser platformUsers;
    private Date insertTime;
    
    
	public WccContactUsDTO() {
		super();
	}
	





	public WccContactUsDTO(Long id, String backImg, String contactUnit,
			String contactWay, WccPlatformUser platformUsers, Date insertTime) {
		super();
		this.id = id;
		this.backImg = backImg;
		this.contactUnit = contactUnit;
		this.contactWay = contactWay;
		this.platformUsers = platformUsers;
		this.insertTime = insertTime;
	}






	public String getBackImg() {
		return backImg;
	}

	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public String getContactUnit() {
		return contactUnit;
	}

	public void setContactUnit(String contactUnit) {
		this.contactUnit = contactUnit;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOtherUrl() {
		return otherUrl;
	}
	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}

	public WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}
	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
}

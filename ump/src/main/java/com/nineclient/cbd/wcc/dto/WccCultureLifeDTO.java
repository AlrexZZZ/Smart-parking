package com.nineclient.cbd.wcc.dto;
import java.util.Date;
import java.util.List;

import com.nineclient.model.WccPlatformUser;


public class WccCultureLifeDTO {
	

    private Long id;
    private Long companyId;	
	private String platFormId;
	private String platFormAccount;
    private String backgroundImg;
    private String backgroundTip;
    private String listImg;
    private String listTip;
    private String detailTitle;
    private String detailContent;
    private String otherUrl;
    private Integer version;
    private WccPlatformUser platformUsers;
    private Date insertTime;
    
    
	public WccCultureLifeDTO() {
		super();
	}
	
	public WccCultureLifeDTO(Long id, String backgroundImg,
			String backgroundTip, String listImg, String listTip,
			String detailTitle, String detailContent, String otherUrl,
			WccPlatformUser platformUsers, Date insertTime) {
		super();
		this.id = id;
		this.backgroundImg = backgroundImg;
		this.backgroundTip = backgroundTip;
		this.listImg = listImg;
		this.listTip = listTip;
		this.detailTitle = detailTitle;
		this.detailContent = detailContent;
		this.otherUrl = otherUrl;
		this.platformUsers = platformUsers;
		this.insertTime = insertTime;
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
	public String getPlatFormId() {
		return platFormId;
	}
	public void setPlatFormId(String platFormId) {
		this.platFormId = platFormId;
	}
	public String getPlatFormAccount() {
		return platFormAccount;
	}
	public void setPlatFormAccount(String platFormAccount) {
		this.platFormAccount = platFormAccount;
	}
	public String getBackgroundImg() {
		return backgroundImg;
	}
	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}
	public String getBackgroundTip() {
		return backgroundTip;
	}
	public void setBackgroundTip(String backgroundTip) {
		this.backgroundTip = backgroundTip;
	}
	public String getListImg() {
		return listImg;
	}
	public void setListImg(String listImg) {
		this.listImg = listImg;
	}
	public String getListTip() {
		return listTip;
	}
	public void setListTip(String listTip) {
		this.listTip = listTip;
	}
	public String getDetailTitle() {
		return detailTitle;
	}
	public void setDetailTitle(String detailTitle) {
		this.detailTitle = detailTitle;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public String getOtherUrl() {
		return otherUrl;
	}
	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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

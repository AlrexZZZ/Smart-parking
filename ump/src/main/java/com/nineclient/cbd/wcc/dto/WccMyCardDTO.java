package com.nineclient.cbd.wcc.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import com.nineclient.model.WccPlatformUser;

public class WccMyCardDTO {
	private Long id;
	private Boolean isDelete;
	private String insertIp;
	private String insertPk;
	private Date insertTime;
	private String updateIp;
	private String updatePk;
	private Date updateTime;
	private String deleteIp;
	private String deletePk;
	private Date deleteTime;
	//公司ID
	private String companyId;
	private String organizationPk;
	
	/**
	 * 关联公众号
     */

    private WccPlatformUser platformUsers;

	private String cardTitle;//标题

	private Date cardTime;//发布日期
	
	private String cardPic;//图片
	
	private String cardName;//一卡通名称
	
	private	String contentTitle;//内容
	
	private String cardIntro;//一卡通介绍
	
	private String cardUrl;//一卡通查询链接

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getInsertIp() {
		return insertIp;
	}

	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}

	public String getInsertPk() {
		return insertPk;
	}

	public void setInsertPk(String insertPk) {
		this.insertPk = insertPk;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public String getUpdatePk() {
		return updatePk;
	}

	public void setUpdatePk(String updatePk) {
		this.updatePk = updatePk;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteIp() {
		return deleteIp;
	}

	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}

	public String getDeletePk() {
		return deletePk;
	}

	public void setDeletePk(String deletePk) {
		this.deletePk = deletePk;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOrganizationPk() {
		return organizationPk;
	}

	public void setOrganizationPk(String organizationPk) {
		this.organizationPk = organizationPk;
	}

	public WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public Date getCardTime() {
		return cardTime;
	}

	public void setCardTime(Date cardTime) {
		this.cardTime = cardTime;
	}

	public String getCardPic() {
		return cardPic;
	}

	public void setCardPic(String cardPic) {
		this.cardPic = cardPic;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getCardIntro() {
		return cardIntro;
	}

	public void setCardIntro(String cardIntro) {
		this.cardIntro = cardIntro;
	}

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public WccMyCardDTO(Long id, Boolean isDelete, String insertIp,
			String insertPk, Date insertTime, String updateIp, String updatePk,
			Date updateTime, String deleteIp, String deletePk, Date deleteTime,
			String companyId, String organizationPk,
			WccPlatformUser platformUsers, String cardTitle, Date cardTime,
			String cardPic, String cardName, String contentTitle,
			String cardIntro, String cardUrl) {
		super();
		this.id = id;
		this.isDelete = isDelete;
		this.insertIp = insertIp;
		this.insertPk = insertPk;
		this.insertTime = insertTime;
		this.updateIp = updateIp;
		this.updatePk = updatePk;
		this.updateTime = updateTime;
		this.deleteIp = deleteIp;
		this.deletePk = deletePk;
		this.deleteTime = deleteTime;
		this.companyId = companyId;
		this.organizationPk = organizationPk;
		this.platformUsers = platformUsers;
		this.cardTitle = cardTitle;
		this.cardTime = cardTime;
		this.cardPic = cardPic;
		this.cardName = cardName;
		this.contentTitle = contentTitle;
		this.cardIntro = cardIntro;
		this.cardUrl = cardUrl;
	}

	public WccMyCardDTO() {
		super();
	}
	
	
	
	
}

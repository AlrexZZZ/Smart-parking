package com.nineclient.cbd.wcc.dto;

import java.util.Date;
import java.util.Set;


import javax.validation.constraints.Size;

import com.nineclient.cbd.wcc.model.WccItemotherinfo;
import com.nineclient.model.WccPlatformUser;

public class WccAppartmentDTO {

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
	
	
    private WccPlatformUser platformUsers;
	
    /**
     * 项目图片地址
     */

	private String itemPick;

	private String itemName;
	/**
     * 项目介绍
     */

	private String itemIntro;
	/**
     * 地址
     */

	private String itemAddress;
	/**
     * 状态
     */

	private String itemStatus;
	/**
     * 经度
     */
	@Size(max = 500)
	private String itemLng;
	/**
     * 纬度
     */

	private String itemLat;
	/**
     * 竣工日期
     */
	private Date itemTime;
	

	/**
     * 电话
     */
	private String itemPhone;
	

	private Set<WccItemotherinfo> wccItemtherinfo;


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


	public String getItemPick() {
		return itemPick;
	}


	public void setItemPick(String itemPick) {
		this.itemPick = itemPick;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemIntro() {
		return itemIntro;
	}


	public void setItemIntro(String itemIntro) {
		this.itemIntro = itemIntro;
	}


	public String getItemAddress() {
		return itemAddress;
	}


	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}


	public String getItemStatus() {
		return itemStatus;
	}


	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}


	public String getItemLng() {
		return itemLng;
	}


	public void setItemLng(String itemLng) {
		this.itemLng = itemLng;
	}


	public String getItemLat() {
		return itemLat;
	}


	public void setItemLat(String itemLat) {
		this.itemLat = itemLat;
	}


	public Date getItemTime() {
		return itemTime;
	}


	public void setItemTime(Date itemTime) {
		this.itemTime = itemTime;
	}




	public String getItemPhone() {
		return itemPhone;
	}


	public void setItemPhone(String itemPhone) {
		this.itemPhone = itemPhone;
	}


	public Set<WccItemotherinfo> getWccItemtherinfo() {
		return wccItemtherinfo;
	}


	public void setWccItemtherinfo(Set<WccItemotherinfo> wccItemtherinfo) {
		this.wccItemtherinfo = wccItemtherinfo;
	}


	public WccAppartmentDTO(Long id, Boolean isDelete, String insertIp,
			String insertPk, Date insertTime, String updateIp, String updatePk,
			Date updateTime, String deleteIp, String deletePk, Date deleteTime,
			String companyId, String organizationPk,
			WccPlatformUser platformUsers, String itemPick,
			String itemName, String itemIntro, String itemAddress,
			String itemStatus, String itemLng, String itemLat, Date itemTime,
		 String itemPhone,
			Set<WccItemotherinfo> wccItemtherinfo) {
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
		this.itemPick = itemPick;
		this.itemName = itemName;
		this.itemIntro = itemIntro;
		this.itemAddress = itemAddress;
		this.itemStatus = itemStatus;
		this.itemLng = itemLng;
		this.itemLat = itemLat;
		this.itemTime = itemTime;

		this.itemPhone = itemPhone;
		this.wccItemtherinfo = wccItemtherinfo;
	}


	public WccAppartmentDTO() {
		super();
	}
	
	
}

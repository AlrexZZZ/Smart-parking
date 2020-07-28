package com.nineclient.cbd.wcc.dto;

import java.util.Date;
import java.util.Set;




import com.nineclient.cbd.wcc.model.WccAlliancestoreType;
import com.nineclient.model.WccPlatformUser;

public class WccAllancestoreDTO {
	
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
	
    private WccPlatformUser  platformUsers;
	
	
	private String storeName;//商家名称
	
	private String storeAddress;//商家地址
	private String storeLng;//经度
	private String storeLat;//纬度
	private	String storePhone;//电话
	private	String storePic;//图片地址
	private String storeIntro; //商家介绍
	private String storeType; //商家类型
	private String typeStr;
	
	private WccAlliancestoreType wccAlliancestoreType;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public String getStoreLng() {
		return storeLng;
	}
	public void setStoreLng(String storeLng) {
		this.storeLng = storeLng;
	}
	public String getStoreLat() {
		return storeLat;
	}
	public void setStoreLat(String storeLat) {
		this.storeLat = storeLat;
	}
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getStorePic() {
		return storePic;
	}
	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}
	public String getStoreIntro() {
		return storeIntro;
	}
	public void setStoreIntro(String storeIntro) {
		this.storeIntro = storeIntro;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	
	public WccAlliancestoreType getWccAlliancestoreType() {
		return wccAlliancestoreType;
	}
	public void setWccAlliancestoreType(WccAlliancestoreType wccAlliancestoreType) {
		this.wccAlliancestoreType = wccAlliancestoreType;
	}
	public WccAllancestoreDTO(Long id, Boolean isDelete, String insertIp,
			String insertPk, Date insertTime, String updateIp, String updatePk,
			Date updateTime, String deleteIp, String deletePk, Date deleteTime,
			String companyId, String organizationPk,
			WccPlatformUser platformUsers, String storeName,
			String storeAddress, String storeLng, String storeLat,
			String storePhone, String storePic, String storeIntro,
			String storeType,String typeStr ,WccAlliancestoreType wccAlliancestoreType) {
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
		this.storeName = storeName;
		this.storeAddress = storeAddress;
		this.storeLng = storeLng;
		this.storeLat = storeLat;
		this.storePhone = storePhone;
		this.storePic = storePic;
		this.storeIntro = storeIntro;
		this.storeType = storeType;
		this.typeStr = typeStr;
		this.wccAlliancestoreType=wccAlliancestoreType;
	}
	
}

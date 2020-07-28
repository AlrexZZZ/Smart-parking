package com.nineclient.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.nineclient.constant.WccMaterialAttr;
import com.nineclient.constant.WccMaterialsType;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;

public class MaterialsDto implements java.io.Serializable{

private Long parent;
	
	private WccMaterialsType type;
	private String title;
	private String content;
	private String resourceUrl;
	private String thumbnailUrl;
	private Boolean isDeleted;
	private String materialId;
	private String materialType;
	private String description;
	private String mode;
	private Date insertTime;
	private Date uploadTime;
	private String remakeUrl;
	private Boolean urlBoolean;
	private String token;
	private String sort;
	private WccPlatformUser wccPlatformUsers;
	private WccMaterialAttr wccMaterialAttr;//素材属性：如新车介绍、优惠活动等
	private String isNecessarySend;//是否必须群发
	private String sourceUrl;//原文url
	private String flagOfficeSource;//总部素材标识
	private List<MaterialsDto> children ;
	private String noEncodeContent;
	private Long id;
	private Integer version;
	private Long companyId;
	private Integer codeId;	
	private boolean codeStatus;

	
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	public WccMaterialsType getType() {
		return type;
	}
	public void setType(WccMaterialsType type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getRemakeUrl() {
		return remakeUrl;
	}
	public void setRemakeUrl(String remakeUrl) {
		this.remakeUrl = remakeUrl;
	}
	public Boolean getUrlBoolean() {
		return urlBoolean;
	}
	public void setUrlBoolean(Boolean urlBoolean) {
		this.urlBoolean = urlBoolean;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public WccPlatformUser getWccPlatformUsers() {
		return wccPlatformUsers;
	}
	public void setWccPlatformUsers(WccPlatformUser wccPlatformUsers) {
		this.wccPlatformUsers = wccPlatformUsers;
	}
	public WccMaterialAttr getWccMaterialAttr() {
		return wccMaterialAttr;
	}
	public void setWccMaterialAttr(WccMaterialAttr wccMaterialAttr) {
		this.wccMaterialAttr = wccMaterialAttr;
	}
	public String getIsNecessarySend() {
		return isNecessarySend;
	}
	public void setIsNecessarySend(String isNecessarySend) {
		this.isNecessarySend = isNecessarySend;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getFlagOfficeSource() {
		return flagOfficeSource;
	}
	public void setFlagOfficeSource(String flagOfficeSource) {
		this.flagOfficeSource = flagOfficeSource;
	}

	public List<MaterialsDto> getChildren() {
		return children;
	}
	public void setChildren(List<MaterialsDto> children) {
		this.children = children;
	}
	public String getNoEncodeContent() {
		return noEncodeContent;
	}
	public void setNoEncodeContent(String noEncodeContent) {
		this.noEncodeContent = noEncodeContent;
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
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public boolean isCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(boolean codeStatus) {
		this.codeStatus = codeStatus;
	}
	
	
}

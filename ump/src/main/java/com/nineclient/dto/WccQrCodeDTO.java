 package com.nineclient.dto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;



public class WccQrCodeDTO {
	

    private Long id;
	
	/**
	 *二维码类型
	 */
    private String codeType;
    private String codeAttr;
    /**
	 *区域信息
	 */
    private String areaInfo;
    
    /**
	 *区域/活动说明
	 */
    private String areaOrActicityDes;
    
    /**
 	 *是否立即生成二维码
 	 */
    private String isCreateCode;
    
    /**
 	 *邮箱地址
 	 */
    private String emailAddress;
    
    /**
 	 *二维码图片地址
 	 */
    private String codeLocalPath;
    
    /**
  	 *二维码名称
  	 */
    private String codeName;
    /**
  	 *二维码 sceneid
  	 */
    private int sceneId;
    
    /**
  	 *二维码url
  	 */
    private String codeUrl;
    
	/**
	 * 关联公众号
     */

    private WccPlatformUser platformUsers;
    /***
     * 活动名称
     */
    private String activityName;
    private Integer expireTime;
    private String actityImg;
    private Date createDate;
/**
 * 
 * getter and setter
 */

	public Long getId() {
		return id;
	}

	public Date getCreateDate() {
	return createDate;
}

public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}

	public String getActityImg() {
	return actityImg;
}

public void setActityImg(String actityImg) {
	this.actityImg = actityImg;
}

	public Integer getExpireTime() {
	return expireTime;
}

public void setExpireTime(Integer expireTime) {
	this.expireTime = expireTime;
}

	public String getActivityName() {
	return activityName;
}

public void setActivityName(String activityName) {
	this.activityName = activityName;
}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeAttr() {
		return codeAttr;
	}

	public void setCodeAttr(String codeAttr) {
		this.codeAttr = codeAttr;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getAreaOrActicityDes() {
		return areaOrActicityDes;
	}

	public void setAreaOrActicityDes(String areaOrActicityDes) {
		this.areaOrActicityDes = areaOrActicityDes;
	}

	public String getIsCreateCode() {
		return isCreateCode;
	}

	public void setIsCreateCode(String isCreateCode) {
		this.isCreateCode = isCreateCode;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCodeLocalPath() {
		return codeLocalPath;
	}

	public void setCodeLocalPath(String codeLocalPath) {
		this.codeLocalPath = codeLocalPath;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}

	public WccQrCodeDTO() {
		
		
	}

	public WccQrCodeDTO(Long id, String codeType, String areaInfo,
			String areaOrActicityDes, String isCreateCode, String emailAddress,
			WccPlatformUser platformUsers,String codeAttr,String codeUrl,String activityName,Integer expireTime
			,String actityImg,Date createDate) {
		super();
		this.id = id;
		this.codeType = codeType;
		this.areaInfo = areaInfo;
		this.areaOrActicityDes = areaOrActicityDes;
		this.isCreateCode = isCreateCode;
		this.emailAddress = emailAddress;
		this.platformUsers = platformUsers;
		this.codeAttr = codeAttr;
		this.codeUrl = codeUrl;
		this.activityName = activityName;
		this.expireTime = expireTime;
		this.actityImg = actityImg;
		this.createDate=createDate;
	}
    
}

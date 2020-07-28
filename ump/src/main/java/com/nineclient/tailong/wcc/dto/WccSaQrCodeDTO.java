package com.nineclient.tailong.wcc.dto;

import java.util.Date;

/**
 * 专属二维码dto
 */






import javax.validation.constraints.Size;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccStore;


public class WccSaQrCodeDTO {
	private Long id;
	/**
	 * 1.关联公众号
     */
    private WccPlatformUser platformUsers;
	
	private String codeName; // 二维码名称
	
	private String remark; // 备注
	private WccStore wccStore; // 关联门店 
    /**
  	 *二维码 scene_str
  	 */
    private String sceneStr;
    
    /**
  	 *二维码url
  	 */
    private String codeUrl;
    
	private String saName;//sa 名称
	private String saPhone;//sa 电话
	private String saMailAddress;//sa 邮件地址
	private String state; // 二维码状态  1 开启；2.关闭
	/**插入时间
     */
    private Date insertTime;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSaName() {
		return saName;
	}
	public void setSaName(String saName) {
		this.saName = saName;
	}
	public String getSaPhone() {
		return saPhone;
	}
	public void setSaPhone(String saPhone) {
		this.saPhone = saPhone;
	}
	public String getSaMailAddress() {
		return saMailAddress;
	}
	public void setSaMailAddress(String saMailAddress) {
		this.saMailAddress = saMailAddress;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WccStore getWccStore() {
		return wccStore;
	}

	public void setWccStore(WccStore wccStore) {
		this.wccStore = wccStore;
	}

	public String getSceneStr() {
		return sceneStr;
	}

	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public WccSaQrCodeDTO() {
		
	}

	public WccSaQrCodeDTO(Long id,WccPlatformUser platformUsers, String codeName,
			String remark, WccStore wccStore, String sceneStr, String codeUrl,
			Date insertTime,String saName,String saPhone,String saMailAddress,String state) {
		this.id=id;
		this.platformUsers = platformUsers;
		this.codeName = codeName;
		this.remark = remark;
		this.wccStore = wccStore;
		this.sceneStr = sceneStr;
		this.codeUrl = codeUrl;
		this.insertTime = insertTime;
		this.saName=saName;
		this.saPhone=saPhone;
		this.saMailAddress=saMailAddress;
		this.state= state;
	}


	
	
}

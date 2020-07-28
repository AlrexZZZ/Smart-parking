package com.nineclient.cbd.wcc.dto;

import com.nineclient.model.WccPlatformUser;

public class WccImgSaveDTO {
	private Long id;
	 private WccPlatformUser  platformUsers;
	 private String type;
	 private String imgPath;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Override
	public String toString() {
		return "WccImgSaveDTO [id=" + id + ", platformUsers=" + platformUsers
				+ ", type=" + type + ", imgPath=" + imgPath + "]";
	}
	public WccImgSaveDTO(Long id, WccPlatformUser platformUsers, String type,
			String imgPath) {
		super();
		this.id = id;
		this.platformUsers = platformUsers;
		this.type = type;
		this.imgPath = imgPath;
	}
	 
}

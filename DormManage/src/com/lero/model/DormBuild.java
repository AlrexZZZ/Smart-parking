package com.lero.model;

public class DormBuild {
	private int dormBuildId;
	private String dormBuildName;
	private String detail;
	private Double lng;
	private Double lat;
	
	public DormBuild() {
	}
	
	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public DormBuild(String dormBuildName, String detail) {
		this.dormBuildName = dormBuildName;
		this.detail = detail;
	}
	
	
	public int getDormBuildId() {
		return dormBuildId;
	}
	public void setDormBuildId(int dormBuildId) {
		this.dormBuildId = dormBuildId;
	}
	public String getDormBuildName() {
		return dormBuildName;
	}
	public void setDormBuildName(String dormBuildName) {
		this.dormBuildName = dormBuildName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}

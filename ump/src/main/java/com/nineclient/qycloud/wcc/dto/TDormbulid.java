package com.nineclient.qycloud.wcc.dto;




public class TDormbulid { 
/*************arrtibute start*************/
  private int dormBuildId; 
  private String dormBuildName;//学号
  private String dormBuildDetail;//姓名
  private double lng;//评测分
  private double lat;// 加分
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
public String getDormBuildDetail() {
	return dormBuildDetail;
}
public void setDormBuildDetail(String dormBuildDetail) {
	this.dormBuildDetail = dormBuildDetail;
}
public double getLng() {
	return lng;
}
public void setLng(double lng) {
	this.lng = lng;
}
public double getLat() {
	return lat;
}
public void setLat(double lat) {
	this.lat = lat;
}
public TDormbulid() {
	// TODO Auto-generated constructor stub
}
public TDormbulid(String dormBuildName, String dormBuildDetail, double lng,
		double lat) {
	this.dormBuildName = dormBuildName;
	this.dormBuildDetail = dormBuildDetail;
	this.lng = lng;
	this.lat = lat;
}

/*************arrtibute end*************/ 

/************* getter  and setter method start*************/ 



} 

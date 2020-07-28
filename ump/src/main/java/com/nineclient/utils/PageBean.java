package com.nineclient.utils;

import java.util.Date;

public class PageBean {
	private final static String  FORMAT="yyyy-MM-dd HH:mm:ss";
	private Date startTime;
	  
	private Date endTime;

	private String startTimeStr;
	  
	private String endTimeStr;
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
		if(startTimeStr!=null&&startTimeStr!=""){
			this.startTime =	DateUtil.formateDate(startTimeStr,FORMAT);
		}
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
		if(endTimeStr!=null&&endTimeStr!=""){
			this.endTime =DateUtil.formateDate(endTimeStr,FORMAT);
		}
	}
	  
	
}

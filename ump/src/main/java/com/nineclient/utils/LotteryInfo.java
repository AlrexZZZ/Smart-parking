package com.nineclient.utils;

import flexjson.JSONSerializer;

public class LotteryInfo {
	
	private int awardLevel;
	
	private String snCode;
	
	private int number;
	
	private int dayNumber;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public int getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(int awardLevel) {
		this.awardLevel = awardLevel;
	}
	
	@Override
	public String toString() {
		return "LotteryInfo [awardLevel=" + awardLevel + ", snCode=" + snCode
				+ ", number=" + number + ",dayNumber=" + dayNumber + "]";
	}
	

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
}

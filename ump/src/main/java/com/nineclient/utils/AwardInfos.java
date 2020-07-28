package com.nineclient.utils;

import flexjson.JSONSerializer;

public class AwardInfos {
	private String oneName;
	
	private int oneNumber;
	
	private double oneProbability;
	
	private String oneAwardInfo;
	
	private String twoName;
	
	private int twoNumber;
	
	private double twoProbability;
	
	private String twoAwardInfo;
	
	private String threeName;
	
	private int threeNumber;
	
	private double threeProbability;
	
	private String threeAwardInfo;
	
	private String fourName;
	
	private int fourNumber;
	
	private double fourProbability;
	
	private String fourAwardInfo;
	
	private String fiveName;
	
	private int fiveNumber;
	
	private double fiveProbability;
	
	private String fiveAwardInfo;
	
	private String sixName;
	
	private int sixNumber;
	
	private double sixProbability;
	
	private String sixAwardInfo;

	public String getOneName() {
		return oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public int getOneNumber() {
		return oneNumber;
	}

	public void setOneNumber(int oneNumber) {
		this.oneNumber = oneNumber;
	}

	public double getOneProbability() {
		return oneProbability;
	}

	public void setOneProbability(double oneProbability) {
		this.oneProbability = oneProbability;
	}

	public String getOneAwardInfo() {
		return oneAwardInfo;
	}

	public void setOneAwardInfo(String oneAwardInfo) {
		this.oneAwardInfo = oneAwardInfo;
	}

	public String getTwoName() {
		return twoName;
	}

	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}

	public int getTwoNumber() {
		return twoNumber;
	}

	public void setTwoNumber(int twoNumber) {
		this.twoNumber = twoNumber;
	}

	public double getTwoProbability() {
		return twoProbability;
	}

	public void setTwoProbability(double twoProbability) {
		this.twoProbability = twoProbability;
	}

	public String getTwoAwardInfo() {
		return twoAwardInfo;
	}

	public void setTwoAwardInfo(String twoAwardInfo) {
		this.twoAwardInfo = twoAwardInfo;
	}

	public String getThreeName() {
		return threeName;
	}

	public void setThreeName(String threeName) {
		this.threeName = threeName;
	}

	public int getThreeNumber() {
		return threeNumber;
	}

	public void setThreeNumber(int threeNumber) {
		this.threeNumber = threeNumber;
	}

	public double getThreeProbability() {
		return threeProbability;
	}

	public void setThreeProbability(double threeProbability) {
		this.threeProbability = threeProbability;
	}

	public String getThreeAwardInfo() {
		return threeAwardInfo;
	}

	public void setThreeAwardInfo(String threeAwardInfo) {
		this.threeAwardInfo = threeAwardInfo;
	}

	public String getFourName() {
		return fourName;
	}

	public void setFourName(String fourName) {
		this.fourName = fourName;
	}

	public int getFourNumber() {
		return fourNumber;
	}

	public void setFourNumber(int fourNumber) {
		this.fourNumber = fourNumber;
	}

	public double getFourProbability() {
		return fourProbability;
	}

	public void setFourProbability(double fourProbability) {
		this.fourProbability = fourProbability;
	}

	public String getFourAwardInfo() {
		return fourAwardInfo;
	}

	public void setFourAwardInfo(String fourAwardInfo) {
		this.fourAwardInfo = fourAwardInfo;
	}

	public String getFiveName() {
		return fiveName;
	}

	public void setFiveName(String fiveName) {
		this.fiveName = fiveName;
	}

	public int getFiveNumber() {
		return fiveNumber;
	}

	public void setFiveNumber(int fiveNumber) {
		this.fiveNumber = fiveNumber;
	}

	public double getFiveProbability() {
		return fiveProbability;
	}

	public void setFiveProbability(double fiveProbability) {
		this.fiveProbability = fiveProbability;
	}

	public String getFiveAwardInfo() {
		return fiveAwardInfo;
	}

	public void setFiveAwardInfo(String fiveAwardInfo) {
		this.fiveAwardInfo = fiveAwardInfo;
	}

	public String getSixName() {
		return sixName;
	}

	public void setSixName(String sixName) {
		this.sixName = sixName;
	}

	public int getSixNumber() {
		return sixNumber;
	}

	public void setSixNumber(int sixNumber) {
		this.sixNumber = sixNumber;
	}

	public double getSixProbability() {
		return sixProbability;
	}

	public void setSixProbability(double sixProbability) {
		this.sixProbability = sixProbability;
	}

	public String getSixAwardInfo() {
		return sixAwardInfo;
	}

	public void setSixAwardInfo(String sixAwardInfo) {
		this.sixAwardInfo = sixAwardInfo;
	}
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	@Override
	public String toString() {
		return "AwardInfos [oneName=" + oneName + ", oneNumber=" + oneNumber
				+ ", oneProbability=" + oneProbability + ", oneAwardInfo="
				+ oneAwardInfo + ", twoName=" + twoName + ", twoNumber="
				+ twoNumber + ", twoProbability=" + twoProbability
				+ ", twoAwardInfo=" + twoAwardInfo + ", threeName=" + threeName
				+ ", threeNumber=" + threeNumber + ", threeProbability="
				+ threeProbability + ", threeAwardInfo=" + threeAwardInfo
				+ ", fourName=" + fourName + ", fourNumber=" + fourNumber
				+ ", fourProbability=" + fourProbability + ", fourAwardInfo="
				+ fourAwardInfo + ", fiveName=" + fiveName + ", fiveNumber="
				+ fiveNumber + ", fiveProbability=" + fiveProbability
				+ ", fiveAwardInfo=" + fiveAwardInfo + ", sixName=" + sixName
				+ ", sixNumber=" + sixNumber + ", sixProbability="
				+ sixProbability + ", sixAwardInfo=" + sixAwardInfo + "]";
	}
}

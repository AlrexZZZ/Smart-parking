package com.nineclient.utils;

import flexjson.JSONSerializer;

public class ReportModel<T> extends PageModel<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4187154062523840261L;
	private long sumNum;
	public long getSumNum() {
		return sumNum;
	}
	public void setSumNum(long sumNum) {
		this.sumNum = sumNum;
	}
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
}

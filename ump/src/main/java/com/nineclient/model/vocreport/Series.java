package com.nineclient.model.vocreport;

import java.util.Collection;
import java.util.List;

import flexjson.JSONSerializer;

public class Series {
	
	private String name;
	private List<Long> data;
	
	public Series(String name,List<Long> data) {
		this.data=data;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Long> getData() {
		return data;
	}
	public void setData(List<Long> data) {
		this.data = data;
	}
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
	public static String toJsonArray(Collection<Series> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
}

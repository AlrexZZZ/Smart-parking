package com.nineclient.model.vocreport;

import java.util.List;

public class TrendModel {
	
	private List<String> categories;
	
	private List<Series> series;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}
	
	
}

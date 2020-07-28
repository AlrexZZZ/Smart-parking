package com.nineclient.qycloud.wcc.util;

import org.apache.poi.hssf.record.formula.functions.T;

import com.nineclient.utils.Pager;
import com.nineclient.utils.QueryModel;

public class PageCondition {
	private Pager pager;
	private QueryModel<?> qm;
	
	public PageCondition() {
	}
	
	public PageCondition(Pager pager, QueryModel<?> qm) {
		this.pager = pager;
		this.qm = qm;
	}

	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public QueryModel<?> getQm() {
		return qm;
	}
	public void setQm(QueryModel<?> qm) {
		this.qm = qm;
	}
	
}

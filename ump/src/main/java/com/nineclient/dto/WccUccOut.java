package com.nineclient.dto;

public class WccUccOut {
	private String opname;
	
	private String msg;
	
	private String info;
	
	public WccUccOut() {
		super();
	}

	public WccUccOut(String opname, String msg, String info) {
		super();
		this.opname = opname;
		this.msg = msg;
		this.info = info;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "WccUccOut [opname=" + opname + ", msg=" + msg + ", info="
				+ info + "]";
	}
	
}

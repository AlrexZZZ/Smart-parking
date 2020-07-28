package com.nineclient.dto;

public class WccUccHand {
	private String virtualAccount;
	
	private String companyPk;
	
	private String busiPK;
	
	private String busiOperateType;
	
	private String channelType;
	
	private String callback;
	
	private String remarks;
	
	private String info;
	
	private String requestMsg ;
	
	public WccUccHand(String virtualAccount, String busiPK,
			String busiOperateType, String channelType, String callback,
			String remarks, String info, String requestMsg) {
		super();
		this.virtualAccount = virtualAccount;
		this.busiPK = busiPK;
		this.busiOperateType = busiOperateType;
		this.channelType = channelType;
		this.callback = callback;
		this.remarks = remarks;
		this.info = info;
		this.requestMsg = requestMsg;
	}

	public WccUccHand(String virtualAccount, String companyPk, String busiPK,
			String busiOperateType, String channelType, String callback,
			String remarks, String info, String requestMsg) {
		super();
		this.virtualAccount = virtualAccount;
		this.companyPk = companyPk;
		this.busiPK = busiPK;
		this.busiOperateType = busiOperateType;
		this.channelType = channelType;
		this.callback = callback;
		this.remarks = remarks;
		this.info = info;
		this.requestMsg = requestMsg;
	}

	public String getVirtualAccount() {
		return virtualAccount;
	}

	public void setVirtualAccount(String virtualAccount) {
		this.virtualAccount = virtualAccount;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public String getBusiPK() {
		return busiPK;
	}

	public void setBusiPK(String busiPK) {
		this.busiPK = busiPK;
	}

	public String getBusiOperateType() {
		return busiOperateType;
	}

	public void setBusiOperateType(String busiOperateType) {
		this.busiOperateType = busiOperateType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}
	
	
}

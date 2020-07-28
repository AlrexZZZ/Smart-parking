package com.nineclient.cbd.wcc.dto;

import java.util.Date;
import java.util.Set;

import com.nineclient.cbd.wcc.model.CbdWccProprietor;
import com.nineclient.cbd.wcc.model.WccAppartment;
import com.nineclient.model.WccPlatformUser;


public class WccFeesDTO {

	/**
	 * 主键
	 */

    private Long id;
	

	/**
	 * 关联项目实体类
	 */

	private CbdWccProprietor cbdWccProprietor;
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 *姓名
	 */
    private String userName;
    /**
	 * 电话
	 */
    private String cellphone;
    
    /**
	 *门牌号
	 */
    private String doorNum;
    
    
    /**
 	 *月份
 	 */

    private Date month;
    
    /**
 	 *支付状态  0未支付/1支付
 	 */
    private int state;
    
    /**
 	 *金额保留两位小数
 	 */
    private float amount;
    
    private float toatl;
    /**
  	 *其他可跳转url
  	 */
    private String otherUrl;

    private Integer version;
	/**
	 * 关联公众号
     */

    private WccPlatformUser  platformUsers;
	
    public float getToatl() {
		return toatl;
	}


	public void setToatl(float toatl) {
		this.toatl = toatl;
	}


	/**插入时间
     */
private String monthStr;
    private Date insertTime;


   private String itemName; 
    
    
    
	public String getItemName() {
	return itemName;
}


public void setItemName(String itemName) {
	this.itemName = itemName;
}


	public String getMonthStr() {
		return monthStr;
	}


	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}


	public WccFeesDTO() {
	
	}


	public WccFeesDTO(Long id, String itemName, String summary,
			String userName, String cellphone, String doorNum, Date month,
			int state, float amount, String otherUrl, Integer version,
			WccPlatformUser platformUsers, Date insertTime ,String monthStr) {
	
		this.id = id;
		this.itemName = itemName;
		this.summary = summary;
		this.userName = userName;
		this.cellphone = cellphone;
		this.doorNum = doorNum;
		this.month = month;
		this.state = state;
		this.amount = amount;
		this.otherUrl = otherUrl;
		this.version = version;
		this.platformUsers = platformUsers;
		this.insertTime = insertTime;
		this.monthStr = monthStr;
	}











	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	public CbdWccProprietor getCbdWccProprietor() {
		return cbdWccProprietor;
	}


	public void setCbdWccProprietor(CbdWccProprietor cbdWccProprietor) {
		this.cbdWccProprietor = cbdWccProprietor;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getCellphone() {
		return cellphone;
	}


	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}


	public String getDoorNum() {
		return doorNum;
	}


	public void setDoorNum(String doorNum) {
		this.doorNum = doorNum;
	}


	public Date getMonth() {
		return month;
	}


	public void setMonth(Date month) {
		this.month = month;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public String getOtherUrl() {
		return otherUrl;
	}


	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public  WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}


	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}


	public Date getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}

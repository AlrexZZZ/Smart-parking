package com.nineclient.tailong.wcc.dto;
import java.util.Date;


/**
 * 泰隆产品实体类
 */


public class WccFriendsAppointmentDTO {
	private Long id;
	private String fname; // 姓名
	
	private String fphone; // 手机号
	
	private String farea; // 所在地区
	private String fnickName; // 粉丝昵称
	
	private String fopenId; // 粉丝openid
	
	private String productType; // 预约产品类型
	private String productName; // 预约产品名称
	
	private String result; // 预约结果
	
	/**插入时间
     */

    private Date insertTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public String getFarea() {
		return farea;
	}

	public void setFarea(String farea) {
		this.farea = farea;
	}

	public String getFnickName() {
		return fnickName;
	}

	public void setFnickName(String fnickName) {
		this.fnickName = fnickName;
	}

	public String getFopenId() {
		return fopenId;
	}

	public void setFopenId(String fopenId) {
		this.fopenId = fopenId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public WccFriendsAppointmentDTO() {
	}

	public WccFriendsAppointmentDTO(Long id, String fname, String fphone,
			String farea, String fnickName, String fopenId, String productType,
			String productName, String result, Date insertTime) {
		this.id = id;
		this.fname = fname;
		this.fphone = fphone;
		this.farea = farea;
		this.fnickName = fnickName;
		this.fopenId = fopenId;
		this.productType = productType;
		this.productName = productName;
		this.result = result;
		this.insertTime = insertTime;
	}

	
	
}

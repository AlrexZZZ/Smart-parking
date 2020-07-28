package com.nineclient.tailong.wcc.dto;

import java.util.Date;

/**
 * 泰隆产品实体类dto
 */
import com.nineclient.model.WccPlatformUser;


public class WccTproductsDTO {

	private Long id;
	private int productType;// 产品类型   1 代表融资缺钱，2 代表钱生钱
	private String thumbImage; // 产品缩略图
	
	private String productName; // 图文信息
	
	private String backImage; // 产品背景图
	
	private String pInfo; // 产品信息
	
	private String pAdvantage; // 产品优势
	
	private String applayStep; // 产品如何申请
	
	/**
	 * 关联公众号
     */
    private WccPlatformUser  platformUsers;
	
    public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	/**插入时间
     */
    private Date insertTime;

	public WccTproductsDTO() {
	}

	public WccTproductsDTO(Long id, String thumbImage, String productName,
			String backImage, String pInfo, String pAdvantage,
			String applayStep, WccPlatformUser platformUsers, Date insertTime,int productType) {
		this.id = id;
		this.thumbImage = thumbImage;
		this.productName = productName;
		this.backImage = backImage;
		this.pInfo = pInfo;
		this.pAdvantage = pAdvantage;
		this.applayStep = applayStep;
		this.platformUsers = platformUsers;
		this.insertTime = insertTime;
		this.productType = productType;
	}

	public WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThumbImage() {
		return thumbImage;
	}

	public void setThumbImage(String thumbImage) {
		this.thumbImage = thumbImage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public String getpInfo() {
		return pInfo;
	}

	public void setpInfo(String pInfo) {
		this.pInfo = pInfo;
	}

	public String getpAdvantage() {
		return pAdvantage;
	}

	public void setpAdvantage(String pAdvantage) {
		this.pAdvantage = pAdvantage;
	}

	public String getApplayStep() {
		return applayStep;
	}

	public void setApplayStep(String applayStep) {
		this.applayStep = applayStep;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
  
	
	
	
	
}

package com.nineclient.cbd.wcc.dto;

import java.util.Date;

import com.nineclient.model.WccPlatformUser;

public class WccFinancialProductDTO {

	private Long id;

	private WccPlatformUser platformUsers;

	private String platFormId;

	private String platFormAccount;

	private String financialName; // 理财产品名称

	private String themeImage; // 主题图

	private String themeImageText; // 图文信息

	private String productCode; // 产品代码

	private String riskLevel; // 风险等级

	private String productType; // 产品类型

	private Date saleBeginDate; // 销售起日

	private Date saleEndDate; // 销售止日

	private Date valueDate; // 起息日

	private Date arrivalDate; // 到账日

	private String investmentHorizon; // 投资期限（天）

	private String expectedYield; // 预期收益率

	private Long minMoney; // 产品起点金额（元）

	private Long maxMoney; // 产品上限金额（元）

	private Long increasingMoney; // 递增金额

	private Long totalMoney; // 微信预约总额（元）

	private Integer totalNumber; // 预约总人数

	private Date insertTime;

	private Boolean isUsing; // 是否启用
	
	
	private String ck;

	public WccFinancialProductDTO() {
		super();
	}

	public WccFinancialProductDTO(Long id, WccPlatformUser platformUsers,
			String financialName, String themeImage, String themeImageText,
			String productCode, String riskLevel, String productType,
			Date saleBeginDate, Date saleEndDate, Date valueDate,
			Date arrivalDate, String investmentHorizon, String expectedYield,
			Long minMoney, Long maxMoney, Long increasingMoney,
			Long totalMoney, Integer totalNumber, Date insertTime,
			Boolean isUsing,String ck) {
		super();
		this.id = id;
		this.platformUsers = platformUsers;
		this.financialName = financialName;
		this.themeImage = themeImage;
		this.themeImageText = themeImageText;
		this.productCode = productCode;
		this.riskLevel = riskLevel;
		this.productType = productType;
		this.saleBeginDate = saleBeginDate;
		this.saleEndDate = saleEndDate;
		this.valueDate = valueDate;
		this.arrivalDate = arrivalDate;
		this.investmentHorizon = investmentHorizon;
		this.expectedYield = expectedYield;
		this.minMoney = minMoney;
		this.maxMoney = maxMoney;
		this.increasingMoney = increasingMoney;
		this.totalMoney = totalMoney;
		this.totalNumber = totalNumber;
		this.insertTime = insertTime;
		this.isUsing = isUsing;
		this.ck=ck;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WccPlatformUser getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(WccPlatformUser platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(String platFormId) {
		this.platFormId = platFormId;
	}

	public String getPlatFormAccount() {
		return platFormAccount;
	}

	public void setPlatFormAccount(String platFormAccount) {
		this.platFormAccount = platFormAccount;
	}

	public String getFinancialName() {
		return financialName;
	}

	public void setFinancialName(String financialName) {
		this.financialName = financialName;
	}

	public String getThemeImage() {
		return themeImage;
	}

	public void setThemeImage(String themeImage) {
		this.themeImage = themeImage;
	}

	public String getThemeImageText() {
		return themeImageText;
	}

	public void setThemeImageText(String themeImageText) {
		this.themeImageText = themeImageText;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Date getSaleBeginDate() {
		return saleBeginDate;
	}

	public void setSaleBeginDate(Date saleBeginDate) {
		this.saleBeginDate = saleBeginDate;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getInvestmentHorizon() {
		return investmentHorizon;
	}

	public void setInvestmentHorizon(String investmentHorizon) {
		this.investmentHorizon = investmentHorizon;
	}

	public String getExpectedYield() {
		return expectedYield;
	}

	public void setExpectedYield(String expectedYield) {
		this.expectedYield = expectedYield;
	}

	public Long getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Long minMoney) {
		this.minMoney = minMoney;
	}

	public Long getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Long maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Long getIncreasingMoney() {
		return increasingMoney;
	}

	public void setIncreasingMoney(Long increasingMoney) {
		this.increasingMoney = increasingMoney;
	}

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Boolean getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Boolean isUsing) {
		this.isUsing = isUsing;
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}
	

}

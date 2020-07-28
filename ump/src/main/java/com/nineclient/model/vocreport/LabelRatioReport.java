package com.nineclient.model.vocreport;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.json.RooJson;

import com.nineclient.model.UmpCompany;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class LabelRatioReport {

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date labelTime;

	/**
	 * 平台名称
     */
	private String platformName;


	/**
	 * 店铺名称
     */
	private String shopName;
	
	/**
	 * 品牌名称
     */
	private String brandNname;

	/**
	 * SKU名称
     */
	private String skuName;

	/**
	 * 评论类型
     */
	private String commentTypes;
	
	/**
	 * 大品牌
     */
	private String bigBrands;

	/**
	 * 效果好
     */
	private String isGood;
	
	/**
	 * 便宜
     */
	private String  cheap;
	
	
	/**
	 * 发货快
     */
	private String  fastGoods;
	
	/**
	 * 包装好
     */
	private String  goodsPackage;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date labelDate;
	
	private String companyCode;


	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("labelTime","platformName","shopName","brandNname","skuName",
					"commentTypes","bigBrands","isGood","cheap","fastGoods","goodsPackage","labelDate","companyCode");

	public static final EntityManager entityManager() {
		EntityManager em = new LabelRatioReport().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}



	
	/**
	 * 查询
	 * @param firstResult
	 * @param maxResults
	 * @param platformName
	 * @param shopName
	 * @param brandNname
	 * @param skuName
	 * @param commentTypes
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<LabelRatioReport> findLabelEntries(int firstResult,
			int maxResults, String platformName,String shopName,
			String brandNname,String skuName,String commentTypes,
			Date startTime, Date endTime,
			UmpCompany company) {
		String sql = "SELECT o FROM LabelRatioReport o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(platformName)) {
			sql += " and o.platformName=:platformName";
			map.put("platformName", platformName);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shopName=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(brandNname)) {
			sql += " and o.brandNname=:brandNname";
			map.put("brandNname", brandNname);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.skuName=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(commentTypes)) {
			sql += " and o.commentTypes=:commentTypes";
			map.put("commentTypes", commentTypes);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.labelDate>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.labelDate<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.companyCode=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		} 
		sql+=" order by labelDate desc";
		TypedQuery<LabelRatioReport> query = entityManager().createQuery(sql,
				LabelRatioReport.class);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		if(firstResult==0 && maxResults==0){
			return query.getResultList();
		}else{
			return query.setFirstResult(firstResult).setMaxResults(maxResults)
					.getResultList();
		}
	}
	

	/**
	 * 查询所有map的
	 * @param firstResult
	 * @param maxResults
	 * @param platformName
	 * @param shopName
	 * @param brandNname
	 * @param skuName
	 * @param commentTypes
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<Map> findLabelEntriesMap(String platformName,String shopName,
			String brandNname,String skuName,String commentTypes,
			Date startTime, Date endTime,
			UmpCompany company) {
		String sql = "SELECT o.big_Brands,o.is_Good,o.cheap,o.fast_Goods,o.goods_Package,o.label_Date"
				+ " FROM Label_Ratio_Report o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(platformName)) {
			sql += " and o.platform_Name=:platformName";
			map.put("platformName", platformName);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_Name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(brandNname)) {
			sql += " and o.brand_Nname=:brandNname";
			map.put("brandNname", brandNname);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_Name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(commentTypes)) {
			sql += " and o.comment_Types=:commentTypes";
			map.put("commentTypes", commentTypes);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.label_Date>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.label_Date<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.company_Code=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		} 
		sql+=" order by label_Date desc";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.list();
	}
	

	/**
	 * 所有长度
	 * @param platformName
	 * @param shopName
	 * @param brandNname
	 * @param skuName
	 * @param commentTypes
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static Long countFindLabelEntries(String platformName,String shopName,
			String brandNname,String skuName,String commentTypes,
			Date startTime, Date endTime,
			UmpCompany company) {
		String sql = "SELECT count(o) FROM LabelRatioReport o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(platformName)) {
			sql += " and o.platformName=:platformName";
			map.put("platformName", platformName);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shopName=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(brandNname)) {
			sql += " and o.brandNname=:brandNname";
			map.put("brandNname", brandNname);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.skuName=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(commentTypes)) {
			sql += " and o.commentTypes=:commentTypes";
			map.put("commentTypes", commentTypes);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.labelDate>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.labelDate<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.companyCode=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		} 
		
		TypedQuery<Long> query = entityManager().createQuery(sql,
				Long.class);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.getSingleResult();
	}


	public static LabelRatioReport findLabelReport(Long id) {
		if (id == null)
			return null;
		return entityManager().find(LabelRatioReport.class, id);
	}
	
	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			LabelRatioReport attached = LabelRatioReport.findLabelReport(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public LabelRatioReport merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		LabelRatioReport merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLabelTime() {
		return labelTime;
	}

	public void setLabelTime(Date labelTime) {
		this.labelTime = labelTime;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	
	public String getBrandNname() {
		return brandNname;
	}

	public void setBrandNname(String brandNname) {
		this.brandNname = brandNname;
	}

	public String getCommentTypes() {
		return commentTypes;
	}

	public void setCommentTypes(String commentTypes) {
		this.commentTypes = commentTypes;
	}

	public Date getLabelDate() {
		return labelDate;
	}

	public void setLabelDate(Date labelDate) {
		this.labelDate = labelDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getBigBrands() {
		return bigBrands;
	}

	public void setBigBrands(String bigBrands) {
		this.bigBrands = bigBrands;
	}

	public String getIsGood() {
		return isGood;
	}

	public void setIsGood(String isGood) {
		this.isGood = isGood;
	}

	public String getCheap() {
		return cheap;
	}

	public void setCheap(String cheap) {
		this.cheap = cheap;
	}

	public String getFastGoods() {
		return fastGoods;
	}

	public void setFastGoods(String fastGoods) {
		this.fastGoods = fastGoods;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	
	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	
	public String getSkuName() {
		return this.skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static LabelRatioReport fromJsonToHotWordReport(String json) {
		return new JSONDeserializer<LabelRatioReport>().use(null,
				LabelRatioReport.class).deserialize(json);
	}

	public static String toJsonArray(Collection<LabelRatioReport> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<LabelRatioReport> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<LabelRatioReport> fromJsonArrayToHotWordReports(
			String json) {
		return new JSONDeserializer<List<LabelRatioReport>>().use("values",
				LabelRatioReport.class).deserialize(json);
	}
}

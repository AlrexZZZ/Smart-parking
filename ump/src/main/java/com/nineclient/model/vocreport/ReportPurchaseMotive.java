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
import org.springframework.web.bind.annotation.RequestParam;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.nineclient.model.WccRecordMsg;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ReportPurchaseMotive {
	/**子行业Id
     */
	private String businessTypeId;
	/**平台名称
     */
	private String channelName;

	/**店铺名称
     */
	private String shopName;
	/**品牌名称
     */
	private String brandName;
	
	/**标签名称
     */
	private String labelName;
	/**sku名称
     */
	private String skuName;
	/** 公司code
     */
	private String companyCode;
	/**时间
     */
	private String ifHas;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
    private Date insertTime;
	/**
	 * 占比
	 */
    private String proportion;
    
    private Float totalScore;
    
	public String getIfHas() {
		return ifHas;
	}

	public void setIfHas(String ifHas) {
		this.ifHas = ifHas;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("insertTime", "labelName", "channelName", "shopName",
					"skuName", "companyCode","brandName" ,"proportion");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportPurchaseMotive().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<ReportPurchaseMotive> findAllReportPurchaseMotive() {
		return entityManager().createQuery("SELECT o FROM ReportPurchaseMotive o",
				ReportPurchaseMotive.class).getResultList();
	}


	public static List<ReportPurchaseMotive> findAllReportPurchaseMotives(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportPurchaseMotive o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportPurchaseMotive.class)
				.getResultList();
	}

	public static ReportPurchaseMotive findReportPurchaseMotive(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportPurchaseMotive.class, id);
	}

	public static List<ReportPurchaseMotive> findReportPurchaseMotiveEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportPurchaseMotive o",
						ReportPurchaseMotive.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
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
			ReportPurchaseMotive attached = ReportPurchaseMotive.findReportPurchaseMotive(this.id);
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
	public ReportPurchaseMotive merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportPurchaseMotive merged = this.entityManager.merge(this);
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

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}


	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public static ReportPurchaseMotive fromJsonToReportPurchaseMotive(String json) {
		return new JSONDeserializer<ReportPurchaseMotive>().use(null,
				ReportPurchaseMotive.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportPurchaseMotive> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportPurchaseMotive> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportPurchaseMotive> fromJsonArrayToReportPurchaseMotives(
			String json) {
		return new JSONDeserializer<List<ReportPurchaseMotive>>().use("values",
				ReportPurchaseMotive.class).deserialize(json);
	}
	//极地蜘蛛网
	public static List<ReportPurchaseMotive> findRangeByQuery(int firstResult, int maxResults,
			String channelName,String shopName,String brandName,String skuName,String insertTime,String companyCode
			) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o FROM ReportPurchaseMotive o where 1=1 ");
		
		if (!StringUtils.isEmpty(channelName)) {
			qlString.append(" and o.channelName = '"+channelName+"'");
			
		}
		if (!StringUtils.isEmpty(shopName)) {
			qlString.append(" and o.shopName = '"+shopName+"'");
			
		}
		if (!StringUtils.isEmpty(brandName)) {
			qlString.append(" and o.brandName = '"+brandName+"'");
			
		}
		if (!StringUtils.isEmpty(skuName)) {
			qlString.append(" and o.skuName = '"+skuName+"'");
			
		}
		if (!StringUtils.isEmpty(companyCode)) {
			qlString.append(" and o.companyCode = '"+companyCode+"'");
		}
		if (!StringUtils.isEmpty(insertTime)) {
			qlString.append(" and o.insertTime >= '"+insertTime+" 00:00:00'");
			qlString.append(" and o.insertTime <= '"+insertTime+" 23:59:59'");
		}
        System.out.println(qlString.toString());
		qlString.append(" order by o.proportion desc");
		List<ReportPurchaseMotive> reclist = entityManager().createQuery(qlString.toString(),ReportPurchaseMotive.class).setFirstResult(0).setMaxResults(10).getResultList();
		return reclist;
	}	
	public static List<Map> findSkuRangeReportEntries(int firstResult, int maxResults,
			String channelName,String shopName,String brandName,String skuName,String insertTime,String companyCode) {
		String sql = "SELECT * FROM report_purchase_motive o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_name=:channelName";
			map.put("channelName", channelName);
		}

		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_name=:brandName";
			map.put("brandName", brandName);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(companyCode)) {
			sql += " and o.company_code=:companyCode";
			map.put("companyCode", companyCode);
		}
		if (!StringUtils.isEmpty(insertTime)) {
			sql += " and o.insert_time>=:insertStartTime";
			map.put("insertStartTime", insertTime+" 00:00:00");
		}
		if (!StringUtils.isEmpty(insertTime)) {
			sql += " and o.insert_time<=:insertEndTime";
			map.put("insertEndTime", insertTime+" 23:59:59");
		}
		
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.setFirstResult(0).setMaxResults(10).list();
	}
	
}

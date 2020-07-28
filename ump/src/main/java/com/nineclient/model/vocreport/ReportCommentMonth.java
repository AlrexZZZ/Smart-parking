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
public class ReportCommentMonth {
	public Integer getCommentGood() {
		return commentGood;
	}

	public void setCommentGood(Integer commentGood) {
		this.commentGood = commentGood;
	}

	public Integer getCommentBetter() {
		return commentBetter;
	}

	public void setCommentBetter(Integer commentBetter) {
		this.commentBetter = commentBetter;
	}

	public Integer getCommentBad() {
		return commentBad;
	}

	public void setCommentBad(Integer commentBad) {
		this.commentBad = commentBad;
	}


	/**平台名称
    */
	private String channelName;

	/**店铺名称
    */
	private String shopName;
	/**品牌名称
    */
	private String brandName;
	
	/**好评
	    */
	private Integer commentGood;
		
	/**中评
	 */
	private Integer commentBetter;
			
	/**差评
	 */
	private Integer commentBad;
	/**sku名称
     */
	private String skuName;

	/** 公司code
     */
	private String companyCode;
	/**时间 -天
	 */
	 @Temporal(TemporalType.TIMESTAMP)
	 @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateDay;
	private String dateMonthStr;

	public String getDateMonthStr() {
		return dateMonthStr;
	}

	public void setDateMonthStr(String dateMonthStr) {
		this.dateMonthStr = dateMonthStr;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("insertTime", "labelName", "channelName", "shopName",
					"skuName", "companyCode","brandName" ,"proportion");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportCommentMonth().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<ReportCommentMonth> findAllReportCommentMonth() {
		return entityManager().createQuery("SELECT o FROM ReportCommentMonth o",
				ReportCommentMonth.class).getResultList();
	}


	public static List<ReportCommentMonth> findAllReportCommentMonths(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportCommentMonth o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportCommentMonth.class)
				.getResultList();
	}

	public static ReportCommentMonth findReportCommentMonth(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportCommentMonth.class, id);
	}

	public static List<ReportCommentMonth> findReportCommentMonthEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportCommentMonth o",
						ReportCommentMonth.class).setFirstResult(firstResult)
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
			ReportCommentMonth attached = ReportCommentMonth.findReportCommentMonth(this.id);
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
	public ReportCommentMonth merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportCommentMonth merged = this.entityManager.merge(this);
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

	public static ReportCommentMonth fromJsonToReportCommentMonth(String json) {
		return new JSONDeserializer<ReportCommentMonth>().use(null,
				ReportCommentMonth.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportCommentMonth> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportCommentMonth> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportCommentMonth> fromJsonArrayToReportCommentMonths(
			String json) {
		return new JSONDeserializer<List<ReportCommentMonth>>().use("values",
				ReportCommentMonth.class).deserialize(json);
	}
	//查询总数的
	public  static long findRcordCountByQuery(
			String channelName,String shopName,String brandName,String skuName,String startTime,String endTime,String companyCode) 
					throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT COUNT(o) FROM ReportCommentMonth o where 1=1 ");
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
		if (!StringUtils.isEmpty(startTime)) {
			qlString.append(" and o.dateDay >= '"+startTime+" 00:00:00'");
		}if (!StringUtils.isEmpty(endTime)) {
			qlString.append(" and o.dateDay <= '"+endTime+" 23:59:59'");
		}
		long count = entityManager().createQuery(qlString.toString(),Long.class).getSingleResult();
		return count;
	}
	
	//
	public static List<ReportCommentMonth> findRangeByQuery(int firstResult,int maxResults ,
			String channelName,String shopName,String brandName,String skuName,String startTime,String endTime,String companyCode
			) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o FROM ReportCommentMonth o where 1=1 ");
   
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
		if (!StringUtils.isEmpty(startTime)) {
			qlString.append(" and o.dateDay >= '"+startTime+" 00:00:00'");
		}if (!StringUtils.isEmpty(endTime)) {
			qlString.append(" and o.dateDay <= '"+endTime+" 23:59:59'");
		}
		qlString.append(" order by o.dateDay");
		List<ReportCommentMonth> reclist = null;
		if(maxResults > 0){
			 reclist = entityManager().createQuery(qlString.toString(),ReportCommentMonth.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		}else{
			 reclist = entityManager().createQuery(qlString.toString(),ReportCommentMonth.class).getResultList();

		}
		return reclist;
	}	
	public static List<Map> findMapReportEntries(String channelName,String shopName,String brandName,String skuName,String startTime,String endTime,String companyCode) {
		String sql = "SELECT * FROM report_comment_month o where 1=1 ";
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
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.date_day>=:startTime";
			map.put("startTime", startTime+" 00:00:00");
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.date_day<=:endTime";
			map.put("endTime", endTime+" 23:59:59");
		}
		
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.list();
	}

	public Date getDateDay() {
		return dateDay;
	}

	public void setDateDay(Date dateDay) {
		this.dateDay = dateDay;
	}
	
}

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
public class ReportTagIncrementTrend {
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
	private String tagName;
	/**标签新增数量
	*/
	private String tagIncrementNum;

	/**sku名称
     */
	private String skuName;
	
	/** 公司code
     */
	private String companyCode;
	/**时间-小时
     */
	 @Temporal(TemporalType.TIMESTAMP)
	 @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date dateDay;
    private String dateDayStr;
	public String getDateDayStr() {
		return dateDayStr;
	}

	public void setDateDayStr(String dateDayStr) {
		this.dateDayStr = dateDayStr;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public Date getDateDay() {
		return dateDay;
	}
	public void setDateDay(Date dateDay) {
		this.dateDay = dateDay;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagIncrementNum() {
		return tagIncrementNum;
	}

	public void setTagIncrementNum(String tagIncrementNum) {
		this.tagIncrementNum = tagIncrementNum;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("insertTime", "labelName", "channelName", "shopName",
					"skuName", "companyCode","brandName" ,"proportion");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportTagIncrementTrend().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<ReportTagIncrementTrend> findAllReportTagIncrementTrend() {
		return entityManager().createQuery("SELECT o FROM ReportTagIncrementTrend o",
				ReportTagIncrementTrend.class).getResultList();
	}


	public static List<ReportTagIncrementTrend> findAllReportTagIncrementTrends(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportTagIncrementTrend o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportTagIncrementTrend.class)
				.getResultList();
	}

	public static ReportTagIncrementTrend findReportTagIncrementTrend(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportTagIncrementTrend.class, id);
	}

	public static List<ReportTagIncrementTrend> findReportTagIncrementTrendEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportTagIncrementTrend o",
						ReportTagIncrementTrend.class).setFirstResult(firstResult)
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
			ReportTagIncrementTrend attached = ReportTagIncrementTrend.findReportTagIncrementTrend(this.id);
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
	public ReportTagIncrementTrend merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportTagIncrementTrend merged = this.entityManager.merge(this);
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

	public static ReportTagIncrementTrend fromJsonToReportTagIncrementTrend(String json) {
		return new JSONDeserializer<ReportTagIncrementTrend>().use(null,
				ReportTagIncrementTrend.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportTagIncrementTrend> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportTagIncrementTrend> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportTagIncrementTrend> fromJsonArrayToReportTagIncrementTrends(
			String json) {
		return new JSONDeserializer<List<ReportTagIncrementTrend>>().use("values",
				ReportTagIncrementTrend.class).deserialize(json);
	}
	
	public  static long findRcordCountByQuery(String channelName,String shopName,String brandName,String skuName,String dateDay,String companyCode) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT COUNT(o) FROM ReportTagIncrementTrend o where 1=1 ");
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
		if (!StringUtils.isEmpty(dateDay)) {
			qlString.append(" and o.dateDay >= '"+dateDay+" 00:00:00'");
			qlString.append(" and o.dateDay <= '"+dateDay+" 23:59:59'");
		}
		long count = entityManager().createQuery(qlString.toString(),Long.class).getSingleResult();
		return count;
	}
	
	//
	public static List<ReportTagIncrementTrend> findReportTagIncrementTrendByQuery(int firstResult,int maxResults ,
			String channelName,String shopName,String brandName,String skuName,String dateDayStart,String dateDayEnd,String companyCode
			) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o FROM ReportTagIncrementTrend o where 1=1 ");
   
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
		if (!StringUtils.isEmpty(dateDayStart)) {
			qlString.append(" and o.dateDay >= '"+dateDayStart+" 00:00:00'");
		}
		if (!StringUtils.isEmpty(dateDayEnd)) {
			qlString.append(" and o.dateDay <= '"+dateDayEnd+" 23:59:59'");
		}
		qlString.append(" order by o.dateDay asc,o.tagIncrementNum desc ");
		List<ReportTagIncrementTrend> reclist = null;
		if(maxResults > 0){
			 reclist = entityManager().createQuery(qlString.toString(),ReportTagIncrementTrend.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		}else{
			 reclist = entityManager().createQuery(qlString.toString(),ReportTagIncrementTrend.class).getResultList();

		}
		return reclist;
	}	
	public static List<Map> findReportTagIncrementTrendEntries(
			String channelName,String shopName,String brandName,String skuName,String dateDayStart,String dateDayEnd,String companyCode) {
		String sql = "SELECT * FROM report_tag_increment_trend o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName) && !"".equals(channelName)) {
			sql += " and o.channel_name=:channelName";
			map.put("channelName", channelName);
		}

		if (!StringUtils.isEmpty(shopName) && !"".equals(shopName)) {
			sql += " and o.shop_name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(brandName) && !"".equals(brandName)) {
			sql += " and o.brand_name=:brandName";
			map.put("brandName", brandName);
		}
		if (!StringUtils.isEmpty(skuName) && !"".equals(skuName)) {
			sql += " and o.sku_name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(companyCode)  && !"".equals(companyCode)) {
			sql += " and o.company_code=:companyCode";
			map.put("companyCode", companyCode);
		}
		if (!StringUtils.isEmpty(dateDayStart)  && !"".equals(dateDayStart)) {
			sql += " and o.date_day>=:dateDayStart";
			map.put("dateDayStart", dateDayStart+" 00:00:00");
		}
		if (!StringUtils.isEmpty(dateDayEnd) && !"".equals(dateDayEnd)) {
			sql += " and o.date_day<=:dateDayEnd";
			map.put("dateDayEnd", dateDayEnd+" 23:59:59");
		}
		sql += " order by o.tag_increment_num desc ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.list();
	}

}

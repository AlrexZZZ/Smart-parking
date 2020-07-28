package com.nineclient.model.vocreport;

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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.model.UmpCompany;
import com.nineclient.utils.DateUtil;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ReportCommentRatioTrendMonth {

	private String channelName;

	private String shopName;

	private String brandName;

	private String skuName;
	
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
	private Date commentTime;

	private Long goodComment;

	private Long commonComment;

	private Long negativeComment;

	private String companyCode;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Long getGoodComment() {
		return goodComment;
	}

	public void setGoodComment(Long goodComment) {
		this.goodComment = goodComment;
	}

	public Long getCommonComment() {
		return commonComment;
	}

	public void setCommonComment(Long commonComment) {
		this.commonComment = commonComment;
	}

	public Long getNegativeComment() {
		return negativeComment;
	}

	public void setNegativeComment(Long negativeComment) {
		this.negativeComment = negativeComment;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new ReportCommentRatioTrendMonth().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static ReportCommentRatioTrendMonth fromJsonToHotWordReport(String json) {
		return new JSONDeserializer<ReportCommentRatioTrendMonth>().use(null,
				ReportCommentRatioTrendMonth.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportCommentRatioTrendMonth> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportCommentRatioTrendMonth> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportCommentRatioTrendMonth> fromJsonArrayToHotWordReports(
			String json) {
		return new JSONDeserializer<List<ReportCommentRatioTrendMonth>>().use("values",
				ReportCommentRatioTrendMonth.class).deserialize(json);
	}

	public static Long countCommentRatioTrend(String channelName,
			String brandName, String skuName, String shopName, Date startTime,
			Date endTime, String companyCode) {
		String sql = "select count(o) from ReportCommentRatioTrendMonth o where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channelName=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brandName=:brandName";
			map.put("brandName", brandName);
		}

		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.skuName=:skuName";
			map.put("skuName", skuName);
		}

		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shopName=:shopName";
			map.put("shopName", shopName);
		}

		if (!StringUtils.isEmpty(companyCode)) {
			sql += " and o.companyCode=:companyCode";
			map.put("companyCode", companyCode);
		}

		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.commentTime>=:startTime";
			map.put("startTime", startTime);
		}

		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.commentTime<=:endTime";
			map.put("endTime", endTime);
		}
		TypedQuery<Long> query = entityManager().createQuery(sql,
				Long.class);
		for(String key :map.keySet()){
			query.setParameter(key, map.get(key));
		}
		return query.getSingleResult();
	}

	public static List<ReportCommentRatioTrendMonth> findCommentRatioTrendList(
			String channelName, String brandName, String skuName,
			String shopName, Date startTime,
			Date endTime,String companyCode, int firstResult,
			int size) {
		String sql = "select o from ReportCommentRatioTrendMonth o where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channelName=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brandName=:brandName";
			map.put("brandName", brandName);
		}

		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.skuName=:skuName";
			map.put("skuName", skuName);
		}

		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shopName=:shopName";
			map.put("shopName", shopName);
		}

		if (!StringUtils.isEmpty(companyCode)) {
			sql += " and o.companyCode=:companyCode";
			map.put("companyCode", companyCode);
		}

		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.commentTime>=:startTime";
			map.put("startTime", startTime);
		}

		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.commentTime<=:endTime";
			map.put("endTime", endTime);
		}
		sql +=" order by o.commentTime asc ";
		TypedQuery<ReportCommentRatioTrendMonth> query = entityManager().createQuery(sql,
				ReportCommentRatioTrendMonth.class);
		for(String key :map.keySet()){
			query.setParameter(key, map.get(key));
		}
		return query.setFirstResult(firstResult).setMaxResults(size).getResultList();
	}
	/**
	 *饼形图比例 当天评论占比
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static Map findAllCommentRatioPie(String channelName,
			String brandName, String skuName, String shopName,
			Date startTime, Date endTime, UmpCompany company,String dateType) {
		String table="";
		if(dateType.equals("1")){
			table="report_comment_ratio_trend_month";
		}
		if(dateType.equals("2")){
			table="report_comment_ratio_trend_year";
		}
		String sql = "select o.good_comment goodComment,o.common_Comment commonComment,o.negative_Comment negativeComment from "+table+" o where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_Name=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_Name=:brandName";
			map.put("brandName", brandName);
		}

		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_Name=:skuName";
			map.put("skuName", skuName);
		}

		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_Name=:shopName";
			map.put("shopName", shopName);
		}

		if (!StringUtils.isEmpty(company)) {
			sql += " and o.company_Code=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		}

//		if (!StringUtils.isEmpty(startTime)) {
//			sql += " and o.comment_Time>=:startTime";
//			map.put("startTime", startTime);
//		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_Time=:endTime";
			map.put("endTime",endTime);
		}
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		List<Map> list = query.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 折线图
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<ReportCommentRatioTrendMonth> findAllEntitys(String channelName,
			String brandName, String skuName, String shopName,
			Date startTime, Date endTime, UmpCompany company) {
		String sql = "select o from ReportCommentRatioTrendMonth o where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channelName=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brandName=:brandName";
			map.put("brandName", brandName);
		}

		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.skuName=:skuName";
			map.put("skuName", skuName);
		}

		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shopName=:shopName";
			map.put("shopName", shopName);
		}

		if (!StringUtils.isEmpty(company)) {
			sql += " and o.companyCode=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		}

		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.commentTime>=:startTime";
			map.put("startTime", startTime);
		}

		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.commentTime<=:endTime";
			map.put("endTime", endTime);
		}
		TypedQuery<ReportCommentRatioTrendMonth> query = entityManager().createQuery(sql,
				ReportCommentRatioTrendMonth.class);
		for(String key :map.keySet()){
			query.setParameter(key, map.get(key));
		}
		
		return query.getResultList();
	}
	/**
	 * 到出
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<Map> findCommentRatioTrendEntries(String channelName,
			String brandName, String skuName, String shopName,
			Date startTime, Date endTime, UmpCompany company) {
		String sql = "select o.channel_name channelName,o.brand_Name brandName,o.sku_Name skuName,o.shop_Name shopName,o.comment_Time dateTime,o.good_comment goodComment,o.common_Comment commonComment,o.negative_Comment negativeComment from report_comment_ratio_trend_month o where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_Name=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_Name=:brandName";
			map.put("brandName", brandName);
		}

		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_Name=:skuName";
			map.put("skuName", skuName);
		}

		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_Name=:shopName";
			map.put("shopName", shopName);
		}

		if (!StringUtils.isEmpty(company)) {
			sql += " and o.company_Code=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		}

		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.comment_Time>=:startTime";
			map.put("startTime", startTime);
		}

		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_Time<=:endTime";
			map.put("endTime", endTime);
		}
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		List<Map> list = query.list();
		return list;
	}
}

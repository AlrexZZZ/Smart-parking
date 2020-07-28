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
public class ReportSkuRange {

	public long getIncrementComment() {
		return incrementComment;
	}

	public void setIncrementComment(long incrementComment) {
		this.incrementComment = incrementComment;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	/**评论时间
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date commentTime;

	/**新增评论数
     */
	private long incrementComment;

	/**平台名称
     */
	private String channelName;

	/**店铺名称
     */
	private String shopName;


	/**sku名称
     */
	private String skuName;
	/** 公司code
     */
	private String companyCode;
    private String commentType;
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("commentTime", "incrementComment", "channelName", "shopName",
					"skuName", "companyCode", "commentType");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportSkuRange().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<ReportSkuRange> findAllReportSkuRange() {
		return entityManager().createQuery("SELECT o FROM ReportSkuRange o",
				ReportSkuRange.class).getResultList();
	}


	public static List<ReportSkuRange> findAllHotWordReports(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM HotWordReport o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportSkuRange.class)
				.getResultList();
	}

	public static ReportSkuRange findHotWordReport(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportSkuRange.class, id);
	}

	public static List<ReportSkuRange> findHotWordReportEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM HotWordReport o",
						ReportSkuRange.class).setFirstResult(firstResult)
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
			ReportSkuRange attached = ReportSkuRange.findHotWordReport(this.id);
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
	public ReportSkuRange merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportSkuRange merged = this.entityManager.merge(this);
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

	public Date getCommentTime() {
		return this.commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
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

	public static ReportSkuRange fromJsonToHotWordReport(String json) {
		return new JSONDeserializer<ReportSkuRange>().use(null,
				ReportSkuRange.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportSkuRange> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportSkuRange> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportSkuRange> fromJsonArrayToHotWordReports(
			String json) {
		return new JSONDeserializer<List<ReportSkuRange>>().use("values",
				ReportSkuRange.class).deserialize(json);
	}
	//条形图
	public static List<ReportSkuRange> findRangeByQuery(int firstResult, int maxResults,
			String channelName,String commentType,String commentTime,String shopName,String companyCode
			) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o FROM ReportSkuRange o where 1=1 ");
		
		if (!StringUtils.isEmpty(channelName)) {
			qlString.append(" and o.channelName = '"+channelName+"'");
			
		}
		if (!StringUtils.isEmpty(shopName)) {
			qlString.append(" and o.shopName = '"+shopName+"'");
			
		}
		if (!StringUtils.isEmpty(commentType)) {
			qlString.append(" and o.commentType = '"+commentType+"'");
			
		}
		if (!StringUtils.isEmpty(companyCode)) {
			qlString.append(" and o.companyCode = '"+companyCode+"'");
			
		}
		if (!StringUtils.isEmpty(commentTime)) {
			qlString.append(" and o.commentTime >= '"+commentTime+" 00:00:00'");
			qlString.append(" and o.commentTime <= '"+commentTime+" 23:59:59'");
		}
        System.out.println(qlString.toString());
		qlString.append(" order by o.incrementComment desc");
		List<ReportSkuRange> reclist = entityManager().createQuery(qlString.toString(),ReportSkuRange.class).setFirstResult(0).setMaxResults(10).getResultList();
		
		return reclist;
	}	
	public static List<Map> findReportSkuRangeEntries(String channelName,
			 String skuName, String shopName, String commentTime,String commentType,
			 String companyCode) {
		String sql = "SELECT o.channel_name channelName,o.shop_name shopName,o.sku_name skuName,o.comment_type commentType,o.increment_comment incrementComment,o.comment_time commentTime FROM report_sku_range o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_name=:channelName";
			map.put("channelName", channelName);
		}

		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(commentType)) {
			sql += " and o.comment_type=:comment_type";
			map.put("comment_type", commentType);
		}
		if (!StringUtils.isEmpty(companyCode)) {
			sql += " and o.company_code=:company_code";
			map.put("company_code", companyCode);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(commentTime)) {
			sql += " and o.comment_time>=:startTime";
			map.put("startTime", commentTime+" 00:00:00");
		}
		if (!StringUtils.isEmpty(commentTime)) {
			sql += " and o.comment_time<=:endTime";
			map.put("endTime", commentTime+" 23:59:59");
		}

		sql+=" order by o.increment_comment desc";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.setFirstResult(0).setMaxResults(10).list();
	}
	
}

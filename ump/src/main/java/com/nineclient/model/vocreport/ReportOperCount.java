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
import java.util.ArrayList;
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
public class ReportOperCount {

	/**时间
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;
/*
 * 坐席名称
 */
  private String operatorName;

	/**平台名称
     */
	private String channelName;

	/**店铺名称
     */
	private String shopName;


	/** 公司code
     */
	private String companyCode;
	
	/**回复数
     */
	private int replyNum;
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("insertTime", "replyNum", "channelName", "shopName",
					"operatorName", "companyCode");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportOperCount().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<ReportOperCount> findAllReportOperCount() {
		return entityManager().createQuery("SELECT o FROM ReportOperCount o",
				ReportOperCount.class).getResultList();
	}


	public static List<ReportOperCount> findAllReportOperCounts(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportOperCount o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportOperCount.class)
				.getResultList();
	}

	public static ReportOperCount findReportOperCount(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportOperCount.class, id);
	}

	public static List<ReportOperCount> findReportOperCountEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportOperCount o",
						ReportOperCount.class).setFirstResult(firstResult)
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
			ReportOperCount attached = ReportOperCount.findReportOperCount(this.id);
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
	public ReportOperCount merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportOperCount merged = this.entityManager.merge(this);
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


	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static ReportOperCount fromJsonToReportOperCount(String json) {
		return new JSONDeserializer<ReportOperCount>().use(null,
				ReportOperCount.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportOperCount> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportOperCount> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportOperCount> fromJsonArrayToReportOperCounts(
			String json) {
		return new JSONDeserializer<List<ReportOperCount>>().use("values",
				ReportOperCount.class).deserialize(json);
	}
	//按条件查询 返回list结果集
	public static List<ReportOperCount> findRangeByQuery(int firstResult, int maxResults,
			String startTime,String operatorName,String endTime,String companyCode
			) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT SUM(o.replyNum),o.operatorName FROM ReportOperCount o where 1=1 ");
		
		if (!StringUtils.isEmpty(operatorName)) {
			qlString.append(" and o.operatorName = '"+operatorName+"'");
			
		}
		if (!StringUtils.isEmpty(companyCode)) {
			qlString.append(" and o.companyCode = '"+companyCode+"'");
			
		}
		if (!StringUtils.isEmpty(startTime)) {
			qlString.append(" and o.insertTime >= '"+startTime+" 00:00:00'");
		}
		if (!StringUtils.isEmpty(endTime)) {
			qlString.append(" and o.insertTime <= '"+endTime+" 23:59:59'");
		}
       
        qlString.append(" group by  o.operatorName ");
		qlString.append(" order by  SUM(o.replyNum) desc ");
		List<Object[]> reclist = entityManager().createQuery(qlString.toString()).getResultList();
		List<ReportOperCount> oplist = new ArrayList<ReportOperCount>();
		for(Object[] a :reclist){
			ReportOperCount op = new ReportOperCount();
			int lo = Integer.parseInt(a[0]+"");
				op.setReplyNum(lo);
			    op.setOperatorName((String) a[1]);
			    oplist.add(op);
		}
		return oplist;
	}	
	public static List<Map> findSkuRangeReportEntries(String operatorName,
			  String startTime,String endTime,
			 String companyCode) {
		String sql = "SELECT sum(o.reply_num) as replyNum,o.operator_name operatorName FROM report_oper_count o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(operatorName)) {
			sql += " and o.operator_name=:operatorName";
			map.put("operatorName", operatorName);
		}
	
		if (!StringUtils.isEmpty(companyCode)) {
			sql += " and o.company_code=:companyCode";
			map.put("companyCode", companyCode);
		}

		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.insert_time>=:startTime";
			map.put("startTime", startTime+" 00:00:00");
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.insert_time<=:endTime";
			map.put("endTime", endTime+" 23:59:59");
		}
		sql+=" group by o.operator_name desc";
		sql+=" order by sum(o.reply_num) desc";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.list();
	}
	
}

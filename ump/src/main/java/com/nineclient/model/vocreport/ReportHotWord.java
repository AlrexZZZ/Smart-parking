package com.nineclient.model.vocreport;

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

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ReportHotWord {

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date commentTime;

	/**
     */
	private String hotWordName;

	/**
     */
	private long increment;

	/**
     */
	private String channelName;

	/**
     */
	private String shopName;

	/**
     */
	private String brandName;

	/**
     */
	private String skuName;

	private String companyCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("commentTime", "hotWordName", "increment", "channelName",
					"shopName", "brandName", "skuName");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportHotWord().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	/**
	 * 评论总数
	 * 
	 * @return
	 */
	public static long sumReportHotWords(String channelName, String brandName,
			String skuName, String shopName, Date startTime, Date endTime,
			UmpCompany company) {

		String sql = "SELECT  o.increment  increment FROM report_hot_word o where 1=1 ";
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
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.comment_Time>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_Time<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.company_Code='"+company.getCompanyCode()+"'";
			//map.put("companyCode", company.getCompanyCode());
		}
		sql+=" order by o.comment_Time,o.increment desc limit 0,10";
		String newSql="select sum(a.increment) increment from ("+sql+") a";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(newSql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		List<Map> list =  query.list();
		Long sum=0l;
		if(list!=null&&list.size()>0){
			sum=Long.parseLong(String.valueOf(list.get(0).get("increment")==null?0:list.get(0).get("increment")));
		}
		
		return sum;

	}

	public static long countReportHotWords(String channelName,
			String brandName, String skuName, String shopName, Date startTime,
			Date endTime, UmpCompany company) {
		String sql = "SELECT count(o) FROM ReportHotWord o where 1=1 ";
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
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.commentTime>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.commentTime<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.companyCode=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		}
		sql+=" order by commentTime,increment desc";
		TypedQuery<Long> query = entityManager().createQuery(sql, Long.class);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}

		return query.getSingleResult();
	}

	public static List<ReportHotWord> findAllReportHotWords() {
		return entityManager().createQuery("SELECT o FROM ReportHotWord o",
				ReportHotWord.class).getResultList();
	}

	/**
	 * 饼形图
	 * @return
	 */
	public static List<Map> findAllReportHotWords(String channelName,
			String brandName, String skuName, String shopName, Date startTime,
			Date endTime, UmpCompany company) {
		String sql = "SELECT o.hot_Word_Name hotWordName,sum(o.increment) increment FROM report_hot_word o where 1=1 ";
//		String groupSql =" group by ";
//		String hotWordName=" o.hot_Word_Name ";
//		String orderBy =" order by increment desc limit 10";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_Name=:channelName";
			map.put("channelName", channelName);
//			groupSql+=" o.channel_Name,";
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_Name=:brandName";
			map.put("brandName", brandName);
//			groupSql+=" o.brand_Name,";
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_Name=:skuName";
			map.put("skuName", skuName);
//			groupSql+=" o.sku_Name,";
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_Name=:shopName";
			map.put("shopName", shopName);
//			groupSql+=" o.shop_Name,";
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.comment_Time>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_Time<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.company_Code=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		}
		sql += " group by o.hot_Word_Name order by increment desc ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult(0).setMaxResults(10);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.list();
	}

	public static List<ReportHotWord> findAllReportHotWords(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportHotWord o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportHotWord.class)
				.getResultList();
	}

	public static ReportHotWord findReportHotWord(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportHotWord.class, id);
	}

	public static List<ReportHotWord> findReportHotWordEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportHotWord o",
						ReportHotWord.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}
	/**
	 * 查询前十条数据
	 * @param firstResult
	 * @param maxResults
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<ReportHotWord> findReportHotWordEntries(int firstResult,
			int maxResults, String channelName, String brandName,
			String skuName, String shopName, Date startTime, Date endTime,
			UmpCompany company) {
		String sql = "SELECT o.hotWordName ,sum(o.increment)  FROM ReportHotWord o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
//			sql += " and o.channelName=:channelName";
//			map.put("channelName", channelName);
			sql += " and o.channelName =  '"+channelName+"' ";
		}
		if (!StringUtils.isEmpty(brandName)) {
//			sql += " and o.brandName=:brandName";
//			map.put("brandName", brandName);
			sql += " and o.brandName= '"+brandName+"' ";
		}
		if (!StringUtils.isEmpty(skuName)) {
//			sql += " and o.skuName=:skuName";
//			map.put("skuName", skuName);
			sql += " and o.skuName= '"+skuName+"' ";
		}
		if (!StringUtils.isEmpty(shopName)) {
//			sql += " and o.shopName=:shopName";
//			map.put("shopName", shopName);
			sql += " and o.shopName= '"+shopName+"' ";
		}
		if (!StringUtils.isEmpty(startTime)) {
//			sql += " and o.commentTime>=:startTime";
//			map.put("startTime", startTime);
			
			sql += " and o.commentTime>= '"+DateUtil.formateDateToString(startTime, "yyyy-MM-dd HH:mm:ss")+"' ";
		}
		if (!StringUtils.isEmpty(endTime)) {
//			sql += " and o.commentTime<=:endTime";
//			map.put("endTime", endTime);
			
			sql += " and o.commentTime<= '"+DateUtil.formateDateToString(endTime, "yyyy-MM-dd HH:mm:ss")+"' ";;
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
//			sql += " and o.companyCode=:companyCode";
//			map.put("companyCode", company.getCompanyCode());
			sql += " and o.companyCode= '"+company.getCompanyCode()+"' ";
		} 
//		sql+=" order by commentTime,increment desc";
		sql+=" group by  o.hotWordName     order by sum(o.increment) desc";
//		TypedQuery<ReportHotWord> query = entityManager().createQuery(sql,ReportHotWord.class);
		List<ReportHotWord> li = new ArrayList<ReportHotWord>();
		@SuppressWarnings("unchecked")
		List<Object[]> objList  = entityManager().createQuery(sql).setFirstResult(0).setMaxResults(10).getResultList();
    if(objList.size() > 0){
		for (Object[] e:objList) {
			ReportHotWord  re = new ReportHotWord();
			re.setHotWordName(e[0]+"");
			re.setIncrement(Long.parseLong((e[1]+"")));
			li.add(re);
		}
		
	}
    return li;
	}
	/**
	 * 导出
	 * 
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<Map> findReportHotWordEntries(String channelName,
			String brandName, String skuName, String shopName, Date startTime,
			Date endTime, UmpCompany company) {
		String sql = "SELECT o.channel_name channelName,o.shop_name shopName,o.brand_name brandName,o.sku_name skuName,o.hot_word_name hotWordName,sum(o.increment) increment  FROM report_hot_word o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_name=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_name=:brandName";
			map.put("brandName", brandName);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.comment_time>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_time<=:endTime";
			map.put("endTime", endTime);
		}
		if (!StringUtils.isEmpty(company.getCompanyCode())) {
			sql += " and o.company_code=:companyCode";
			map.put("companyCode", company.getCompanyCode());
		}
		sql += " group by o.channel_name,o.shop_name,o.brand_name,o.sku_name,o.hot_word_name ";
		sql+=" order by o.increment desc";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult(0).setMaxResults(10);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.list();
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
			ReportHotWord attached = ReportHotWord.findReportHotWord(this.id);
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
	public ReportHotWord merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportHotWord merged = this.entityManager.merge(this);
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

	public String getHotWordName() {
		return this.hotWordName;
	}

	public void setHotWordName(String hotWordName) {
		this.hotWordName = hotWordName;
	}

	public long getIncrement() {
		return this.increment;
	}

	public void setIncrement(long increment) {
		this.increment = increment;
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

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public static ReportHotWord fromJsonToReportHotWord(String json) {
		return new JSONDeserializer<ReportHotWord>().use(null,
				ReportHotWord.class).deserialize(json);
	}

	public static String toJsonArray(Collection<ReportHotWord> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<ReportHotWord> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<ReportHotWord> fromJsonArrayToReportHotWords(
			String json) {
		return new JSONDeserializer<List<ReportHotWord>>().use("values",
				ReportHotWord.class).deserialize(json);
	}
}

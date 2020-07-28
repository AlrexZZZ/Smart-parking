package com.nineclient.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccContent implements Serializable {

	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 微内容标题
	 */
	@NotNull
	@Size(min = 1, max = 200)
	private String title;

	/**
	 * 微内容创建者
	 */
	@NotNull
	@Size(min = 1, max = 32)
	private String userName;

	/**
	 * 是否点赞
	 */
	private Boolean isPrizeCommit;

	/**
	 * 审核状态
	 */
	private int isCheck;

	/**
	 * 微内容链接
	 */
	@Size(max = 4000)
	private String contentUrl;

	/**
	 * 微内容内容
	 */
	@NotNull
	@Lob
	private String content;

	/**
	 * 公司Id
	 */
	private Long companyId;

	/**
	 * 点赞数
	 */
	@NotNull
	@Value("0")
	private int praiseCount;

	/**
	 * 评论数
	 */
	@NotNull
	@Value("0")
	private int commentCount;

	/**
	 * 点击数
	 */
	@NotNull
	@Value("0")
	private int clickCount;

	/**
	 * 微内容创建时间
	 */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * 版本
	 */
	@Version
	@Column(name = "version")
	private Integer version;

	/**
	 * 是否可见
	 */
	private Boolean isVisible;

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("isDeleted", "title", "userName", "isPrizeCommit",
					"isCheck", "contentUrl", "content", "praiseCount",
					"clickCount", "insertTime");

	public static final EntityManager entityManager() {
		EntityManager em = new WccContent().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	/**
	 * 获取该公司所有的微内容
	 * 
	 * @param pub
	 * @return
	 */
	public static long countWccContents(PubOperator pub) {
		StringBuilder jpaQuery = new StringBuilder();
		jpaQuery.append(
				"SELECT COUNT(o) FROM WccContent o where o.companyId = ")
				.append(pub.getCompany().getId());
		return entityManager().createQuery(jpaQuery.toString(), Long.class)
				.getSingleResult();
	}

	/**
	 * 获取所有未删除的微内容
	 * 
	 * @return
	 */
	public static List<WccContent> findAllWccContents(Long companyId,
			Integer limit) {
		TypedQuery<WccContent> query = entityManager()
				.createQuery(
						" FROM WccContent o where o.isDeleted = 0 and o.companyId=:companyId ",
						WccContent.class);
		query.setParameter("companyId", companyId);
		return query.getResultList();
	}

	/**
	 * 根据公司获取所有内容
	 * 
	 * @param sortFieldName
	 * @param sortOrder
	 * @param pubOper
	 * @return
	 */
	public static List<WccContent> findAllWccContents(String sortFieldName,
			String sortOrder, PubOperator pubOper) {
		StringBuilder jpaQuery = new StringBuilder();
		jpaQuery.append(
				"SELECT o FROM WccContent o where 1=1 and o.companyId = ")
				.append(pubOper.getCompany().getId())
				.append(" ORDER BY o.insertTime desc");
		return entityManager().createQuery(jpaQuery.toString(),
				WccContent.class).getResultList();
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public static WccContent findWccContent(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccContent.class, id);
	}

	public static List<WccContent> findWccContentEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM WccContent o", WccContent.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<WccContent> findWccContentEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder,
			PubOperator pubOper) {
		StringBuilder jpaQuery = new StringBuilder();
		jpaQuery.append(
				"SELECT o FROM WccContent o where 1=1 and o.companyId = ")
				.append(pubOper.getCompany().getId())
				.append(" ORDER BY o.insertTime desc");
		return entityManager()
				.createQuery(jpaQuery.toString(), WccContent.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	/**
	 * 根据id删除
	 */
	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			WccContent attached = WccContent.findWccContent(this.id);
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

	/**
	 * 根据id修改
	 * 
	 * @return
	 */
	@Transactional
	public WccContent merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccContent merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static List<WccContent> findAllUmpLogsByFiled(WccContent wccContent,
			String startTime, String endTime) {
		String jpaQuery = "SELECT o FROM WccContent o where 1=1 ";
		if (null != wccContent.getTitle() && !"".equals(wccContent.getTitle())) {
			jpaQuery = jpaQuery + "and o.title like '%" + wccContent.getTitle()
					+ "%'";
		}
		if (wccContent.getIsCheck() >= 0) {
			jpaQuery = jpaQuery + "and o.isCheck =  " + wccContent.getIsCheck();
		}
		if (null != wccContent.getIsVisible()
				&& !"".equals(wccContent.getIsVisible())) {
			jpaQuery = jpaQuery + "and o.isVisible =  "
					+ wccContent.getIsCheck();
		}
		if (null != startTime && !"".equals(startTime)) {
			jpaQuery = jpaQuery + "and o.insertTime >=  " + startTime;
		}
		if (null != endTime && !"".equals(endTime)) {
			jpaQuery = jpaQuery + "and o.insertTime <=  " + endTime;
		}
		jpaQuery = jpaQuery + " ORDER BY o.insertTime DESC";

		return entityManager().createQuery(jpaQuery, WccContent.class)
				.getResultList();
	}

	public static List<WccContent> findWccContentByTitle(String title,
			PubOperator pubOper) {
		if (null == title || "".equals(title)) {
			return null;
		}
		if (null == pubOper)
			return null;

		TypedQuery<WccContent> query = entityManager()
				.createQuery(
						" FROM WccContent o where o.title=:title and o.companyId =:companyId and o.isDeleted = 0 ",
						WccContent.class);
		query.setParameter("title", title);
		query.setParameter("companyId", pubOper.getCompany().getId());
		return query.getResultList();
	}

	public static Long countWccContentsByFiled(Boolean isPrizeCommit,
			Integer isCheck2, String title2, String startTime, String endTime,
			Long companyId) {
		String jpaQuery = "SELECT COUNT(o) FROM WccContent o where 1=1 and o.isDeleted = 0 and o.companyId="
				+ companyId;
		if (null != title2 && !"".equals(title2)) {
			jpaQuery = jpaQuery + " and o.title like '%" + title2 + "%'";
		}
		if (!"".equals(isPrizeCommit) && null != isPrizeCommit) {
			jpaQuery = jpaQuery + " and o.isPrizeCommit = " + isPrizeCommit;
		}
		if (null != startTime && !"".equals(startTime)) {
			jpaQuery = jpaQuery + " and o.insertTime >= '" + startTime + "'";
		}
		if (null != endTime && !"".equals(endTime)) {
			jpaQuery = jpaQuery + " and o.insertTime <='" + endTime + "'";
		}
		if (null != isCheck2 && !"".equals(isCheck2)) {
			jpaQuery = jpaQuery + " and o.isCheck = " + isCheck2;
		}
		return entityManager().createQuery(jpaQuery.toString(), Long.class)
				.getSingleResult();
	}

	public static List<WccContent> findWccContentEntriesByFiled(
			int firstResult, int maxResults, Boolean isPrizeCommit,
			Integer isCheck2, String startTime, String endTime, String title2,
			Long companyId) {
		String jpaQuery = "SELECT o FROM WccContent o where 1=1 and o.companyId ="
				+ companyId;
		if (null != title2 && !"".equals(title2)) {
			jpaQuery = jpaQuery + " and o.title like '%" + title2 + "%'";
		}
		if (!"".equals(isPrizeCommit) && null != isPrizeCommit) {
			jpaQuery = jpaQuery + " and o.isPrizeCommit = " + isPrizeCommit;
		}
		if (null != startTime && !"".equals(startTime)) {
			jpaQuery = jpaQuery + " and o.insertTime >= '" + startTime + "'";
		}
		if (null != endTime && !"".equals(endTime)) {
			jpaQuery = jpaQuery + " and o.insertTime <='" + endTime + "'";
		}
		if (null != isCheck2 && !"".equals(isCheck2)) {
			jpaQuery = jpaQuery + " and o.isCheck = " + isCheck2;
		}
		jpaQuery = jpaQuery + " and o.isDeleted = 0 ORDER BY o.insertTime desc";
		System.out.println(jpaQuery);

		return entityManager().createQuery(jpaQuery, WccContent.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Long findWccContentByPubOer(PubOperator pub) {
		StringBuilder jpaQuery = new StringBuilder();
		jpaQuery.append(
				"SELECT min(o.id) FROM WccContent o where o.companyId = ")
				.append(pub.getCompany().getId())
				.append(" and o.commentCount > 0");
		return entityManager().createQuery(jpaQuery.toString(), Long.class)
				.getSingleResult();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

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

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsPrizeCommit() {
		return this.isPrizeCommit;
	}

	public void setIsPrizeCommit(Boolean isPrizeCommit) {
		this.isPrizeCommit = isPrizeCommit;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public String getContentUrl() {
		return this.contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPraiseCount() {
		return this.praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public int getClickCount() {
		return this.clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Boolean getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public static String toJsonArray(Collection<WccContent> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<WccContent> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<WccContent> fromJsonArrayToVocWordExpressionss(
			String json) {
		return new JSONDeserializer<List<WccContent>>().use("values",
				WccContent.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}
}

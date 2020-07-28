package com.nineclient.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.WccTemplateType;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccTemplate {

	/**
     */
	@Enumerated
	private WccTemplateType type;

	/**
     */
	@ManyToOne
	private PubOperator name;

	/**
     */
	@Size(max = 32)
	private String parentId;

	/**
     */
	@Size(max = 300)
	private String title;

	/**
     */
	@Size(max = 4000)
	private String content;

	/**
     */
	private int useCount;

	/**
     */
	@Size(max = 40)
	private String sort;

	/**
     */
	private Boolean isDeleted;

	/**
     */
	private Boolean isVisable;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("type", "name", "parentId", "title", "content", "useCount",
					"sort", "isDeleted", "isVisable", "insertTime");

	public static final EntityManager entityManager() {
		EntityManager em = new WccTemplate().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countWccTemplates() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM WccTemplate o", Long.class)
				.getSingleResult();
	}

	public static long countWccTemplatesByPid(String pid) {
		return entityManager().createQuery(
				"SELECT  COUNT(o) FROM WccTemplate o WHERE o.parentId =" + pid,
				Long.class).getSingleResult();
	}

	public static List<WccTemplate> findAllWccTemplates() {
		return entityManager().createQuery("SELECT o FROM WccTemplate o",
				WccTemplate.class).getResultList();
	}

	public static List<WccTemplate> findWccTemplatesByPidAndTitle(long pid,
			String title) {
		return entityManager().createQuery(
				"FROM WccTemplate WHERE parentId = " + pid + " AND title = '"
						+ title + "'", WccTemplate.class).getResultList();
	}
	
	public static List<WccTemplate> findWccTemplatesByPidAndTitle(long id,long pid,
			String title) {
		return entityManager().createQuery(
				"FROM WccTemplate WHERE parentId = " + pid + " AND title = '"
						+ title + "' AND id <>"+id, WccTemplate.class).getResultList();
	}

	public static List<WccTemplate> findWccTemplatesByTypeAndPlat(
			Long companyId, List<WccPlatformUser> platList) {
		StringBuffer sql = new StringBuffer(
				"SELECT o FROM WccTemplate o WHERE o.type = 0");
		sql.append(" AND o.platformUser.company = " + companyId + " AND");
		String hql = "";
		if (platList.size() > 0) {
			sql.append("( ");
			for (WccPlatformUser plat : platList) {
				sql.append(" o.platformUser = " + plat.getId() + " OR");
			}
			hql = sql.toString().substring(0, sql.length() - 2);
			hql += ") ";
		} else {
			hql = sql.toString().substring(0, sql.length() - 3);
		}
		hql += " ORDER BY insertTime DESC";
		return entityManager().createQuery(hql, WccTemplate.class)
				.getResultList();
	}

	public static List<WccTemplate> findWccTemplates(Long companyId, List<WccPlatformUser> platList) {
		StringBuffer sql = new StringBuffer(
				"SELECT o FROM WccTemplate o WHERE 1=1");
		sql.append(" AND o.platformUser.company = " + companyId + " AND");
		String hql = "";
		if (platList.size() > 0) {
			sql.append("( ");
			for (WccPlatformUser plat : platList) {
				sql.append(" o.platformUser = " + plat.getId() + " OR");
			}
			hql = sql.toString().substring(0, sql.length() - 2);
			hql += ") ";
		} else {
			hql = sql.toString().substring(0, sql.length() - 3);
		}
		hql += " ORDER BY insertTime DESC";
		return entityManager().createQuery(hql, WccTemplate.class)
				.getResultList();
	}

	public static List<WccTemplate> findWccTemplatesByPid(String pid) {
		return entityManager().createQuery(
				"SELECT o FROM WccTemplate o WHERE o.parentId =" + pid,
				WccTemplate.class).getResultList();
	}

	public static List<WccTemplate> findAllWccTemplates(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM WccTemplate o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccTemplate.class)
				.getResultList();
	}

	public static WccTemplate findWccTemplate(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccTemplate.class, id);
	}

	public static List<WccTemplate> findWccTemplateEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM WccTemplate o", WccTemplate.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<WccTemplate> findWccTemplateEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM WccTemplate o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccTemplate.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<WccTemplate> findWccTemplateEntriesByPid(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder, String pid) {
		String jpaQuery = "SELECT o FROM WccTemplate o WHERE o.parentId ="
				+ pid;
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccTemplate.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
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
			WccTemplate attached = WccTemplate.findWccTemplate(this.id);
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
	public WccTemplate merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccTemplate merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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

	public WccTemplateType getType() {
		return this.type;
	}

	public void setType(WccTemplateType type) {
		this.type = type;
	}

	public PubOperator getName() {
		return name;
	}

	public void setName(PubOperator name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUseCount() {
		return this.useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsVisable() {
		return this.isVisable;
	}

	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public static PageModel<WccTemplate> getQueryTemplate(
			QueryModel<WccTemplate> qm, String pid) {
		PageModel<WccTemplate> pageModel = new PageModel<WccTemplate>();
		StringBuffer sql = new StringBuffer(
				"SELECT o FROM WccTemplate o WHERE o.parentId =" + pid);
		sql.append(" ORDER BY o.type ASC,sort ASC, o.insertTime DESC");
		Map<String, Object> map = new HashMap<String, Object>();

		TypedQuery<WccTemplate> query = entityManager().createQuery(
				sql.toString(), WccTemplate.class);

		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}

		int totalCount = 0;
		{ // 查总数
			TypedQuery<Long> countQuery = entityManager().createQuery(
					sql.toString().replaceAll("SELECT o", "SELECT count(o)"),
					Long.class);
			for (String key : map.keySet()) {
				countQuery.setParameter(key, map.get(key));
			}
			totalCount = countQuery.getSingleResult().intValue();
		}

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());

		return pageModel;

	}

	public static PageModel<WccTemplate> getQueryTemplate(
			QueryModel<WccTemplate> qm) {

		PageModel<WccTemplate> pageModel = new PageModel<WccTemplate>();
		StringBuffer sql = new StringBuffer("SELECT o FROM WccTemplate o");
		Map<String, Object> map = new HashMap<String, Object>();

		TypedQuery<WccTemplate> query = entityManager().createQuery(
				sql.toString(), WccTemplate.class);

		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}

		int totalCount = 0;
		{ // 查总数
			TypedQuery<Long> countQuery = entityManager().createQuery(
					sql.toString().replaceAll("SELECT o", "SELECT count(o)"),
					Long.class);
			for (String key : map.keySet()) {
				countQuery.setParameter(key, map.get(key));
			}
			totalCount = countQuery.getSingleResult().intValue();
		}

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());

		return pageModel;

	}

	@ManyToOne
	private WccPlatformUser platformUser;

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public static List<WccTemplate> findWccTemplateByPidAndPlatform(Long platId) {
		String sql = "SELECT o FROM WccTemplate o WHERE o.parentId = 0 AND o.platformUser = "
				+ platId + " ORDER BY insertTime DESC";
		return entityManager().createQuery(sql, WccTemplate.class)
				.getResultList();
	}

	public static List<WccTemplate> findWccTemplateByPlatform(Long platformId) {
		String sql = "SELECT o FROM WccTemplate o WHERE o.platformUser = "
				+ platformId + " ORDER BY o.type ASC, o.insertTime DESC";
		return entityManager().createQuery(sql, WccTemplate.class)
				.getResultList();
	}

}

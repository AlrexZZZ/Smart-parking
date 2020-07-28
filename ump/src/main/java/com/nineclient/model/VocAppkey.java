package com.nineclient.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocAppkey {

	/**
     */
	@Size(max = 32)
	private String appkey;

	/**
     */
	@Size(max = 255)
	private String clientSecret;
	@Size(max = 255)
	private String clientId;
	/**
     */
	@Size(max = 50)
	private String name;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	/**
     */
	@Size(max = 255)
	private String redirectUrl;

	/**
     */
	private long sort;

	/**
     */
	private Boolean isVisable;

	/**
     */
	private Boolean isDeleted;

	/**
     */
	@Size(max = 4000)
	private String remark;

	private Long companyId;
	@OneToMany(mappedBy = "vocAppKey")
	private Set<VocAccount> vocAccounts;

	public Set<VocAccount> getVocAccounts() {
		return vocAccounts;
	}

	public void setVocAccounts(Set<VocAccount> vocAccounts) {
		this.vocAccounts = vocAccounts;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("appkey", "clientSecret", "name", "createTime",
					"redirectUrl", "sort", "isVisable", "isDeleted", "remark");

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public static final EntityManager entityManager() {
		EntityManager em = new VocAppkey().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countVocAppkeys(VocAppkey appKey) {
		String sql = " SELECT COUNT(o) FROM VocAppkey o where o.companyId="
				+ appKey.getCompanyId();
		return entityManager().createQuery(sql, Long.class).getSingleResult();
	}

	/**
	 * 校验唯一性
	 * 
	 * @param appKey
	 * @return
	 */
	public static List<VocAppkey> findAllVocAppkeys(VocAppkey appKey) {
		String sql = "SELECT o FROM VocAppkey o where  o.companyId="
				+ appKey.getCompanyId();
		if (appKey.getName() != null && !StringUtils.isEmpty(appKey.getName())) {
			sql += " and o.name='" + appKey.getName() + "'";
		}
		if (!StringUtils.isEmpty(appKey.getId())) {
			sql += " and o.id!=" + appKey.getId();
		}
		return entityManager().createQuery(sql, VocAppkey.class)
				.getResultList();
	}

	/**
	 * 根据公司查询appkey
	 * 
	 * @param appKey
	 * @return
	 */
	public static List<VocAppkey> findAllVocAppkeysByCompany(UmpCompany company) {
		String sql = "SELECT o FROM VocAppkey o";
		return entityManager().createQuery(sql, VocAppkey.class)
				.getResultList();
	}

	public static List<VocAppkey> findAllVocAppkeys(VocAppkey appKey,
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM VocAppkey o where  o.companyId="
				+ appKey.getCompanyId();
		jpaQuery += " order by o.createTime desc ";
		return entityManager().createQuery(jpaQuery, VocAppkey.class)
				.getResultList();
	}

	public static VocAppkey findVocAppkey(Long id) {
		if (id == null)
			return null;
		return entityManager().find(VocAppkey.class, id);
	}

	public static List<VocAppkey> findVocAppkeyEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM VocAppkey o", VocAppkey.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<VocAppkey> findVocAppkeyEntries(VocAppkey appKey,
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM VocAppkey o where o.companyId="
				+ appKey.getCompanyId();
		jpaQuery += " order by o.createTime desc ";
		return entityManager().createQuery(jpaQuery, VocAppkey.class)
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
			VocAppkey attached = VocAppkey.findVocAppkey(this.id);
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
	public VocAppkey merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		VocAppkey merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String getAppkey() {
		return this.appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public long getSort() {
		return this.sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public Boolean getIsVisable() {
		return this.isVisable;
	}

	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public static String toJsonArray(Collection<VocAppkey> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<VocAppkey> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<VocAppkey> fromJsonArrayToVocAppkeys(String json) {
		return new JSONDeserializer<List<VocAppkey>>().use("values",
				VocAppkey.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static String toJsonArrayNameAndId(Collection<VocAppkey> collection) {
		String json = "[";
		boolean flag = true;
		for (VocAppkey vocAppkey : collection) {
			if (flag) {
				flag = false;
				json += "{id:" + vocAppkey.getId() + ",name:\'"
						+ vocAppkey.getName() + "\'}";
			} else {
				json += ",{id:" + vocAppkey.getId() + ",name:\'"
						+ vocAppkey.getName() + "\'}";
			}
		}
		json += "]";
		return json;
	}
}

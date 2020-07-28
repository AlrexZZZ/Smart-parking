package com.nineclient.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PubOrganization implements java.io.Serializable {
	/**
	 * 父id
	 */
	@NotNull
	private long parentId;

	/**
	 * 组织名称
	 */
	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	/**
	 * 排序
	 */
	@NotNull
	@Value("0")
	private int sort;

	/**
	 * 是否可见
	 */
	private Boolean isVisable;

	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 备注
	 */
	@Size(max = 4000)
	private String remark;

	/**
	 * 公司id
	 */
	private Long companyId;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;

	/**
	 * 关联账号
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<PubOperator> pubOpers = new HashSet<PubOperator>();

	/**
	 * 关联门店
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
	private Set<WccStore> wccStores = new HashSet<WccStore>();

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
	/*
	 * public String toString() { return
	 * ReflectionToStringBuilder.toString(this,
	 * ToStringStyle.SHORT_PREFIX_STYLE); }
	 */

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("parentId", "name", "sort", "isVisable", "isDeleted",
					"remark", "insertTime", "comments");

	public static final EntityManager entityManager() {
		EntityManager em = new PubOrganization().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countPubOrganizations() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM PubOrganization o", Long.class)
				.getSingleResult();
	}

	public static List<PubOrganization> findAllPubOrganizations(Long companyId) {
		return entityManager().createQuery(
				"SELECT o FROM PubOrganization o where 1=1 and o.companyId = "
						+ companyId, PubOrganization.class).getResultList();
	}

	public static List<PubOrganization> findAllPubOrganizations(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM PubOrganization o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, PubOrganization.class)
				.getResultList();
	}

	public static PubOrganization findPubOrganization(Long id) {
		if (id == null)
			return null;
		return entityManager().find(PubOrganization.class, id);
	}

	public static List<PubOrganization> findPubOrganizationEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM PubOrganization o",
						PubOrganization.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<PubOrganization> findPubOrganizationEntries(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM PubOrganization o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, PubOrganization.class)
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
			PubOrganization attached = PubOrganization
					.findPubOrganization(this.id);
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
	public PubOrganization merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PubOrganization merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static List<PubOrganization> findAllPubOrganizationsByName(
			String name, PubOperator pub) {
		if (name == null || "".equals(name))
			return null;
		return entityManager().createQuery(
				"SELECT o FROM PubOrganization o where o.name ='" + name
						+ "' and o.companyId=" + pub.getCompany().getId(),
				PubOrganization.class).getResultList();
	}

	public static List<PubOrganization> findAllPubOrganizationsById(long id,
			boolean type) {
		if (id == 0)
			return null;
		if (type == true) {
			return entityManager().createQuery(
					"SELECT o FROM PubOrganization o where o.id ='" + id + "'",
					PubOrganization.class).getResultList();
		} else {
			return entityManager().createQuery(
					"SELECT o FROM PubOrganization o where o.parentId ='" + id
							+ "'", PubOrganization.class).getResultList();
		}
	}

	public static PubOrganization findPubOrganizationByName(String name,
			PubOperator pub) {
		TypedQuery<PubOrganization> query = entityManager()
				.createQuery(
						"FROM PubOrganization o where o.name =:name  and o.companyId=:companyId ",
						PubOrganization.class);
		query.setParameter("name", name);
		query.setParameter("companyId", pub.getCompany().getId());
		return query.getSingleResult();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Set<WccStore> getWccStores() {
		return wccStores;
	}

	public void setWccStores(Set<WccStore> wccStores) {
		this.wccStores = wccStores;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
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

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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

	public Set<PubOperator> getPubOpers() {
		return pubOpers;
	}

	public void setPubOpers(Set<PubOperator> pubOpers) {
		this.pubOpers = pubOpers;
	}
	
	//查询当前登录用户的组织架构及子组织架构
	public static List<PubOrganization> getAllOrganizationByOperator(PubOperator pubOpers){
		List<PubOrganization> orglist=new ArrayList<PubOrganization>();
		//获取当前登录用户所在组织
		PubOrganization parentorg=pubOpers.getOrganization();
		if(parentorg!=null){
		    orglist.add(parentorg);
		//查询当前组织下的子组织
			String sql="select o from PubOrganization o where o.parentId="+parentorg.getId();
			List<PubOrganization> chirdorgList=entityManager().createQuery(sql,PubOrganization.class).getResultList();		
				if(chirdorgList!=null){
				  for (PubOrganization pubOrganization : chirdorgList) {
					  orglist.add(pubOrganization);
				}
			}
		}
		return orglist;
	}

}

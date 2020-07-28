package com.nineclient.model;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpParentBusinessType implements java.io.Serializable {
	  /**
     * 平台品牌
     */
    @OneToMany(mappedBy="business")
    private Set<UmpBrand> brands = new HashSet<UmpBrand>();
    /**
     * voc 品牌
     */
    @OneToMany(mappedBy = "umpParentBusinessType")
    private Set<VocBrand> vocBrands = new HashSet<VocBrand>();
    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(max = 30)
    private String businessName;

    /**
     */
    @ManyToMany(mappedBy="parentBusinessType")
    private Set<UmpCompany> company = new HashSet<UmpCompany>();
    
    public Set<UmpCompany> getCompany() {
		return company;
	}

	public void setCompany(Set<UmpCompany> company) {
		this.company = company;
	}

	/**
     */
    private int sort;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isVisable;

    /**
     */
    private Boolean isDeleted;
    
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;
    @OneToMany(mappedBy="parentBusinessType")
    private Set<UmpBusinessType> subBusinesses=new HashSet<UmpBusinessType>();
	public String getBusinessName() {
        return this.businessName;
    }

	public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

	public int getSort() {
        return this.sort;
    }

	public void setSort(int sort) {
        this.sort = sort;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
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

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("businessName", "sort", "remark", "isVisable", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpParentBusinessType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpParentBusinessTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpParentBusinessType o", Long.class).getSingleResult();
    }
	/**
	 * 校验名称唯一性
	 * @param name
	 * @param id
	 * @return
	 */
	public static List<UmpParentBusinessType> findAllUmpParentBusinessTypes(String name,Long id) {
		String sql="SELECT o FROM UmpParentBusinessType o where 1=1";
		if(!StringUtils.isEmpty(name)){
			sql+=" and o.businessName='"+name+"'";
		}
		if(!StringUtils.isEmpty(id)){
			sql+=" and o.id!="+id;
		}
        return entityManager().createQuery(sql, UmpParentBusinessType.class).getResultList();
    }

	public static List<UmpParentBusinessType> findAllUmpParentBusinessTypes() {
        return entityManager().createQuery("SELECT o FROM UmpParentBusinessType o where o.isVisable=1", UmpParentBusinessType.class).getResultList();
    }

	public static List<UmpParentBusinessType> findAllUmpParentBusinessTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpParentBusinessType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpParentBusinessType.class).getResultList();
    }
	/**
	 * 根据公司查询父行业
	 * @param company
	 * @return
	 */
	public static UmpParentBusinessType findUmpParentBusinessTypeByCompany(UmpCompany company) {
        if (company == null) return null;
        String sql ="SELECT o FROM UmpParentBusinessType o join o.company c where c.id =:companyId";
        TypedQuery<UmpParentBusinessType> query = entityManager().createQuery(sql, UmpParentBusinessType.class);
        query.setParameter("companyId", company.getId());
        return query.getSingleResult();
    }
	public static UmpParentBusinessType findUmpParentBusinessType(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpParentBusinessType.class, id);
    }

	public static List<UmpParentBusinessType> findUmpParentBusinessTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpParentBusinessType o", UmpParentBusinessType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpParentBusinessType> findUmpParentBusinessTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpParentBusinessType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        jpaQuery+=" order by createTime desc";
        return entityManager().createQuery(jpaQuery, UmpParentBusinessType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	@Transactional
    public void remove(Set<UmpBusinessType> list) {
		for (UmpBusinessType umpBusinessType : list) {
			umpBusinessType.remove();
		}
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UmpParentBusinessType attached = UmpParentBusinessType.findUmpParentBusinessType(this.id);
            this.entityManager.remove(attached);
        }
    }
	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UmpParentBusinessType attached = UmpParentBusinessType.findUmpParentBusinessType(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public UmpParentBusinessType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpParentBusinessType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Set<UmpBusinessType> getSubBusinesses() {
		return subBusinesses;
	}

	public void setSubBusinesses(Set<UmpBusinessType> subBusinesses) {
		this.subBusinesses = subBusinesses;
	}

	public Set<UmpBrand> getBrands() {
		return brands;
	}

	public void setBrands(Set<UmpBrand> brands) {
		this.brands = brands;
	}

	public Set<VocBrand> getVocBrands() {
		return vocBrands;
	}

	public void setVocBrands(Set<VocBrand> vocBrands) {
		this.vocBrands = vocBrands;
	}
	public static String toJsonArray(Collection<UmpParentBusinessType> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpParentBusinessType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<UmpParentBusinessType> fromJsonArrayToVocBrands(String json) {
        return new JSONDeserializer<List<UmpParentBusinessType>>()
        .use("values", UmpParentBusinessType.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
}

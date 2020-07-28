package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

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
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocWordCategory {

	@OneToMany(mappedBy="vocWordCatagory")
	private Set<VocWordExpressions> vocWordExpressionses;
    /**
     */
    @NotNull
    private String name;

    /**
     */
    @NotNull
    private Long parentId;

    /**
     */
    private Boolean isDeleted;
    /**是否是默认分类**/
    private Boolean isDefault;
    /**菜单级别**/
    private Integer level;
    
    private Boolean isBusinessType;
    
    private Long businessId;
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	
	@OneToMany(mappedBy="vocWordCategory")
	private Set<VocTemplate> template = new HashSet<VocTemplate>();
	
	private Long companyId;
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	
	/**
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}


	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "parentId", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocWordCategory().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocWordCategorys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocWordCategory o", Long.class).getSingleResult();
    }
	/*
	 * 根据公司查询
	 */
	public static List<VocWordCategory> findAllVocWordCategorysByCompany(UmpCompany company) {
		String sql = " SELECT o FROM VocWordCategory o where o.companyId="+company.getId()+" or o.isDefault="+true;
        return entityManager().createQuery(sql.toString(), VocWordCategory.class).getResultList();
    }
	public static List<VocWordCategory> findAllVocWordCategorys() {
        return entityManager().createQuery("SELECT o FROM VocWordCategory o", VocWordCategory.class).getResultList();
    }
	
	public static List<VocWordCategory> findVocWordCategorysByParentId(Long parentId,UmpCompany company) {
		String sql = "SELECT o FROM VocWordCategory o WHERE 1=1 and o.parentId = "+parentId;
			sql+=" and (o.companyId="+company.getId()+" or o.isDefault="+true+")";
        return entityManager().createQuery(sql, VocWordCategory.class).getResultList();
    }
	/**
	 * 查询默认分类
	 * @param parentId
	 * @param company
	 * @return
	 */
	public static List<VocWordCategory> findVocWordCategorysByParentId(Long parentId) {
		String sql = "SELECT o FROM VocWordCategory o WHERE o.parentId = "+parentId+" and o.isDefault="+true;
        return entityManager().createQuery(sql, VocWordCategory.class).getResultList();
    }
	/**
	 * 查询子节点
	 * @param parentId
	 * @param company
	 * @return
	 */
	public static List<VocWordCategory> findVocWordCategorysByParentIdAndCompany(Long parentId,UmpCompany company) {
		String sql = "SELECT o FROM VocWordCategory o WHERE 1=1 and o.parentId = "+parentId;
			  sql+=" and (o.companyId="+company.getId()+" or o.isDefault="+true+")";
        return entityManager().createQuery(sql, VocWordCategory.class).getResultList();
    }
	public static List<VocWordCategory> findAllVocWordCategorys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWordCategory o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWordCategory.class).getResultList();
    }
	

	public static VocWordCategory findVocWordCategory(Long id) {
        if (id == null) return null;
        return entityManager().find(VocWordCategory.class, id);
    }

	public static List<VocWordCategory> findVocWordCategoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocWordCategory o", VocWordCategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocWordCategory> findVocWordCategoryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWordCategory o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWordCategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            VocWordCategory attached = VocWordCategory.findVocWordCategory(this.id);
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
    public VocWordCategory merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocWordCategory merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Long getParentId() {
        return this.parentId;
    }

	public void setParentId(Long parentId) {
        this.parentId = parentId;
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
	
	public Set<VocWordExpressions> getVocWordExpressionses() {
		return vocWordExpressionses;
	}

	public void setVocWordExpressionses(Set<VocWordExpressions> vocWordExpressionses) {
		this.vocWordExpressionses = vocWordExpressionses;
	}
	
	public Set<VocTemplate> getTemplate() {
		return template;
	}

	public void setTemplate(Set<VocTemplate> template) {
		this.template = template;
	}
	
	/**
	 * @return the businessId
	 */
	public Long getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the isBusinessType
	 */
	public Boolean getIsBusinessType() {
		return isBusinessType;
	}

	/**
	 * @param isBusinessType the isBusinessType to set
	 */
	public void setIsBusinessType(Boolean isBusinessType) {
		this.isBusinessType = isBusinessType;
	}

	public static String toJsonArray(Collection<VocWordCategory> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocWordCategory> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocWordCategory> fromJsonArrayToVocWordCatagorys(String json) {
        return new JSONDeserializer<List<VocWordCategory>>()
        .use("values", VocWordCategory.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public static List<VocWordCategory> findCommentLevel() {
		String sql = " SELECT o FROM VocWordCategory o WHERE  o.isDefault="+true;
		  sql+=" and o.level=1 and o.name!='预警'";
		  return entityManager().createQuery(sql, VocWordCategory.class).getResultList();
	}
}

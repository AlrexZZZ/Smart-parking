package com.nineclient.model;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
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
@RooJson
public class UmpBusinessType implements java.io.Serializable {

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(max = 30)
    private String businessName;

    /**
     */
    @NotNull
    private int sort;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isDeleted;
   
    
    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    private Boolean isVisable;
    @OneToMany(mappedBy="umpBusinessType")
    private Set<VocGoodsProperty> vocGoodsProperties;
    @ManyToOne
    private UmpParentBusinessType parentBusinessType;
    
    @OneToMany(mappedBy="umpBusinessType")
    private Set<VocSku> vocSkus;
    
    @OneToMany(mappedBy="bussinessType")
    private Set<VocTags> vocTagses;
    

	public Set<VocSku> getVocSkus() {
		return vocSkus;
	}

	public void setVocSkus(Set<VocSku> vocSkus) {
		this.vocSkus = vocSkus;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("businessName", "sort", "remark", "isDeleted", "createTime", "isVisable", "brands");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpBusinessType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpBusinessTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpBusinessType o", Long.class).getSingleResult();
    }
	/**
	 * 校验唯一
	 * @param name
	 * @param id
	 * @return
	 */
	public static List<UmpBusinessType> findAllUmpBusinessTypes(String name,Long id) {
		String sql="SELECT o FROM UmpBusinessType o where 1=1";
		if(!StringUtils.isEmpty(name)){
			sql+=" and o.businessName='"+name+"'";
		}
		if(!StringUtils.isEmpty(id)){
			sql+=" and o.id!="+id;
		}
        return entityManager().createQuery(sql, UmpBusinessType.class).getResultList();
    }
	public static List<UmpBusinessType> findAllUmpBusinessTypes() {
        return entityManager().createQuery("SELECT o FROM UmpBusinessType o", UmpBusinessType.class).getResultList();
    }
	/**
	 * 加载子行业
	 * @param parentBusinessType
	 * @return
	 */
	public static List<UmpBusinessType> findAllUmpBusinessTypesByParentId(long parentBusinessType) {
        return entityManager().createQuery("SELECT o FROM UmpBusinessType o where o.parentBusinessType="+parentBusinessType, UmpBusinessType.class).getResultList();
    }
	public static List<UmpBusinessType> findAllUmpBusinessTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpBusinessType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpBusinessType.class).getResultList();
    }
	/**
	 * 名称查询子行业
	 * @param id
	 * @return
	 */
	public static UmpBusinessType findUmpBusinessTypeByName(String name) {
        if (name == null) return null;
        String sql = "SELECT o FROM UmpBusinessType o where o.businessName='"+name+"'";
        return  entityManager().createQuery(sql, UmpBusinessType.class).getSingleResult();
    }
	public static UmpBusinessType findUmpBusinessType(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpBusinessType.class, id);
    }

	public static List<UmpBusinessType> findUmpBusinessTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpBusinessType o", UmpBusinessType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpBusinessType> findUmpBusinessTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpBusinessType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        jpaQuery+=" order by createTime desc";
        return entityManager().createQuery(jpaQuery, UmpBusinessType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpBusinessType attached = UmpBusinessType.findUmpBusinessType(this.id);
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
    public UmpBusinessType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpBusinessType merged = this.entityManager.merge(this);
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
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

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

	public Boolean getIsVisable() {
        return this.isVisable;
    }

	public void setIsVisable(Boolean isVisable) {
        this.isVisable = isVisable;
    }

	public UmpParentBusinessType getParentBusinessType() {
		return parentBusinessType;
	}

	public void setParentBusinessType(UmpParentBusinessType parentBusinessType) {
		this.parentBusinessType = parentBusinessType;
	}

	public Set<VocGoodsProperty> getVocGoodsProperties() {
		return vocGoodsProperties;
	}

	public void setVocGoodsProperties(Set<VocGoodsProperty> vocGoodsProperties) {
		this.vocGoodsProperties = vocGoodsProperties;
	}
	
	
	public Set<VocTags> getVocTagses() {
		return vocTagses;
	}

	public void setVocTagses(Set<VocTags> vocTagses) {
		this.vocTagses = vocTagses;
	}

	public static String toJsonArray(Collection<UmpBusinessType> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpBusinessType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<UmpBusinessType> fromJsonArrayToUmpBusinessTypes(String json) {
        return new JSONDeserializer<List<UmpBusinessType>>()
        .use("values", UmpBusinessType.class).deserialize(json);
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

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
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocWorkOrderType {

    /**
     */
    @NotNull
    private String name;

    /**
     */
    private Boolean isDeleted;
    @NotNull
    private String companyCode;
    
    @OneToMany(mappedBy="vocWorkOrderType")
    private Set<VocWorkOrder> workOrder;
    
    
	/**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    
    public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
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

	
	public Set<VocWorkOrder> getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(Set<VocWorkOrder> workOrder) {
		this.workOrder = workOrder;
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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocWorkOrderType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocWorkOrderTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocWorkOrderType o", Long.class).getSingleResult();
    }
	/**
	 * 根据公司编码查询
	 * @return
	 */
	public static List<VocWorkOrderType> findAllVocWorkOrderTypesByCompanyCode(String code) {
        return entityManager().createQuery("SELECT o FROM VocWorkOrderType o where o.companyCode='"+code+"'", VocWorkOrderType.class).getResultList();
    }
	public static List<VocWorkOrderType> findAllVocWorkOrderTypes() {
        return entityManager().createQuery("SELECT o FROM VocWorkOrderType o", VocWorkOrderType.class).getResultList();
    }

	public static List<VocWorkOrderType> findAllVocWorkOrderTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWorkOrderType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWorkOrderType.class).getResultList();
    }

	public static VocWorkOrderType findVocWorkOrderType(Long id) {
        if (id == null) return null;
        return entityManager().find(VocWorkOrderType.class, id);
    }
	/**
	 * 根据id查询
	 * @return
	 */
	public static List<VocWorkOrderType> findVocWorkOrderTypeEntriesById(String Ids){
		return entityManager().createQuery("SELECT o FROM VocWorkOrderType o where o.id in ("+Ids+")", VocWorkOrderType.class).getResultList();
	}
	/**
	 * 根据名称查询工单类型
	 * @param name
	 * @return
	 */
	public static List<VocWorkOrderType> findVocWorkOrderTypeEntriesByName(String name) {
        return entityManager().createQuery("SELECT o FROM VocWorkOrderType o where o.name='"+name+"'", VocWorkOrderType.class).getResultList();
    }
	public static List<VocWorkOrderType> findVocWorkOrderTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocWorkOrderType o", VocWorkOrderType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocWorkOrderType> findVocWorkOrderTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWorkOrderType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWorkOrderType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	@Transactional
    public static void  remove(List<VocWorkOrderType> list) {
       for (VocWorkOrderType vocWorkOrderType : list) {
    	   vocWorkOrderType.remove();
       }
    }
	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            VocWorkOrderType attached = VocWorkOrderType.findVocWorkOrderType(this.id);
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
    public VocWorkOrderType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocWorkOrderType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	public static String toJsonArray(Collection<VocWorkOrderType> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocWorkOrderType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocWorkOrderType> fromJsonArrayToVocWorkOrderTypes(String json) {
        return new JSONDeserializer<List<VocWorkOrderType>>()
        .use("values", VocWorkOrderType.class).deserialize(json);
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
package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpDictionary {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Size(max = 200)
    private String typeCode;

    /**
     */
    @Size(max = 200)
    private String typeName;

    /**
     */
    @Size(max = 200)
    private String typeTitle;

    /**
     */
    @Size(max = 200)
    private String typeTitleCh;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date insertTime;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "typeCode", "typeName", "typeTitle", "typeTitleCh", "insertTime");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpDictionary().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpDictionarys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpDictionary o", Long.class).getSingleResult();
    }

	public static List<UmpDictionary> findAllUmpDictionarys() {
        return entityManager().createQuery("SELECT o FROM UmpDictionary o", UmpDictionary.class).getResultList();
    }

	public static List<UmpDictionary> findAllUmpDictionarys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpDictionary o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpDictionary.class).getResultList();
    }

	public static UmpDictionary findUmpDictionary(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpDictionary.class, id);
    }

	public static List<UmpDictionary> findUmpDictionaryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpDictionary o", UmpDictionary.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpDictionary> findUmpDictionaryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpDictionary o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpDictionary.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpDictionary attached = UmpDictionary.findUmpDictionary(this.id);
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
    public UmpDictionary merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpDictionary merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getTypeCode() {
        return this.typeCode;
    }

	public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

	public String getTypeName() {
        return this.typeName;
    }

	public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

	public String getTypeTitle() {
        return this.typeTitle;
    }

	public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

	public String getTypeTitleCh() {
        return this.typeTitleCh;
    }

	public void setTypeTitleCh(String typeTitleCh) {
        this.typeTitleCh = typeTitleCh;
    }

	public Date getInsertTime() {
        return this.insertTime;
    }

	public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}

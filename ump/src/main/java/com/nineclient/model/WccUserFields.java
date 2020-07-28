package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccUserFields {

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
    private Date createTime;

    /**
     */
    @Size(max = 400)
    @Pattern(regexp = "(^[\\x80-\\xff]+$)")
    private String fieldNameCh;

    /**
     */
    @Size(max = 400)
    @Pattern(regexp = "(^[A-Za-z]+$)")
    private String fieldNameEN;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isEmail;

    /**
     */
    private Boolean isKey;

    /**
     */
    private Boolean isQueryType;

    /**
     */
    private Boolean isCombox;

    /**
     */
    private Boolean isComBoxValue;

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

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getFieldNameCh() {
        return this.fieldNameCh;
    }

	public void setFieldNameCh(String fieldNameCh) {
        this.fieldNameCh = fieldNameCh;
    }

	public String getFieldNameEN() {
        return this.fieldNameEN;
    }

	public void setFieldNameEN(String fieldNameEN) {
        this.fieldNameEN = fieldNameEN;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public Boolean getIsEmail() {
        return this.isEmail;
    }

	public void setIsEmail(Boolean isEmail) {
        this.isEmail = isEmail;
    }

	public Boolean getIsKey() {
        return this.isKey;
    }

	public void setIsKey(Boolean isKey) {
        this.isKey = isKey;
    }

	public Boolean getIsQueryType() {
        return this.isQueryType;
    }

	public void setIsQueryType(Boolean isQueryType) {
        this.isQueryType = isQueryType;
    }

	public Boolean getIsCombox() {
        return this.isCombox;
    }

	public void setIsCombox(Boolean isCombox) {
        this.isCombox = isCombox;
    }

	public Boolean getIsComBoxValue() {
        return this.isComBoxValue;
    }

	public void setIsComBoxValue(Boolean isComBoxValue) {
        this.isComBoxValue = isComBoxValue;
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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "isVisable", "createTime", "fieldNameCh", "fieldNameEN", "remark", "isEmail", "isKey", "isQueryType", "isCombox", "isComBoxValue");

	public static final EntityManager entityManager() {
        EntityManager em = new WccUserFields().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccUserFieldses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccUserFields o", Long.class).getSingleResult();
    }

	public static List<WccUserFields> findAllWccUserFieldses() {
        return entityManager().createQuery("SELECT o FROM WccUserFields o", WccUserFields.class).getResultList();
    }

	public static List<WccUserFields> findAllWccUserFieldses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUserFields o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUserFields.class).getResultList();
    }

	public static WccUserFields findWccUserFields(Long id) {
        if (id == null) return null;
        return entityManager().find(WccUserFields.class, id);
    }

	public static List<WccUserFields> findWccUserFieldsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccUserFields o", WccUserFields.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccUserFields> findWccUserFieldsEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUserFields o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUserFields.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccUserFields attached = WccUserFields.findWccUserFields(this.id);
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
    public WccUserFields merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccUserFields merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}

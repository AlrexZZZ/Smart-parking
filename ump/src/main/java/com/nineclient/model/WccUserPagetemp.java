package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
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

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccUserPagetemp {

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(max = 200)
    private String pageTitle;

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(max = 4000)
    private String proName;

    /**
     */
    @Size(max = 4000)
    private String temImage;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    private Boolean isVisable;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("pageTitle", "proName", "temImage", "remark", "createTime", "isDeleted", "isVisable");

	public static final EntityManager entityManager() {
        EntityManager em = new WccUserPagetemp().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccUserPagetemps() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccUserPagetemp o", Long.class).getSingleResult();
    }

	public static List<WccUserPagetemp> findAllWccUserPagetemps() {
        return entityManager().createQuery("SELECT o FROM WccUserPagetemp o", WccUserPagetemp.class).getResultList();
    }

	public static List<WccUserPagetemp> findAllWccUserPagetemps(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUserPagetemp o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUserPagetemp.class).getResultList();
    }

	public static WccUserPagetemp findWccUserPagetemp(Long id) {
        if (id == null) return null;
        return entityManager().find(WccUserPagetemp.class, id);
    }

	public static List<WccUserPagetemp> findWccUserPagetempEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccUserPagetemp o", WccUserPagetemp.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccUserPagetemp> findWccUserPagetempEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUserPagetemp o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUserPagetemp.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccUserPagetemp attached = WccUserPagetemp.findWccUserPagetemp(this.id);
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
    public WccUserPagetemp merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccUserPagetemp merged = this.entityManager.merge(this);
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

	public String getPageTitle() {
        return this.pageTitle;
    }

	public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

	public String getProName() {
        return this.proName;
    }

	public void setProName(String proName) {
        this.proName = proName;
    }

	public String getTemImage() {
        return this.temImage;
    }

	public void setTemImage(String temImage) {
        this.temImage = temImage;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}

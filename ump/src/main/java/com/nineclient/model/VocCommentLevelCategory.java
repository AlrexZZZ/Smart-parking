package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
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

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocCommentLevelCategory {

    /**
     */
    @NotNull
    @Size(min = 1, max = 500)
    private String name;

    /**
     */
    private long parentId;

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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "parentId", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocCommentLevelCategory().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocCommentLevelCategorys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocCommentLevelCategory o", Long.class).getSingleResult();
    }

	public static List<VocCommentLevelCategory> findAllVocCommentLevelCategorys() {
        return entityManager().createQuery("SELECT o FROM VocCommentLevelCategory o", VocCommentLevelCategory.class).getResultList();
    }

	public static List<VocCommentLevelCategory> findAllVocCommentLevelCategorys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocCommentLevelCategory o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocCommentLevelCategory.class).getResultList();
    }

	public static VocCommentLevelCategory findVocCommentLevelCategory(Long id) {
        if (id == null) return null;
        return entityManager().find(VocCommentLevelCategory.class, id);
    }

	public static List<VocCommentLevelCategory> findVocCommentLevelCategoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocCommentLevelCategory o", VocCommentLevelCategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocCommentLevelCategory> findVocCommentLevelCategoryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocCommentLevelCategory o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocCommentLevelCategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocCommentLevelCategory attached = VocCommentLevelCategory.findVocCommentLevelCategory(this.id);
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
    public VocCommentLevelCategory merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocCommentLevelCategory merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public long getParentId() {
        return this.parentId;
    }

	public void setParentId(long parentId) {
        this.parentId = parentId;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

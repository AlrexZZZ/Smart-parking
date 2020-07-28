package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.nineclient.constant.WccPlateStatus;
import javax.persistence.Enumerated;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccPlate {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 200)
    private String plateName;

    /**
     */
    @Enumerated
    private WccPlateStatus status;

    /**
     */
    @Size(max = 4000)
    private String remark;

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getPlateName() {
        return this.plateName;
    }

	public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

	public WccPlateStatus getStatus() {
        return this.status;
    }

	public void setStatus(WccPlateStatus status) {
        this.status = status;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "plateName", "status", "remark");

	public static final EntityManager entityManager() {
        EntityManager em = new WccPlate().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccPlates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccPlate o", Long.class).getSingleResult();
    }

	public static List<WccPlate> findAllWccPlates() {
        return entityManager().createQuery("SELECT o FROM WccPlate o", WccPlate.class).getResultList();
    }

	public static List<WccPlate> findAllWccPlates(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccPlate o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccPlate.class).getResultList();
    }

	public static WccPlate findWccPlate(Long id) {
        if (id == null) return null;
        return entityManager().find(WccPlate.class, id);
    }

	public static List<WccPlate> findWccPlateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccPlate o", WccPlate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccPlate> findWccPlateEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccPlate o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccPlate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccPlate attached = WccPlate.findWccPlate(this.id);
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
    public WccPlate merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccPlate merged = this.entityManager.merge(this);
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
}

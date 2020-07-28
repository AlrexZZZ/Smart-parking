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

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccActivities {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 400)
    private String activitiesName;

    /**
     */
    @NotNull
    @Size(min = 1, max = 400)
    private String activitiesTopic;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date beginTime;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endTime;

    /**
     */
    private Boolean isAudit;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date codeValidtime;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String activitiesUrl;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String imageUrl;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String activityProvince;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String activityCity;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "activitiesName", "activitiesTopic", "beginTime", "endTime", "isAudit", "codeValidtime", "activitiesUrl", "imageUrl", "activityProvince", "activityCity");

	public static final EntityManager entityManager() {
        EntityManager em = new WccActivities().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccActivitieses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccActivities o", Long.class).getSingleResult();
    }

	public static List<WccActivities> findAllWccActivitieses() {
        return entityManager().createQuery("SELECT o FROM WccActivities o", WccActivities.class).getResultList();
    }

	public static List<WccActivities> findAllWccActivitieses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccActivities o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccActivities.class).getResultList();
    }

	public static WccActivities findWccActivities(Long id) {
        if (id == null) return null;
        return entityManager().find(WccActivities.class, id);
    }

	public static List<WccActivities> findWccActivitiesEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccActivities o", WccActivities.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccActivities> findWccActivitiesEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccActivities o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccActivities.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccActivities attached = WccActivities.findWccActivities(this.id);
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
    public WccActivities merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccActivities merged = this.entityManager.merge(this);
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

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getActivitiesName() {
        return this.activitiesName;
    }

	public void setActivitiesName(String activitiesName) {
        this.activitiesName = activitiesName;
    }

	public String getActivitiesTopic() {
        return this.activitiesTopic;
    }

	public void setActivitiesTopic(String activitiesTopic) {
        this.activitiesTopic = activitiesTopic;
    }

	public Date getBeginTime() {
        return this.beginTime;
    }

	public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

	public Date getEndTime() {
        return this.endTime;
    }

	public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public Boolean getIsAudit() {
        return this.isAudit;
    }

	public void setIsAudit(Boolean isAudit) {
        this.isAudit = isAudit;
    }

	public Date getCodeValidtime() {
        return this.codeValidtime;
    }

	public void setCodeValidtime(Date codeValidtime) {
        this.codeValidtime = codeValidtime;
    }

	public String getActivitiesUrl() {
        return this.activitiesUrl;
    }

	public void setActivitiesUrl(String activitiesUrl) {
        this.activitiesUrl = activitiesUrl;
    }

	public String getImageUrl() {
        return this.imageUrl;
    }

	public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	public String getActivityProvince() {
        return this.activityProvince;
    }

	public void setActivityProvince(String activityProvince) {
        this.activityProvince = activityProvince;
    }

	public String getActivityCity() {
        return this.activityCity;
    }

	public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }
}

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
import com.nineclient.constant.WccStatus;
import javax.persistence.Enumerated;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccQuestionnaire {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Size(min = 1, max = 200)
    private String name;

    /**
     */
    @Size(min = 1, max = 200)
    private String questionnaireCode;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date visableTime;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    @Size(min = 1, max = 400)
    private String url;

    /**
     */
    private int sort;

    /**
     */
    @Enumerated
    private WccStatus status;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "name", "questionnaireCode", "visableTime", "remark", "url", "sort", "status");

	public static final EntityManager entityManager() {
        EntityManager em = new WccQuestionnaire().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccQuestionnaires() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccQuestionnaire o", Long.class).getSingleResult();
    }

	public static List<WccQuestionnaire> findAllWccQuestionnaires() {
        return entityManager().createQuery("SELECT o FROM WccQuestionnaire o", WccQuestionnaire.class).getResultList();
    }

	public static List<WccQuestionnaire> findAllWccQuestionnaires(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQuestionnaire o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQuestionnaire.class).getResultList();
    }

	public static WccQuestionnaire findWccQuestionnaire(Long id) {
        if (id == null) return null;
        return entityManager().find(WccQuestionnaire.class, id);
    }

	public static List<WccQuestionnaire> findWccQuestionnaireEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccQuestionnaire o", WccQuestionnaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccQuestionnaire> findWccQuestionnaireEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQuestionnaire o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQuestionnaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccQuestionnaire attached = WccQuestionnaire.findWccQuestionnaire(this.id);
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
    public WccQuestionnaire merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccQuestionnaire merged = this.entityManager.merge(this);
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

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getQuestionnaireCode() {
        return this.questionnaireCode;
    }

	public void setQuestionnaireCode(String questionnaireCode) {
        this.questionnaireCode = questionnaireCode;
    }

	public Date getVisableTime() {
        return this.visableTime;
    }

	public void setVisableTime(Date visableTime) {
        this.visableTime = visableTime;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getUrl() {
        return this.url;
    }

	public void setUrl(String url) {
        this.url = url;
    }

	public int getSort() {
        return this.sort;
    }

	public void setSort(int sort) {
        this.sort = sort;
    }

	public WccStatus getStatus() {
        return this.status;
    }

	public void setStatus(WccStatus status) {
        this.status = status;
    }
}

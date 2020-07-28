package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import com.nineclient.constant.WccTopicStatus;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccTopic {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Enumerated
    private WccTopicStatus status;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String content;

    /**
     */
    private Boolean isOfficial;

    /**
     */
    @Value("0")
    private int commentCount;

    /**
     */
    @Value("0")
    private int praiseCount;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String topicImage;

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public WccTopicStatus getStatus() {
        return this.status;
    }

	public void setStatus(WccTopicStatus status) {
        this.status = status;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	public Boolean getIsOfficial() {
        return this.isOfficial;
    }

	public void setIsOfficial(Boolean isOfficial) {
        this.isOfficial = isOfficial;
    }

	public int getCommentCount() {
        return this.commentCount;
    }

	public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

	public int getPraiseCount() {
        return this.praiseCount;
    }

	public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

	public String getTopicImage() {
        return this.topicImage;
    }

	public void setTopicImage(String topicImage) {
        this.topicImage = topicImage;
    }

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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "status", "content", "isOfficial", "commentCount", "praiseCount", "topicImage");

	public static final EntityManager entityManager() {
        EntityManager em = new WccTopic().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccTopics() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccTopic o", Long.class).getSingleResult();
    }

	public static List<WccTopic> findAllWccTopics() {
        return entityManager().createQuery("SELECT o FROM WccTopic o", WccTopic.class).getResultList();
    }

	public static List<WccTopic> findAllWccTopics(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccTopic o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccTopic.class).getResultList();
    }

	public static WccTopic findWccTopic(Long id) {
        if (id == null) return null;
        return entityManager().find(WccTopic.class, id);
    }

	public static List<WccTopic> findWccTopicEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccTopic o", WccTopic.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccTopic> findWccTopicEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccTopic o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccTopic.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccTopic attached = WccTopic.findWccTopic(this.id);
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
    public WccTopic merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccTopic merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}

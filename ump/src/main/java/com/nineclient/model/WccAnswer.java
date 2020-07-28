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
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccAnswer {

    /**
     */
    @Size(min = 1, max = 4000)
    private String answer;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Size(min = 1, max = 200)
    private String userName;

    /**
     */
    @Pattern(regexp = "(^[0-9]{11}$)")
    private String userPhone;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getAnswer() {
        return this.answer;
    }

	public void setAnswer(String answer) {
        this.answer = answer;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getUserName() {
        return this.userName;
    }

	public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getUserPhone() {
        return this.userPhone;
    }

	public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("answer", "isDeleted", "userName", "userPhone");

	public static final EntityManager entityManager() {
        EntityManager em = new WccAnswer().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccAnswers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccAnswer o", Long.class).getSingleResult();
    }

	public static List<WccAnswer> findAllWccAnswers() {
        return entityManager().createQuery("SELECT o FROM WccAnswer o", WccAnswer.class).getResultList();
    }

	public static List<WccAnswer> findAllWccAnswers(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccAnswer o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccAnswer.class).getResultList();
    }

	public static WccAnswer findWccAnswer(Long id) {
        if (id == null) return null;
        return entityManager().find(WccAnswer.class, id);
    }

	public static List<WccAnswer> findWccAnswerEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccAnswer o", WccAnswer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccAnswer> findWccAnswerEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccAnswer o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccAnswer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccAnswer attached = WccAnswer.findWccAnswer(this.id);
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
    public WccAnswer merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccAnswer merged = this.entityManager.merge(this);
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
}

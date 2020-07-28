package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.validation.constraints.Size;
import com.nineclient.constant.WccQuestionType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccQuestion {

    /**
     */
    @Size(min = 1, max = 400)
    private String name;

    /**
     */
    @Size(min = 1, max = 50)
    private String questionNo;

    /**
     */
    private int sort;

    /**
     */
    private Boolean isVisable;

    /**
     */
    @Enumerated
    private WccQuestionType questionType;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "questionNo", "sort", "isVisable", "questionType");

	public static final EntityManager entityManager() {
        EntityManager em = new WccQuestion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccQuestions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccQuestion o", Long.class).getSingleResult();
    }

	public static List<WccQuestion> findAllWccQuestions() {
        return entityManager().createQuery("SELECT o FROM WccQuestion o", WccQuestion.class).getResultList();
    }

	public static List<WccQuestion> findAllWccQuestions(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQuestion o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQuestion.class).getResultList();
    }

	public static WccQuestion findWccQuestion(Long id) {
        if (id == null) return null;
        return entityManager().find(WccQuestion.class, id);
    }

	public static List<WccQuestion> findWccQuestionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccQuestion o", WccQuestion.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccQuestion> findWccQuestionEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQuestion o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQuestion.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccQuestion attached = WccQuestion.findWccQuestion(this.id);
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
    public WccQuestion merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccQuestion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getQuestionNo() {
        return this.questionNo;
    }

	public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

	public int getSort() {
        return this.sort;
    }

	public void setSort(int sort) {
        this.sort = sort;
    }

	public Boolean getIsVisable() {
        return this.isVisable;
    }

	public void setIsVisable(Boolean isVisable) {
        this.isVisable = isVisable;
    }

	public WccQuestionType getQuestionType() {
        return this.questionType;
    }

	public void setQuestionType(WccQuestionType questionType) {
        this.questionType = questionType;
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

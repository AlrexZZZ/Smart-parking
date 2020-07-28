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
public class WccLeavemsgRecord {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String msgUserName;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String msgContent;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date msgTime;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "msgUserName", "msgContent", "msgTime");

	public static final EntityManager entityManager() {
        EntityManager em = new WccLeavemsgRecord().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccLeavemsgRecords() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccLeavemsgRecord o", Long.class).getSingleResult();
    }

	public static List<WccLeavemsgRecord> findAllWccLeavemsgRecords() {
        return entityManager().createQuery("SELECT o FROM WccLeavemsgRecord o", WccLeavemsgRecord.class).getResultList();
    }

	public static List<WccLeavemsgRecord> findAllWccLeavemsgRecords(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccLeavemsgRecord o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccLeavemsgRecord.class).getResultList();
    }

	public static WccLeavemsgRecord findWccLeavemsgRecord(Long id) {
        if (id == null) return null;
        return entityManager().find(WccLeavemsgRecord.class, id);
    }

	public static List<WccLeavemsgRecord> findWccLeavemsgRecordEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccLeavemsgRecord o", WccLeavemsgRecord.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccLeavemsgRecord> findWccLeavemsgRecordEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccLeavemsgRecord o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccLeavemsgRecord.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccLeavemsgRecord attached = WccLeavemsgRecord.findWccLeavemsgRecord(this.id);
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
    public WccLeavemsgRecord merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccLeavemsgRecord merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getMsgUserName() {
        return this.msgUserName;
    }

	public void setMsgUserName(String msgUserName) {
        this.msgUserName = msgUserName;
    }

	public String getMsgContent() {
        return this.msgContent;
    }

	public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

	public Date getMsgTime() {
        return this.msgTime;
    }

	public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
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

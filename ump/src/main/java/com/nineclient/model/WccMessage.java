package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import com.nineclient.constant.WccMessageMsgType;
import javax.persistence.Enumerated;
import com.nineclient.constant.WccMessageContentType;
import javax.validation.constraints.Size;
import com.nineclient.constant.WccMessageStatus;
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

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMessage {

    /**
     */
    @Enumerated
    private WccMessageMsgType MsgType;

    /**
     */
    @Enumerated
    private WccMessageContentType contentType;

    /**
     */
    @Size(max = 4000)
    private String content;

    /**
     */
    @Enumerated
    private WccMessageStatus status;

    /**
     */
    private int dateTime;

    /**
     */
    @Size(max = 4000)
    private String Msg;

    /**
     */
    @Size(max = 4000)
    private String resourceUrl;

    /**
     */
    @Size(max = 4000)
    private String thumbnailUrl;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date insertTime;

    /**
     */
    @Size(max = 200)
    private String servicePlatform;

    /**
     */
    @Size(max = 100)
    private String fileSize;

	public WccMessageMsgType getMsgType() {
        return this.MsgType;
    }

	public void setMsgType(WccMessageMsgType MsgType) {
        this.MsgType = MsgType;
    }

	public WccMessageContentType getContentType() {
        return this.contentType;
    }

	public void setContentType(WccMessageContentType contentType) {
        this.contentType = contentType;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	public WccMessageStatus getStatus() {
        return this.status;
    }

	public void setStatus(WccMessageStatus status) {
        this.status = status;
    }

	public int getDateTime() {
        return this.dateTime;
    }

	public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

	public String getMsg() {
        return this.Msg;
    }

	public void setMsg(String Msg) {
        this.Msg = Msg;
    }

	public String getResourceUrl() {
        return this.resourceUrl;
    }

	public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

	public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

	public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Date getInsertTime() {
        return this.insertTime;
    }

	public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

	public String getServicePlatform() {
        return this.servicePlatform;
    }

	public void setServicePlatform(String servicePlatform) {
        this.servicePlatform = servicePlatform;
    }

	public String getFileSize() {
        return this.fileSize;
    }

	public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("MsgType", "contentType", "content", "status", "dateTime", "Msg", "resourceUrl", "thumbnailUrl", "isDeleted", "insertTime", "servicePlatform", "fileSize");

	public static final EntityManager entityManager() {
        EntityManager em = new WccMessage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccMessages() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccMessage o", Long.class).getSingleResult();
    }

	public static List<WccMessage> findAllWccMessages() {
        return entityManager().createQuery("SELECT o FROM WccMessage o", WccMessage.class).getResultList();
    }

	public static List<WccMessage> findAllWccMessages(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccMessage o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccMessage.class).getResultList();
    }

	public static WccMessage findWccMessage(Long id) {
        if (id == null) return null;
        return entityManager().find(WccMessage.class, id);
    }

	public static List<WccMessage> findWccMessageEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccMessage o", WccMessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccMessage> findWccMessageEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccMessage o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccMessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccMessage attached = WccMessage.findWccMessage(this.id);
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
    public WccMessage merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccMessage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
}

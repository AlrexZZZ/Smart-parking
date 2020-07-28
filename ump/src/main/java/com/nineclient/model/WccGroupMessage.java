package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
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
import com.nineclient.constant.WccSendMssageStatus;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.nineclient.constant.UmpSexValue;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccGroupMessage {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    @Enumerated
    private WccSendMssageStatus sendStatus;

    /**
     */
    @NotNull
    @Size(max = 400)
    private String title;

    /**
     */
    @Size(max = 200)
    private String author;

    /**
     */
    @Size(max = 4000)
    private String content;

    /**
     */
    @Size(max = 4000)
    private String digest;

    /**
     */
    @Size(max = 200)
    private String url;

    /**
     */
    @Enumerated
    private UmpSexValue sex;

    /**
     */
    @Size(max = 400)
    private String area;

    /**
     */
    @Size(max = 400)
    private String mediaUrl;

    /**
     */
    private int msgNum;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "createTime", "sendStatus", "title", "author", "content", "digest", "url", "sex", "area", "mediaUrl", "msgNum");

	public static final EntityManager entityManager() {
        EntityManager em = new WccGroupMessage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccGroupMessages() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccGroupMessage o", Long.class).getSingleResult();
    }

	public static List<WccGroupMessage> findAllWccGroupMessages() {
        return entityManager().createQuery("SELECT o FROM WccGroupMessage o", WccGroupMessage.class).getResultList();
    }

	public static List<WccGroupMessage> findAllWccGroupMessages(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccGroupMessage o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccGroupMessage.class).getResultList();
    }

	public static WccGroupMessage findWccGroupMessage(Long id) {
        if (id == null) return null;
        return entityManager().find(WccGroupMessage.class, id);
    }

	public static List<WccGroupMessage> findWccGroupMessageEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccGroupMessage o", WccGroupMessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccGroupMessage> findWccGroupMessageEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccGroupMessage o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccGroupMessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccGroupMessage attached = WccGroupMessage.findWccGroupMessage(this.id);
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
    public WccGroupMessage merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccGroupMessage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public WccSendMssageStatus getSendStatus() {
        return this.sendStatus;
    }

	public void setSendStatus(WccSendMssageStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

	public String getTitle() {
        return this.title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public String getAuthor() {
        return this.author;
    }

	public void setAuthor(String author) {
        this.author = author;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	public String getDigest() {
        return this.digest;
    }

	public void setDigest(String digest) {
        this.digest = digest;
    }

	public String getUrl() {
        return this.url;
    }

	public void setUrl(String url) {
        this.url = url;
    }

	public UmpSexValue getSex() {
        return this.sex;
    }

	public void setSex(UmpSexValue sex) {
        this.sex = sex;
    }

	public String getArea() {
        return this.area;
    }

	public void setArea(String area) {
        this.area = area;
    }

	public String getMediaUrl() {
        return this.mediaUrl;
    }

	public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

	public int getMsgNum() {
        return this.msgNum;
    }

	public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
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

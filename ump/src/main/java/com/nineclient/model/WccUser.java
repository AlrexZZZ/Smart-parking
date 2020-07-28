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
import com.nineclient.constant.WccUserStatus;
import javax.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.format.annotation.DateTimeFormat;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccUser implements java.io.Serializable {

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 200)
    private String userName;

    /**
     */
    @Enumerated
    private WccUserStatus status;

    /**
     */
    @NotNull
    @Value("0")
    private int commentCount;

    /**
     */
    @NotNull
    @Value("0")
    private int praiseCount;

    /**
     */
    @Value("0")
    private int givenCommentCount;

    /**
     */
    @Value("0")
    private int givenPraiseCount;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    private Boolean isOfficial;

    /**
     */
    @Size(max = 100)
    private String openId;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date firstTopicTime;

    /**
     */
    @NotNull
    @Value("0")
    private int userTopicCount;

    /**
     */
    @NotNull
    @Size(min = 1, max = 200)
    private String lastTopicComment;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastTopicTime;

    /**
     */
    @NotNull
    @Size(min = 1, max = 300)
    private String userImage;

	public String getUserName() {
        return this.userName;
    }

	public void setUserName(String userName) {
        this.userName = userName;
    }

	public WccUserStatus getStatus() {
        return this.status;
    }

	public void setStatus(WccUserStatus status) {
        this.status = status;
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

	public int getGivenCommentCount() {
        return this.givenCommentCount;
    }

	public void setGivenCommentCount(int givenCommentCount) {
        this.givenCommentCount = givenCommentCount;
    }

	public int getGivenPraiseCount() {
        return this.givenPraiseCount;
    }

	public void setGivenPraiseCount(int givenPraiseCount) {
        this.givenPraiseCount = givenPraiseCount;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Boolean getIsOfficial() {
        return this.isOfficial;
    }

	public void setIsOfficial(Boolean isOfficial) {
        this.isOfficial = isOfficial;
    }

	public String getOpenId() {
        return this.openId;
    }

	public void setOpenId(String openId) {
        this.openId = openId;
    }

	public Date getFirstTopicTime() {
        return this.firstTopicTime;
    }

	public void setFirstTopicTime(Date firstTopicTime) {
        this.firstTopicTime = firstTopicTime;
    }

	public int getUserTopicCount() {
        return this.userTopicCount;
    }

	public void setUserTopicCount(int userTopicCount) {
        this.userTopicCount = userTopicCount;
    }

	public String getLastTopicComment() {
        return this.lastTopicComment;
    }

	public void setLastTopicComment(String lastTopicComment) {
        this.lastTopicComment = lastTopicComment;
    }

	public Date getLastTopicTime() {
        return this.lastTopicTime;
    }

	public void setLastTopicTime(Date lastTopicTime) {
        this.lastTopicTime = lastTopicTime;
    }

	public String getUserImage() {
        return this.userImage;
    }

	public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("userName", "status", "commentCount", "praiseCount", "givenCommentCount", "givenPraiseCount", "remark", "isDeleted", "isOfficial", "openId", "firstTopicTime", "userTopicCount", "lastTopicComment", "lastTopicTime", "userImage");

	public static final EntityManager entityManager() {
        EntityManager em = new WccUser().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccUsers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccUser o", Long.class).getSingleResult();
    }

	public static List<WccUser> findAllWccUsers() {
        return entityManager().createQuery("SELECT o FROM WccUser o", WccUser.class).getResultList();
    }

	public static List<WccUser> findAllWccUsers(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUser o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUser.class).getResultList();
    }

	public static WccUser findWccUser(Long id) {
        if (id == null) return null;
        return entityManager().find(WccUser.class, id);
    }

	public static List<WccUser> findWccUserEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccUser o", WccUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccUser> findWccUserEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUser o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccUser attached = WccUser.findWccUser(this.id);
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
    public WccUser merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccUser merged = this.entityManager.merge(this);
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

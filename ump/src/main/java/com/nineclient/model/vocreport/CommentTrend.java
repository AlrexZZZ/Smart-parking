package com.nineclient.model.vocreport;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.Collection;
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
import org.springframework.roo.addon.json.RooJson;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class CommentTrend {

    /**
     */
    private String commentType;

    /**
     */
    private double commentTypePercent;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date commentTime;

    /**
     */
    private long commentNum;

    /**
     */
    private String channelName;

    /**
     */
    private String brandName;

    /**
     */
    private String shopName;

    /**
     */
    private String skuName;

    public String getCommentType() {
        return this.commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public double getCommentTypePercent() {
        return this.commentTypePercent;
    }

    public void setCommentTypePercent(double commentTypePercent) {
        this.commentTypePercent = commentTypePercent;
    }

    public Date getCommentTime() {
        return this.commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public long getCommentNum() {
        return this.commentNum;
    }

    public void setCommentNum(long commentNum) {
        this.commentNum = commentNum;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("commentType", "commentTypePercent", "commentTime", "commentNum", "channelName", "brandName", "shopName", "skuName");

    public static final EntityManager entityManager() {
        EntityManager em = new CommentTrend().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCommentTrends() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CommentTrend o", Long.class).getSingleResult();
    }

    public static List<CommentTrend> findAllCommentTrends() {
        return entityManager().createQuery("SELECT o FROM CommentTrend o", CommentTrend.class).getResultList();
    }

    public static List<CommentTrend> findAllCommentTrends(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM CommentTrend o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, CommentTrend.class).getResultList();
    }

    public static CommentTrend findCommentTrend(Long id) {
        if (id == null) return null;
        return entityManager().find(CommentTrend.class, id);
    }

    public static List<CommentTrend> findCommentTrendEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CommentTrend o", CommentTrend.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<CommentTrend> findCommentTrendEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM CommentTrend o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, CommentTrend.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CommentTrend attached = CommentTrend.findCommentTrend(this.id);
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
    public CommentTrend merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CommentTrend merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static CommentTrend fromJsonToCommentTrend(String json) {
        return new JSONDeserializer<CommentTrend>()
        .use(null, CommentTrend.class).deserialize(json);
    }

	public static String toJsonArray(Collection<CommentTrend> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<CommentTrend> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<CommentTrend> fromJsonArrayToCommentTrends(String json) {
        return new JSONDeserializer<List<CommentTrend>>()
        .use("values", CommentTrend.class).deserialize(json);
    }
}

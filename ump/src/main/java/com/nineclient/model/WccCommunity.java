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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.nineclient.constant.WccTopicStatus;
import javax.persistence.Enumerated;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccCommunity {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 200)
    private String communityName;

    /**
     */
    @NotNull
    @Size(min = 1, max = 400)
    private String communityUrl;

    /**
     */
    @Enumerated
    private WccTopicStatus topicStatus;

    /**
     */
    @NotNull
    private int topicSort;

    /**
     */
    @NotNull
    @Size(min = 1, max = 400)
    private String headImage;

    /**
     */
    @NotNull
    @Size(min = 1, max = 400)
    private String officialImage;

    /**
     */
    @NotNull
    @Size(min = 1, max = 400)
    private String officialNickName;

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getCommunityName() {
        return this.communityName;
    }

	public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

	public String getCommunityUrl() {
        return this.communityUrl;
    }

	public void setCommunityUrl(String communityUrl) {
        this.communityUrl = communityUrl;
    }

	public WccTopicStatus getTopicStatus() {
        return this.topicStatus;
    }

	public void setTopicStatus(WccTopicStatus topicStatus) {
        this.topicStatus = topicStatus;
    }

	public int getTopicSort() {
        return this.topicSort;
    }

	public void setTopicSort(int topicSort) {
        this.topicSort = topicSort;
    }

	public String getHeadImage() {
        return this.headImage;
    }

	public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

	public String getOfficialImage() {
        return this.officialImage;
    }

	public void setOfficialImage(String officialImage) {
        this.officialImage = officialImage;
    }

	public String getOfficialNickName() {
        return this.officialNickName;
    }

	public void setOfficialNickName(String officialNickName) {
        this.officialNickName = officialNickName;
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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "communityName", "communityUrl", "topicStatus", "topicSort", "headImage", "officialImage", "officialNickName");

	public static final EntityManager entityManager() {
        EntityManager em = new WccCommunity().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccCommunitys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccCommunity o", Long.class).getSingleResult();
    }

	public static List<WccCommunity> findAllWccCommunitys() {
        return entityManager().createQuery("SELECT o FROM WccCommunity o", WccCommunity.class).getResultList();
    }

	public static List<WccCommunity> findAllWccCommunitys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccCommunity o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccCommunity.class).getResultList();
    }

	public static WccCommunity findWccCommunity(Long id) {
        if (id == null) return null;
        return entityManager().find(WccCommunity.class, id);
    }

	public static List<WccCommunity> findWccCommunityEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccCommunity o", WccCommunity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccCommunity> findWccCommunityEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccCommunity o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccCommunity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccCommunity attached = WccCommunity.findWccCommunity(this.id);
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
    public WccCommunity merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccCommunity merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}

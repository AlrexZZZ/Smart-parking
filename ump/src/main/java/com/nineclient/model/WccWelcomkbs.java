package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;

import com.nineclient.constant.WccWelcomkbsType;

import javax.persistence.Enumerated;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import com.nineclient.constant.WccWelcomkbsReplyType;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccWelcomkbs {

    /**
     */
    @Size(max = 300)
    private String title;

    
    /**
     */
    @Size(max = 4000)
    private String content;

    /**
     */
    @Enumerated
    private WccWelcomkbsType type;

    /**
     */
    private Boolean active;

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
    @Enumerated
    private WccWelcomkbsReplyType replyType;

	public String getTitle() {
        return this.title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	public WccWelcomkbsType getType() {
        return this.type;
    }

	public void setType(WccWelcomkbsType type) {
        this.type = type;
    }

	public Boolean getActive() {
        return this.active;
    }

	public void setActive(Boolean active) {
        this.active = active;
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

	public WccWelcomkbsReplyType getReplyType() {
        return this.replyType;
    }

	public void setReplyType(WccWelcomkbsReplyType replyType) {
        this.replyType = replyType;
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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("title", "content", "type", "active", "isDeleted", "insertTime", "replyType","platformUser");

	public static final EntityManager entityManager() {
        EntityManager em = new WccWelcomkbs().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccWelcomkbses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccWelcomkbs o", Long.class).getSingleResult();
    }

	public static List<WccWelcomkbs> findAllWccWelcomkbses() {
        return entityManager().createQuery("SELECT o FROM WccWelcomkbs o", WccWelcomkbs.class).getResultList();
    }
	public static List<WccWelcomkbs> findWccWelcomkbsesByPlatformUserAndTypeAndActive(long platformid,int robot) {
		return entityManager().createQuery("SELECT o FROM WccWelcomkbs o WHERE o.type = "+robot+" AND o.platformUser = "+platformid+" AND o.active = 1", WccWelcomkbs.class).getResultList();
	}
	public static List<WccWelcomkbs> findAllWccWelcomkbses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccWelcomkbs o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccWelcomkbs.class).getResultList();
    }

	public static WccWelcomkbs findWccWelcomkbs(Long id) {
        if (id == null) return null;
        return entityManager().find(WccWelcomkbs.class, id);
    }
	

	public static List<WccWelcomkbs> findWccWelcomkbsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccWelcomkbs o", WccWelcomkbs.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccWelcomkbs> findWccWelcomkbsEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccWelcomkbs o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccWelcomkbs.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccWelcomkbs attached = WccWelcomkbs.findWccWelcomkbs(this.id);
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
    public WccWelcomkbs merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccWelcomkbs merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	@ManyToOne
    private WccPlatformUser platformUser;

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}
	
	@ManyToOne
    private WccMaterials materials;

	public WccMaterials getMaterials() {
		return materials;
	}

	public void setMaterials(WccMaterials materials) {
		this.materials = materials;
	}

	public static List<WccWelcomkbs> findWccWelcomkbsesByPlatformUserAndType(
			long platformid, int robot) {
		return entityManager().createQuery(
				"SELECT o FROM WccWelcomkbs o WHERE o.type = " + robot
						+ " AND o.platformUser = " + platformid, WccWelcomkbs.class)
				.getResultList();
	}
	public static List<WccWelcomkbs> findWccWelcomkbsesByMaterial(Long id) {
		return entityManager().createQuery(
				"SELECT o FROM WccWelcomkbs o WHERE o.materials.id = " + id, WccWelcomkbs.class)
				.getResultList();
	}
	private boolean isCustomer;

	public boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}
	
}

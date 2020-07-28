package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
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
import javax.persistence.TypedQuery;
import javax.persistence.Version;
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
/**
 * 点赞表
 * @author
 *
 */
public class WccPraise {

    /**
     */
    private Boolean isDeleted;
    
    /**
     * 粉丝id
     */
    private Long friendId;
    /**
     * 点赞状态
     */
    private Boolean praiseStatus;
    /**
     * 点赞时间
     */
    private Date creatTime;
    
    /**
     * 微内容id
     */
    private Long contentId;
    /**
     * 公众平台id
     */
    private Long platFormId;
    
    private Long companyId;
    

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("type", "isDeleted");

	public static final EntityManager entityManager() {
        EntityManager em = new WccPraise().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccPraises() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccPraise o", Long.class).getSingleResult();
    }

	public static List<WccPraise> findAllWccPraises() {
        return entityManager().createQuery("SELECT o FROM WccPraise o", WccPraise.class).getResultList();
    }

	public static List<WccPraise> findAllWccPraises(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccPraise o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccPraise.class).getResultList();
    }

	public static WccPraise findWccPraise(Long id) {
        if (id == null) return null;
        return entityManager().find(WccPraise.class, id);
    }

	public static List<WccPraise> findWccPraiseEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccPraise o", WccPraise.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	public static List<WccPraise> findWccPraiseByContentId(Long friendId,Long contentId) {
		if(friendId == null ){return null;}
		if(contentId == null ){return null;}
		TypedQuery<WccPraise> query = null;
		if(friendId > 0 ){
			query = entityManager().createQuery("FROM WccPraise o where o.friendId=:friendId and o.contentId =:contentId ",WccPraise.class);
			query.setParameter("contentId", contentId);
			query.setParameter("friendId", friendId);
		}else{
			query = entityManager().createQuery("FROM WccPraise o where o.contentId=:contentId  ",WccPraise.class);
			query.setParameter("contentId", contentId);
		}
		return query.getResultList();
	}

	public static List<WccPraise> findWccPraiseEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccPraise o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccPraise.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccPraise attached = WccPraise.findWccPraise(this.id);
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
    public WccPraise merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccPraise merged = this.entityManager.merge(this);
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

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public Boolean getPraiseStatus() {
		return praiseStatus;
	}

	public void setPraiseStatus(Boolean praiseStatus) {
		this.praiseStatus = praiseStatus;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(Long platFormId) {
		this.platFormId = platFormId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	

}

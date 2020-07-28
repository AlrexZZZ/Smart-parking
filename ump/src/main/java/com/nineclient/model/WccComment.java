package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Persister;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.CommonUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
/**
 * 评论表
 * @author
 *
 */
public class WccComment {

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 评论内容
     */
    @Size(max = 4000)
    private String content;
    
    /**
     * 微内容id
     */
    private Long contentId;
    
    /**
     * 内容标题
     */
    private String contentTitle;
    /**
     * 粉丝id
     */
    private Long friendId;
    /**
     */
    private Date createTime;
    /**
     * 公司Id
     */
    private Long companyId;
    
    @Transient
    private WccFriend wccFriend;
    
    /**
     * 公众平台名称
     */
    private String platFormName;
    
    /**
     * 公众平台
     */
    private Long platFormId;
    /*@Enumerated
    private WccCommentStatus status;*/

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "content", "status");

	public static final EntityManager entityManager() {
        EntityManager em = new WccComment().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccComments() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccComment o", Long.class).getSingleResult();
    }
	
	public static float countWccCommentsByContentIds(String ids,PubOperator pub) {
		String sql = "";
		if(ids != null &&!"".equals(ids)){
			sql = "SELECT COUNT(o) FROM WccComment o where o.platFormId in ("+ids+")";
		}else{
			sql = "SELECT COUNT(o) FROM WccComment o where o.companyId= "+pub.getCompany().getId();
		}
		return entityManager().createQuery(sql, Long.class).getSingleResult();
	}
	
	public static float countWccCommentsByContentId(Long contentId) {
		if(null == contentId) return (float) 0.0;
		TypedQuery<Long> query = entityManager().createQuery(" SELECT COUNT(o) FROM WccComment o where  o.isDeleted = 0 and o.contentId =:contentId ",Long.class);
		query.setParameter("contentId", contentId);
		return query.getSingleResult();
	}

	public static List<WccComment> findAllWccComments() {
        return entityManager().createQuery("SELECT o FROM WccComment o", WccComment.class).getResultList();
    }

	public static List<WccComment> findAllWccComments(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccComment o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccComment.class).getResultList();
    }

	public static WccComment findWccComment(Long id) {
        if (id == null) return null;
        return entityManager().find(WccComment.class, id);
    }
	
	public static List<WccComment> findWccCommentByContentId(Long id) {
        if (id == null) return null;
        TypedQuery<WccComment> query = entityManager().createQuery(" FROM WccComment o where o.contentId =:id and o.isDeleted = 0 order by o.createTime desc ",WccComment.class); 
        query.setParameter("id", id);
        return query.getResultList();
    }
	
	public static List<WccComment> findWccCommentByContentId2(int firstResult,int maxResults, Long contentId,PubOperator pub) {
		 if (contentId == null) return null;
	        String hql = "SELECT o FROM WccComment o where o.contentId = "+contentId+" and o.companyId="+pub.getCompany().getId()+" and o.isDeleted = 0 order by o.createTime desc";
		 return entityManager().createQuery(hql, WccComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static List<WccComment> findWccCommentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccComment o", WccComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccComment> findWccCommentEntries(int firstResult, int maxResults,String ids,PubOperator pub) {
		String jpaQuery = "";
		if(ids != null && !"".equals(ids)){
			jpaQuery = "SELECT o FROM WccComment o where 1=1 and o.platFormId in ("+ids+") order by o.createTime desc";
		}else{
			jpaQuery = "SELECT o FROM WccComment o where 1=1 and o.companyId ="+pub.getCompany().getId()+" order by o.createTime desc";
		}
        return entityManager().createQuery(jpaQuery, WccComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccComment attached = WccComment.findWccComment(this.id);
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
    public WccComment merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccComment merged = this.entityManager.merge(this);
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

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public WccFriend getWccFriend() {
		return wccFriend;
	}

	public void setWccFriend(WccFriend wccFriend) {
		this.wccFriend = wccFriend;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public Long getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(Long platFormId) {
		this.platFormId = platFormId;
	}

	
	public static String toJsonArray(Collection<WccComment> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<WccComment> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<WccComment> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<WccComment>>()
        .use("values", WccComment.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static List<WccComment> findWccCommentEntriesByFiled(
			int firstResult, int maxResults, String platId, String title,PubOperator pub,Long contentId) {
		String jpaQuery = "SELECT o FROM WccComment o where 1=1 and o.companyId = "+pub.getCompany().getId();
   	    if (null != platId && !"".equals(platId)) {
            jpaQuery = jpaQuery + " and o.platFormId in ("+platId+")";
        }
        if (null != title && !"".equals(title)) {
            jpaQuery = jpaQuery + " and o.contentTitle like '%"+title+"%'";
        }
        if (null != contentId && !"".equals(contentId)){
        	 jpaQuery = jpaQuery + " and o.contentId = "+ contentId;
        }
        jpaQuery = jpaQuery + " and o.isDeleted = 0 ORDER BY o.createTime desc";
        return entityManager().createQuery(jpaQuery, WccComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Long countWccCommentByFiled(String platId, String title) {
		 String jpaQuery = "SELECT COUNT(o) FROM WccComment o where 1=1 and o.isDeleted = 0 ";
	   	 if (null != platId && !"".equals(platId)) {
	         jpaQuery = jpaQuery + " and o.platFormId in("+platId+")";
	     }
	     if (null != title && !"".equals(title)) {
	         jpaQuery = jpaQuery + " and o.contentTitle like '%"+title+"%'";
	     }
		 return entityManager().createQuery(jpaQuery, Long.class).getSingleResult();
	}

	public static List<WccComment> findWccCommentByContentId3(int firstResult,
			int maxResults) {
		 return entityManager().createQuery("SELECT o FROM WccComment o", WccComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getPlatFormName() {
		return platFormName;
	}

	public void setPlatFormName(String platFormName) {
		this.platFormName = platFormName;
	}
	
}

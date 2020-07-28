package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
import javax.persistence.Version;

/**
 * wcc实用工具
 */
@Configurable
@Entity
public class WccUtils {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

    /**
     * 实用工具名称
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 200)
    private String utilName;

    /**
     * 工具介绍
     */
    @NotNull
    private String utilDesc;

    /**
     * 工具url
     */
    @NotNull
    private String utilUrl;

    /**
     * 备注
     */
    private String remark;


    /**
     */
    @Value("0")
    private Boolean isDeleted;

    /**
     * 创建日期
     */
    private Date createTime;
    
    /**
     * 创建者
     */
    private String userName;
    
	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new WccUtils().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
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
    public WccUtils merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccUtils merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

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

	public String getUtilName() {
		return utilName;
	}

	public void setUtilName(String utilName) {
		this.utilName = utilName;
	}

	public String getUtilDesc() {
		return utilDesc;
	}

	public void setUtilDesc(String utilDesc) {
		this.utilDesc = utilDesc;
	}

	public String getUtilUrl() {
		return utilUrl;
	}

	public void setUtilUrl(String utilUrl) {
		this.utilUrl = utilUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static List<WccUtils> findWccUtilsEntries(int firstResult, int maxResults) {
		 String jpaQuery = "SELECT o FROM WccUtils o order by o.createTime desc";
		 return entityManager().createQuery(jpaQuery, WccUtils.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}
	
	public static List<WccUtils> findAllWccUtils() {
		 String jpaQuery = "SELECT o FROM WccUtils o order by o.createTime desc";
		return entityManager().createQuery(jpaQuery, WccUtils.class).getResultList();
	}

	public static long countWccUtils() {
		 return entityManager().createQuery("SELECT COUNT(o) FROM WccUtils o", Long.class).getSingleResult();
	}

	public static WccUtils findWccUtils(Long id) {
		 if (id == null) return null;
	        return entityManager().find(WccUtils.class, id);
	}

	 @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
        	WccUtils attached = WccUtils.findWccUtils(this.id);
            this.entityManager.remove(attached);
        }
    }

	
}

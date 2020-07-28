package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;

import com.nineclient.constant.WccActionType;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

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
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccQrcode {

	/**
     */
    @Size(max = 4000)
    private String codeUrl;

    /**
     */
    @Size(max = 4000)
    private String codeId;
    /**
     */
    private Boolean isUse;

    /**
     */
    private float expireSeconds;
    
    private int actionType;
    
    private Long wccStroeId;

    /**
     */
    @NotNull
    @Size(max = 30)
    private String useType;

    /**
     */
    private Boolean isDeleted;

    /**
     * 生成二维码所需要
     */
    private int sceneId;
    
    public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	
	public Long getWccStroeId() {
		return wccStroeId;
	}

	public void setWccStroeId(Long wccStroeId) {
		this.wccStroeId = wccStroeId;
	}

	/**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
	 * 关联公司
	 */
	@ManyToOne
	private UmpCompany company;
	
	@ManyToOne
	private WccPlatformUser platformUser;
	
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

	public String getCodeUrl() {
        return this.codeUrl;
    }

	public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

	public String getCodeId() {
        return this.codeId;
    }

	public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

	public Boolean getIsUse() {
        return this.isUse;
    }

	public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

	public float getExpireSeconds() {
        return this.expireSeconds;
    }

	public void setExpireSeconds(float expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

	
	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public String getUseType() {
        return this.useType;
    }

	public void setUseType(String useType) {
        this.useType = useType;
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

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("codeUrl", "codeId", "isUse", "expireSeconds", "actionType", "useType", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new WccQrcode().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccQrcodes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccQrcode o", Long.class).getSingleResult();
    }

	public static List<WccQrcode> findAllWccQrcodes() {
        return entityManager().createQuery("SELECT o FROM WccQrcode o", WccQrcode.class).getResultList();
    }
	
	public static WccQrcode findWccQrcodeByScendId(String useType,int scenId) {
		WccQrcode wccQrcode = null;
		List<WccQrcode> qrcodeList = entityManager().createQuery("SELECT o FROM WccQrcode o where o.isUse = 1 and o.useType = "+useType+" and o.sceneId="+scenId, WccQrcode.class).getResultList();
		if(null != qrcodeList && qrcodeList.size() > 0){
    	   wccQrcode = qrcodeList.get(0);
        }
       return wccQrcode;
    }

	public static List<WccQrcode> findAllWccQrcodes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQrcode o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQrcode.class).getResultList();
    }

	public static WccQrcode findWccQrcode(Long id) {
        if (id == null) return null;
        return entityManager().find(WccQrcode.class, id);
    }

	public static List<WccQrcode> findWccQrcodeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccQrcode o", WccQrcode.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccQrcode> findWccQrcodeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQrcode o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQrcode.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccQrcode attached = WccQrcode.findWccQrcode(this.id);
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
    public WccQrcode merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccQrcode merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static WccQrcode findWccQrcode(UmpCompany company,WccPlatformUser platformUser, long actionType) {
		actionType = 2l;//默认为2
		
		return null;
	}

	public static int getMaxSceneid(int actionType) {
		StringBuilder sql = new StringBuilder("SELECT MAX(o.sceneId) FROM WccQrcode o where 1=1");
		return entityManager().createQuery(sql.toString(), Integer.class).getSingleResult();
	}

	public static WccQrcode findWccQrcodeByScenId(int sceneId) {
		if(sceneId == 0){return null;}
		TypedQuery<WccQrcode> query = entityManager().createQuery("FROM WccQrcode o where  o.sceneId =:sceneId ",WccQrcode.class);
		query.setParameter("sceneId", sceneId);
		return query.getSingleResult();
	}
	
	public static WccQrcode findWccQrcodeByScenIdToOn(int sceneId) {
		if(sceneId == 0){return null;}
		WccQrcode wccQrcode = null;
		TypedQuery<WccQrcode> query = entityManager().createQuery(" FROM WccQrcode o where  o.sceneId =:sceneId and o.useType = 0 ",WccQrcode.class);
		query.setParameter("sceneId", sceneId);
		List<WccQrcode> wccQrcodeList = query.getResultList();
		if(null != wccQrcodeList && wccQrcodeList.size() > 0){
			wccQrcode = wccQrcodeList.get(0);
		}
		return wccQrcode;
	}
	
	public static WccQrcode findWccQrcodeByScendIdAndPlatform(int sceneId,WccPlatformUser wccPlatform) {
		if(sceneId == 0){return null;}
		if(wccPlatform == null){return null;}
		WccQrcode wccQrcode = null;
		TypedQuery<WccQrcode> query = entityManager().createQuery(" FROM WccQrcode o where  o.sceneId =:sceneId and o.platformUser=:platformUser ",WccQrcode.class);
		query.setParameter("sceneId", sceneId);
		query.setParameter("platformUser", wccPlatform);
		List<WccQrcode> wccQrcodeList = query.getResultList();
		if(null != wccQrcodeList && wccQrcodeList.size() > 0){
			wccQrcode = wccQrcodeList.get(0);
		}
		return wccQrcode;
	}
	
	public static List<WccQrcode> findWccQrcodeByStoreIdAndPlatFormId(Long storeId,long platFormId) {
		if(storeId == 0l)return null;
		if(platFormId == 0l)return null;
		String sql = "SELECT o FROM WccQrcode o where  o.wccStroeId = "+storeId+" and o.platformUser ="+platFormId;
		return entityManager().createQuery(sql, WccQrcode.class).getResultList();
	}

	public static  List<WccQrcode> findWccQrcodeByPlatform(Boolean bool,Long platform) {
		StringBuilder sql = new StringBuilder("SELECT o FROM WccQrcode o where  o.isUse = "+bool+" and o.platformUser="+platform);
		return entityManager().createQuery(sql.toString(), WccQrcode.class).getResultList();
	}
	
	public UmpCompany getCompany() {
		return company;
	}

	public void setCompany(UmpCompany company) {
		this.company = company;
	}
}

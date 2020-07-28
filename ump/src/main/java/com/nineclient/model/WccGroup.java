package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class WccGroup {

    /**
     */
    @NotNull
    @Size(min = 1, max = 300)
    private String name;

    /**
     */
    private Boolean isDelete;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date insertTime;

    private long groupId;
    
    @ManyToOne
    private WccPlatformUser platformUser;

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Boolean getIsDelete() {
        return this.isDelete;
    }

	public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

	public Date getInsertTime() {
        return this.insertTime;
    }

	public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

	public WccPlatformUser getPlatformUser() {
        return this.platformUser;
    }

	public void setPlatformUser(WccPlatformUser platformUser) {
        this.platformUser = platformUser;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "isDelete", "insertTime", "platformUser");

	public static final EntityManager entityManager() {
        EntityManager em = new WccGroup().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	/**
	 * 根据name查询分组
	 * add by hunter
	 * @param ids
	 * @return
	 */
	public static List<WccGroup> findWccGroupByName(Long platformUserId,String name) {
        if (name == null||"".equals(name)) return null;
        return entityManager().createQuery("SELECT o FROM WccGroup o where o.name ='"+name+"' AND o.platformUser = "+platformUserId, WccGroup.class).getResultList();
    }
	
	
	public static List<WccGroup> findWccGroupByName2(String platformUserId,String name) {
        if (name == null||"".equals(name)) return null;
        return entityManager().createQuery("SELECT o FROM WccGroup o where o.name ='"+name+"' AND o.platformUser in ("+platformUserId+")", WccGroup.class).getResultList();
    }
	/**
	 * 根据groupId查询分组
	 * add by hunter
	 * @param ids
	 * @return
	 */
	public static WccGroup findWccGroupByGroupId(Long platformUserId,Long gourpId) {
        if (gourpId == null) return null;
        WccGroup group = null;
        List<WccGroup> groups = entityManager().createQuery("SELECT o FROM WccGroup o where o.groupId ="+gourpId +" AND o.platformUser = "+platformUserId, WccGroup.class).getResultList();
        if(groups.size()>0){
        	group = groups.get(0);
        }
        return group;
	}
	
	/**
	 * 根据公众平台查分组
	 * add by hunter
	 * @param ids
	 * @return
	 */
	public static List<WccGroup> findGroupByPlatformUser(String platformUserId) {
        if (platformUserId == null||"".equals(platformUserId)) return null;
        return entityManager().createQuery("SELECT o FROM WccGroup o where o.platformUser ='"+platformUserId+"'", WccGroup.class).getResultList();
    }


	public static long countWccGroups() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccGroup o", Long.class).getSingleResult();
    }

	public static List<WccGroup> findAllWccGroups() {
        return entityManager().createQuery("SELECT o FROM WccGroup o", WccGroup.class).getResultList();
    }

	public static List<WccGroup> findAllWccGroups(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccGroup o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccGroup.class).getResultList();
    }
	
	public static List<WccGroup> findAllWccGroupList(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccGroup o where o.platformUser is null ";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccGroup.class).getResultList();
    }
	
	public static List<WccGroup> findWccGroupByName(String name){
		   String jpaQuery = "SELECT o FROM WccGroup o where o.name ='"+name+"' ";
	       if (name == null) return null;
	       return entityManager().createQuery(jpaQuery, WccGroup.class).getResultList();
	}
	
	public static WccGroup ckeckWccGroupByName(String name) {
		if (name == null) return null;
        WccGroup group = null;
        List<WccGroup> groups = findWccGroupByName(name);
        if(groups.size()>0){
        	group = groups.get(0);
        }
        return group;
    }

	public static WccGroup findWccGroup(Long id) {
        if (id == null) return null;
        return entityManager().find(WccGroup.class, id);
    }

	public static List<WccGroup> findWccGroupEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccGroup o", WccGroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccGroup> findWccGroupEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccGroup o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccGroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccGroup attached = WccGroup.findWccGroup(this.id);
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
    public WccGroup merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccGroup merged = this.entityManager.merge(this);
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

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public static WccGroup findWccDefaultGroup(Long platformUserId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}

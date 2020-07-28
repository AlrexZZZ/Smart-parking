package com.nineclient.model;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpAuthority implements java.io.Serializable{


	/**
     */
    @Size(max = 32)
    private String parentId;

    /**
     */
    @NotNull
    @Size(max = 50)
    private String displayName;

    /**
     */
    @NotNull
    @Size(max = 255)
    private String url;

    /**
     */
    @Size(max = 255)
    private String image;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    @NotNull
    private int sort;

    /**
     */
    private Boolean isVisable;

    /**
     */
    private Boolean isDeleted;
    
    @ManyToMany(fetch=FetchType.LAZY,mappedBy="umpAuthoritys")
    private Set<UmpRole>  umpRoles;
    
    @ManyToMany(fetch=FetchType.LAZY,mappedBy="authoritys")
    private Set<PubRole> pubRoles;

	public Set<PubRole> getPubRoles() {
		return pubRoles;
	}

	public void setPubRoles(Set<PubRole> pubRoles) {
		this.pubRoles = pubRoles;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("parentId", "displayName", "url", "image", "remark", "sort", "isVisable", "isDeleted", "umpRoles");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpAuthority().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpAuthoritys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpAuthority o", Long.class).getSingleResult();
    }

	public static List<UmpAuthority> findAllUmpAuthoritys() {
        return entityManager().createQuery("SELECT o FROM UmpAuthority o where o.product=1", UmpAuthority.class).getResultList();
    }
	public static List<UmpAuthority> findUmpAuthoritysProductIsUmp(Long productId) {
		return entityManager().createQuery("FROM UmpAuthority WHERE product.id ="+productId, UmpAuthority.class).getResultList();
	}
	/**
	 *  add by hunter
	 * @param versionId
	 * @return
	 */
	public static List<UmpAuthority> findAllUmpAuthoritysByVersion(Long versionId) {
        return entityManager().createQuery("SELECT o FROM UmpAuthority o join o.umpVersions v where v.id="+versionId, UmpAuthority.class).getResultList();
    }
	
	/**
	 * 根据产品查询菜单
	 * @param versionId
	 * @return
	 */
	public static List<UmpAuthority> findAllUmpAuthoritysByProduct(Long id) {
        return entityManager().createQuery("SELECT o FROM UmpAuthority o where o.product="+id, UmpAuthority.class).getResultList();
    }
	/**
	 *  add by hunter
	 * @param versionId
	 * @return
	 */
	public static List<UmpAuthority> findAllUmpAuthoritysByProductId(Long productId) {
        return entityManager().createQuery("SELECT o FROM UmpAuthority o join o.product p where p.id="+productId, UmpAuthority.class).getResultList();
    }
	public static List<UmpAuthority> findAllUmpAuthoritys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpAuthority o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpAuthority.class).getResultList();
    }

	public static UmpAuthority findUmpAuthority(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpAuthority.class, id);
    }
	/**
	 * 根据id查询菜单
	 * add by hunter
	 * @param ids
	 * @return
	 */
	public static List<UmpAuthority> findUmpAuthorityByIds(String ids) {
        if (ids == null) return null;
        
        return entityManager().createQuery("SELECT o FROM UmpAuthority o where o.id in("+ids+")", UmpAuthority.class).getResultList();
    }
	
	/**
	 * 根据name查询菜单
	 * add by hunter
	 * @param ids
	 * @return
	 */
	public static List<UmpAuthority> findUmpAuthorityByName(String name) {
        if (name == null||"".equals(name)) return null;
        return entityManager().createQuery("SELECT o FROM UmpAuthority o where o.displayName ='"+name+"'", UmpAuthority.class).getResultList();
    }
	
	public static List<UmpAuthority> findUmpAuthorityEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpAuthority o", UmpAuthority.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpAuthority> findUmpAuthorityEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpAuthority o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpAuthority.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpAuthority attached = UmpAuthority.findUmpAuthority(this.id);
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
    public UmpAuthority merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpAuthority merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="umpAuthoritys")
	private Set<UmpVersion> umpVersions;
	@ManyToOne
	private UmpProduct product;
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

	public String getParentId() {
        return this.parentId;
    }

	public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public String getDisplayName() {
        return this.displayName;
    }

	public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

	public String getUrl() {
        return this.url;
    }

	public void setUrl(String url) {
        this.url = url;
    }

	public String getImage() {
        return this.image;
    }

	public void setImage(String image) {
        this.image = image;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public int getSort() {
        return this.sort;
    }

	public void setSort(int sort) {
        this.sort = sort;
    }

	public Boolean getIsVisable() {
        return this.isVisable;
    }

	public void setIsVisable(Boolean isVisable) {
        this.isVisable = isVisable;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Set<UmpRole> getUmpRoles() {
        return this.umpRoles;
    }

	public void setUmpRoles(Set<UmpRole> umpRoles) {
        this.umpRoles = umpRoles;
    }

	public Set<UmpVersion> getUmpVersions() {
		return umpVersions;
	}

	public void setUmpVersions(Set<UmpVersion> umpVersions) {
		this.umpVersions = umpVersions;
	}

	public UmpProduct getProduct() {
		return product;
	}

	public void setProduct(UmpProduct product) {
		this.product = product;
	}

	public static List<UmpAuthority> findUmpAuthoritysVersionIdIsUmp(UmpVersion uv) {
		StringBuilder hql = new StringBuilder();
		hql.append("select o FROM UmpAuthority o inner join o.umpVersions umpver where umpver.id=").append(uv.getId());
		return entityManager().createQuery(hql.toString(), UmpAuthority.class).getResultList();
	}
	
}

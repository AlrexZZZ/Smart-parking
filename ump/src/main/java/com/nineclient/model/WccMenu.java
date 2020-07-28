package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
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
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMenu {

    /**
     * 菜单名称
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String menuName;

    /**
     * 菜单回复所需的key
     */
    private String mkey;

    /**
     * 菜单类型
     */
    private long type;

    /**
     * 菜单父id
     */
    private long parentId;

    /**
     * 菜单排序
     */
    private long sort;

    /**
     * 菜单备注
     */
    @Size(max = 4000)
    private String remark;
    
    /**
     * 公司id
     */
    private Long companyId;
    
    /**
     * 菜单连接
     */
    @Size(max = 4000)
    private String url;

    /**
     * 菜单回复内容
     */
    @Size(max = 4000)
    private String content;

    /**
     * 是否验证
     */
    private long flag;
    
    /**
     * 是否删除
     */
    private Boolean isDeleted;

	/**
	 * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    
    /**
     * 回复类型 1、文本，2、图片，3、图文，4、语音，5，视频
     */
    private int contentSelect;
    
    /**
     * id、主键、自增长
     */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	/**
	 * 版本，修改需要。
	 */
	@Version
    @Column(name = "version")
    private Integer version;

	/**
	 * 关联素材
	 */
	@ManyToOne
	private WccMaterials materials;
	
	/**
	 * 关联公众平台
	 */
	@ManyToOne
	private WccPlatformUser platformUser;
	
	/*public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }*/

	/**
	 * 根据字段排序
	 */
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("menuName", "mkey", "type", "parentId", "sort", "remark", "url", "content", "token", "flag");

	@PersistenceContext
    transient EntityManager entityManager;
	
	public static final EntityManager entityManager() {
        EntityManager em = new WccMenu().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccMenus(PubOperator pub) {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccMenu o where o.companyId = "+pub.getCompany().getId(), Long.class).getSingleResult();
    }

	public static List<WccMenu> findAllWccMenus() {
        return entityManager().createQuery("SELECT o FROM WccMenu o", WccMenu.class).getResultList();
    }

	public static List<WccMenu> findAllWccMenus(String sortFieldName, String sortOrder,PubOperator pub) {
        String jpaQuery = "SELECT o FROM WccMenu o where 1=1 and o.companyId = "+pub.getCompany().getId()+" ORDER BY o.sort";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccMenu.class).getResultList();
    }

	public static WccMenu findWccMenu(Long id) {
        if (id == null) return null;
        return entityManager().find(WccMenu.class, id);
    }

	public static List<WccMenu> findWccMenuEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccMenu o", WccMenu.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccMenu> findWccMenuEntries(String sortFieldName, String sortOrder,PubOperator pub) {
        String jpaQuery = "SELECT o FROM WccMenu o where o.companyId = "+pub.getCompany().getId()+" ORDER BY o.sort";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccMenu.class).getResultList();
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
            WccMenu attached = WccMenu.findWccMenu(this.id);
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
    public WccMenu merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccMenu merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static List<WccMenu> findAllWccMenusByName(String menuName) {
			if (menuName == null||"".equals(menuName)) return null;
		     return entityManager().createQuery("SELECT o FROM WccMenu o where o.menuName ='"+menuName+"'", WccMenu.class).getResultList();
		}

	public static List<WccMenu> findAllWccMenusById(long id, boolean type) {
		if (id == 0) return null;
		if(type == true){
			 return entityManager().createQuery("SELECT o FROM WccMenu o where o.id ='"+id+"'", WccMenu.class).getResultList();
		}else{
			return entityManager().createQuery("SELECT o FROM WccMenu o where o.parentId ='"+id+"'", WccMenu.class).getResultList();
		}
	}

	public static List<WccMenu> findAllWccMenusByPid(WccPlatformUser platform,PubOperator pub) {
		if (null == platform) return null;
		if (null == pub) return null;
		TypedQuery<WccMenu>  query = entityManager().createQuery("SELECT o FROM WccMenu o where o.parentId = 0 and o.platformUser =:platformUser and o.companyId=:companyId ", WccMenu.class);
		query.setParameter("platformUser", platform);
		query.setParameter("companyId", pub.getCompany().getId());
		return query.getResultList();
	}
	
	public static List<WccMenu> findAllWccMenusBypId(Long pId,PubOperator pub) {
		if (null == pId) return null;
		if (null == pub) return null;
		TypedQuery<WccMenu> query = entityManager().createQuery("SELECT o FROM WccMenu o where o.parentId =:pId and o.companyId=:companyId ", WccMenu.class);
		query.setParameter("pId", pId);
		query.setParameter("companyId", pub.getCompany().getId());
		return query.getResultList();
	}
	
	public static List<WccMenu> findAllWccMenusByPrentId(Long parentId,PubOperator pub){
		if (null == pub) return null;
		TypedQuery<WccMenu> query = entityManager().createQuery("SELECT o FROM WccMenu o where o.parentId =:parentId and o.companyId=:companyId ", WccMenu.class);
		query.setParameter("parentId", parentId);
		query.setParameter("companyId", pub.getCompany().getId());
		return query.getResultList();
	}

	/**
	 * 根据公众平台和name查询菜单名称是否存在
	 * @param name
	 * @param platform
	 * @return
	 */
	public static List<WccMenu> findAllWccMenusByMenuName(String name,WccPlatformUser platform) {
		if(null == name)return null;
		if(null == platform)return null;
		TypedQuery<WccMenu> query = entityManager().createQuery(" FROM WccMenu o where o.menuName =:name and o.platformUser =:platform ", WccMenu.class);
		query.setParameter("name", name);
		query.setParameter("platform", platform);
		return query.getResultList();
	}

	public static WccMenu findWccMenuByPlatId(Long platId) {
		 if (platId == null) return null;
		 return entityManager().createQuery("SELECT o FROM WccMenu o where o.platformUser ="+platId+"", WccMenu.class).getSingleResult();
	}

	/**
	 * 查询公众平台下的一级菜单
	 * @param id
	 * @return
	 */
	public static List<WccMenu> findWccMenuById(Long id) {
		 return entityManager().createQuery("SELECT o FROM WccMenu o where o.parentId = 0 and o.platformUser ="+id+"", WccMenu.class).getResultList();
	}
	public static List<WccMenu> findWccMenuByMaterial(Long id) {
		return entityManager().createQuery("SELECT o FROM WccMenu o where o.materials.id="+id, WccMenu.class).getResultList();
	}
	
	/**
	 * 查询二级菜单
	 * @param id
	 * @return
	 */
	public static List<WccMenu> findWccMenuByParentId(Long id) {
		 return entityManager().createQuery("SELECT o FROM WccMenu o where o.parentId ="+id+"", WccMenu.class).getResultList();
	}

	public static WccMenu findWccMenuByType(Long type) {
		 return entityManager().createQuery("SELECT o FROM WccMenu o where o.type ="+type+"", WccMenu.class).getSingleResult();
	}

	public static WccMenu findWccMenuByKeyAndPlatForm(String key,Long platId) {
		return entityManager().createQuery("SELECT o FROM WccMenu o where o.mkey ='"+key+"' and o.platformUser="+platId, WccMenu.class).getSingleResult();
	}

	public static List<WccMenu> findAllWccMenusByKeyExits(String mkey,WccPlatformUser wccPlat) {
		if(null == mkey)return null;
		if(null == wccPlat)return null;
		TypedQuery<WccMenu> query = entityManager().createQuery(" FROM WccMenu o where o.mkey =:mkey and o.platformUser =:platformUser ",WccMenu.class);
		query.setParameter("mkey", mkey);
		query.setParameter("platformUser", wccPlat);
		return query.getResultList();
	}
	
	/**
	 * 微内容删除判断菜单是否引用
	 * @param id
	 * @param menuUrl
	 * @return
	 */
	public static List<WccMenu> findIdToWccMenu(String menuUrl) {
		if(null == menuUrl)return null;
		TypedQuery<WccMenu> query = entityManager().createQuery(" FROM WccMenu o where o.url =:menuUrl",WccMenu.class);
		query.setParameter("menuUrl", menuUrl);
		return query.getResultList();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Boolean getIsDeleted() {
			return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMenuName() {
        return this.menuName;
    }

	public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

	public String getMkey() {
        return this.mkey;
    }

	public void setMkey(String mkey) {
        this.mkey = mkey;
    }

	public long getType() {
        return this.type;
    }

	public void setType(long type) {
        this.type = type;
    }

	public long getParentId() {
        return this.parentId;
    }

	public void setParentId(long parentId) {
        this.parentId = parentId;
    }

	public long getSort() {
        return this.sort;
    }

	public void setSort(long sort) {
        this.sort = sort;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getUrl() {
        return this.url;
    }

	public void setUrl(String url) {
        this.url = url;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	public long getFlag() {
        return this.flag;
    }

	public void setFlag(long flag) {
        this.flag = flag;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getContentSelect() {
		return contentSelect;
	}

	public void setContentSelect(int contentSelect) {
		this.contentSelect = contentSelect;
	}
	
	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public WccMaterials getMaterials() {
		return materials;
	}

	public void setMaterials(WccMaterials materials) {
		this.materials = materials;
	}

}

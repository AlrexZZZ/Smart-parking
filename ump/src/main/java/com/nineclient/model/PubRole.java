package com.nineclient.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PubRole  implements java.io.Serializable{

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 是否可见
     */
    private Boolean isVisable;

    /**
     * 权限组名称
     */
    @Size(min = 1, max = 100)
    private String roleName;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     * 备注
     */
    @Size(max = 200)
    private String remark;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<UmpAuthority> authoritys;
	
	
	private Long companyId;
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public List<UmpAuthority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<UmpAuthority> authoritys) {
		this.authoritys = authoritys;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "isVisable", "roleName", "createTime", "remark");

	public static final EntityManager entityManager() {
        EntityManager em = new PubRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPubRoles(Long companyId) {
        return entityManager().createQuery("SELECT COUNT(o) FROM PubRole o where o.companyId="+companyId, Long.class).getSingleResult();
    }

	public static List<PubRole> findAllPubRoles(UmpCompany company) {
		if(null == company)return null;
		TypedQuery<PubRole> query = entityManager().createQuery("FROM PubRole o where o.companyId =:companyId ",PubRole.class);
		query.setParameter("companyId", company.getId());
		return query.getResultList();
    }

	public static List<PubRole> findAllPubRoles(String sortFieldName, String sortOrder,UmpCompany company) {
		String jpaQuery = "SELECT o FROM PubRole o where o.companyId = :companyId ORDER BY o.createTime DESC ";
        TypedQuery<PubRole> query = entityManager().createQuery(jpaQuery, PubRole.class);
        query.setParameter("companyId", company.getId());
        return query.getResultList();
    }

	public static PubRole findPubRole(Long id) {
        if (id == null) return null;
        return entityManager().find(PubRole.class, id);
    }

	public static List<PubRole> findPubRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PubRole o", PubRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	public static List<PubRole> findPubRoleByName(String name,Long companyId) {
		String qlStr = "SELECT o FROM PubRole o WHERE o.roleName = :name AND o.companyId = :companyId";
		TypedQuery<PubRole> query = entityManager().createQuery(qlStr, PubRole.class);
		query.setParameter("name", name);
		query.setParameter("companyId", companyId);
        return query.getResultList();
    }

	public static List<PubRole> findPubRoleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder,Long companyId) {
        String jpaQuery = "SELECT o FROM PubRole o where o.companyId=:companyId ORDER BY o.createTime DESC";
        TypedQuery<PubRole> query = entityManager().createQuery(jpaQuery, PubRole.class);
        query.setParameter("companyId", companyId);
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return query.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            PubRole attached = PubRole.findPubRole(this.id);
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
    public PubRole merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PubRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Boolean getIsVisable() {
        return this.isVisable;
    }

	public void setIsVisable(Boolean isVisable) {
        this.isVisable = isVisable;
    }

	public String getRoleName() {
        return this.roleName;
    }

	public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
    }
	
	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "pubRole")
	 private Set<PubOperator> pubOperators = new HashSet<PubOperator>();

	public Set<PubOperator> getPubOperators() {
		return pubOperators;
	}

	public void setPubOperators(Set<PubOperator> pubOperators) {
		this.pubOperators = pubOperators;
	}
	
	
	public static PageModel<PubRole> getQueryPubRole(QueryModel<PubRole> qm){
		 Map parmMap = qm.getInputMap();
		 PubRole model = qm.getEntity();
		PageModel<PubRole> pageModel = new PageModel<PubRole>();
		Long companyId = Long.parseLong(parmMap.get("company")+"");
		StringBuffer sql = new StringBuffer(" SELECT o FROM PubRole o WHERE o.companyId = :companyId ORDER BY o.createTime DESC");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
       TypedQuery<PubRole> query = entityManager().createQuery(sql.toString(), PubRole.class);
       for(String key:map.keySet()){
           query.setParameter(key, map.get(key));	
         }
       System.out.println(query.toString());
          int totalCount = 0;
          
          { //查总数
           TypedQuery<Long> countQuery = entityManager().createQuery(sql.toString().replaceAll(" SELECT o", "SELECT count(o)"), Long.class);
           for(String key:map.keySet()){
           	countQuery.setParameter(key, map.get(key));	
             }
           totalCount = countQuery.getSingleResult().intValue();
          }
        
        if(qm.getLimit() > 0){ //分页截取
        	query.setFirstResult(qm.getStart());
            query.setMaxResults(qm.getLimit());
        }
        List<PubRole> list = query.getResultList();
        pageModel.setTotalCount(totalCount);
        pageModel.setDataList(query.getResultList());
        return pageModel;
	}
}

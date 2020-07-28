package com.nineclient.model;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpRole implements java.io.Serializable {

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(max = 30)
    private String roleName;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    private Boolean isVisable;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startTime;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    @Size(max = 32)
    private String parentId;
    
    @ManyToMany
    private List<UmpAuthority> umpAuthoritys;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("roleName", "remark", "isDeleted", "isVisable", "startTime", "createTime", "parentId", "umpAuthoritys","startTime");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpRoles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpRole o", Long.class).getSingleResult();
    }

	public static List<UmpRole> findAllUmpRoles() {
        return entityManager().createQuery("SELECT o FROM UmpRole o where  o.isVisable=1 order by o.createTime desc", UmpRole.class).getResultList();
    }

	public static List<UmpRole> findAllUmpRoles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpRole o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpRole.class).getResultList();
    }

	public static UmpRole findUmpRole(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpRole.class, id);
    }

	public static List<UmpRole> findUmpRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpRole o", UmpRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpRole> findUmpRoleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpRole o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpRole attached = UmpRole.findUmpRole(this.id);
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
    public UmpRole merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getRoleName() {
        return this.roleName;
    }

	public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
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

	public Date getStartTime() {
        return this.startTime;
    }

	public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getParentId() {
        return this.parentId;
    }

	public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	
	public List<UmpAuthority> getUmpAuthoritys() {
		return umpAuthoritys;
	}

	public void setUmpAuthoritys(List<UmpAuthority> umpAuthoritys) {
		this.umpAuthoritys = umpAuthoritys;
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
	 /**
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "urole")
    private Set<UmpOperator> operators = new HashSet<UmpOperator>();

	public Set<UmpOperator> getOperators() {
        return this.operators;
    }

	public void setOperators(Set<UmpOperator> operators) {
        this.operators = operators;
    }
	
	public static PageModel<UmpRole> getQueryUmpRole(QueryModel<UmpRole> qm){
		 Map parmMap = qm.getInputMap();
		PageModel<UmpRole> pageModel = new PageModel<UmpRole>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM UmpRole o ORDER BY o.createTime DESC");
		Map<String,Object> map = new HashMap<String, Object>();
      TypedQuery<UmpRole> query = entityManager().createQuery(sql.toString(), UmpRole.class);
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
       List<UmpRole> list = query.getResultList();
       pageModel.setTotalCount(totalCount);
       pageModel.setDataList(query.getResultList());
       return pageModel;
	}
	
	public static List<UmpRole> findUmpRoleByName(String name) {
        return entityManager().createQuery("SELECT o FROM UmpRole o WHERE o.roleName = '"+name+"'", UmpRole.class).getResultList();
    }
}

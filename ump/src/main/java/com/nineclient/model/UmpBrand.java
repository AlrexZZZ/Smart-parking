package com.nineclient.model;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.constant.UmpCheckStatus;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class UmpBrand {
	/**
	 * 
	 */
	@ManyToOne
	private UmpParentBusinessType business;
    /**
     */
    @Enumerated
    private UmpCheckStatus checkStatus;

    /**
     */
    private Boolean isDeleted;
    
    
    @OneToMany(mappedBy="brand")
    private Set<VocTemplate> template = new HashSet<VocTemplate>();

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime=new Date();

    /**
     */
    @Size(max = 4000)
    private String remark;
    /**
     * 
     * @param firstResult
     * @param maxResults
     * @param sortFieldName
     * @param sortOrder
     * @param isAdimin
     * @param map
     * @return
     */
	public static List<UmpBrand> findUmpBrandEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder, Boolean isAdimin,Map<String,Object> map) {
    	 StringBuilder jpaQuery = new StringBuilder("SELECT o FROM UmpBrand o join o.business b where o.isDeleted=0 ");
        String brandName = (String) (map.get("brandName") == null ? "" : map.get("brandName"));
        String businessTypeId = (String) (map.get("businessTypeId") == null ? "" : map.get("businessTypeId"));
        String keyName = (String) (map.get("keyName") == null ? "" : map.get("keyName"));
        
        if (!StringUtils.isEmpty(brandName.trim())) {
            jpaQuery.append(" and o.brandName like '%" + brandName + "%'");
        }
        if (!StringUtils.isEmpty(businessTypeId.trim())) {
            jpaQuery.append(" and b.id=" + businessTypeId);
        }
        if (!StringUtils.isEmpty(keyName.trim())) {
            jpaQuery.append(" and o.keyName like '%" + keyName+"%'");
        }
        jpaQuery.append(" order by o.createTime desc");
        return entityManager().createQuery(jpaQuery.toString(), UmpBrand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<UmpBrand> findUmpBrandCheckStatus(int firstResult, int maxResults, String sortFieldName, String sortOrder, Map<String, String> map) {
        StringBuilder jpaQuery = new StringBuilder("SELECT o FROM UmpBrand o inner join o.companys c inner join o.businesses b  where o.isDeleted=0 ");
        String brandName = map.get("brandName") == null ? "" : map.get("brandName");
        String companyCode = map.get("companyId") == null ? "" : map.get("companyCode");
        String businessTypeId = map.get("businessTypeId") == null ? "" : map.get("businessTypeId");
        if (!StringUtils.isEmpty(brandName.trim())) {
            jpaQuery.append(" and o.brandName like '%" + brandName + "%'");
        }
        if (!StringUtils.isEmpty(companyCode.trim())) {
            jpaQuery.append(" and c.companyCode =" + companyCode);
        }
        if (!StringUtils.isEmpty(businessTypeId.trim())) {
            jpaQuery.append(" and b.id=" + businessTypeId);
        }
        jpaQuery.append(" order by o.createTime desc ");
        return entityManager().createQuery(jpaQuery.toString(), UmpBrand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    /**
     */
    @NotNull
    @Size(max = 30)
    private String brandName;
    @Size(max = 4000)
    private String keyName;
    
    private boolean isVisable;

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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("checkStatus", "isDeleted", "createTime", "remark", "businesses", "companys", "brandName");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpBrand().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	/**
	 * 查询品牌总数
	 * @return
	 */
	public static long countUmpBrands(Map<String,Object> map) {
   	 StringBuilder jpaQuery = new StringBuilder("SELECT count(o) FROM UmpBrand o join o.business b where o.isDeleted=0 ");
       String brandName = (String) (map.get("brandName") == null ? "" : map.get("brandName"));
       String businessTypeId = (String) (map.get("businessTypeId") == null ? "" : map.get("businessTypeId"));
       String keyName = (String) (map.get("keyName") == null ? "" : map.get("keyName"));
       
       if (!StringUtils.isEmpty(brandName.trim())) {
           jpaQuery.append(" and o.brandName like '%" + brandName + "%'");
       }
       if (!StringUtils.isEmpty(businessTypeId.trim())) {
           jpaQuery.append(" and b.id=" + businessTypeId);
       }
       if (!StringUtils.isEmpty(keyName.trim())) {
           jpaQuery.append(" and o.keyName like '%" + keyName+"%'");
       }
        return entityManager().createQuery(jpaQuery.toString(), Long.class).getSingleResult();
    }
	public static long countUmpBrands() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpBrand o", Long.class).getSingleResult();
    }

	public static List<UmpBrand> findAllUmpBrands() {
        return entityManager().createQuery("SELECT o FROM UmpBrand o where o.isDeleted=0", UmpBrand.class).getResultList();
    }
	/**
	 * 根据行业Id和品牌名称 查询品牌
	 * @return
	 */
	public static List<UmpBrand> findAllUmpBrandsByBusinessId(long id,String brandName) {
		String sql = "SELECT o FROM UmpBrand o join o.business b where o.isDeleted=0 ";
		if(!StringUtils.isEmpty(id)){
			sql+=" and b.id="+id;
		}
		if(!StringUtils.isEmpty(brandName)){
			sql+=" and o.brandName ='"+brandName+"'";
		}
        return entityManager().createQuery(sql, UmpBrand.class).getResultList();
    }
	/**
	 * 校验唯一性
	 * @param name
	 * @param id
	 * @return
	 */
	public static List<UmpBrand> findAllUmpBrandsByName(String name,Long id){
		if(name==null)return null;
		String sql="SELECT o FROM UmpBrand o where 1=1";
		if(!StringUtils.isEmpty(name)){
			sql+=" and o.brandName='"+name+"'";
		}
		if(!StringUtils.isEmpty(id)){
			sql+=" and o.id!="+id;
		}
		return entityManager().createQuery(sql, UmpBrand.class).getResultList();
	}
	public static List<UmpBrand> findAllUmpBrands(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpBrand o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpBrand.class).getResultList();
    }

	public static UmpBrand findUmpBrand(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpBrand.class, id);
    }

	public static List<UmpBrand> findUmpBrandEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpBrand o", UmpBrand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpBrand> findUmpBrandEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpBrand o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpBrand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpBrand attached = UmpBrand.findUmpBrand(this.id);
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
    public UmpBrand merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpBrand merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public UmpCheckStatus getCheckStatus() {
        return this.checkStatus;
    }

	public void setCheckStatus(UmpCheckStatus checkStatus) {
        this.checkStatus = checkStatus;
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

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }


	public String getBrandName() {
        return this.brandName;
    }

	public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

	public String getKeyName() {
        return this.keyName;
    }

	public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

	public Boolean getIsVisable() {
        return this.isVisable;
    }

	public void setIsVisable(Boolean isVisable) {
        this.isVisable = isVisable;
    }

	public UmpParentBusinessType getBusiness() {
		return business;
	}

	public void setBusiness(UmpParentBusinessType business) {
		this.business = business;
	}
	public Set<VocTemplate> getTemplate() {
		return template;
	}

	public void setTemplate(Set<VocTemplate> template) {
		this.template = template;
	}

	public static String toJsonArray(Collection<UmpBrand> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpBrand> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<UmpBrand> fromJsonArrayToUmpBrands(String json) {
        return new JSONDeserializer<List<UmpBrand>>()
        .use("values", UmpBrand.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

}

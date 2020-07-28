package com.nineclient.model;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.constant.UmpCompanyServiceStatus;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpProduct implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     */
    @NotNull
    private String productName;

    /**
     */
    private String remark;

    /**
     */
    private Boolean isVisable;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;
    
    /**
     */
    @OneToMany (mappedBy = "product")
    private Set<UmpChannel> channels = new HashSet<UmpChannel>();

    /**
     */
    @OneToMany(mappedBy = "product")
    private Set<UmpVersion> versions = new HashSet<UmpVersion>();
    @OneToMany(mappedBy = "product")
    private Set<UmpAuthority> umpAuthoritys;
	public String getProductName() {
        return this.productName;
    }

	public void setProductName(String productName) {
        this.productName = productName;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
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

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Set<UmpChannel> getChannels() {
        return this.channels;
    }

	public void setChannels(Set<UmpChannel> channels) {
        this.channels = channels;
    }

	public Set<UmpVersion> getVersions() {
        return this.versions;
    }

	public void setVersions(Set<UmpVersion> versions) {
        this.versions = versions;
    }

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
	public Set<UmpAuthority> getUmpAuthoritys() {
		return umpAuthoritys;
	}

	public void setUmpAuthoritys(Set<UmpAuthority> umpAuthoritys) {
		this.umpAuthoritys = umpAuthoritys;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("productName", "remark", "isVisable", "isDeleted", "createTime", "channels", "versions");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpProduct().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpProducts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpProduct o", Long.class).getSingleResult();
    }

	public static List<UmpProduct> findAllUmpProducts() {
        return entityManager().createQuery("SELECT o FROM UmpProduct o where o.id!=1 and o.isVisable=1", UmpProduct.class).getResultList();
    }
	public static List<UmpProduct> findAllUmpProductsByCompany(UmpCompany company) {
        return entityManager().createQuery("SELECT o FROM UmpProduct o where o.id!=1 and o.isVisable=1", UmpProduct.class).getResultList();
    }
	
	/**
	 * 查询公司可以添加的产品
	 * @param companyCode
	 * @return
	 */
	public static List<Object[]> queryAddableProductByCompanyId(String companyCode){
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.id,p.product_name as name from ump_product p where p.id !=1 and p.is_visable = 1 and p.is_deleted = 0 and not exists( ");
		sql.append(" select * from  ump_company_service cs ");
		sql.append(" where cs.company_service_status not in (:serviceStatus)"); 
		sql.append(" and cs.product_id  =  p.id ");
		sql.append(" and cs.company_code = :companyCode) ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		 map.put("companyCode", companyCode);
		  Set<Integer> status = new HashSet<Integer>();
		  status.add(UmpCompanyServiceStatus.停止.ordinal());
		  status.add(UmpCompanyServiceStatus.关闭.ordinal());
		 map.put("serviceStatus", status);
		 
			javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
			for (String key : map.keySet()) {
				query.setParameter(key, map.get(key));
			}
			
		/*反例：用下面的代码会查不出数据（生成的sql可以查出来 --sea 20150109）
		 Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql.toString()).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}*/
			
		return  query.getResultList();
	}
	
	/**
	 * 不包含UMP
	 * @param id
	 * @return
	 */
	public static List<UmpProduct> findAllUmpProducts(Long id) {
		String sql="SELECT o FROM UmpProduct o where o.id!=1 and o.isVisable=1 ";
		if(id!=null){
			sql+=" and o.id = "+id;
		}
		sql+="order by createTime asc";
        return entityManager().createQuery(sql, UmpProduct.class).getResultList();
    }
	public static List<UmpProduct> findAllUmpProducts(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpProduct o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpProduct.class).getResultList();
    }

	public static UmpProduct findUmpProduct(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpProduct.class, id);
    }
	/**
	 * 校验产品名称唯一性
	 * @param name
	 * @param id
	 * @return
	 */
	public static List<UmpProduct> findUmpProductByName(String name,Long id) {
		String sql="SELECT o FROM UmpProduct o where 1=1";
        if (name == null) return null;
        if(!StringUtils.isEmpty(name)){
        	sql+=" and  o.productName='"+name+"'";
        }
        if(!StringUtils.isEmpty(id)){
        	sql+=" and  o.id!='"+id+"'";
        }
        return entityManager().createQuery(sql, UmpProduct.class).getResultList();
    }
	
	public static List<UmpProduct> findUmpProductById(Long productId) {
		String sql="SELECT o FROM UmpProduct o where 1=1 and o.id = "+productId;
		 return entityManager().createQuery(sql, UmpProduct.class).getResultList();
	}
	
	
	public static List<UmpProduct> findUmpProductEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpProduct o", UmpProduct.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpProduct> findUmpProductEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpProduct o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        jpaQuery+=" order by o.createTime desc";
        return entityManager().createQuery(jpaQuery, UmpProduct.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpProduct attached = UmpProduct.findUmpProduct(this.id);
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
    public UmpProduct merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpProduct merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	

	public static String toJsonArray(Collection<UmpProduct> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpProduct> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<UmpProduct> fromJsonArrayToVocBrands(String json) {
        return new JSONDeserializer<List<UmpProduct>>()
        .use("values", UmpProduct.class).deserialize(json);
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

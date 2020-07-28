package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import com.nineclient.web.VocSkuPropertyController;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.roo.addon.json.RooJson;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooWebJson(jsonObject = VocSkuPropertyController.class)
@RooJson
public class VocSkuProperty {
	@ManyToOne
	private UmpCompany umpCompany;
    /**
     */
    @Size(min = 1, max = 20)
    private String name;

    /**
     */
    @Size(max = 4000)
    private String propertyDetail;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    private Boolean isVisable;
    @OneToMany(mappedBy="vocSkuProperty")
    private Set<VocSku> vocSkus;
    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "propertyDetail", "createTime", "isDeleted", "isVisable");

    public static final EntityManager entityManager() {
        EntityManager em = new VocSkuProperty().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countVocSkuPropertys(UmpCompany company,Long skuPropertyId,Boolean isVisable,Date startTime,Date endTime) {
    	String sql = "SELECT COUNT(o) FROM VocSkuProperty o join o.umpCompany c where 1=1";
    	Map<String,Object> map = new HashMap<String,Object>();
    	if(!StringUtils.isEmpty(skuPropertyId)){	
    		sql +=" and o.id="+skuPropertyId;
    	}
    	if(!StringUtils.isEmpty(isVisable)){
    		sql +=" and o.isVisable=:isVisable";
    		map.put("isVisable", isVisable);
    	}
    	if(!StringUtils.isEmpty(startTime)){
    		sql +=" and o.createTime>=:startTime";
    		map.put("startTime", startTime);
    	}
    	if(!StringUtils.isEmpty(endTime)){
    		sql +=" and o.createTime<=:endTime";
    		map.put("endTime", endTime);
    	}
    	if(company!=null){
    		sql +=" and c.id=:umpcompanyId";
    		map.put("umpcompanyId", company.getId());
    	}
    	TypedQuery<Long>  query = entityManager().createQuery(sql, Long.class);
    	  for(String key:map.keySet()){
          	query.setParameter(key, map.get(key));	
          }
    	
        return query.getSingleResult();
    }

    public static List<VocSkuProperty> findAllVocSkuPropertys(UmpCompany company) {
    	String sql =" SELECT o FROM VocSkuProperty o join o.umpCompany c where 1=1 ";
    	sql+=" and  c.id="+company.getId();
    	sql+=" order by o.createTime desc ";
        return entityManager().createQuery(sql, VocSkuProperty.class).getResultList();
    }
    public static List<VocSkuProperty> findAllVocSkuPropertys() {
        return entityManager().createQuery("SELECT o FROM VocSkuProperty o", VocSkuProperty.class).getResultList();
    }

    public static List<VocSkuProperty> findAllVocSkuPropertys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocSkuProperty o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocSkuProperty.class).getResultList();
    }
    /**
     * 属性名称查询属性
     * @param id
     * @return
     */
    public static List<VocSkuProperty> findVocSkuPropertyByName(String name) {
        if (name == null) return null;
        return entityManager().createQuery("SELECT o FROM VocSkuProperty o where o.name ='"+name+"'", VocSkuProperty.class).getResultList();
    }
    public static VocSkuProperty findVocSkuProperty(Long id) {
        if (id == null) return null;
        return entityManager().find(VocSkuProperty.class, id);
    }

    public static List<VocSkuProperty> findVocSkuPropertyEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocSkuProperty o", VocSkuProperty.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<VocSkuProperty> findVocSkuPropertyEntries(
    		UmpCompany company,
    		Long skuPropertyId,Boolean isVisable,Date startTime,Date endTime,
    		int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String sql = "SELECT o FROM VocSkuProperty o join o.umpCompany c where 1=1 ";
        Map<String,Object> map = new HashMap<String,Object>();
    	if(!StringUtils.isEmpty(skuPropertyId)){	
    		sql +=" and  o.id="+skuPropertyId;
    	}
    	if(!StringUtils.isEmpty(isVisable)){
    		sql +=" and o.isVisable=:isVisable";
    		map.put("isVisable", isVisable);
    	}
    	if(!StringUtils.isEmpty(startTime)){
    		sql +=" and o.createTime>=:startTime";
    		map.put("startTime", startTime);
    	}
    	if(!StringUtils.isEmpty(endTime)){
    		sql +=" and o.createTime<=:endTime";
    		map.put("endTime", endTime);
    	}
    	if(company!=null){
    		sql +=" and c.id=:umpcompanyId";
    		map.put("umpcompanyId", company.getId());
    	}
    	sql+=" order by o.createTime desc ";
    	TypedQuery<VocSkuProperty>  query = entityManager().createQuery(sql, VocSkuProperty.class);
    	  for(String key:map.keySet()){
          	query.setParameter(key, map.get(key));	
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
            VocSkuProperty attached = VocSkuProperty.findVocSkuProperty(this.id);
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
    public VocSkuProperty merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocSkuProperty merged = this.entityManager.merge(this);
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropertyDetail() {
        return this.propertyDetail;
    }

    public void setPropertyDetail(String propertyDetail) {
        this.propertyDetail = propertyDetail;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    
	

	public Set<VocSku> getVocSkus() {
		return vocSkus;
	}

	public void setVocSkus(Set<VocSku> vocSkus) {
		this.vocSkus = vocSkus;
	}

	public UmpCompany getUmpCompany() {
		return umpCompany;
	}

	public void setUmpCompany(UmpCompany umpCompany) {
		this.umpCompany = umpCompany;
	}

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static VocSkuProperty fromJsonToVocSkuProperty(String json) {
        return new JSONDeserializer<VocSkuProperty>()
        .use(null, VocSkuProperty.class).deserialize(json);
    }

	public static String toJsonArray(Collection<VocSkuProperty> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocSkuProperty> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocSkuProperty> fromJsonArrayToVocSkuPropertys(String json) {
        return new JSONDeserializer<List<VocSkuProperty>>()
        .use("values", VocSkuProperty.class).deserialize(json);
    }
	/**
	 * 根据属性名称查询sku属性
	 * @param company
	 * @param name
	 * @return
	 */
	public static VocSkuProperty findVocSkuPropertyCompanyAndName(
			UmpCompany company, String name) {
		if(name==null){
			return null;
		}
		String sql = "SELECT o FROM VocSkuProperty o join o.umpCompany c where o.name='"+name+"' and c.id="+company.getId();
		return entityManager().createQuery(sql, VocSkuProperty.class).getSingleResult();
	}
	
}

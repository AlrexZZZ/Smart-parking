package com.nineclient.model;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
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

import com.nineclient.constant.VocAuditStatus;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocWordExpressions {
	@ManyToOne
	private VocWordCategory vocWordCatagory;
    /**
     */
    @NotNull
    private String name;

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
    @Enumerated
    private VocAuditStatus auditStatus;
    
    private Long companyId;
    

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
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

	public VocAuditStatus getAuditStatus() {
        return this.auditStatus;
    }

	public void setAuditStatus(VocAuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "isDeleted", "createTime", "auditStatus");

	public static final EntityManager entityManager() {
        EntityManager em = new VocWordExpressions().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocWordExpressionses(Map<String,Object> map) {
		String keyName = (String) (map.get("keyName")==null?"": map.get("keyName"));
		String pId=String.valueOf(map.get("pId")==null?"":map.get("pId"));
		String cId=String.valueOf(map.get("cId")==null?"":map.get("cId"));
		String companyId=String.valueOf(map.get("companyId")==null?"":map.get("companyId"));
		String sql = "SELECT COUNT(o) FROM VocWordExpressions  o join o.vocWordCatagory c where 1=1 and o.companyId="+companyId;
		if(!StringUtils.isEmpty(keyName)){
			sql+=" and o.name like '%"+keyName+"%'";
		}
		if(!StringUtils.isEmpty(pId)){
			sql+=" and c.parentId="+pId;
        	
        }
        if(!StringUtils.isEmpty(cId)){
        	sql+=" and c.id="+cId;
        	
        }
        return entityManager().createQuery(sql, Long.class).getSingleResult();
    }

	public static List<VocWordExpressions> findAllVocWordExpressionses() {
        return entityManager().createQuery("SELECT o FROM VocWordExpressions o", VocWordExpressions.class).getResultList();
    }

	public static List<VocWordExpressions> findAllVocWordExpressionses(String sortFieldName, String sortOrder,Map<String,Object> map) {
        String jpaQuery = "SELECT o FROM VocWordExpressions o where 1=1 ";
        String keyName = (String) (map.get("keyName")==null?"":map.get("keyName"));
        if(!StringUtils.isEmpty(keyName)){
        	jpaQuery+=" and o.name like '%"+keyName+"'";
        }
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWordExpressions.class).getResultList();
    }

	public static VocWordExpressions findVocWordExpressions(Long id) {
        if (id == null) return null;
        return entityManager().find(VocWordExpressions.class, id);
    }

	
	public static List<VocWordExpressions> findVocWordExpressionsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocWordExpressions o", VocWordExpressions.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocWordExpressions> findVocWordExpressionsEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder,Map<String,Object> map) {
		String keyName = (String) (map.get("keyName")==null?"":map.get("keyName"));
		String pId=String.valueOf(map.get("pId")==null?"":map.get("pId"));
		String cId=String.valueOf(map.get("cId")==null?"":map.get("cId"));
		String companyId=String.valueOf(map.get("companyId")==null?"":map.get("companyId"));
        String jpaQuery = "SELECT o FROM VocWordExpressions o join o.vocWordCatagory c where 1=1 and o.companyId="+companyId;
        if(!StringUtils.isEmpty(keyName)){
        	jpaQuery+=" and o.name like '%"+keyName+"%'";
        }
        if(!StringUtils.isEmpty(pId)){
        	jpaQuery+=" and c.parentId="+pId;
        	
        }
        if(!StringUtils.isEmpty(cId)){
        	jpaQuery+=" and c.id="+cId;
        	
        }
        jpaQuery+=" order by o.createTime desc ";
        return entityManager().createQuery(jpaQuery, VocWordExpressions.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static PageModel<VocWordExpressions> getQueryWordExpressions(QueryModel<VocWordExpressions> qm){
		 Map parmMap = qm.getInputMap();
		 VocWordExpressions model = qm.getEntity();
		 
		PageModel<VocWordExpressions> pageModel = new PageModel<VocWordExpressions>();
		
		StringBuffer sql = new StringBuffer(" SELECT o FROM VocWordExpressions o where 1=1 and o.auditStatus=:status ");
		Map<String,Object> map = new HashMap<String, Object>();
			map.put("status", VocAuditStatus.审核通过);
		 if(null != model.getName() && !"".equals(model.getName())){
			 sql.append(" and o.name like :name ");
			 map.put("name", "%"+model.getName()+"%");
		 }
		 
		 if(null !=  parmMap.get("companyId") && !"".equals(parmMap.get("companyId"))){
			 sql.append(" and o.companyId = :companyId ");
			 map.put("companyId", Long.parseLong(String.valueOf(parmMap.get("companyId"))));
		 }
		 if(null !=  parmMap.get("commentSubLevelId") && !"".equals(parmMap.get("commentSubLevelId"))){
			 sql.append(" and o.vocWordCatagory.id = :vocWordCatagoryId ");
			 map.put("vocWordCatagoryId", Long.parseLong(String.valueOf(parmMap.get("commentSubLevelId"))));
		 }
		
       TypedQuery<VocWordExpressions> query = entityManager().createQuery(sql.toString(), VocWordExpressions.class);
       
        for(String key:map.keySet()){
          query.setParameter(key, map.get(key));	
        }
        
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
        pageModel.setTotalCount(totalCount);
        pageModel.setDataList(query.getResultList());
        
        return pageModel;

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
            VocWordExpressions attached = VocWordExpressions.findVocWordExpressions(this.id);
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
    public VocWordExpressions merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocWordExpressions merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public VocWordCategory getVocWordCatagory() {
		return vocWordCatagory;
	}

	public void setVocWordCatagory(VocWordCategory vocWordCatagory) {
		this.vocWordCatagory = vocWordCatagory;
	}
	public static String toJsonArray(Collection<VocWordExpressions> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocWordExpressions> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocWordExpressions> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<VocWordExpressions>>()
        .use("values", VocWordExpressions.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static Long uniqueVocWordExpressionses(String keyWord, Long nodeId,
			Long id) {
		String sql="select count(o) from VocWordExpressions o join o.vocWordCatagory w where o.name='"+keyWord+"' and w.id="+nodeId+" and o.companyId="+id;
		return entityManager().createQuery(sql, Long.class).getSingleResult();
	}

}

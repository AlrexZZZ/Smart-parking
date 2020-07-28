package com.nineclient.model;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

import com.nineclient.constant.VocCommentLevel;
import com.nineclient.utils.CommonUtils;
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
public class VocTemplate {

    /**
     */
    @NotNull
    private String title;

    /**
     */
    @NotNull
    private String content;

    /**
     */
    private Boolean isVisable;

    /**
     */
    private Boolean isDeleted;
    
    
    
    /**
     * 词库分类
     */
    @ManyToOne
    private VocWordCategory vocWordCategory;
    
    /**
     * 所属品牌
     */
    @ManyToOne
    private VocBrand brand;
    
    /**
     * 评论分类
     */
    private VocCommentLevel commentLevel;
    
    
    /**
     * 模板规则
     */
    @OneToMany(mappedBy="template")
    private Set<VocTemplateRule> templateRule = new HashSet<VocTemplateRule>();
    

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("title", "content", "isVisable", "isDeleted", "createTime");

	/**
	 * 根据条件查询模板
	 * @param qm
	 * @return
	 */
	public static PageModel<VocTemplate> getQueryTemplate(QueryModel<VocTemplate> qm){
		 Map parmMap = qm.getInputMap();
		 VocTemplate model = qm.getEntity();
		PageModel<VocTemplate> pageModel = new PageModel<VocTemplate>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM VocTemplate o where 1=1 ");
		Map<String,Object> map = new HashMap<String, Object>();
		if( null != parmMap.get("brandIds") && !"".equals(parmMap.get("brandIds"))){
        	String brandIds = parmMap.get("brandIds").toString();
        	sql.append(" and o.brand.id in (:brandIds)");
        	 map.put("brandIds",CommonUtils.stringArray2Set(brandIds));
          }
		
		if( null != parmMap.get("commentLevels") && !"".equals(parmMap.get("commentLevels"))){
        	String commentLevels = parmMap.get("commentLevels").toString();
        	sql.append(" and o.commentLevel in (:commentLevels)");
        	List<Long> list = CommonUtils.stringArray2List(commentLevels);
        	List<VocCommentLevel> eList = new ArrayList<VocCommentLevel>();
        	 for(Long eId:list){
        		 eList.add(VocCommentLevel.getEnum(eId.intValue()));
        	 }
        	 map.put("commentLevels",eList);
          }
		
		
      TypedQuery<VocTemplate> query = entityManager().createQuery(sql.toString(), VocTemplate.class);
      
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
	
	
	
	/**
	 * 根据规则查询模板
	 * @param model
	 * @return
	 */
	public static List<VocTemplate> findVocTemplates(Map param) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT o FROM VocTemplate o WHERE 1=1 ");
		Map<String,Object> map = new HashMap<String, Object>();
		
        if(null != param.get("brandId") && !"".equals(param.get("brandId"))){
        	Long brandId = Long.parseLong(param.get("brandId").toString());
        	sql.append(" and brand.id =:brandId ");
        	map.put("brandId", brandId);
        	
        }
		
        if(null != param.get("commentLevel") && !"".equals(param.get("commentLevel"))){
        	Integer commentLevel = Integer.parseInt(param.get("commentLevel").toString());
        	sql.append(" and o.commentLevel = :commentLevel ");
        	map.put("commentLevel",VocCommentLevel.getEnum(commentLevel));
        }
        TypedQuery<VocTemplate> query = null;
        try{
	      query = entityManager().createQuery(sql.toString(), VocTemplate.class);
	      for(String key:map.keySet()){
		     query.setParameter(key, map.get(key));	
		   }   
        }catch(Exception e){
        	e.printStackTrace();
        }
	    
		
       return query.getResultList();
    }
	
	public static final EntityManager entityManager() {
        EntityManager em = new VocTemplate().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocTemplates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocTemplate o", Long.class).getSingleResult();
    }

	public static List<VocTemplate> findAllVocTemplates() {
        return entityManager().createQuery("SELECT o FROM VocTemplate o", VocTemplate.class).getResultList();
    }

	public static List<VocTemplate> findAllVocTemplates(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocTemplate o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocTemplate.class).getResultList();
    }

	public static VocTemplate findVocTemplate(Long id) {
        if (id == null) return null;
        return entityManager().find(VocTemplate.class, id);
    }

	public static List<VocTemplate> findVocTemplateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocTemplate o", VocTemplate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocTemplate> findVocTemplateEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocTemplate o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocTemplate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocTemplate attached = VocTemplate.findVocTemplate(this.id);
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
    public VocTemplate merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocTemplate merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getTitle() {
        return this.title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
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

	
	public Set<VocTemplateRule> getTemplateRule() {
		return templateRule;
	}

	public void setTemplateRule(Set<VocTemplateRule> templateRule) {
		this.templateRule = templateRule;
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
	
	

	public VocWordCategory getVocWordCategory() {
		return vocWordCategory;
	}

	public void setVocWordCategory(VocWordCategory vocWordCategory) {
		this.vocWordCategory = vocWordCategory;
	}


	public VocBrand getBrand() {
		return brand;
	}



	public void setBrand(VocBrand brand) {
		this.brand = brand;
	}



	public VocCommentLevel getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(VocCommentLevel commentLevel) {
		this.commentLevel = commentLevel;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public static String toJsonArray(Collection<VocTemplate> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocTemplate> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocTemplate> fromJsonArrayToVocTemplates(String json) {
        return new JSONDeserializer<List<VocTemplate>>()
        .use("values", VocTemplate.class).deserialize(json);
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

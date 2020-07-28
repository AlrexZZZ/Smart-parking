package com.nineclient.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class VocTemplateRule {

    /**
     * 关键词名称,多个关键词用逗号分隔
     */
    @NotNull
    private String name;
    
    @ManyToOne
    private VocTemplate template;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "createTime");

	/**
	 * 根据条件查询总数
	 * @param qm
	 * @return
	 */
	public static PageModel<VocTemplateRule> getQueryTemplateRule(QueryModel<VocTemplateRule> qm){
		 Map parmMap = qm.getInputMap();
		 VocTemplateRule model = qm.getEntity();
		PageModel<VocTemplateRule> pageModel = new PageModel<VocTemplateRule>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM VocTemplateRule o inner join o.template template where 1=1 ");
		Map<String,Object> map = new HashMap<String, Object>();
//		 parmMap.put("commentLevels", commentLevels); 
		
		if( null != parmMap.get("brandIds") && !"".equals(parmMap.get("brandIds"))){
        	String brandIds = parmMap.get("brandIds").toString();
        	sql.append(" and template.brand.id in (:brandIds)");
        	 map.put("brandIds",CommonUtils.stringArray2Set(brandIds));
          }
		
		if( null != parmMap.get("commentLevels") && !"".equals(parmMap.get("commentLevels"))){
        	String commentLevels = parmMap.get("commentLevels").toString();
        	sql.append(" and template.commentLevel in (:commentLevels)");
         	List<Long> list = CommonUtils.stringArray2List(commentLevels);
         	List<VocCommentLevel> eList = new ArrayList<VocCommentLevel>();
         	 for(Long eId:list){
         		 eList.add(VocCommentLevel.getEnum(eId.intValue()));
         	 }
         	 map.put("commentLevels",eList);
          }
		
      TypedQuery<VocTemplateRule> query = entityManager().createQuery(sql.toString(), VocTemplateRule.class);
      
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
	
	public static final EntityManager entityManager() {
        EntityManager em = new VocTemplateRule().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocTemplateRules() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocTemplateRule o", Long.class).getSingleResult();
    }

	public static List<VocTemplateRule> findAllVocTemplateRules() {
        return entityManager().createQuery("SELECT o FROM VocTemplateRule o", VocTemplateRule.class).getResultList();
    }

	public static List<VocTemplateRule> findAllVocTemplateRules(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocTemplateRule o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocTemplateRule.class).getResultList();
    }

	public static VocTemplateRule findVocTemplate(Long id) {
        if (id == null) return null;
        return entityManager().find(VocTemplateRule.class, id);
    }

	public static List<VocTemplateRule> findVocTemplateRuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocTemplateRule o", VocTemplateRule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocTemplateRule> findVocTemplateRuleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocTemplateRule o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocTemplateRule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocTemplateRule attached = VocTemplateRule.findVocTemplate(this.id);
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
    public VocTemplateRule merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocTemplateRule merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	
	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VocTemplate getTemplate() {
		return template;
	}

	public void setTemplate(VocTemplate template) {
		this.template = template;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public static String toJsonArray(Collection<VocTemplateRule> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocTemplateRule> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocTemplateRule> fromJsonArrayToVocTemplates(String json) {
        return new JSONDeserializer<List<VocTemplateRule>>()
        .use("values", VocTemplateRule.class).deserialize(json);
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

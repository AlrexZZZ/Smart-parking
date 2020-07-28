package com.nineclient.model;
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
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.VocCommentLevel;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocCommentLevelRule {

    /**
     */
    @Size(min = 1, max = 100)
    private String name;

    /**
     */
    private Boolean isDeleted;
    
    /**
     * 关键词规则所属分类
     */
    private VocCommentLevel commentLevel;
    
    
    
    @ManyToOne
    private UmpBusinessType bussinessType;
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

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
	
	private Long companyId;

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
	
	

	public VocCommentLevel getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(VocCommentLevel commentLevel) {
		this.commentLevel = commentLevel;
	}

	public UmpBusinessType getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(UmpBusinessType bussinessType) {
		this.bussinessType = bussinessType;
	}
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocCommentLevelRule().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocCommentLevelRules() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocCommentLevelRule o", Long.class).getSingleResult();
    }

	public static PageModel<VocCommentLevelRule> getQueryCommentLevelRule(QueryModel<VocCommentLevelRule> qm){
		 Map parmMap = qm.getInputMap();
		 VocCommentLevelRule model = qm.getEntity();
		PageModel<VocCommentLevelRule> pageModel = new PageModel<VocCommentLevelRule>();
		
		StringBuffer sql = new StringBuffer(" SELECT o FROM VocCommentLevelRule o where 1=1 ");
		Map<String,Object> map = new HashMap<String, Object>();
         if(null != model.getCompanyId()){
        	 sql.append(" and o.companyId = :companyId ");
			 map.put("companyId", model.getCompanyId()); 
         }
		
		 if(null != model.getCommentLevel()){
			 sql.append(" and o.commentLevel = :commentLevel ");
			 map.put("commentLevel", model.getCommentLevel());
		 }

		 if(null != parmMap.get("parentBussinessTypeId")){
			 Long parentBussinessTypeId = Long.parseLong(parmMap.get("parentBussinessTypeId").toString());
			 sql.append(" and o.bussinessType.parentBusinessType.id = :parentBusinessType ");
			 map.put("parentBusinessType", parentBussinessTypeId);
		 }
         if(null != parmMap.get("bussinessTypeId")){
        	 Long bussinessTypeId = Long.parseLong(parmMap.get("bussinessTypeId").toString());
        	 sql.append(" and o.bussinessType.id = :bussinessTypeId ");
        	 map.put("bussinessTypeId", bussinessTypeId);
		 }
         String sortedSql = " order by o.createTime desc";
      TypedQuery<VocCommentLevelRule> query = entityManager().createQuery(sql.toString()+sortedSql, VocCommentLevelRule.class);
      
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
	
	public static List<VocCommentLevelRule> findAllVocCommentLevelRules() {
        return entityManager().createQuery("SELECT o FROM VocCommentLevelRule o", VocCommentLevelRule.class).getResultList();
    }

	public static List<VocCommentLevelRule> findAllVocCommentLevelRules(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocCommentLevelRule o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocCommentLevelRule.class).getResultList();
    }

	public static VocCommentLevelRule findVocCommentLevelRule(Long id) {
        if (id == null) return null;
        return entityManager().find(VocCommentLevelRule.class, id);
    }

	public static List<VocCommentLevelRule> findVocCommentLevelRuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocCommentLevelRule o", VocCommentLevelRule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	public static VocCommentLevelRule  findUniqueVocCommentLevelRule(VocCommentLevelRule model) {
       return entityManager().createQuery("SELECT o FROM VocCommentLevelRule o where o.companyId ="+model.getCompanyId()+" and o.name = '"+model.getName()+"' and o.bussinessType.id = "+model.getBussinessType().getId()+" ", VocCommentLevelRule.class).getSingleResult();
    }
	
	public static long findCountVocCommentLevelRule(VocCommentLevelRule model) {
        return entityManager().createQuery("SELECT count(o) FROM VocCommentLevelRule o where o.companyId ="+model.getCompanyId()+" and o.name = '"+model.getName()+"' and o.bussinessType.id = "+model.getBussinessType().getId()+" ", Long.class).getSingleResult();
    }


	public static List<VocCommentLevelRule> findVocCommentLevelRuleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocCommentLevelRule o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocCommentLevelRule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocCommentLevelRule attached = VocCommentLevelRule.findVocCommentLevelRule(this.id);
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
    public VocCommentLevelRule merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocCommentLevelRule merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}

package com.nineclient.model.vocreport;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

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
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.json.RooJson;

import com.nineclient.constant.VocCommentLevel;
import com.nineclient.constant.VocReplayState;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.VocComment;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ProductProblemRandList {

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date commentTime;

    /**
     */
    private String commentTagName;

    /**
     */
    private String commentType;

    /**
     */
    private String skuName;

    /**
     */
    private String channelName;

    /**
     */
    private String brandName;

    /**
     */
    private String shopName;
    
    private Long companyId;
    /**
     * 标签增量
     */
    private  Long increaseCount;  

    public Date getCommentTime() {
        return this.commentTime;
    }

    public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentTagName() {
        return this.commentTagName;
    }

    public void setCommentTagName(String commentTagName) {
        this.commentTagName = commentTagName;
    }

    public String getCommentType() {
        return this.commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getIncreaseCount() {
		return increaseCount;
	}

	public void setIncreaseCount(Long increaseCount) {
		this.increaseCount = increaseCount;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("commentTime", "commentTagName", "commentType", "skuName", "channelName", "brandName", "shopName");

    public static final EntityManager entityManager() {
        EntityManager em = new ProductProblemRandList().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    
    
    
    
    
    /**
	 * 全网搜索查询
	 * @param model
	 * @param parmMap
	 * @return
	 */
	public static PageModel<ProductProblemRandList> getQueryProductProblemRandList(QueryModel<ProductProblemRandList> qm){
		 Map parmMap = qm.getInputMap();
		 ProductProblemRandList model = qm.getEntity();
		PageModel<ProductProblemRandList> pageModel = new PageModel<ProductProblemRandList>();
		
		StringBuffer sql = new StringBuffer(" SELECT o FROM ProductProblemRandList o  WHERE 1=1 ");
		  Map<String,Object> map = new HashMap<String, Object>();
		
		   if(null != model && null != model.getBrandName() && !"".equals(model.getBrandName())){
			  sql.append(" and o.brandName in (:brandName)");
			  map.put("brandName", model.getBrandName());
		   }
			
		 
		    String orderByField = qm.getFirstOrderName();
		    String orderByType  =  qm.getFirstOrderType().trim(); 
		    String orderBySql = "";
		    if(null != orderByField && !"".equals(orderByField)){
		    	orderBySql = " order by "+orderByField+" "+orderByType;
		    }
		    
		    
        TypedQuery<ProductProblemRandList> query = entityManager().createQuery(sql.toString()+orderBySql, ProductProblemRandList.class);
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
    
	
	///图表
	public static List<Map<String,Object>> findProductProblemRandList(
			 String channelName, String brandName,
			String skuName, String shopName, Date startTime, Date endTime,
			UmpCompany company) {
		String sql = "select a.increaseCount,a.commentTagName from (SELECT sum(o.increase_count) increaseCount,o.comment_tag_name commentTagName FROM product_problem_rand_list o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_Name=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_Name=:brandName";
			map.put("brandName", brandName);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_Name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_Name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.comment_Time>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_Time<=:endTime";
			map.put("endTime", endTime);
		}
		
		if (!StringUtils.isEmpty(company.getId())) {
			sql += " and o.company_Id=:companyId";
			map.put("companyId", company.getId());
		} 
		sql+=" group by o.comment_tag_name) a";
		sql+=" order by a.increaseCount desc ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.setFirstResult(0).setMaxResults(10).list();
				
	}
//导出excel
	/**
	 * 导出
	 * 
	 * @param channelName
	 * @param brandName
	 * @param skuName
	 * @param shopName
	 * @param startTime
	 * @param endTime
	 * @param company
	 * @return
	 */
	public static List<Map> findProblemEntriesMap(String channelName,
			String brandName, String skuName, String shopName, String startTime,
			String endTime, UmpCompany company) {
		String sql = "select a.comment_tag_name,a.increase_count,a.company_id from (SELECT o.comment_tag_name,sum(o.increase_count) increase_count,o.company_id FROM product_problem_rand_list o where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(channelName)) {
			sql += " and o.channel_name=:channelName";
			map.put("channelName", channelName);
		}
		if (!StringUtils.isEmpty(brandName)) {
			sql += " and o.brand_name=:brandName";
			map.put("brandName", brandName);
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.sku_name=:skuName";
			map.put("skuName", skuName);
		}
		if (!StringUtils.isEmpty(shopName)) {
			sql += " and o.shop_name=:shopName";
			map.put("shopName", shopName);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.comment_time>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.comment_time<=:endTime";
			map.put("endTime", endTime);
		}
		
		if (!StringUtils.isEmpty(company.getId())) {
			sql += " and o.company_id=:companyId";
			map.put("companyId", company.getId());
		} 
		sql+=" group by comment_tag_name ) a";
		sql+=" order by a.increase_count desc ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.setFirstResult(0).setMaxResults(10).list();
	}
    public ProductProblemRandList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductProblemRandList(Date commentTime, String skuName,
			String channelName, String brandName, String shopName) {
		super();
		this.commentTime = commentTime;
		this.skuName = skuName;
		this.channelName = channelName;
		this.brandName = brandName;
		this.shopName = shopName;
	}

	public static long countProductProblemRandLists() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProductProblemRandList o", Long.class).getSingleResult();
    }

    public static List<ProductProblemRandList> findAllProductProblemRandLists() {
        return entityManager().createQuery("SELECT o FROM ProductProblemRandList o", ProductProblemRandList.class).getResultList();
    }

    public static List<ProductProblemRandList> findAllProductProblemRandLists(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ProductProblemRandList o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ProductProblemRandList.class).getResultList();
    }

    public static ProductProblemRandList findProductProblemRandList(Long id) {
        if (id == null) return null;
        return entityManager().find(ProductProblemRandList.class, id);
    }

    public static List<ProductProblemRandList> findProductProblemRandListEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProductProblemRandList o", ProductProblemRandList.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<ProductProblemRandList> findProductProblemRandListEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ProductProblemRandList o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ProductProblemRandList.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ProductProblemRandList attached = ProductProblemRandList.findProductProblemRandList(this.id);
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
    public ProductProblemRandList merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProductProblemRandList merged = this.entityManager.merge(this);
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

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static ProductProblemRandList fromJsonToProductProblemRandList(String json) {
        return new JSONDeserializer<ProductProblemRandList>()
        .use(null, ProductProblemRandList.class).deserialize(json);
    }

	public static String toJsonArray(Collection<ProductProblemRandList> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<ProductProblemRandList> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<ProductProblemRandList> fromJsonArrayToProductProblemRandLists(String json) {
        return new JSONDeserializer<List<ProductProblemRandList>>()
        .use("values", ProductProblemRandList.class).deserialize(json);
    }
}

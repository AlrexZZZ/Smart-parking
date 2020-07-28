package com.nineclient.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.VocCommentLevel;
import com.nineclient.constant.VocDispatchState;
import com.nineclient.constant.VocReplayState;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocComment {

    /**
     */
    @Size(max = 4000)
    private String commentContent;

    /**
     */
    @Size(max = 4000)
    private String commentTitle;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date commentTime;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date buyTime;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    @Size(max = 4000)
    private String commentId;

    /**
     */
    @Size(max = 4000)
    private String replyUrl;

    /**
     */
    @Enumerated
    private VocReplayState replyState;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date replyTime;

    /**
     */
    @Enumerated
    private VocDispatchState dispatchState;


    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dispatchTime;

    /**
     * 评论等级
     */
    private VocCommentLevel commentLevel;

    /**
     */
    @Size(max = 32)
    private String userName;
    
    
    private String tagName;
    
    
    private String childTradeId;
    
    /**
     */
    @Size(max = 500)
    private String replyContent;

    
    /**
     * 
     * @return
     */
    @ManyToOne
    private VocGoods goods;
    
    /**
     *分配坐席
     */
    
    @ManyToOne
    private PubOperator dispatchOperator;
    
    /**
     * 回复坐席
     */
    @ManyToOne
    private PubOperator replyOperator;
    
    @ManyToMany
    private Set<VocTags> vocTags;
    

	public PubOperator getDispatchOperator() {
		return dispatchOperator;
	}

	public void setDispatchOperator(PubOperator dispatchOperator) {
		this.dispatchOperator = dispatchOperator;
	}


	public String getCommentContent() {
        return this.commentContent;
    }

	public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

	public String getCommentTitle() {
        return this.commentTitle;
    }

	public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

	public Date getCommentTime() {
        return this.commentTime;
    }

	public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

	public Date getBuyTime() {
        return this.buyTime;
    }

	public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	/**
	 * @return the childTradeId
	 */
	public String getChildTradeId() {
		return childTradeId;
	}

	/**
	 * @param childTradeId the childTradeId to set
	 */
	public void setChildTradeId(String childTradeId) {
		this.childTradeId = childTradeId;
	}

	/**
	 * @return the commentId
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getReplyUrl() {
        return this.replyUrl;
    }

	public void setReplyUrl(String replyUrl) {
        this.replyUrl = replyUrl;
    }

	public VocReplayState getReplyState() {
        return this.replyState;
    }

	public void setReplyState(VocReplayState replyState) {
        this.replyState = replyState;
    }

	public Date getReplyTime() {
        return this.replyTime;
    }

	public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

	public VocDispatchState getDispatchState() {
        return this.dispatchState;
    }

	public void setDispatchState(VocDispatchState dispatchState) {
        this.dispatchState = dispatchState;
    }

	public Date getDispatchTime() {
        return this.dispatchTime;
    }

	public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }


	public VocCommentLevel getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(VocCommentLevel commentLevel) {
		this.commentLevel = commentLevel;
	}

	public String getUserName() {
        return this.userName;
    }

	public void setUserName(String userName) {
        this.userName = userName;
    }
	


	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	public static String toJsonArray(Collection<VocComment> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocComment> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocComment> fromJsonArrayToVocComments(String json) {
        return new JSONDeserializer<List<VocComment>>()
        .use("values", VocComment.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("commentContent", "commentTitle", "commentTime", "buyTime", "createTime", "replyUrl", "replyState", "replyTime", "dispatchState", "dispatchOperator", "dispatchTime", "commentLevel", "userName", "commentCategory");

	public static final EntityManager entityManager() {
        EntityManager em = new VocComment().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocComments() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocComment o", Long.class).getSingleResult();
    }
	
	/**
	 * 根据条件查询数量
	 * @param companyId
	 * @param objectName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public static long queryUniqueCount(Long companyId,String objectName,String fieldName,Object fieldValue) {
		Map<String,Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(" SELECT COUNT(o) FROM "+objectName+" o  WHERE "+fieldName+" = :fieldName ");
		   map.put("fieldName", fieldValue);
		 if(null != companyId && companyId > 0){
			sql.append(" and o.companyId =:companyId ");
			map.put("companyId", companyId);
		 }
		 
	   TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
		  for(String key:map.keySet()){
		    query.setParameter(key, map.get(key));	
		  }
		        
		return query.getSingleResult();
    }

	public static List<VocComment> findAllVocComments() {
        return entityManager().createQuery("SELECT o FROM VocComment o", VocComment.class).getResultList();
    }
	
	/**
	 * 全网搜索查询
	 * @param model
	 * @param parmMap
	 * @return
	 */
	public static PageModel<VocComment> getQueryComments(QueryModel<VocComment> qm){
		 Map parmMap = qm.getInputMap();
		 VocComment model = qm.getEntity();
		
		PageModel<VocComment> pageModel = new PageModel<VocComment>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM VocComment o  inner join  o.goods  goods  WHERE 1= 1 ");
		
		Map<String,Object> map = new HashMap<String, Object>();
     
		if(null != parmMap.get("companyId") && !"".equals(parmMap.get("companyId"))){
			 Long companyId = Long.parseLong(String.valueOf(parmMap.get("companyId")));
			 sql.append(" and goods.umpCompany.id =:companyId ");
			 map.put("companyId", companyId);
		 }
		
		if(null != parmMap.get("commentIds") && !"".equals(parmMap.get("commentIds"))){
			 String commentIds = parmMap.get("commentIds").toString();
			 sql.append(" and o.id in (:ids ) ");
			 map.put("ids", CommonUtils.stringArray2Set(commentIds));
		}
		
		if(null != parmMap.get("goodsName") && !"".equals(parmMap.get("goodsName"))){
			 String goodsName = parmMap.get("goodsName").toString();
			 sql.append(" and goods.name like :goodsName ");
			 map.put("goodsName", "%"+goodsName+"%");
		}
		if(null != parmMap.get("tagName") && !"".equals(parmMap.get("tagName"))){
			 String tagName = parmMap.get("tagName").toString();
			 sql.append(" and o.tagName like :tagName ");
			 map.put("tagName", "%"+tagName+"%");
		}
		
		 if(null != parmMap.get("channels") && !"".equals(parmMap.get("channels"))){
			  String channels = parmMap.get("channels").toString();
			  sql.append(" and goods.channel.id in (:channels)");
			  map.put("channels", CommonUtils.stringArray2List(channels));
		}
		 
	    if(null != parmMap.get("shops") && !"".equals(parmMap.get("shops"))){
	    	 String shops = parmMap.get("shops").toString();
			 sql.append(" and goods.shop.id in (:shops)");
			 map.put("shops", CommonUtils.stringArray2List(shops));
		}
	    
	    if(null != parmMap.get("brands") && !"".equals(parmMap.get("brands"))){
	    	String brands = parmMap.get("brands").toString();
	    	 sql.append(" and goods.vocBrand.id in (:brands)");
			 map.put("brands", CommonUtils.stringArray2List(brands));
	    }
	    
	    if(null != parmMap.get("dispatchOperators") && !"".equals(parmMap.get("dispatchOperators"))){
	    	String dispatchOperators = parmMap.get("dispatchOperators").toString();
	    	 sql.append(" and o.dispatchOperator.id in (:dispatchOperators)");
			 map.put("dispatchOperators", CommonUtils.stringArray2List(dispatchOperators));
	    }
	    
	    if(null != parmMap.get("replyOperators") && !"".equals(parmMap.get("replyOperators"))){
	    	String replyOperators = parmMap.get("replyOperators").toString();
	    	 sql.append(" and o.replyOperator.id in (:replyOperators)");
			 map.put("replyOperators", CommonUtils.stringArray2List(replyOperators));
	    }
	    
	    if(null != parmMap.get("replyStates") && !"".equals(parmMap.get("replyStates"))){
	    	String replyStates = parmMap.get("replyStates").toString();
	    	 sql.append(" and o.replyState in :replyState");
	    	 List<VocReplayState> state = new ArrayList<VocReplayState>();
	    	  for(String str:replyStates.split(",")){
	    		  int id = Integer.parseInt(str);
	    		  state.add(VocReplayState.getEnum(id));
	    	  }
			  map.put("replyState", state);
	    }
	    
	    if(null != parmMap.get("commentLevels") && !"".equals(parmMap.get("commentLevels"))){
	    	String commentLevels = parmMap.get("commentLevels").toString();
	    	 sql.append(" and o.commentLevel in :commentLevel");
	    	 List<VocCommentLevel> commentLevel = new ArrayList<VocCommentLevel>();
	    	  for(String str:commentLevels.split(",")){
	    		int id = Integer.parseInt(str);
	    	    commentLevel.add(VocCommentLevel.getEnum(id));
	    	  }
			  map.put("commentLevel", commentLevel);
	    }
	    
	    
		if(null != model.getCommentContent() && !"".equals(model.getCommentContent())){
			 sql.append(" and o.commentContent like :commentContent ");
			 map.put("commentContent", "%"+model.getCommentContent()+"%");
		}
		
		if(null != model.getUserName() && !"".equals(model.getUserName())){
			 sql.append(" and o.userName like :userName ");
			 map.put("userName", "%"+model.getUserName()+"%");
		}
		
		if(null != parmMap.get("commentStartTime")){
		  Date commentStartTime = (Date)parmMap.get("commentStartTime");
		  sql.append(" and o.commentTime >= :commentStartTime ");
		   map.put("commentStartTime", commentStartTime);
		}
		
		if(null != parmMap.get("commentEndTime")){
		  Date commentStartTime = (Date)parmMap.get("commentEndTime");
		  sql.append(" and o.commentTime <= :commentEndTime ");
		  map.put("commentEndTime", commentStartTime);
		}
		
		if(null != parmMap.get("createStartTime")){
		   Date createStartTime = (Date)parmMap.get("createStartTime");
		   sql.append(" and o.createTime >= :createStartTime ");
		   map.put("createStartTime", createStartTime);
		}
		
		if(null != parmMap.get("createEndTime")){
		   Date createEndTime = (Date)parmMap.get("createEndTime");
		   sql.append(" and o.createTime <= :createEndTime ");
		   map.put("createEndTime", createEndTime);
		 }
		
		 if(null != parmMap.get("replyStartTime")){
			  Date replyStartTime = (Date)parmMap.get("replyStartTime");
			  sql.append(" and o.replyTime >= :replyStartTime ");
			  map.put("replyStartTime", replyStartTime);
		 }
			
		 if(null != parmMap.get("replyEndTime")){
			 Date replyEndTime = (Date)parmMap.get("replyEndTime");
			 sql.append(" and o.replyTime <= :replyEndTime ");
			 map.put("replyEndTime", replyEndTime);
		  }
		 
		 if(null != model && null != model.getDispatchState()){
			 sql.append(" and o.dispatchState = :dispatchState ");
			 map.put("dispatchState", model.getDispatchState());
		 }
		
		 
        TypedQuery<VocComment> query = null;
       
        try {
          String sortedSql = "";	
          if(null != qm.getOrderMap() && qm.getOrderMap().size() > 0){
        	  sortedSql = " order by "+qm.getFirstOrderName()+" "+qm.getFirstOrderType();
        	}else{
        	  sortedSql = " order by o.commentTime,o.replyTime desc";
        	}
        	
			query = entityManager().createQuery(sql.append(sortedSql).toString(), VocComment.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
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
	 * 是否要关联表
	 * @param parmMap
	 * @return true 关联  false 不关联
	 */
 static boolean isInnerJoin(Map parmMap){
	  
	   if(null != parmMap.get("goodsName") && !"".equals(parmMap.get("goodsName"))){
		     return true;
	   }
	   if(null != parmMap.get("channels") && !"".equals(parmMap.get("channels"))){
			  return true;
		}
	   if(null != parmMap.get("shops") && !"".equals(parmMap.get("shops"))){
			  return true;
		}
		
		return false;
	}
	

	public static List<VocComment> findAllVocComments(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocComment o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocComment.class).getResultList();
    }

	public static VocComment findVocComment(Long id) {
        if (id == null) return null;
        return entityManager().find(VocComment.class, id);
    }

	public static List<VocComment> findVocCommentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocComment o", VocComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocComment> findVocCommentEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocComment o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocComment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocComment attached = VocComment.findVocComment(this.id);
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
    public VocComment merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocComment merged = this.entityManager.merge(this);
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

	public VocGoods getGoods() {
		return goods;
	}

	public void setGoods(VocGoods goods) {
		this.goods = goods;
	}
	

	public PubOperator getReplyOperator() {
		return replyOperator;
	}

	public void setReplyOperator(PubOperator replyOperator) {
		this.replyOperator = replyOperator;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}


	public Set<VocTags> getVocTags() {
		return vocTags;
	}

	public void setVocTags(Set<VocTags> vocTags) {
		this.vocTags = vocTags;
	}
	
	
	
}

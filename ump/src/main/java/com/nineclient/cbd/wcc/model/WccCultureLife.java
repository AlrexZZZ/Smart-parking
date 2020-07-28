package com.nineclient.cbd.wcc.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;



@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccCultureLife {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
   /**
   * 公司Id
   */
    private Long companyId;	
	/**
	 * 粉丝公共账号
	 */
	private String platFormId;
	/**
	 * 粉丝公共账号
	 */
	private String platFormAccount;
	
	/**
	 *横幅背景大图
	 */
    private String backgroundImg;
    /**
	 *横幅背景提示语
	 */
    private String backgroundTip;
    
    /**
	 *列表标题图
	 */
    private String listImg;
    
    /**
 	 *列表标题提示语
 	 */
    private String listTip;
    
    /**
 	 *列表标详情标题
 	 */
    private String detailTitle;
    
    /**
 	 *列表详情内容
 	 */
    @Size(max = 4000)
    private String detailContent;
    
    /**
  	 *其他可跳转url
  	 */
    private String otherUrl;
	@Version
    @Column(name = "version")
    private Integer version;
	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
    /**插入时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date insertTime;

    
    private String insertTimeStr;
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getInsertTimeStr() {
		return insertTimeStr;
	}

	public void setInsertTimeStr(String insertTimeStr) {
		this.insertTimeStr = insertTimeStr;
	}

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

	public Date getInsertTime() {
        return this.insertTime;
    }

	public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
	public String getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(String platFormId) {
		this.platFormId = platFormId;
	}
    public String getPlatFormAccount() {
		return platFormAccount;
	}
   
	public void setPlatFormAccount(String platFormAccount) {
		this.platFormAccount = platFormAccount;
	}
	

	public String getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getBackgroundTip() {
		return backgroundTip;
	}

	public void setBackgroundTip(String backgroundTip) {
		this.backgroundTip = backgroundTip;
	}

	public String getListImg() {
		return listImg;
	}

	public void setListImg(String listImg) {
		this.listImg = listImg;
	}

	public String getListTip() {
		return listTip;
	}

	public void setListTip(String listTip) {
		this.listTip = listTip;
	}

	public String getDetailTitle() {
		return detailTitle;
	}

	public void setDetailTitle(String detailTitle) {
		this.detailTitle = detailTitle;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public String getOtherUrl() {
		return otherUrl;
	}

	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nickName", "headImg", "sex", "area", "signature", "remarkName", "userInfo", "isValidated", "isDeleted", "insertTime", "fromType", "subscribeTime", "messageEndTime", "sendEmailTime");

	public static final EntityManager entityManager() {
        EntityManager em = new WccCultureLife().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccCultureLife() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccCultureLife o", Long.class).getSingleResult();
    }

	public static List<WccCultureLife> findAllWccCultureLife() {
        return entityManager().createQuery("SELECT o FROM WccCultureLife o", WccCultureLife.class).getResultList();
    }

	public static List<WccCultureLife> findAllWccCultureLife(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccCultureLife o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccCultureLife.class).getResultList();
    }

	public static WccCultureLife findWccCultureLife(Long id) {
        if (id == null) return null;
        return entityManager().find(WccCultureLife.class, id);
    }
	public static String toJsonArray(Collection<WccCultureLife> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
	public static String toJsonArray(Collection<WccCultureLife> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<WccCultureLife> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<WccCultureLife>>()
        .use("values", WccCultureLife.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	public static List<WccCultureLife> findWccCultureLifeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccCultureLife o", WccCultureLife.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccCultureLife> findWccCultureLifeByToUser(Long id) {
		return entityManager().createQuery("SELECT o FROM WccCultureLife o WHERE o.msgFrom = 4 and o.toUserRecord = "+id, WccCultureLife.class).getResultList();
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
            WccCultureLife attached = WccCultureLife.findWccCultureLife(this.id);
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
    public WccCultureLife merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccCultureLife merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	

	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccCultureLife> getRecord(QueryModel<WccCultureLife> qm){
		PageModel<WccCultureLife> pageModel = new PageModel<WccCultureLife>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccCultureLife.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
       
      Criteria cr = criteria.createCriteria("platformUsers");
      String[] lid = null;
      Long[] larray = null;
      //获取公众平台上id
     String pltStr =  parm.get("platFormId")+""; 
     if(null != pltStr && !"".equals(pltStr.trim())){
    	 lid = pltStr.split(",");
     }
     if(null  != lid && lid.length > 0){
    	 larray = new Long[lid.length];
    	 for(int i=0;i<lid.length;i++){
    		 larray[i] = Long.parseLong(lid[i]); 
    	 }
     }
     if(null != larray && larray.length > 0){
    	 cr.add(Restrictions.in("id",larray));
     }

        	if(null != parm.get("nickName") && !"".equals(parm.get("nickName")+"")){
        		criteria.add(Restrictions.like("detailContent", parm.get("nickName")+"",MatchMode.ANYWHERE));
        	}
        	
			if (null != parm.get("beginTime") && !"".equals(parm.get("beginTime")+"")) {
				criteria.add(Restrictions.ge("insertTime", DateUtil.parseDateFormat(parm.get("beginTime")+" 00:00:00")));
			}
			
			if (null != parm.get("endTime") && !"".equals(parm.get("endTime")+"")) {
				criteria.add(Restrictions.le("insertTime", DateUtil.parseDateFormat(parm.get("endTime")+" 23:59:59")));
			}
        
      Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
    	criteria.setProjection(null);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
//        criteria.setFirstResult(qm.getStart());//当前第几页
//        criteria.setMaxResults(qm.getLimit());//页的大小
        
		//List<WccCultureLife> list = criteria.list();
        List<WccCultureLife> list = null;
        if(null != larray && larray.length > 0){
    		list = new ArrayList<WccCultureLife>();
    		WccCultureLife u = null;
    		Map<Long,WccCultureLife> mm = new LinkedHashMap<Long,WccCultureLife>();
    		List <Object> lis =  criteria.list();
    		if(null != lis && lis.size() >0 ){
    			for(Object o:lis){
    				u= (WccCultureLife) o;
    				mm.put(u.getId(), u);
    			}
    			if(mm.size() > 0){
    				for(Map.Entry<Long,WccCultureLife> mp:mm.entrySet()){
    					list.add(mp.getValue());
    				}
    			}
    			
    		}
        }else{
        	list = criteria.list();
        }
        
		pageModel.setDataList(list);
		
		return pageModel;
      
	}






	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static PageModel<WccCultureLife> getQueryRecord(QueryModel<WccCultureLife> qm){
		PageModel<WccCultureLife> pageModel = new PageModel<WccCultureLife>();
		Map<String, Object> param = qm.getInputMap();
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccCultureLife o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccCultureLife> query = entityManager().createQuery(sql.toString(), WccCultureLife.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
//      List<WccCultureLife> list = query.getResultList();
      pageModel.setTotalCount(totalCount);
      pageModel.setDataList(query.getResultList());
      return pageModel;
	}

	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static List<WccCultureLife> getQueryRecordList(Long id,Long pltId){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccCultureLife.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id){
	    	  criteria.add(Restrictions.eq("id",id));
	      }
	      if(null != pltId){
	    	  cr.add(Restrictions.eq("id",pltId));
	      }
      return criteria.list();
	}
	public static List<WccCultureLife> getQueryRecordByDetailId(Long id){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccCultureLife.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id){
	    	  criteria.add(Restrictions.eq("id",id));
	      }
	      
      return criteria.list();
	}
}

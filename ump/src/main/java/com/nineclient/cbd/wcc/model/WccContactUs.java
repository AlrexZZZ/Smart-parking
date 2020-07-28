package com.nineclient.cbd.wcc.model;
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
import org.hibernate.FetchMode;
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


//联系我们
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccContactUs {
	
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
	 *背景图
	 */
    private String backImg;

    /**
	 *联系单位/人
	 */
    private String contactUnit;
    
    /**
	 *联系方式
	 */
    private String contactWay;
    
    
    
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

	public String getBackImg() {
		return backImg;
	}

	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public String getContactUnit() {
		return contactUnit;
	}

	public void setContactUnit(String contactUnit) {
		this.contactUnit = contactUnit;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
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
        EntityManager em = new WccContactUs().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccContactUs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccContactUs o", Long.class).getSingleResult();
    }

	public static List<WccContactUs> findAllWccContactUs() {
        return entityManager().createQuery("SELECT o FROM WccContactUs o", WccContactUs.class).getResultList();
    }

	public static List<WccContactUs> findAllWccContactUs(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccContactUs o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccContactUs.class).getResultList();
    }

	public static WccContactUs findWccContactUs(Long id) {
        if (id == null) return null;
        return entityManager().find(WccContactUs.class, id);
    }
	public static String toJsonArray(Collection<WccContactUs> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
	public static String toJsonArray(Collection<WccContactUs> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<WccContactUs> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<WccContactUs>>()
        .use("values", WccContactUs.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	public static List<WccContactUs> findWccContactUsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccContactUs o", WccContactUs.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccContactUs> findWccContactUsByToUser(Long id) {
		return entityManager().createQuery("SELECT o FROM WccContactUs o WHERE o.msgFrom = 4 and o.toUserRecord = "+id, WccContactUs.class).getResultList();
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
            WccContactUs attached = WccContactUs.findWccContactUs(this.id);
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
    public WccContactUs merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccContactUs merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	

	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccContactUs> getRecord(QueryModel<WccContactUs> qm){
		PageModel<WccContactUs> pageModel = new PageModel<WccContactUs>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
     // Criteria criteria = s.createCriteria(WccContactUs.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
      Criteria criteria = s.createCriteria(WccContactUs.class).setFetchMode("platformUsers", FetchMode.SELECT);
      WccContactUs u = new WccContactUs();
      
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
        
		List<WccContactUs> list = criteria.list();
		pageModel.setDataList(criteria.list());
		
      return pageModel;
      
	}


	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccContactUs> getRecord2(QueryModel<WccContactUs> qm){
		PageModel<WccContactUs> pageModel = new PageModel<WccContactUs>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccContactUs.class);
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
    	 criteria.createAlias("platformUsers", "platformUsers")
    	.add(Restrictions.in("platformUsers.id",larray));
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
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
//        criteria.setFirstResult(qm.getStart());//当前第几页
//        criteria.setMaxResults(qm.getLimit());//页的大小
        List<WccContactUs> list = null;
        if(null != larray && larray.length > 0){
    		list = new ArrayList<WccContactUs>();
    		WccContactUs u = null;
    		List <Object[]> lis =  criteria.list();
    		if(null != lis && lis.size() >0 ){
    			for(Object[] o:lis){
    				u= (WccContactUs) o[1];
    				list.add(u);
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
	public static PageModel<WccContactUs> getQueryRecord(QueryModel<WccContactUs> qm){
		PageModel<WccContactUs> pageModel = new PageModel<WccContactUs>();
		Map<String, Object> param = qm.getInputMap();
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccContactUs o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccContactUs> query = entityManager().createQuery(sql.toString(), WccContactUs.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
	
	public static List<WccContactUs> getQueryRecordList(Long platId){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccContactUs.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	   
	      if(null != platId){
	    	  cr.add(Restrictions.eq("id",platId));
	      }
      return criteria.list();
	}
	
}

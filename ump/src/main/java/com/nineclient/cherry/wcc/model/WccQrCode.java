package com.nineclient.cherry.wcc.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.cbd.wcc.model.WccContactUs;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccQrcode;
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
public class WccQrCode {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	/**
	 *二维码类型/来源
	 */
    private String codeType;
	/**
	 *二维码属性：永久/临时
	 */
    private String codeAttr;
    /**
	 *区域信息
	 */
    private String areaInfo;
    
    /**
	 *区域/活动说明
	 */
    @Size(max = 10000)
    private String areaOrActicityDes;
    /***
     * 活动名称
     */
    private String activityName;
    
    /**
 	 *是否立即生成二维码
 	 */
    private String isCreateCode;
    
    /**
 	 *邮箱地址
 	 */
    private String emailAddress;
    
    public String getActityImg() {
		return actityImg;
	}
	public void setActityImg(String actityImg) {
		this.actityImg = actityImg;
	}
	/**
 	 *二维码图片地址
 	 */
    private String codeLocalPath;
    
    /**
  	 *二维码名称
  	 */
    private String codeName;
    /**
  	 *二维码 sceneid
  	 */
    private int sceneId;
    
    /**
  	 *二维码 scene_str
  	 */
    private String sceneStr;
    
    /**
  	 *二维码url
  	 */
    private String codeUrl;
    
    /**
  	 *二维码过期时间
  	 */
   private Integer expireTime;
   
   /**
 	 *活动主题图片
 	 */
  private String actityImg;
	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	/**
	 * 关联组织架构
     */	
    @ManyToOne
    private PubOrganization organization;
    
	/**
	 * 创建日期
     */	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;
/**
 * 
 * getter and setter
 */
    
public String getActivityName() {
		return activityName;
	}
	public String getSceneStr() {
	return sceneStr;
}
public void setSceneStr(String sceneStr) {
	this.sceneStr = sceneStr;
}
	public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
	public Integer getExpireTime() {
	return expireTime;
}
public void setExpireTime(Integer expireTime) {
	this.expireTime = expireTime;
}
	public PubOrganization getOrganization() {
	return organization;
}
public void setOrganization(PubOrganization organization) {
	this.organization = organization;
}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
	public String getCodeAttr() {
		return codeAttr;
	}
	public void setCodeAttr(String codeAttr) {
		this.codeAttr = codeAttr;
	}
	public String getAreaInfo() {
		return areaInfo;
	}
	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}
	public String getAreaOrActicityDes() {
		return areaOrActicityDes;
	}
	public void setAreaOrActicityDes(String areaOrActicityDes) {
		this.areaOrActicityDes = areaOrActicityDes;
	}
	public String getIsCreateCode() {
		return isCreateCode;
	}
	public void setIsCreateCode(String isCreateCode) {
		this.isCreateCode = isCreateCode;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getCodeLocalPath() {
		return codeLocalPath;
	}
	public void setCodeLocalPath(String codeLocalPath) {
		this.codeLocalPath = codeLocalPath;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}
	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}
	public Long getId() {
        return this.id;
    }
	public void setId(Long id) {
        this.id = id;
    }
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
        EntityManager em = new WccQrCode().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccQrCode() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccQrCode o", Long.class).getSingleResult();
    }

	public static List<WccQrCode> findAllWccQrCode() {
        return entityManager().createQuery("SELECT o FROM WccQrCode o", WccQrCode.class).getResultList();
    }

	public static List<WccQrCode> findAllWccQrCode(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccQrCode o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccQrCode.class).getResultList();
    }

	public static WccQrCode findWccQrCode(Long id) {
        if (id == null) return null;
        return entityManager().find(WccQrCode.class, id);
    }
	public static String toJsonArray(Collection<WccQrCode> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
	public static String toJsonArray(Collection<WccQrCode> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<WccQrCode> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<WccQrCode>>()
        .use("values", WccQrCode.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	public static List<WccQrCode> findWccQrCodeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccQrCode o", WccQrCode.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccQrCode> findWccQrCodeByToUser(Long id) {
		return entityManager().createQuery("SELECT o FROM WccQrCode o WHERE o.msgFrom = 4 and o.toUserRecord = "+id, WccQrCode.class).getResultList();
	}

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	public static int getMaxSceneid(WccPlatformUser wccPlatformUser) {
		Set <WccPlatformUser> setp = new HashSet<WccPlatformUser>();
		setp.add(wccPlatformUser);
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != wccPlatformUser){
	    	  cr.add(Restrictions.eq("id",wccPlatformUser.getId()));
	      }
	      Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
          int a = Integer.parseInt(totalCount+"");
		
		  return a;
	}
	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            WccQrCode attached = WccQrCode.findWccQrCode(this.id);
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
    public WccQrCode merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccQrCode merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	/**
	 * 查询所有记录
	 * @param qm  
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<WccQrCode> getQueryRecordList(Long id){
		  Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      if(null != id){
	    	  criteria.add(Restrictions.eq("id",id));
	      }
      return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<WccQrCode> getExcelRecord(QueryModel<WccQrCode> qm){
		  Session s = (Session) entityManager().getDelegate();
		  Map parm = qm.getInputMap();
	      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      String[] lid = null;
	      Long[] larray = null;
	      //获取公众平台上id
	     String pltStr =  (String) parm.get("platFormId"); 
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
	//二维码来源
	       	if(null != parm.get("codeType") && !"".equals(parm.get("codeType")+"")){
	        		criteria.add(Restrictions.eq("codeType", parm.get("codeType")+""));
	        	}
	      //二维属性
	       	if(null != parm.get("codeAttr") && !"".equals(parm.get("codeAttr")+"")){
	        		criteria.add(Restrictions.eq("codeAttr", parm.get("codeAttr")+""));
	        	}     
	      //区域二维码活动名称  	
	    	if(null != parm.get("activityName") && !"".equals(parm.get("activityName")+"")){
	    		criteria.add(Restrictions.like("activityName", parm.get("activityName")+"",MatchMode.ANYWHERE));
	    	}  
	        //区域	
	      	if(null != parm.get("areaInfo") && !"".equals(parm.get("areaInfo")+"")){
	      		criteria.add(Restrictions.like("areaInfo", parm.get("areaInfo")+"",MatchMode.ANYWHERE));
	      	}        
	      	String tempArea = (String)parm.get("currentArea");
	      	if(null != tempArea && !"".equals(tempArea)){
	      		String arrayStr[] = tempArea.split(",");
	      		if(null != arrayStr && arrayStr.length > 0){
	      	     	Disjunction dis=Restrictions.disjunction();  
	      	      	for(String sstr:arrayStr){
	      	      	 dis.add(Restrictions.like("areaInfo", sstr, MatchMode.ANYWHERE));
	      	      	}
	      	        criteria.add(dis);
	      		}
	 
	        }
	     
	    	criteria.setProjection(null);
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(Order.desc("id"));
	        List<WccQrCode> list = null;
	        if(null != larray && larray.length > 0){
	    		list = new ArrayList<WccQrCode>();
	    		WccQrCode u = null;
	    		Map<Long,WccQrCode> mm = new LinkedHashMap<Long,WccQrCode>();
	    		List <Object> lis =  criteria.list();
	    		if(null != lis && lis.size() >0 ){
	    			for(Object o:lis){
	    				u= (WccQrCode) o;
	    				mm.put(u.getId(), u);
	    			}
	    			if(mm.size() > 0){
	    				for(Map.Entry<Long,WccQrCode> mp:mm.entrySet()){
	    					list.add(mp.getValue());
	    				}
	    			}
	    			
	    		}
	        }else{
	        	list = criteria.list();
	        }
           return list;
	}
	/**
	 * 查询所有未生成二维码的记录
	 * @param qm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<WccQrCode> getNeedCodeList(){
		  Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	    	       criteria.add(Restrictions.isNull("codeUrl"));
      return criteria.list();
	}
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccQrCode> getRecord(QueryModel<WccQrCode> qm){
		PageModel<WccQrCode> pageModel = new PageModel<WccQrCode>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
       
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
     
//二维码来源
       	if(null != parm.get("codeType") && !"".equals(parm.get("codeType")+"")){
        		criteria.add(Restrictions.eq("codeType", parm.get("codeType")+""));
        	}
      //二维属性
       	if(null != parm.get("codeAttr") && !"".equals(parm.get("codeAttr")+"")){
        		criteria.add(Restrictions.eq("codeAttr", parm.get("codeAttr")+""));
        	}     
      //区域二维码说明  	
    	if(null != parm.get("areaOrActicityDes") && !"".equals(parm.get("areaOrActicityDes")+"")){
    		criteria.add(Restrictions.like("activityName", parm.get("areaOrActicityDes")+"",MatchMode.ANYWHERE));
    	}  
        //区域	
      	if(null != parm.get("areaInfo") && !"".equals(parm.get("areaInfo"))&&!"请选择-请选择".equals(parm.get("areaInfo"))){
      		criteria.add(Restrictions.like("areaInfo", parm.get("areaInfo")+"",MatchMode.ANYWHERE));
      	}        
      	String tempArea = (String)parm.get("currentArea");
      	if(null != tempArea && !"".equals(tempArea)){
      		String arrayStr[] = tempArea.split(",");
      		if(null != arrayStr && arrayStr.length > 0){
      	     	Disjunction dis=Restrictions.disjunction();  
      	      	for(String sstr:arrayStr){
      	      	 dis.add(Restrictions.like("areaInfo", sstr, MatchMode.ANYWHERE));
      	      	}
      	        criteria.add(dis);
      		}
 
        }
      Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
    	criteria.setProjection(null);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("id"));
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());

        List<WccQrCode> list = null;
        if(null != larray && larray.length > 0){
    		list = new ArrayList<WccQrCode>();
    		WccQrCode u = null;
    		Map<Long,WccQrCode> mm = new LinkedHashMap<Long,WccQrCode>();
    		List <Object> lis =  criteria.list();
    		if(null != lis && lis.size() >0 ){
    			for(Object o:lis){
    				u= (WccQrCode) o;
    				mm.put(u.getId(), u);
    			}
    			if(mm.size() > 0){
    				for(Map.Entry<Long,WccQrCode> mp:mm.entrySet()){
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
	public static PageModel<WccQrCode> getQueryRecord(QueryModel<WccQrCode> qm){
		PageModel<WccQrCode> pageModel = new PageModel<WccQrCode>();
		Map<String, Object> param = qm.getInputMap();
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccQrCode o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccQrCode> query = entityManager().createQuery(sql.toString(), WccQrCode.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
//      List<WccQrCode> list = query.getResultList();
      pageModel.setTotalCount(totalCount);
      pageModel.setDataList(query.getResultList());
      return pageModel;
	}

	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static List<WccQrCode> getQueryRecordList(Long id,Long pltId){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id){
	    	  criteria.add(Restrictions.eq("id",id));
	      }
	      if(null != pltId){
	    	  cr.add(Restrictions.eq("id",pltId));
	      }
      return criteria.list();
	}
	public static List<WccQrCode> getQueryRecordByDetailId(Long id){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id){
	    	  criteria.add(Restrictions.eq("id",id));
	      }
	      
      return criteria.list();
	}
	public static List<WccQrCode> findAllWccQrCodeByType(String pcodetype,String platId,String orgsid) {
		/*TypedQuery<WccQrCode> query=entityManager().createQuery("SELECT o FROM WccQrCode o where o.codeType=:codeType", WccQrCode.class);
		query.setParameter("codeType", pcodetype);
        return query.getResultList();*/
		  List<WccQrCode > list=null;
		Session s = (Session) entityManager().getDelegate();
	    Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	    criteria.add(Restrictions.eq("codeType", pcodetype));
	    Criteria cr = criteria.createCriteria("platformUsers");
	    if(StringUtils.isNotBlank(platId)){
	  	  cr.add(Restrictions.eq("id",Long.parseLong(platId)));
	    }
	    String[] oid = null;
		Long[] lay = null;
		// 组织架构查询
		if (!StringUtils.isBlank(orgsid)) {
			oid = orgsid.split(",");
			if (null != oid && oid.length > 0) {
				lay = new Long[oid.length];
				for (int i = 0; i < oid.length; i++) {
					lay[i] = Long.parseLong(oid[i]);
				}
			}
			Criteria cro = criteria.createCriteria("organization");
			cro.add(Restrictions.in("id", lay));
			/*
			 * criteria.createAlias("pubOrganization", "pubOrganization").add(
			 * Restrictions.in("pubOrganization.id", larray));
			 */
		}
	    try {
	    	   list=criteria.list();
		} catch (Exception e) {
			list=null;
		}
	    return list;
    }
	public static WccQrCode findWccQrCodeByScenId(int sceneId) {
		if(sceneId == 0){return null;}
		TypedQuery<WccQrCode> query = entityManager().createQuery("FROM WccQrCode o where  o.sceneId =:sceneId ",WccQrCode.class);
		query.setParameter("sceneId", sceneId);
		WccQrCode wccqrcode=null;
		try {
			wccqrcode=query.getResultList().get(0);
		} catch (Exception e) {
			wccqrcode=null;
		}
		return wccqrcode;
	}
	public static WccQrCode findWccQrCodeByCre(String sceneStr,WccPlatformUser wccPlatformUser) {
		WccQrCode wccQrCode=null;
	Session s = (Session) entityManager().getDelegate();
    Criteria criteria = s.createCriteria(WccQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
    criteria.add(Restrictions.eqOrIsNull("sceneStr", sceneStr));
    Criteria cr = criteria.createCriteria("platformUsers");
    if(null != wccPlatformUser){
  	  cr.add(Restrictions.eq("id",wccPlatformUser.getId()));
    }
    try {
    	   List<WccQrCode > list=criteria.list();
    	   wccQrCode=list.get(0);
	} catch (Exception e) {
		
	}
 
   return wccQrCode;
	}
}

package com.nineclient.tailong.wcc.model;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import java.util.Set;



/**
 * 泰隆产品实体类
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;


@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccTproducts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private int productType;// 产品类型   1 代表融资缺钱，2 代表钱生钱
	@Size(max = 100)
	private String thumbImage; // 产品缩略图
	
	@Size(max = 50)
	private String productName; // 图文信息
	
	@Size(max = 100)
	private String backImage; // 产品背景图
	
	@Size(max = 6000)
	private String pInfo; // 产品信息
	
	@Size(max = 6000)
	private String pAdvantage; // 产品优势
	
	@Size(max = 6000)
	private String applayStep; // 产品如何申请
	
	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	
    public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}
	/**插入时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date insertTime;

	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThumbImage() {
		return thumbImage;
	}

	public void setThumbImage(String thumbImage) {
		this.thumbImage = thumbImage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public String getpInfo() {
		return pInfo;
	}

	public void setpInfo(String pInfo) {
		this.pInfo = pInfo;
	}

	public String getpAdvantage() {
		return pAdvantage;
	}

	public void setpAdvantage(String pAdvantage) {
		this.pAdvantage = pAdvantage;
	}

	public String getApplayStep() {
		return applayStep;
	}

	public void setApplayStep(String applayStep) {
		this.applayStep = applayStep;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
    /****
     * methods zone
     */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@PersistenceContext
    transient EntityManager entityManager;
	public static final EntityManager entityManager() {
        EntityManager em = new WccTproducts().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
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
            WccTproducts attached = WccTproducts.findWccTproducts(this.id);
            this.entityManager.remove(attached);
        }
    }
	
	// find by id
	public static WccTproducts findWccTproducts(Long id) {
        if (id == null) return null;
        return entityManager().find(WccTproducts.class, id);
    }	
	
	@Transactional
    public WccTproducts merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccTproducts merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static PageModel<WccTproducts> getQueryRecord(QueryModel<WccTproducts> qm){
		PageModel<WccTproducts> pageModel = new PageModel<WccTproducts>();
		Map<String, Object> param = qm.getInputMap();
//		StringBuffer sql = new StringBuffer(" SELECT o FROM WccTproducts o WHERE o.openId = '"+param.get("openId")+"'" + "ORDER BY o.insertTime DESC");
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccTproducts o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccTproducts> query = entityManager().createQuery(sql.toString(), WccTproducts.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
//      List<WccTproducts> list = query.getResultList();
      pageModel.setTotalCount(totalCount);
      pageModel.setDataList(query.getResultList());
      return pageModel;
	}
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccTproducts> getRecord(QueryModel<WccTproducts> qm){
		PageModel<WccTproducts> pageModel = new PageModel<WccTproducts>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccTproducts.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
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
     
 	if(null != parm.get("productName") && !"".equals(parm.get("productName")+"")){
		criteria.add(Restrictions.like("productName", parm.get("productName")+"",MatchMode.ANYWHERE));
	}
     
 	if(null != parm.get("productType") && !"".equals(parm.get("productType")+"")){
		criteria.add(Restrictions.eq("productType", Integer.parseInt(parm.get("productType")+"")));
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
        pageModel.setPageSize  (qm.getLimit());
//        criteria.setFirstResult(qm.getStart());//当前第几页
//        criteria.setMaxResults(qm.getLimit());//页的大小
        
		//List<WccTproducts> list = criteria.list();
        
        
        List<WccTproducts> list = null;
        if(null != larray && larray.length > 0){
    		list = new ArrayList<WccTproducts>();
    		WccTproducts u = null;
    		Map<Long,WccTproducts> mm = new LinkedHashMap<Long,WccTproducts>();
    		List <Object> lis =  criteria.list();
    		if(null != lis && lis.size() >0 ){
    			for(Object o:lis){
    				u= (WccTproducts) o;
    				mm.put(u.getId(), u);
    			}
    			if(mm.size() > 0){
    				for(Map.Entry<Long,WccTproducts> mp:mm.entrySet()){
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
	
	//手机端页面 查询
	public static List<WccTproducts> getRecordListByType(Long paltformiId,String type){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccTproducts.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != paltformiId){
	    	  cr.add(Restrictions.eq("id",paltformiId));
	      }
	      if(null != type && !"".equals(type)){
	    	  criteria.add(Restrictions.eq("productType",Integer.parseInt(type)));
	      }
	      criteria.addOrder(Order.asc("insertTime"));  
      return criteria.list();
	}
	
	
	
	
	
}

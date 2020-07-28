package com.nineclient.tailong.wcc.model;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import java.util.Set;





import javax.persistence.CascadeType;
/**
 * 泰隆产品实体类
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
import com.nineclient.model.WccStore;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;


@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccSaQrCode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	/**
	 * 1.关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	
	@Size(max = 100)
	private String codeName; // 二维码名称
	
	@Size(max = 5)
	private String state; // 二维码状态  1 开启；2.关闭
	@Size(max = 6000)
	private String remark; // 备注
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private WccStore wccStore; // 关联门店 
	
    /**
  	 *二维码 scene_str
  	 */
	@Size(max = 255)
    private String sceneStr;
	@Size(max = 255)
	private String saName;//sa 名称
	@Size(max = 255)
	private String saPhone;//sa 电话
	@Size(max = 255)
	private String saMailAddress;//sa 邮件地址
    /**
  	 *二维码url
  	 */
	@Size(max = 255)
    private String codeUrl;
		
	/**插入时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date insertTime;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSaName() {
		return saName;
	}
	public void setSaName(String saName) {
		this.saName = saName;
	}
	public String getSaPhone() {
		return saPhone;
	}
	public void setSaPhone(String saPhone) {
		this.saPhone = saPhone;
	}
	public String getSaMailAddress() {
		return saMailAddress;
	}
	public void setSaMailAddress(String saMailAddress) {
		this.saMailAddress = saMailAddress;
	}
	public String getSceneStr() {
		return sceneStr;
	}
	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WccStore getWccStore() {
		return wccStore;
	}

	public void setWccStore(WccStore wccStore) {
		this.wccStore = wccStore;
	}

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
        EntityManager em = new WccSaQrCode().entityManager;
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
            WccSaQrCode attached = WccSaQrCode.findWccTproducts(this.id);
            this.entityManager.remove(attached);
        }
    }
	
	// find by id
	public static WccSaQrCode findWccTproducts(Long id) {
        if (id == null) return null;
        return entityManager().find(WccSaQrCode.class, id);
    }	
	
	@Transactional
    public WccSaQrCode merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccSaQrCode merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static PageModel<WccSaQrCode> getQueryRecord(QueryModel<WccSaQrCode> qm){
		PageModel<WccSaQrCode> pageModel = new PageModel<WccSaQrCode>();
		Map<String, Object> param = qm.getInputMap();
//		StringBuffer sql = new StringBuffer(" SELECT o FROM WccTproducts o WHERE o.openId = '"+param.get("openId")+"'" + "ORDER BY o.insertTime DESC");
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccTproducts o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccSaQrCode> query = entityManager().createQuery(sql.toString(), WccSaQrCode.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
	public static PageModel<WccSaQrCode> getRecord(QueryModel<WccSaQrCode> qm){
		PageModel<WccSaQrCode> pageModel = new PageModel<WccSaQrCode>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccSaQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
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
		criteria.add(Restrictions.like("saName", parm.get("productName")+"",MatchMode.ANYWHERE));
	}
     
if(null != parm.get("productType") && !"".equals(parm.get("productType")+"")){
		criteria.add(Restrictions.eq("state", parm.get("productType")));
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
        
        
        List<WccSaQrCode> list = null;
        if(null != larray && larray.length > 0){
    		list = new ArrayList<WccSaQrCode>();
    		WccSaQrCode u = null;
    		Map<Long,WccSaQrCode> mm = new LinkedHashMap<Long,WccSaQrCode>();
    		List <Object> lis =  criteria.list();
    		if(null != lis && lis.size() >0 ){
    			for(Object o:lis){
    				u= (WccSaQrCode) o;
    				mm.put(u.getId(), u);
    			}
    			if(mm.size() > 0){
    				for(Map.Entry<Long,WccSaQrCode> mp:mm.entrySet()){
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
	public static List<WccSaQrCode> getRecordListByType(Long paltformiId,String type){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccSaQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != paltformiId){
	    	  cr.add(Restrictions.eq("id",paltformiId));
	      }
	      if(null != type && !"".equals(type)){
	    	  criteria.add(Restrictions.eq("codeName",Integer.parseInt(type)));
	      }
	      criteria.addOrder(Order.asc("insertTime"));  
      return criteria.list();
	}
	//
	public static List<WccSaQrCode> getRecordListByState(Long paltformiId[],String state){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccSaQrCode.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != paltformiId){
	    	  cr.add(Restrictions.in("id",paltformiId));
	      }
	      if(null != state && !"".equals(state)){
	    	  criteria.add(Restrictions.eq("state",(state)));
	      }
	      criteria.addOrder(Order.asc("insertTime"));  
      return criteria.list();
	}
	
	
	
	
}

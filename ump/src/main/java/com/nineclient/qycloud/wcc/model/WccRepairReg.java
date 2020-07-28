package com.nineclient.qycloud.wcc.model;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 泰隆产品实体类
 */
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

import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;


@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccRepairReg {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Size(max = 100)
	private String fname; // 姓名
	
	@Size(max = 50)
	private String fphone; // 手机号
	
	@Size(max = 200)
	private String stuNo; // 学号
	
	@Size(max = 200)
	private String dept; // 院系
	
	@Size(max = 200)
	private String area; // 所在区
	
	@Size(max = 200)
	private String address; // 联系地址
	
	@Size(max = 200)
	private String problemType; // 故障分类

	@Size(max = 200)
	private String isBack; // 是否资料备案
	
	@Size(max = 1000)
	private String problemDes; // 故障描述
	
	@Size(max = 200)
	private String fnickName; // 粉丝昵称
	
	@Size(max = 100)
	private String openId; // 粉丝openid
	
	
	
	/**插入时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date insertTime;


	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getProblemDes() {
		return problemDes;
	}

	public void setProblemDes(String problemDes) {
		this.problemDes = problemDes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}



	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getFnickName() {
		return fnickName;
	}

	public void setFnickName(String fnickName) {
		this.fnickName = fnickName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
    public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
        EntityManager em = new WccRepairReg().entityManager;
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
            WccRepairReg attached = WccRepairReg.findWccRepairReg(this.id);
            this.entityManager.remove(attached);
        }
    }
	
	// find by id
	public static WccRepairReg findWccRepairReg(Long id) {
        if (id == null) return null;
        return entityManager().find(WccRepairReg.class, id);
    }	
	
	@Transactional
    public WccRepairReg merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccRepairReg merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static PageModel<WccRepairReg> getQueryRecord(QueryModel<WccRepairReg> qm){
		PageModel<WccRepairReg> pageModel = new PageModel<WccRepairReg>();
		Map<String, Object> param = qm.getInputMap();
//		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFriendsAppointment o WHERE o.openId = '"+param.get("openId")+"'" + "ORDER BY o.insertTime DESC");
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFriendsAppointment o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccRepairReg> query = entityManager().createQuery(sql.toString(), WccRepairReg.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
//      List<WccFriendsAppointment> list = query.getResultList();
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
	public static PageModel<WccRepairReg> getRecord(QueryModel<WccRepairReg> qm){
		PageModel<WccRepairReg> pageModel = new PageModel<WccRepairReg>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccRepairReg.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
   
     
 	if(null != parm.get("productName") && !"".equals(parm.get("productName")+"")){
		criteria.add(Restrictions.like("productName", parm.get("productName")+"",MatchMode.ANYWHERE));
	}
     
 	if(null != parm.get("productType") && !"".equals(parm.get("productType")+"")){
		criteria.add(Restrictions.eq("productType", (parm.get("productType").equals("1")?"融资缺钱":"钱生钱")));
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
        criteria.setFirstResult(qm.getStart());//当前第几页
        criteria.setMaxResults(qm.getLimit());//页的大小
        criteria.addOrder(Order.desc("insertTime"));
        System.out.println("start:==="+qm.getStart());
        System.out.println(qm.getLimit());
		List<WccRepairReg> list = criteria.list();
        

		pageModel.setDataList(list);
		
      return pageModel;
      
	}	
	
	//手机端页面 查询
	public static List<WccRepairReg> getRecordListByType(Long paltformiId,String type){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccRepairReg.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      
	      if(null != paltformiId){
	    	  Criteria cr = criteria.createCriteria("platformUsers");
	    	  cr.add(Restrictions.eq("id",paltformiId));
	      }
	      if(null != type && !"".equals(type)){
	    	  criteria.add(Restrictions.eq("productType",Integer.parseInt(type)));
	      }
      return criteria.list();
	}
	
	
	
	
	
}

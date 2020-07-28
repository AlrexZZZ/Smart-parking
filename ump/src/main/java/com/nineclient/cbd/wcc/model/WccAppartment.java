package com.nineclient.cbd.wcc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccAppartment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private Boolean isDelete;
	private String insertIp;
	private String insertPk;
	private Date insertTime;
	private String updateIp;
	private String updatePk;
	private Date updateTime;
	private String deleteIp;
	private String deletePk;
	private Date deleteTime;
	//公司ID
	private String companyId;
	private String organizationPk;
	

	
	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	
    /**
     * 项目图片地址
     */
	@Size(max = 4000)
	private String itemPick;
	/**
     * 项目名称
     */
	@Size(max = 500)
	private String itemName;
	/**
     * 项目介绍
     */
	@Size(max = 4000)
	private String itemIntro;
	/**
     * 地址
     */
	@Size(max = 500)
	private String itemAddress;
	/**
     * 状态 1 代表新品  2 代表 旧品
     */
	@Size(max = 500)
	private String itemStatus;
	/**
     * 经度
     */
	@Size(max = 500)
	private String itemLng;
	/**
     * 纬度
     */
	@Size(max = 500)
	private String itemLat;
	/**
     * 竣工日期
     */
	private Date itemTime;
	
	
	/**
     * 电话
     */
	private String itemPhone;
	
	@OneToMany
	private Set<WccItemotherinfo> wccItemtherinfo;
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getInsertIp() {
		return insertIp;
	}
	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}
	public String getInsertPk() {
		return insertPk;
	}
	public void setInsertPk(String insertPk) {
		this.insertPk = insertPk;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getUpdatePk() {
		return updatePk;
	}
	public void setUpdatePk(String updatePk) {
		this.updatePk = updatePk;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeleteIp() {
		return deleteIp;
	}
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeletePk() {
		return deletePk;
	}
	public void setDeletePk(String deletePk) {
		this.deletePk = deletePk;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrganizationPk() {
		return organizationPk;
	}
	public void setOrganizationPk(String organizationPk) {
		this.organizationPk = organizationPk;
	}
	public String getItemPick() {
		return itemPick;
	}
	public void setItemPick(String itemPick) {
		this.itemPick = itemPick;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemIntro() {
		return itemIntro;
	}
	public void setItemIntro(String itemIntro) {
		this.itemIntro = itemIntro;
	}
	public String getItemAddress() {
		return itemAddress;
	}
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getItemLng() {
		return itemLng;
	}
	public void setItemLng(String itemLng) {
		this.itemLng = itemLng;
	}
	public String getItemLat() {
		return itemLat;
	}
	public void setItemLat(String itemLat) {
		this.itemLat = itemLat;
	}
	public Date getItemTime() {
		return itemTime;
	}
	public void setItemTime(Date itemTime) {
		this.itemTime = itemTime;
	}
	public String getItemPhone() {
		return itemPhone;
	}
	public void setItemPhone(String itemPhone) {
		this.itemPhone = itemPhone;
	}
	
	public Set<WccItemotherinfo> getWccItemtherinfo() {
		return wccItemtherinfo;
	}
	public void setWccItemtherinfo(Set<WccItemotherinfo> wccItemtherinfo) {
		this.wccItemtherinfo = wccItemtherinfo;
	}


	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	public static List<WccAppartment> getWccAppartmentByName(String itemName,String itemStatus){
		Map<String, Object> map=new HashMap<String, Object>();
		String hql=" FROM WccAppartment o WHERE 1=1  ";
		if(itemName!=null && !itemName.equals("")){
			hql+="  and o.itemName in :itemName ";
			map.put("itemName",itemName);
		}
		if(itemStatus!=null && !itemStatus.equals("")){
			hql+="  and o.itemStatus in :itemStatus ";
			map.put("itemStatus",itemStatus);
		}
		 TypedQuery<WccAppartment> query=entityManager().createQuery(hql,WccAppartment.class);
		 for(String key:map.keySet()){
	            query.setParameter(key, map.get(key));	
	          }
		 return query.getResultList();
	}
	
	//根据公众账号查找
	public static List<WccAppartment> getWccAppartment(Set<WccPlatformUser> platSets){
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer();
		 sql.append("from WccAppartment p  ");
		 sql.append("  WHERE  p.itemStatus='2' ");
//		 if(platSets!=null && platSets.size()>0)
//		 {   
//	            Iterator<WccPlatformUser> it = platSets.iterator();
//	            WccPlatformUser u =null;
//	            String str="";
//	            while (it.hasNext()) {
//	            	u=it.next();
//	            	str+=u.getId()+",";
//	            	break;
//	            }
//			 sql.append(" and p.platformUsers in ("+str.substring(0,str.length()-1)+")");
//		 }
		 TypedQuery<WccAppartment> query = entityManager().createQuery(sql.toString(), WccAppartment.class);
		 return query.getResultList();

	}
	//删除
	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			WccAppartment attached = WccAppartment
					.findPubOrganization(this.id);
			this.entityManager.remove(attached);
		}
	}
	//根据ID查询
	public static WccAppartment findPubOrganization(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccAppartment.class, id);
	}
	
	/**
	 * 分页查询
	 * @param qm
	 * @param pubOper
	 * @return
	 */
	public static PageModel<WccAppartment> getQueryPlatformUser(QueryModel<WccAppartment> qm,PubOperator pubOper){
		
//		jpaQuery = "SELECT o FROM WccPlatformUser o inner join o.pubOperators p where 1=1"
//        		+ " o.company = " + pubOper.getCompany().getId()+" and "
//        		+ " p.id = "+pubOper.getId();
		
		 Map parmMap = qm.getInputMap();
		 WccAppartment platformUser = qm.getEntity();
		PageModel<WccAppartment> pageModel = new PageModel<WccAppartment>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM CbdWccAppartment o WHERE o.isDeleted = 1 AND o.company ="+pubOper.getCompany().getId() +" ORDER BY o.insertTime DESC");
		Map<String,Object> map = new HashMap<String, Object>();
      TypedQuery<WccAppartment> query = entityManager().createQuery(sql.toString(), WccAppartment.class);
      for(String key:map.keySet()){
          query.setParameter(key, map.get(key));	
        }
      System.out.println(query.toString());
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
       List<WccAppartment> list = query.getResultList();
       List<WccAppartment> lists = new ArrayList<WccAppartment>();
       if(null != pubOper.getPubRole()){
    	   for(WccAppartment platform : list){
    		   if(platform.getId()==pubOper.getId()){
    			   lists.add(platform);
    		   }
    	   }
       }else{
    	   lists = list;
       }
       pageModel.setTotalCount(totalCount);
       pageModel.setDataList(lists);
       return pageModel;
	}
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	
	public static WccAppartment findWccAppartmentById(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccAppartment.class, id);
	}
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}
	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	/**
	 * 分页查询
	 * @param qm
	 * @param pubOper
	 * @return
	 */
	public static PageModel<WccAppartment> getQueryAppartment(QueryModel<WccAppartment> qm,PubOperator pubOper){
		
//		jpaQuery = "SELECT o FROM WccPlatformUser o inner join o.pubOperators p where 1=1"
//        		+ " o.company = " + pubOper.getCompany().getId()+" and "
//        		+ " p.id = "+pubOper.getId();
		
		 Map parmMap = qm.getInputMap();
		 WccAppartment platformUser = qm.getEntity();
		PageModel<WccAppartment> pageModel = new PageModel<WccAppartment>();
		// WHERE  o.company ="+pubOper.getCompany().getId() +" 根据公众账号
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccAppartment o ORDER BY o.insertTime DESC");
		Map<String,Object> map = new HashMap<String, Object>();
      TypedQuery<WccAppartment> query = entityManager().createQuery(sql.toString(), WccAppartment.class);
      for(String key:map.keySet()){
          query.setParameter(key, map.get(key));	
        }
      System.out.println(query.toString());
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
       List<WccAppartment> list = query.getResultList();
       List<WccAppartment> lists = new ArrayList<WccAppartment>();
       if(null != pubOper.getPubRole()){
    	   for(WccAppartment platform : list){
    		   if(platform.getId()==pubOper.getId()){
    			   lists.add(platform);
    		   }
    	   }
       }else{
    	   lists = list;
       }
       pageModel.setTotalCount(totalCount);
       pageModel.setDataList(lists);
       return pageModel;
	}
	public static List<WccAppartment> getWccAppartmentByGc(String itemName,String platformUsers,String itemStatus){
		
		String hql=" FROM WccAppartment  o  WHERE 1=1  ";
		if(itemName!=null && !itemName.equals("")){
			hql+="  and o.itemName like '%"+ itemName +"%' ";
		}
		if(platformUsers!=null && !platformUsers.equals("")){
			Long platformUsersLong=Long.parseLong(platformUsers);
			hql+="  and  o.platformUsers in ("+platformUsersLong+") ";
			
		}
		if(itemStatus!=null  &&  !itemStatus.equals("")){
			
			hql+="  and o.itemStatus ='"+itemStatus+"'  ";
			
		}
		TypedQuery<WccAppartment> query=entityManager().createQuery(hql,WccAppartment.class);
		 
		 return query.getResultList();
	}
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageModel<WccAppartment> getRecord(QueryModel<WccAppartment> qm,String itemName,String itemStatus,String platformUsers,String itemIntro){
		PageModel<WccAppartment> pageModel=new PageModel<WccAppartment>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
        //Criteria criteria = s.createCriteria(WccAppartment.class);
        DetachedCriteria dc = DetachedCriteria.forClass(WccAppartment.class).setFetchMode("platformUsers", FetchMode.SELECT);
        Criteria criteria = dc.getExecutableCriteria(s);
        //        Criteria cr = criteria .createCriteria("platformUsers");
//        Criteria cr2=cr.setFetchMode("platformUsers", org.hibernate.FetchMode.JOIN.LAZY);
   //   Criteria cr = criteria.createCriteria("platformUsers");
        String[] lid = null;
        Long[] larray = null;
        String pltStr = platformUsers; 
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
       
       
       
       if(itemName!=null && !itemName.equals("")){
    	   criteria.add(Restrictions.like("itemName", "%"+itemName+"%"));
       }
       if(itemStatus!=null && !itemStatus.equals("")){
    	   criteria.add(Restrictions.like("itemStatus", itemStatus));
      	  System.out.println(itemStatus);
       }
       if(itemIntro!=null && !itemIntro.equals("")){
    	   criteria.add(Restrictions.like("itemIntro", "%"+itemIntro+"%"));
       }
     
       criteria.addOrder(Order.asc("id"));
       Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
   	   criteria.setProjection(null);
 //    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
       pageModel.setTotalCount(Integer.parseInt(totalCount+""));
       pageModel.setPageSize(qm.getLimit());
//       criteria.setFirstResult(qm.getStart());//当前第几页
//       criteria.setMaxResults(qm.getLimit());//页的大小
       List<WccAppartment> list = null;
       if(null != larray && larray.length > 0){
   		list = new ArrayList<WccAppartment>();
   		WccAppartment u = null;
   		Map<Long,WccAppartment> mm = new LinkedHashMap<Long,WccAppartment>();
   		List <Object[]> lis =  criteria.list();
   		if(null != lis && lis.size() >0 ){
   			for(Object[] o:lis){
   				u= (WccAppartment) o[1];
   				mm.put(u.getId(), u);
   			}
   			if(mm.size() > 0){
   				for(Map.Entry<Long,WccAppartment> mp:mm.entrySet()){
   					list.add(mp.getValue());
   				}
   			}
   			
   		}
       }else{
       	list = criteria.list();
       }
       
		//List<WccLifeHelper> list = criteria.list();
		pageModel.setDataList(list);
		return pageModel;
	}
	
   public static List<WccAppartment> getWccAppartmentByXp(String id,String itemStatus){
		
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccAppartment.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id && !id.equals("")){
	    	  cr.add(Restrictions.eq("id",Long.parseLong(id)));
	      }
	      if(null != itemStatus && !itemStatus.equals("")){
	    	  criteria.add(Restrictions.eq("itemStatus",itemStatus));
	      }
	      return criteria.list();
	}

	//修改
	@Transactional
    public WccAppartment merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccAppartment merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	@Override
	public String toString() {
		return "WccAppartment [id=" + id + ", isDelete=" + isDelete
				+ ", insertIp=" + insertIp + ", insertPk=" + insertPk
				+ ", insertTime=" + insertTime + ", updateIp=" + updateIp
				+ ", updatePk=" + updatePk + ", updateTime=" + updateTime
				+ ", deleteIp=" + deleteIp + ", deletePk=" + deletePk
				+ ", deleteTime=" + deleteTime + ", companyId=" + companyId
				+ ", organizationPk=" + organizationPk + ", platformUsers="
				+ platformUsers + ", itemPick=" + itemPick + ", itemName="
				+ itemName + ", itemIntro=" + itemIntro + ", itemAddress="
				+ itemAddress + ", itemStatus=" + itemStatus + ", itemLng="
				+ itemLng + ", itemLat=" + itemLat + ", itemTime=" + itemTime
				+ ", itemPhone=" + itemPhone + ", wccItemtherinfo="
				+ wccItemtherinfo + "]";
	}
	
	
	
}

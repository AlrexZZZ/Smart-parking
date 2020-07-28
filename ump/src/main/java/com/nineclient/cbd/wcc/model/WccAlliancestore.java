package com.nineclient.cbd.wcc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;
//周边特惠
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccAlliancestore {
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
	
	@Size(max = 500)
	private String storeName;//商家名称
	@Size(max = 4000)
	private String storeAddress;//商家地址
	@Size(max = 500)
	private String storeLng;//经度
	@Size(max = 500)
	private String storeLat;//纬度
	@Size(max = 500)
	private	String storePhone;//电话
	@Size(max = 4000)
	private	String storePic;//图片地址
	@Size(max = 4000)
	private String storeIntro; //商家介绍
	@Size(max = 500)
	private String storeType; //商家类型
	@Size(max = 500)
	private String typeStr;//是否发布
	@ManyToOne
	private	WccAlliancestoreType wccAlliancestoreType;
	
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
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}
	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public String getStoreLng() {
		return storeLng;
	}
	public void setStoreLng(String storeLng) {
		this.storeLng = storeLng;
	}
	public String getStoreLat() {
		return storeLat;
	}
	public void setStoreLat(String storeLat) {
		this.storeLat = storeLat;
	}
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getStorePic() {
		return storePic;
	}
	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}
	public String getStoreIntro() {
		return storeIntro;
	}
	public void setStoreIntro(String storeIntro) {
		this.storeIntro = storeIntro;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	
	public WccAlliancestoreType getWccAlliancestoreType() {
		return wccAlliancestoreType;
	}
	public void setWccAlliancestoreType(WccAlliancestoreType wccAlliancestoreType) {
		this.wccAlliancestoreType = wccAlliancestoreType;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	

	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageModel<WccAlliancestore> getRecord(QueryModel<WccAlliancestore> qm){
		PageModel<WccAlliancestore> pageModel=new PageModel<WccAlliancestore>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(WccAlliancestore.class);
        String[] lid = null;
        Long[] larray = null;
        //获取公众平台上id
       String pltStr = parm.get("platformUser")+""; 
       if(!pltStr.equals("null")  && !"".equals(pltStr.trim()) && null!=pltStr){
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
       if(parm.get("storeType")!=null && !parm.get("storeType").equals("")){
    	   criteria.add(Restrictions.eq("storeType", parm.get("storeType")));
       }
       if(parm.get("type")!=null && !parm.get("type").equals("")){
    	   criteria.add(Restrictions.eq("typeStr", parm.get("type")));
       }
       if(parm.get("storeName")!=null && !parm.get("storeName").equals("")){
    	   criteria.add(Restrictions.like("storeName", "%"+parm.get("storeName")+"%"));
       }
       
       
       Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
   	   criteria.setProjection(null);
	//   criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
       pageModel.setTotalCount(Integer.parseInt(totalCount+""));
       pageModel.setPageSize(qm.getLimit());
//       criteria.setFirstResult(qm.getStart());//当前第几页
//       criteria.setMaxResults(qm.getLimit());//页的大小
       List<WccAlliancestore> list = null;
       if(null != larray && larray.length > 0){
   		list = new ArrayList<WccAlliancestore>();
   		WccAlliancestore u = null;
   		Map<Long,WccAlliancestore> mm = new HashMap<Long,WccAlliancestore>();
   		List <Object[]> lis =  criteria.list();
   		if(null != lis && lis.size() >0 ){
   			for(Object[] o:lis){
   				u= (WccAlliancestore) o[1];
   				mm.put(u.getId(), u);
   			}
   			if(mm.size() > 0){
   				for(Map.Entry<Long,WccAlliancestore> mp:mm.entrySet()){
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
	
	
	public static WccAlliancestore findPubOrganization(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccAlliancestore.class, id);
	}
	//修改
			@Transactional
		    public WccAlliancestore merge() {
		        if (this.entityManager == null) this.entityManager = entityManager();
		        WccAlliancestore merged = this.entityManager.merge(this);
		        this.entityManager.flush();
		        return merged;
		    }
			
			
			//删除
			@Transactional
			public void remove() {
				if (this.entityManager == null)
					this.entityManager = entityManager();
				if (this.entityManager.contains(this)) {
					this.entityManager.remove(this);
				} else {
					WccAlliancestore attached = WccAlliancestore
							.findPubOrganization(this.id);
					this.entityManager.remove(attached);
				}
			}
			//添加
			@Transactional
		    public void persist() {
		        if (this.entityManager == null) this.entityManager = entityManager();
		        this.entityManager.persist(this);
		    }
			
			
			public static List<WccAlliancestore> findMyCard(String storeType,String gzjh){
			/*	String hql="From WccAlliancestore  where 1=1 ";
				System.out.println(storeType);
				if(gzjh!=null && !gzjh.equals("")){
					hql+=" and  platformUsers="+gzjh;
				}
				if(storeType!=null  &&	!storeType.equals("")){
					hql+="  and  storeType='"+storeType+"'";
				}
				if(storeType!=null  &&	!storeType.equals("")){
					hql+="  and  typeStr='"+1+"'";
				}
				TypedQuery<WccAlliancestore> query=entityManager().createQuery(hql,WccAlliancestore.class);
				 
				 return query.getResultList();*/
				 
						Session s = (Session) entityManager().getDelegate();
				      Criteria criteria = s.createCriteria(WccAlliancestore.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				      Criteria cr = criteria.createCriteria("platformUsers");
				      if(null != gzjh && !gzjh.equals("")){
				    	  cr.add(Restrictions.eq("id",Long.parseLong(gzjh)));
				      }
				      if(null != storeType && !storeType.equals("")){
				    	  criteria.add(Restrictions.eq("wccAlliancestoreType",WccAlliancestoreType.getById(Long.parseLong(storeType))));
				      }
				      if(null == storeType || storeType.equals("")){
				    	  List<WccAlliancestoreType> type1=WccAlliancestoreType.getwccAlliancestoreType();
				    	  criteria.add(Restrictions.eq("wccAlliancestoreType",WccAlliancestoreType.getById(type1.get(0).getId())));
				      }
				      return criteria.list();
			}
	
}

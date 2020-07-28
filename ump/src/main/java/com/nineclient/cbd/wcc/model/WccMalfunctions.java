package com.nineclient.cbd.wcc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;
//故障报修
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMalfunctions {
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
//	@ManyToMany
//    private Set<WccPlatformUser> platformUsers;
	
	@ManyToOne
	private WccAppartment itemPk;
	
	@ManyToOne
	private WccMalfunctionType malfunctionType;//报修类型
	
	@Size(max = 500)
	private String name;//姓名
	
	@Size(max = 500)
	private String phone;//电话
	
	@Size(max = 500)
	private String address;//地址
	
	@Size(max = 500)
	private String malPic;//图片
	
	@Size(max = 500)
	private	String remark;//备注
	
	@Size(max = 500)
	private String isDealed;//是否已处理
	
	private Date dealDate;//处理日期

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


	public WccAppartment getItemPk() {
		return itemPk;
	}

	public void setItemPk(WccAppartment itemPk) {
		this.itemPk = itemPk;
	}
	


	public WccMalfunctionType getMalfunctionType() {
		return malfunctionType;
	}

	public void setMalfunctionType(WccMalfunctionType malfunctionType) {
		this.malfunctionType = malfunctionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMalPic() {
		return malPic;
	}

	public void setMalPic(String malPic) {
		this.malPic = malPic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsDealed() {
		return isDealed;
	}

	public void setIsDealed(String isDealed) {
		this.isDealed = isDealed;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	
	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	//添加
		@Transactional
		public void persist() {
		      if (this.entityManager == null) this.entityManager = entityManager();
		      this.entityManager.persist(this);
		}
		/**
		 * 查询消息记录（分页）
		 * @param qm
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static PageModel<WccMalfunctions> getRecord(QueryModel<WccMalfunctions> qm){
			PageModel<WccMalfunctions> pageModel=new PageModel<WccMalfunctions>();
			Map<String, Object> param = qm.getInputMap();
			Session s = (Session) entityManager().getDelegate();
	        Criteria criteria = s.createCriteria(WccMalfunctions.class);
	        if(param.get("itemPk")!=null &&  !param.get("itemPk").equals("")){
	          	String[] str=(param.get("itemPk")+"").split(",");
	        	WccAppartment[] wcc=new WccAppartment[str.length];
	        	for(int i=0;i<str.length;i++){
	        		wcc[i]=WccAppartment.findPubOrganization(Long.parseLong(str[i]));
	        	}
	        	criteria.add(Restrictions.in("itemPk", wcc ));
	        }
	        if(param.get("isDealed")!=null &&  !param.get("isDealed").equals("")){
	        	
	        	criteria.add(Restrictions.like("isDealed",param.get("isDealed")));
	        }
	        if(param.get("nickName")!=null &&  !param.get("nickName").equals("")){
	        	List<WccMalfunctionType> list=WccMalfunctionType.getBytype(param.get("nickName")+"");
	        	if(list!=null && list.size()>0  ){
	        		criteria.add(Restrictions.in("malfunctionType", list));
	        	}
	        	else{ 
	        		PageModel<WccMalfunctions> pageModel2=new PageModel<WccMalfunctions>();
	        		pageModel.setTotalCount(0);
	      	        pageModel.setPageSize(qm.getLimit());
	      	        pageModel.setDataList(new ArrayList<WccMalfunctions>());
	        		return pageModel2;
	        	}
	        }
	        
	        
	        Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	    	criteria.setProjection(null);
	 	    criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	 	    System.out.println(totalCount);
	        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
	        pageModel.setPageSize(qm.getLimit());
	        criteria.setFirstResult(qm.getStart());//当前第几页
	        criteria.setMaxResults(qm.getLimit());//页的大小
	        pageModel.setDataList(criteria.list());
	        return pageModel;
		}
		public static WccMalfunctions getById(Long id){
			if (id == null)
				return null;
			return entityManager().find(WccMalfunctions.class, id);
		}
		
		
		//修改
		@Transactional
	    public WccMalfunctions merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        WccMalfunctions merged = this.entityManager.merge(this);
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
				WccMalfunctions attached = WccMalfunctions
						.getById(this.id);
				this.entityManager.remove(attached);
			}
		}
}

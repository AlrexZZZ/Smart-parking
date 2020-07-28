package com.nineclient.cbd.wcc.model;


import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccStore;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONSerializer;
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class CbdWccProprietor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private Boolean isDelete;
	private String insertIp;
	private String insertPk;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;
	private String updateIp;
	private String updatePk;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date updateTime;
	private String deleteIp;
	private String deletePk;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date deleteTime;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private WccAppartment wccappartment;
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private WccFriend wccFriend;
	/**
	 * 关联物业费信息信息实体类
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private  Set<WccFees> wccFees;
	/**
	 * 关联公众号
     */
	/**
	 * 关联组织架构
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private PubOrganization organization;
	/**
	 * 关联公司
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private UmpCompany company;
	/**
	 * 关联公众号
     */
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<WccPlatformUser> platformUsers;

	//业务相关
	@Size(max = 200)
	private String name;
	@Size(max = 50)
    private String phone;
	@Size(max = 50)
	private String friendPk;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date certificationTime;
	@Size(max = 300)
	private String doorplate;
	@Size(max = 100)
	private String messageCode;
	@Size(max = 300)
	private String reservedField1;
	@Size(max = 300)
	private String reservedField2;
	@Size(max = 50)
	private String isEnsured;	
	@Size(max = 200)
	private String tempname;
	@Size(max = 50)
    private String tempphone;
	@Size(max = 300)
	private String tempdoorplate;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private WccAppartment tempappartment;
	
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
	public PubOrganization getOrganization() {
		return organization;
	}
	public void setOrganization(PubOrganization organization) {
		this.organization = organization;
	}
	public UmpCompany getCompany() {
		return company;
	}
	public void setCompany(UmpCompany company) {
		this.company = company;
	}
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}
	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
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

	public String getFriendPk() {
		return friendPk;
	}
	public void setFriendPk(String friendPk) {
		this.friendPk = friendPk;
	}
	public Date getCertificationTime() {
		return certificationTime;
	}
	public void setCertificationTime(Date certificationTime) {
		this.certificationTime = certificationTime;
	}
	public String getDoorplate() {
		return doorplate;
	}
	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getReservedField1() {
		return reservedField1;
	}
	public void setReservedField1(String reservedField1) {
		this.reservedField1 = reservedField1;
	}
	public String getReservedField2() {
		return reservedField2;
	}
	public void setReservedField2(String reservedField2) {
		this.reservedField2 = reservedField2;
	}
	public String getIsEnsured() {
		return isEnsured;
	}
	public void setIsEnsured(String isEnsured) {
		this.isEnsured = isEnsured;
	}

	public WccAppartment getWccappartment() {
		return wccappartment;
	}
	public void setWccappartment(WccAppartment wccappartment) {
		this.wccappartment = wccappartment;
	}

	public WccFriend getWccFriend() {
		return wccFriend;
	}
	public void setWccFriend(WccFriend wccFriend) {
		this.wccFriend = wccFriend;
	}
	public String getTempname() {
		return tempname;
	}
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	public String getTempphone() {
		return tempphone;
	}
	public void setTempphone(String tempphone) {
		this.tempphone = tempphone;
	}
	public String getTempdoorplate() {
		return tempdoorplate;
	}
	public void setTempdoorplate(String tempdoorplate) {
		this.tempdoorplate = tempdoorplate;
	}
	public WccAppartment getTempappartment() {
		return tempappartment;
	}
	public void setTempappartment(WccAppartment tempappartment) {
		this.tempappartment = tempappartment;
	}




	public Set<WccFees> getWccFees() {
		return wccFees;
	}
	public void setWccFees(Set<WccFees> wccFees) {
		this.wccFees = wccFees;
	}




	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CbdWccProprietor().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	public static CbdWccProprietor findCbdWccProprietor(Long id) {
		if (id == null)
			return null;
		return entityManager().find(CbdWccProprietor.class, id);
	}
	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}
	 /**
     * 批量保存业主信息
     * 
     * @param list
     */
    @Transactional
    public static void batchPersist(Collection<CbdWccProprietor> list) {
        for (CbdWccProprietor cbdwccproprietor : list) {
        	cbdwccproprietor.persist();
        }
    }
	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			CbdWccProprietor attached = CbdWccProprietor.findCbdWccProprietor(this.id);
			this.entityManager.remove(attached);
		}
	}
	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}
	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}
	@Transactional
	public CbdWccProprietor merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CbdWccProprietor merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
/**
 * 业主信息分页查询
 * @param qm
 * @return
 */
	public static  PageModel<CbdWccProprietor> getProprietorForPage(QueryModel<CbdWccProprietor> qm)
	{
		 Map parmMap = qm.getInputMap();
		 Map<String, Object> map=new HashMap<String, Object>();
		 CbdWccProprietor modelCbdWccProprietor=qm.getEntity();
		 PageModel<CbdWccProprietor> pageModel = new PageModel<CbdWccProprietor>();
		 StringBuffer sql=new StringBuffer();
		 sql.append("select p from CbdWccProprietor p where 1=1 ");
		// sql.append("left join p.platformUsers plat WHERE 1=1 ");
		 /* if(modelCbdWccProprietor.getPlatformUsers()!=null&&modelCbdWccProprietor.getPlatformUsers().size()>0)
		 {   sql.append(" and  plat in :platformUsers");
			 map.put("platformUsers", modelCbdWccProprietor.getPlatformUsers());
		 }*/
		/* if(modelCbdWccProprietor.getCompany()!=null)
		 {   sql.append(" and p.company=:company");
			 map.put("company", modelCbdWccProprietor.getCompany());
		 }*/
		
		
		 if(StringUtils.isNotEmpty(modelCbdWccProprietor.getIsEnsured()))
		 {
			 sql.append(" and p.isEnsured=:isEnsured");
			 map.put("isEnsured", modelCbdWccProprietor.getIsEnsured());
		 }
		 if(null!=modelCbdWccProprietor.getWccappartment())
		 {
			 sql.append(" and p.wccappartment=:wccappartment");
			 map.put("wccappartment", modelCbdWccProprietor.getWccappartment());
		 }
		 if(StringUtils.isNotEmpty(modelCbdWccProprietor.getName()))
		 {
			 sql.append(" and p.name like:name");
			 map.put("name", "%"+modelCbdWccProprietor.getName()+"%");
		 }	
		 if(StringUtils.isNotEmpty(modelCbdWccProprietor.getPhone()))
		 {
			 sql.append(" and p.phone like:phone");
			 map.put("phone", "%"+modelCbdWccProprietor.getPhone()+"%");
		 }	
			if(StringUtils.isNotEmpty(parmMap.get("createStartTime").toString())){
				   Date createStartTime = DateUtil.dateStringToTimestamp(parmMap.get("createStartTime").toString());
				   sql.append(" and p.insertTime >= :createStartTime ");
				   map.put("createStartTime", createStartTime);
			}
				
		if(StringUtils.isNotEmpty(parmMap.get("createEndTime").toString())){
				   Date createEndTime = DateUtil.dateStringToTimestamp(parmMap.get("createEndTime").toString());
				   sql.append(" and p.insertTime <= :createEndTime ");
				   map.put("createEndTime", createEndTime);
				 }
		if(StringUtils.isNotEmpty(parmMap.get("confirmStartTime").toString())){
			   Date createStartTime = DateUtil.dateStringToTimestamp(parmMap.get("confirmStartTime").toString());
			   sql.append(" and p.certificationTime >= :confirmStartTime ");
			   map.put("confirmStartTime", createStartTime);
		}
			
	   if(StringUtils.isNotEmpty(parmMap.get("confirmEndTime").toString())){
			   Date createEndTime = DateUtil.dateStringToTimestamp(parmMap.get("confirmEndTime").toString());
			   sql.append(" and p.certificationTime <= :confirmEndTime ");
			   map.put("confirmEndTime", createEndTime);
			 }
			
		/* if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
				sql.append(" AND p.platformUsers = :platformUserId");
				map.put("platformUserId",WccPlatformUser.findWccPlatformUser(Long.parseLong(parmMap.get("platformUserId")+"")));
			}*/
		 if(null!=parmMap.get("itempk")&&!"".equals(parmMap.get("itempk"))){
				sql.append(" AND p.wccappartment in ("+parmMap.get("itempk")+")");
				//map.put("itempk","("+parmMap.get("itempk")+")");
		}
		
		 sql.append("  order by p.insertTime");
		 TypedQuery<CbdWccProprietor> query = entityManager().createQuery(sql.toString(), CbdWccProprietor.class);
		 for(String key:map.keySet()){
	            query.setParameter(key, map.get(key));	
	          }
         int totalCount = 0;
           
           { //查总数
        	 String countsqlString=sql.toString().replaceAll("select p", "select count(p)");
            TypedQuery<Long> countQuery = entityManager().createQuery(countsqlString, Long.class);
            for(String key:map.keySet()){
            	countQuery.setParameter(key, map.get(key));	
              }
            totalCount = countQuery.getSingleResult().intValue();
           }
           if(qm.getLimit() > 0){ //分页截取
           	query.setFirstResult(qm.getStart());
               query.setMaxResults(qm.getLimit());
            }  
           List<CbdWccProprietor> list = query.getResultList();
           pageModel.setTotalCount(totalCount);
           pageModel.setDataList(query.getResultList());
           return pageModel;
	}
	
	
	public static List<CbdWccProprietor> getProprietorForList(Long id){
		String hql="from CbdWccProprietor WHERE wccFriend="+id;
		 TypedQuery<CbdWccProprietor> query = entityManager().createQuery(hql, CbdWccProprietor.class);
		return query.getResultList();
	}
	/**
	 * 查询业主信息返回List
	 */
	public static List<CbdWccProprietor> getProprietorForList(WccFriend friend,Set<WccPlatformUser> platSets,String isEnsured,String name,UmpCompany company,WccAppartment wccappartment)
	{    Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer();
		 sql.append(" select p from CbdWccProprietor p  ");
		 sql.append(" inner join p.platformUsers plat WHERE 1=1 ");
		 if(platSets!=null)
		 {   sql.append(" and p.plat in :platformUsers");
			 map.put("platformUsers", platSets); 
		 }
		if(company!=null)
		{
			map.put("company",company);
			sql.append(" and p.company=:company");
		}
		if(friend!=null)
		{
			
			sql.append(" and p.wccFriend =:wccFriend");
			map.put("wccFriend",friend);
		}
		 if(StringUtils.isNotEmpty(isEnsured))
		 {
			 sql.append(" and p.isEnsured=:isEnsured");
			 map.put("isEnsured", isEnsured);
		 }
		 if(null!=wccappartment)
		 {
			 sql.append("and p.wccappartment=:wccappartment");
			 map.put("wccappartment", wccappartment);
		 }
		 if(StringUtils.isNotEmpty(name))
		 {
			 sql.append("and p.name like :name");
			 map.put("name", "%"+name+"%");
		 }		 
		 sql.append(" order by p.insertTime");
		 TypedQuery<CbdWccProprietor> query = entityManager().createQuery(sql.toString(), CbdWccProprietor.class);
		 for(String key:map.keySet()){
	            query.setParameter(key, map.get(key));	
	          }
		 return query.getResultList();
	}
	/**
	 * 根据名称，姓名查询
	 * @param name
	 * @param phone
	 * @param appartment
	 * @param doorplate
	 * @return
	 */
	public static List<CbdWccProprietor> getProprietorsList(String name,String phone,WccAppartment appartment,String doorplate)
	{
		List<CbdWccProprietor> list=null;
		 Map<String, Object> map=new HashMap<String, Object>();
	    StringBuffer sql=new StringBuffer();
		sql.append("select p from CbdWccProprietor p where 1=1 ");
		if(StringUtils.isNotEmpty(name))
		{
			sql.append("and p.name=:name ");
			map.put("name", name);
		}
		if(StringUtils.isNotEmpty(phone))
		{
			sql.append(" and p.phone=:phone ");
			map.put("phone", phone);
		}
		if(appartment!=null)
		{
			sql.append(" and p.wccappartment=:wccappartment ");
			map.put("wccappartment", appartment);
		}
		if(StringUtils.isNotEmpty(doorplate))
		{
			sql.append(" and p.doorplate=:doorplate");
			map.put("doorplate", doorplate);
		}
		 TypedQuery<CbdWccProprietor> query=entityManager().createQuery(sql.toString(),CbdWccProprietor.class);
		 for(String key:map.keySet()){
	            query.setParameter(key, map.get(key));	
	          }
		 return query.getResultList();
	}
	public static String toJsonArray(Collection<CbdWccProprietor> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}


} 


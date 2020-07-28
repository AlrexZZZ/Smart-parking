package com.nineclient.cbd.wcc.model;

import java.util.ArrayList;
import java.util.Date;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccFriend;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 理财用户
 * 
 * @author 9client
 *
 */
@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccFinancialUser {

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

	private String userName; // 姓名

	@ManyToOne
	private WccCredentialType credentialType; // 证件类型

	private String credentialNumber; // 证件号码

	private Long money; // 申请预约额度

	@Size(max = 13)
	private String phone; // 手机

	@Pattern(regexp = "(^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$)")
	private String email;

	@ManyToOne
	private WccFriend wccFriend; // 对应的微信粉丝

	@ManyToMany
	private Set<WccFinancialProduct> wccFinancialProducts; // 理财产品
	
	private String platformUsers;
	
	private Boolean isActive;
	
	private String ck;//是否新添加的（0否1是）

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public WccCredentialType getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(WccCredentialType credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public WccFriend getWccFriend() {
		return wccFriend;
	}

	public void setWccFriend(WccFriend wccFriend) {
		this.wccFriend = wccFriend;
	}

	public Set<WccFinancialProduct> getWccFinancialProducts() {
		return wccFinancialProducts;
	}

	public void setWccFinancialProducts(
			Set<WccFinancialProduct> wccFinancialProducts) {
		this.wccFinancialProducts = wccFinancialProducts;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(String platformUsers) {
		this.platformUsers = platformUsers;
	}

	

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}



	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccFinancialProduct().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static WccFinancialUser findWccFinancialUser(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccFinancialUser.class, id);
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			WccFinancialUser attached = WccFinancialUser
					.findWccFinancialUser(this.id);
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
	public WccFinancialUser merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccFinancialUser merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WccFinancialUser> findFinancialUsersByThisYear(
			String date,Long id) {
		List<WccFinancialUser> wccFinancialUsers = null;
		if (date != null) {
			Session session = (Session) entityManager().getDelegate();
			Criteria criteria = session
					.createCriteria(WccFinancialUser.class)
					.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.ge("insertTime",
					DateUtil.parseDateFormat(date)));
			if(id!=null && !id.equals("")){
				 criteria.createAlias("wccFinancialProducts", "wccFinancialProducts")
			       	.add(Restrictions.eq("wccFinancialProducts.id",id));
			//	criteria.add(Restrictions.eq("wccFinancialProducts", id));
			}
			wccFinancialUsers = criteria.list();
		}
		return wccFinancialUsers;
	}
	//通过粉丝id 和产品id来查询该
	@SuppressWarnings("unchecked")
	public static List<WccFinancialUser> findFinancialUsersInfo(
			Long financialProjectId,Long friendId) {
		List<WccFinancialUser> wccFinancialUsers = null;
		if (financialProjectId != null) {
			Session session = (Session) entityManager().getDelegate();
			Criteria criteria = session
					.createCriteria(WccFinancialUser.class)
					.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			Criteria cr = criteria.createCriteria("wccFinancialProducts");
			Criteria cf = criteria.createCriteria("wccFriend");
			cr.add(Restrictions.eq("id", financialProjectId));
			cf.add(Restrictions.eq("id", friendId));
			wccFinancialUsers = criteria.list();
		}
		return wccFinancialUsers;
	}
	/**
	 * 根据理财产品ID查询预约此产品的用户
	 * 
	 * @param financialProjectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<WccFinancialUser> findFinancialUsersByFinancialProjectId(
			Long financialProjectId) {
		List<WccFinancialUser> wccFinancialUsers = null;
		if (financialProjectId != null) {
			Session session = (Session) entityManager().getDelegate();
			Criteria criteria = session
					.createCriteria(WccFinancialUser.class)
					.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			Criteria cr = criteria.createCriteria("wccFinancialProducts");
			cr.add(Restrictions.eq("id", financialProjectId));
			wccFinancialUsers = criteria.list();
		}
		return wccFinancialUsers;
	}
	
	/**
	 * 查询用户（分页）
	 * 
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccFinancialUser> getRecord(
			QueryModel<WccFinancialUser> qm) {
		PageModel<WccFinancialUser> pageModel = new PageModel<WccFinancialUser>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(WccFinancialUser.class)
				.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		Criteria cr = criteria.createCriteria("wccFinancialProducts");
		
		String[] lid = null;
		Long[] larray = null;
		// 获取公众平台上id
		String pltStr = parm.get("dataId") + "";
		if (null != pltStr && !"".equals(pltStr.trim())) {
			lid = pltStr.split(",");
		}
		if (null != lid && lid.length > 0) {
			larray = new Long[lid.length];
			for (int i = 0; i < lid.length; i++) {
				larray[i] = Long.parseLong(lid[i]
						+
						"");
			}
		}
		if (null != larray && larray.length > 0) {
			cr.add(Restrictions.in("id", larray));
//			criteria.createAlias("area", "o").add(
//					Restrictions.sqlRestriction("AREA_ID  like  (?)",
//							"%" + searchCondition.getAreaId().toString().substring(0, 4) + "%",
//							Hibernate.STRING));
		}
		
		Long totalCount = 0l;
		try {
			
			totalCount =   (Long) criteria.setProjection(
					Projections.countDistinct("id")).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		criteria.setProjection(null);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		pageModel.setTotalCount(Integer.parseInt(totalCount + ""));
		pageModel.setPageSize(qm.getLimit());
		criteria.setFirstResult(qm.getStart());// 当前第几页
		criteria.setMaxResults(qm.getLimit());// 页的大小

		pageModel.setDataList(criteria.list());
		return pageModel;
	}
	
	
	/**
	 * 根据根据手机号查询
	 * 
	 * @param financialProjectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<WccFriend> findFinancialUsersByPhone(
			String phoneStr) {
		List<WccFinancialUser> wccFinancialUsers = null;
		if ( null != phoneStr && !"".equals(phoneStr.trim()) ) {
			Session session = (Session) entityManager().getDelegate();
			Criteria criteria = session
					.createCriteria(WccFinancialUser.class)
					.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			//Criteria cr = criteria.createCriteria("wccFinancialProducts");
			if(null != phoneStr && !"".equals(phoneStr.trim())){
				
				criteria.add(Restrictions.in("phone", phoneStr.split(",")));
			}
			wccFinancialUsers = criteria.list();
		}
		
		List<WccFriend> lf = null;
		if(null != wccFinancialUsers && wccFinancialUsers.size() > 0){
			lf = new ArrayList<WccFriend>();
			for(WccFinancialUser e:wccFinancialUsers){
				lf.add(e.getWccFriend());	
			}
			
		}
		return lf;
	}
	

}

package com.nineclient.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.PlatformType;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.ListSort;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccPlatformUser implements java.io.Serializable {

	/**
     */
	@NotNull
	@Size(min = 1, max = 32)
	private String platformId;

	/**
     */
	@Size(min = 1, max = 200)
	private String account;

	/**
     */
	@Size(max = 64)
	private String password;

	/**
     */
	@Size(max = 4000)
	private String remark;

	/**
     */
	@Size(min = 1, max = 400)
	private String nickname;

	/**
     */
	@Size(min = 1, max = 500)
	private String headImage;

	/**
     */
	private Boolean isValidation;

	/**
     */
	private String loginInfo;

	/**
     */
	private Boolean isDeleted;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;

	/**
     */
	private String appId;

	/**
     */
	private String appSecret;
	
    private PlatformType platformType;
	
	private String authority;
	
	private String authorState;
	//========================
	public String getAuthorState() {
		return authorState;
	}

	public void setAuthorState(String authorState) {
		this.authorState = authorState;
	}
	//========================
	
	public String getAuthority() {
		return authority;
	}
	
	

	@Enumerated(EnumType.STRING)
	public PlatformType getPlatformType() {
		return platformType;
	}

	public void setPlatformType(PlatformType platformType) {
		this.platformType = platformType;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	//========================
	/*@ManyToOne( targetEntity=WccPlatForm.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="wccplatformId",nullable = false, insertable = false, updatable = false)
	private WccPlatForm wccplatform;*/

	public String getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Boolean getIsValidation() {
		return this.isValidation;
	}

	public void setIsValidation(Boolean isValidation) {
		this.isValidation = isValidation;
	}

	public String getLoginInfo() {
		return this.loginInfo;
	}

	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

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
	
	/*public WccPlatForm getWccplatform() {
	return wccplatform;
    }

    public void setWccplatform(WccPlatForm wccplatform) {
	    this.wccplatform = wccplatform;
     }*/
	
	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("platformId", "account", "password", "remark", "fakeId",
					"nickname", "headImage", "isValidation", "loginInfo",
					"isDeleted", "insertTime", "appId", "appSecret", "company",
					"welcomkbses", "groups", "wccAutokbss", "lotteryActivity",
					"userLotterys", "wccContents", "wccStores");

	/**
	 * 根据name查询公众平台
	 * 
	 * @param 
	 * @return
	 */
	public static List<WccPlatformUser> findWccPlatformUserByNameAndCompany(String name,UmpCompany companyId) {
		return name == null || "".equals(name)?null:entityManager()
				.createQuery("SELECT o FROM WccPlatformUser o where o.isDeleted=true and o.account = :name and o.company=:companyId ", WccPlatformUser.class)
				.setParameter("name", name)
				.setParameter("companyId", companyId)
				.getResultList();
	}

	/**
	 * 根据原始ID查询公众平台 add by hunter
	 * 
	 * @param ids
	 * @return
	 */
	public static WccPlatformUser findWccPlatformUserByPlatformId(
			String platformId) {
		if (platformId == null || "".equals(platformId))
			return null;
		WccPlatformUser platformUser = null;
		List<WccPlatformUser> wccPlatformUser = entityManager().createQuery(
				"SELECT o FROM WccPlatformUser o where o.platformId ='"
						+ platformId + "'", WccPlatformUser.class)
				.getResultList();
		if (null != wccPlatformUser && wccPlatformUser.size() > 0) {
			platformUser = wccPlatformUser.get(0);
		}
		return platformUser;
	}

	/**
	 * 根据原始ID查询公众平台 add by hunter
	 * 
	 * @param ids
	 * @return
	 */
	public static List<WccPlatformUser> findWccPlatformUserByplatid(
			String platid) {
		return platid == null || "".equals(platid)?null:entityManager()
				.createQuery("SELECT o FROM WccPlatformUser o where o.isDeleted=true and o.platformId = :platid", WccPlatformUser.class)
				.setParameter("platid", platid)
				.getResultList();
	}

	public static final EntityManager entityManager() {
		EntityManager em = new WccPlatformUser().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countWccPlatformUsers(PubOperator pubOper) {
		String jpaQuery = "";
		if (pubOper.getPubRole() == null) {
			jpaQuery = "SELECT COUNT(o) FROM WccPlatformUser o where 1=1 and"
					+ " o.company = " + pubOper.getCompany().getId();
			jpaQuery = jpaQuery + " ORDER BY o.id DESC";
		} else {
			jpaQuery = "SELECT COUNT(o) FROM WccPlatformUser o inner join o.pubOperators p where "
					+ " o.company = "
					+ pubOper.getCompany().getId()
					+ " and "
					+ " p.id = " + pubOper.getId();
			jpaQuery = jpaQuery + " ORDER BY o.id DESC";
		}

		return entityManager().createQuery(jpaQuery.toString(), Long.class)
				.getSingleResult();
	}

	public static List<WccPlatformUser> findAllWccPlatformUsers(
			PubOperator pubOper) {
		String jpaQuery = "";
		if (pubOper != null) {
			if (pubOper.getPubRole() == null) {
				jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
						+ " o.company = " + pubOper.getCompany().getId();
				jpaQuery = jpaQuery + " ORDER BY o.id DESC";
				return entityManager().createQuery(jpaQuery.toString(),
						WccPlatformUser.class).getResultList();
			} else {
				pubOper = PubOperator.findPubOperator(pubOper.getId());
				List<WccPlatformUser> list = CommonUtils.set2List(pubOper
						.getPlatformUsers());
				ListSort<WccPlatformUser> listSort = new ListSort<WccPlatformUser>();
				listSort.Sort(list, "getInsertTime", "desc");
				return list;
			}
		}
		return null;
	}
	
	//================================TODO 0324
	public static List<WccPlatformUser> findAllWccPlatformUsersBySearchCondi(
			PubOperator pubOper,String inputText) {
		String jpaQuery = "";
		if (pubOper != null) {
			if (pubOper.getPubRole() == null) {
				jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
						+ " o.company = " + pubOper.getCompany().getId();
				if(inputText!=null&&!"".equals(inputText)){
					jpaQuery = jpaQuery + " and o.account like '%"+inputText+"%'";	
				}
				jpaQuery = jpaQuery + " ORDER BY o.id DESC";
				return entityManager().createQuery(jpaQuery.toString(),
						WccPlatformUser.class).getResultList();
			} else {
				pubOper = PubOperator.findPubOperator(pubOper.getId());
				List<WccPlatformUser> list = CommonUtils.set2List(pubOper
						.getPlatformUsers());
				ListSort<WccPlatformUser> listSort = new ListSort<WccPlatformUser>();
				listSort.Sort(list, "getInsertTime", "desc");
				return list;
			}
		}
		return null;
	}
	
	//===========================TODO
	public static List<WccPlatformUser> findAllPlatformByPlatType(String platType,PubOperator pubOper){
		String jpaQuery = "";
		jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
				+ " o.company = " + pubOper.getCompany().getId();
		if(platType!=null&&!"".equals(platType)){
			if("订阅号".equals(platType)){
				jpaQuery =	jpaQuery + " and o.platformType = '0'";
			}
			else{
				jpaQuery =	jpaQuery + " and o.platformType = '1'";
			}
		}
		jpaQuery = jpaQuery + " ORDER BY o.id DESC";
		
		return entityManager().createQuery(jpaQuery.toString(),
				WccPlatformUser.class).getResultList();
		
	}
	
	//=============================================================
	
	
	public static List<WccPlatformUser> findAllWccPlatformList(
			PubOperator pubOper) {
		String jpaQuery = "";
		if (pubOper != null) {
				jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
						+ " o.company = " + pubOper.getCompany().getId();
				jpaQuery = jpaQuery + " ORDER BY o.id DESC";
				return entityManager().createQuery(jpaQuery.toString(),
						WccPlatformUser.class).getResultList();
		}
		return null;
	}

	public static List<WccPlatformUser> findAllWccPlatformUsers(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM WccPlatformUser o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccPlatformUser.class)
				.getResultList();
	}
	
	

	public static WccPlatformUser findWccPlatformUser(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccPlatformUser.class, id);
	}

	public static List<WccPlatformUser> findWccPlatformUserByaccount(
			String account) {
		if (account == null)
			return null;
		return entityManager().createQuery(
				"SELECT o FROM WccPlatformUser o where o.account = '" + account
						+ "'", WccPlatformUser.class).getResultList();
	}

	public static WccPlatformUser findWccPlatformUserByOneAccount(String account,Long companyId) {
		if (account == null)
			return null;
		return entityManager().createQuery(
				"SELECT o FROM WccPlatformUser o where o.account = '" + account
						+ "' and o.company="+companyId, WccPlatformUser.class).getSingleResult();
	}

	public static List<WccPlatformUser> findWccPlatformUserById(Long id) {
		if (id == null)
			return null;
		return entityManager().createQuery(
				"SELECT o FROM WccPlatformUser o where o.id =" + id,
				WccPlatformUser.class).getResultList();
	}
	
	public static List<WccPlatformUser> findWccPlatformUserById2(String id) {
		if (id == null)
			return null;
		return entityManager().createQuery(
				"SELECT o FROM WccPlatformUser o where o.id in(" +id+")",
				WccPlatformUser.class).getResultList();
	}
	

	public static List<WccPlatformUser> findWccPlatformUserEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM WccPlatformUser o",
						WccPlatformUser.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<WccPlatformUser> findWccPlatformUserByIds(String ids) {
		if ("".equals(ids)) {
			return null;
		}
		long id = Long.parseLong(ids);
		return entityManager().createQuery(
				"SELECT o FROM WccPlatformUser o where o.id = '" + id + "'",
				WccPlatformUser.class).getResultList();
	}

	public static List<WccPlatformUser> findWccPlatformUserEntries(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder, PubOperator pubOper) {
		String jpaQuery = null;
		if (pubOper.getPubRole() == null) {
			jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
					+ " o.company = " + pubOper.getCompany().getId();
			jpaQuery = jpaQuery + " ORDER BY o.insertTime DESC";
		} else {
			jpaQuery = "SELECT o FROM WccPlatformUser o inner join o.pubOperators p where "
					+ " o.company = "
					+ pubOper.getCompany().getId()
					+ " and "
					+ " p.id = " + pubOper.getId();
			jpaQuery = jpaQuery + " ORDER BY o.insertTime DESC";
		}
		return entityManager()
				.createQuery(jpaQuery.toString(), WccPlatformUser.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
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
			WccPlatformUser attached = WccPlatformUser
					.findWccPlatformUser(this.id);
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
	public WccPlatformUser merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccPlatformUser merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
     */
	@ManyToOne
	private UmpCompany company;

	/*
	 * @ManyToMany(mappedBy="platformUsers") private Set<WccStore> wccStores =
	 * new HashSet<WccStore>();
	 */
	// /**
	// * 关联二维码
	// */
	// @OneToMany(cascade = CascadeType.ALL, mappedBy =
	// "platformUser",fetch=FetchType.LAZY)
	// private Set<WccQrcode> wccQrcodes = new HashSet<WccQrcode>();
	//
	// /**
	// * 关联门店
	// */
	// @OneToMany(cascade = CascadeType.ALL, mappedBy =
	// "platformUser",fetch=FetchType.LAZY)
	// private Set<WccStore> wccStores = new HashSet<WccStore>();
	//
	// /**
	// * 关联微内容
	// */
	// @OneToMany(cascade = CascadeType.ALL, mappedBy =
	// "PlatformUser",fetch=FetchType.LAZY)
	// private Set<WccContent> wccContents= new HashSet<WccContent>();
	//
	/**
	 * 关联公众平台
	 * 
	 * @return
	 */
	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy =
	 * "platformUser",fetch=FetchType.LAZY) private Set<PubOperator>
	 * pubOperators = new HashSet<PubOperator>();
	 * 
	 * public Set<PubOperator> getPubOperators() { return pubOperators; }
	 * 
	 * public void setPubOperators(Set<PubOperator> pubOperators) {
	 * this.pubOperators = pubOperators; }
	 */
	
	@ManyToMany(mappedBy ="platformUsers")
	private  Set<PubOperator> pubOperators;
	public Set<PubOperator> getPubOperators() {
		return pubOperators;
	}

	public void setPubOperators(Set<PubOperator> pubOperators) {
		this.pubOperators = pubOperators;
	}

	public UmpCompany getCompany() {
		return this.company;
	}

	public void setCompany(UmpCompany company) {
		this.company = company;
	}

	// public Set<WccStore> getWccStores() {
	// return wccStores;
	// }
	//
	// public void setWccStores(Set<WccStore> wccStores) {
	// this.wccStores = wccStores;
	// }
	//
	// public Set<WccContent> getWccContents() {
	// return wccContents;
	// }
	//
	// public void setWccContents(Set<WccContent> wccContents) {
	// this.wccContents = wccContents;
	// }

	// @OneToMany(cascade = CascadeType.ALL, mappedBy =
	// "platformUser",fetch=FetchType.LAZY)
	// private Set<WccAutokbs> wccAutokbss = new HashSet<WccAutokbs>();
	//
	// public Set<WccAutokbs> getWccAutokbss() {
	// return wccAutokbss;
	// }
	//
	// public void setWccAutokbss(Set<WccAutokbs> wccAutokbss) {
	// this.wccAutokbss = wccAutokbss;
	// }
	//

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "platformUser", fetch = FetchType.LAZY)
	private List<WccGroup> groups = new ArrayList<WccGroup>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "platformUser", fetch = FetchType.LAZY)
	private Set<WccMenu> wccMenus = new HashSet<WccMenu>();

	// public Set<WccQrcode> getWccQrcodes() {
	// return wccQrcodes;
	// }
	//
	// public void setWccQrcodes(Set<WccQrcode> wccQrcodes) {
	// this.wccQrcodes = wccQrcodes;
	// }

	public Set<WccMenu> getWccMenus() {
		return wccMenus;
	}

	public void setWccMenus(Set<WccMenu> wccMenus) {
		this.wccMenus = wccMenus;
	}

	public List<WccGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<WccGroup> groups) {
		this.groups = groups;
	}


	// json to dto
	public static String toJsonArray(Collection<WccPlatformUser> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<WccPlatformUser> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	// json to dto
	public static Collection<WccPlatformUser> fromJsonArrayToUmpBrands(
			String json) {
		return new JSONDeserializer<List<WccPlatformUser>>().use("values",
				UmpBrand.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	/**
	 * 公众平台分页查询
	 * @param qm
	 * @param pubOper
	 * @return
	 */
	public static PageModel<WccPlatformUser> getQueryPlatformUser(QueryModel<WccPlatformUser> qm,PubOperator pubOper){
		
//		jpaQuery = "SELECT o FROM WccPlatformUser o inner join o.pubOperators p where 1=1"
//        		+ " o.company = " + pubOper.getCompany().getId()+" and "
//        		+ " p.id = "+pubOper.getId();
		
		 Map parmMap = qm.getInputMap();
		 WccPlatformUser platformUser = qm.getEntity();
		PageModel<WccPlatformUser> pageModel = new PageModel<WccPlatformUser>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccPlatformUser o WHERE o.isDeleted = 1 AND o.company ="+pubOper.getCompany().getId() +" ORDER BY o.insertTime DESC");
		Map<String,Object> map = new HashMap<String, Object>();
      TypedQuery<WccPlatformUser> query = entityManager().createQuery(sql.toString(), WccPlatformUser.class);
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
       List<WccPlatformUser> list = query.getResultList();
       List<WccPlatformUser> lists = new ArrayList<WccPlatformUser>();
       if(null != pubOper.getPubRole()){
    	   for(WccPlatformUser platform : list){
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
	
	/**
	 * 根据查询条件查询公众平台信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static PageModel<WccPlatformUser> getPlatformByCond(QueryModel<WccPlatformUser> qm,PubOperator operator) throws UnsupportedEncodingException{
		Map paramMap = qm.getInputMap();
		PageModel<WccPlatformUser> pageModel = new PageModel<WccPlatformUser>();
		Map<String,Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("SELECT o FROM WccPlatformUser o WHERE o.isDeleted = 1 AND o.company ="+operator.getCompany().getId()+"");
		if(null!=paramMap.get("platformName")&&!"".equals(paramMap.get("platformName"))){
			sql.append("  AND o.account like :platformName");
			try {
				//map.put("platformName", "%"+URLEncoder.encode(paramMap.get("platformName").toString(), "utf-8")+"%");
				map.put("platformName", "%"+paramMap.get("platformName")+"%");
			} catch (Exception e) {
				e.printStackTrace();
			}
			//String name = URLEncoder.encode(paramMap.get("platformName").toString(),"utf-8");
			//sql.append(" AND o.account like '%"+name+"%'");
		}
		
		if(null!=paramMap.get("platType")&&!"".equals(paramMap.get("platType"))){
			sql.append(" AND o.platformType = :platformType");
			if("订阅号".equals(paramMap.get("platType"))){
			//map.put("platformType", paramMap.get("platType"));
				map.put("platformType",PlatformType.订阅号);
			}
			else{
				map.put("platformType", PlatformType.服务号);
			}
		}
		
		sql.append(" ORDER BY o.insertTime DESC");
		
		 TypedQuery<WccPlatformUser> query = entityManager().createQuery(sql.toString(), WccPlatformUser.class);
	      for(String key:map.keySet()){
	          query.setParameter(key, map.get(key));	
	        }
	      System.out.println(query.toString());
	         int totalCount = 0;
	         
	         { //查总数
	          TypedQuery<Long> countQuery = entityManager().createQuery(sql.toString().replaceAll("SELECT o", "SELECT count(o)"), Long.class);
	          for(String key:map.keySet()){
	          	countQuery.setParameter(key, map.get(key));	
	            }
	          totalCount = countQuery.getSingleResult().intValue();
	         }
	       
	       if(qm.getLimit() > 0){ //分页截取
	       	query.setFirstResult(qm.getStart());
	           query.setMaxResults(qm.getLimit());
	       }
	       List<WccPlatformUser> list = query.getResultList();
	       List<WccPlatformUser> lists = new ArrayList<WccPlatformUser>();
	       if(null != operator.getPubRole()){
	    	   for(WccPlatformUser platform : list){
	    		   if(platform.getId()==operator.getId()){
	    			   lists.add(platform);
	    		   }
	    	   }
	       }else{
	    	   lists = list;
	       }
	       pageModel.setTotalCount(totalCount);
	       pageModel.setDataList(lists);
	       return pageModel;
		//return null;
		
	}
	
	public static List<WccPlatformUser> findAllWccPlatformUsersByType(
			PubOperator pubOper,String type) {
		String jpaQuery = "";
		if(type.equals("订阅号")){
			jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
					+ " o.company = " + pubOper.getCompany().getId()+" and o.platformType='0'";
			jpaQuery = jpaQuery + " ORDER BY o.id DESC";
		}if(type.equals("服务号")){
			jpaQuery = "SELECT o FROM WccPlatformUser o where 1=1 and"
					+ " o.company = " + pubOper.getCompany().getId()+" and o.platformType='1'";
			jpaQuery = jpaQuery + " ORDER BY o.id DESC";
		}
		return entityManager().createQuery(jpaQuery.toString(),
				WccPlatformUser.class).getResultList();
	}

	public static List<WccPlatformUser> getplatformuserListByOrg(PubOperator currentoper){
		List<PubOrganization> orgList=PubOrganization.getAllOrganizationByOperator(currentoper);
		List<WccPlatformUser> platformUsers=new ArrayList<WccPlatformUser>();
		if(orgList!=null&&orgList.size()>0){
			for (PubOrganization pubOrganization : orgList) {
				List<PubOperator> puboper=PubOperator.getOperatorByOrg(pubOrganization);
		       for (PubOperator pubOperator : puboper) {
		    	  Set<WccPlatformUser> platformUserset= pubOperator.getPlatformUsers();
		    	  for (WccPlatformUser wccPlatformUser : platformUserset) {
		    		  platformUsers.add(wccPlatformUser);
				}
			   }
			}
		}else{
			platformUsers = WccPlatformUser.findAllWccPlatformUsers(currentoper);
		}
		return platformUsers;
	}
	
	
	public static List<WccPlatformUser> findReportByDateBetween(String startTime, String endTime, int firstResult, int maxResults) {
	      String jpaQuery = "select o from WccPlatformUser o where 1=1 ";
	      if(!"".equals(startTime)){
	    	  jpaQuery+=" and  o.insertTime >='"+startTime+" 00:00:00'";
	      }if(!"".equals(endTime)){
	    	  jpaQuery+=" and o.insertTime <='"+endTime+" 23:59:59'";
	      }
	      jpaQuery+=" order by o.insertTime  desc";
	      List<WccPlatformUser> list=new ArrayList<WccPlatformUser>();
	      if(firstResult!=-1&&maxResults!=-1){
	    	  list=entityManager().createQuery(jpaQuery, WccPlatformUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	        }else{
	        	 list=entityManager().createQuery(jpaQuery, WccPlatformUser.class).getResultList();
	        }
	       return list;

}
	 public static Long getPlatNum(){
	    	String sql="SELECT COUNT(o) FROM WccPlatformUser o ";
	    	Long count =entityManager().createQuery(sql, Long.class).getSingleResult();
	    	return count;
	    }
}

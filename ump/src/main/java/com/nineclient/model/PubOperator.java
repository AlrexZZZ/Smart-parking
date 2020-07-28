package com.nineclient.model;

import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.PlatformType;
import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.dto.PubOperatorDto;
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
public class PubOperator implements java.io.Serializable {

	/**
	 * 邮箱
     */
	@NotNull
	private String email;

	/**
	 * 昵称
     */
	@NotNull
	private String operatorName;

	/**
	 * 账号
     */
	@NotNull
	private String account;

	/**
	 * 密码
     */
	@NotNull
	@Size(min = 6, max = 32)
	private String password;

	/**
	 * 手机
     */
	@NotNull
	private String mobile;

	/**
	 * 账号状态
     */
	private Boolean active;

	/**
	 * 是否自动分配
	 * 1（true）,是
	 * 0(false)，否
     */
	private Boolean autoAllocate;

	/**
	 * 是否删除
     */
	private Boolean isDeleted;

	/**
	 * 创建时间
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy="replyOperator")
    private Set<VocComment> replyComment;
	 
    @OneToMany(mappedBy="dispatchOperator")
    private Set<VocComment> dispatchComments;
	 
	@Version
	@Column(name = "version")
	private Integer version;

	/**
	 * 关联组织架构
	 */
	@ManyToOne
	private PubOrganization organization;

	/**
	 * 关联公司
	 */
	@ManyToOne
	private UmpCompany company;

	/**
	 * 关联权限
	 */
	@ManyToOne
	private PubRole pubRole;

	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;

	/**
	 * 关联平台
	 */
	@ManyToMany
	private Set<UmpChannel> channels;
	
	/**
	 * 关联店铺
	 */
	@ManyToMany
	private Set<VocShop> vocShops;
	
	/**
	 * 关联品牌
	 */
	@ManyToMany
	private Set<VocBrand> vocBrands;
	
	/**
	 * 关联电商账号
	 */
	@ManyToMany
	private Set<VocAccount> vocAccounts;
	
	
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getAutoAllocate() {
		return this.autoAllocate;
	}

	public void setAutoAllocate(Boolean autoAllocate) {
		this.autoAllocate = autoAllocate;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String toString() {
//		return ReflectionToStringBuilder.toString(this,
//				ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("email", "operatorName", "account", "password",
					"telephone", "mobile", "active", "autoAllocate",
					"isDeleted", "createTime");

	public static final EntityManager entityManager() {
		EntityManager em = new PubOperator().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countPubOperators(Long companyId) {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM PubOperator o where o.company = " +companyId, Long.class)
				.getSingleResult();
	}

	/**
	 * 根据公司查询账号
	 * @param company
	 * @return
	 */
	public static List<PubOperator> findPubOperatorsByCompany(UmpCompany company) {
		 TypedQuery<PubOperator> query = entityManager().createQuery(" FROM PubOperator o where o.company =:company ",PubOperator.class);
		 query.setParameter("company", company);
		 return query.getResultList();
	}
	
	public static List<PubOperator> findAllPubOperators() {
		return entityManager().createQuery("SELECT o FROM PubOperator o",
				PubOperator.class).getResultList();
	}

	public static List<PubOperator> findAllPubOperators(String sortFieldName,
			String sortOrder,PubOperator pub) {
		String jpaQuery = "SELECT o FROM PubOperator o where o.company =:company and o.account !=:account ORDER BY createTime DESC";
		TypedQuery<PubOperator> query = entityManager().createQuery(jpaQuery,PubOperator.class);
		query.setParameter("company", pub.getCompany());
		query.setParameter("account", pub.getAccount());
		return query.getResultList();
	}
	
	public static List<PubOperator> findAllPubOperatorsByCompanyId(Long companyId) {
		String jpaQuery = "SELECT o FROM PubOperator o where o.company.id = "+companyId+" ORDER BY createTime DESC";
		return entityManager().createQuery(jpaQuery, PubOperator.class).getResultList();
	}
	
	public static List<PubOperator> findAllPubOperatorsByPlatforms(WccPlatformUser platform) {
		String jpaQuery = "SELECT o FROM PubOperator o inner join o.platformUsers plat where "+platform+" = plat ORDER BY createTime DESC";
		return entityManager().createQuery(jpaQuery, PubOperator.class)
				.getResultList();
	}
	
	public static int findAllPubOperatorsByPlatform(QueryModel<PubOperator> qm){
		PubOperator model = qm.getEntity();
		StringBuffer sql = new StringBuffer(" SELECT o FROM PubOperator o ");
		Map<String,Object> map = new HashMap<String, Object>();
		sql.append(" inner join o.platformUsers plat WHERE plat in :platformUsers ");
		map.put("platformUsers",model.getPlatformUsers());
		sql.append(" ORDER BY o.createTime DESC");
        int totalCount = 0;
        {
        //查总数
        TypedQuery<Long> countQuery = entityManager().createQuery(sql.toString().replaceAll(" SELECT o", "SELECT count(o)"), Long.class);
         for(String key:map.keySet()){
           	countQuery.setParameter(key, map.get(key));	
         }
         totalCount = countQuery.getSingleResult().intValue();
        }
        return totalCount;
	}
	
	public static PubOperator findPubOperator(Long id) {
		if (id == null)
			return null;
		return entityManager().find(PubOperator.class, id);
	}

	public static List<PubOperator> findPubOperatorEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM PubOperator o", PubOperator.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<PubOperator> findPubOperatorEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder,PubOperator pub) {
		String jpaQuery = "SELECT o FROM PubOperator o where o.company = "+pub.getCompany().getId()+" and o.account != '"+pub.getAccount()+"' ORDER BY o.createTime DESC";
		return entityManager().createQuery(jpaQuery, PubOperator.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<PubOperator> findAllPubOperatorsById(long id) {
		return entityManager().createQuery(
				"SELECT o FROM PubOperator o where o.organization ='" + id
						+ "'", PubOperator.class).getResultList();
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
			PubOperator attached = PubOperator.findPubOperator(this.id);
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
	public PubOperator merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PubOperator merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	/**
	 * 根据公司，account
	 * 判断是否存在
	 * @param account
	 * @param pub
	 * @return
	 */
	public static List<PubOperator> findPubOperatorByAccount(String account,PubOperator pub) {
		if(null == account || "".equals(account)) return null;
		TypedQuery<PubOperator> query = entityManager().createQuery(" FROM PubOperator o where o.account =:account and o.company=:company ",PubOperator.class);
		query.setParameter("account", account);
		query.setParameter("company", pub.getCompany());
		return query.getResultList();
	}

	public static List<PubOperator> getPubOperListByFiled2(
			PubOperator pubOperator, String organName) {
		String jpaQuery = "SELECT o FROM PubOperator o where 1=1 and o.isDeleted = 1 ";
		if (null != pubOperator.getPubRole()
				&& !"".equals(pubOperator.getPubRole())) {
			jpaQuery = jpaQuery + " and o.pubRole = "
					+ pubOperator.getPubRole().getId();
		}
		if (null != pubOperator.getActive()
				&& !"".equals(pubOperator.getActive())) {
			jpaQuery = jpaQuery + " and o.active = " + pubOperator.getActive();
		}
		if (null != pubOperator.getCompany()
				&& !"".equals(pubOperator.getCompany().getId())) {
			jpaQuery = jpaQuery + " and o.company = " + pubOperator.getCompany().getId();
		}
		if (null != pubOperator.getAccount()
				&& !"".equals(pubOperator.getAccount())) {
			jpaQuery = jpaQuery + " and o.account like '%"
					+ pubOperator.getAccount() + "%'";
		}
		if (null != pubOperator.getEmail()
				&& !"".equals(pubOperator.getEmail())) {
			jpaQuery = jpaQuery + " and o.email like '%"
					+ pubOperator.getEmail() + "%'";
		}
		if (null != organName && !"".equals(organName)) {
			//PubOrganization pubOrgan = PubOrganization
			//		.findPubOrganizationByName(organName);
			//jpaQuery = jpaQuery + " and o.organization = " + pubOrgan.getId();
		}

		jpaQuery = jpaQuery + " ORDER BY o.createTime desc";
		System.out.println(jpaQuery);
		return entityManager().createQuery(jpaQuery, PubOperator.class)
				.getResultList();
	}
	
	/**
	 * 查询分页获取总数
	 * @param organName
	 * @param account2
	 * @param email2
	 * @param pubRole2
	 * @param active2
	 * @return
	 */
	public static Long countWccStoresByFiled(String organName, String account2,String email2, Long pubRole2, Boolean active2,PubOperator pub) {
	    String jpaQuery = "SELECT COUNT(o) FROM PubOperator o where 1=1 and o.account != '"+pub.getAccount()+"' and o.company="+pub.getCompany().getId();
		if (null != account2 && !"".equals(account2)) {
			jpaQuery = jpaQuery + " and o.account like '%" + account2 + "%'";
        }
        if (!"".equals(email2) && null != email2) {
        	jpaQuery = jpaQuery + " and o.email like '%"+ email2 + "%'";
        }
        if (null != pubRole2 && !"".equals(pubRole2)) {
        	jpaQuery = jpaQuery + " and o.pubRole = " + pubRole2;
        }
        if (null != active2 && !"".equals(active2)) {
        	jpaQuery = jpaQuery + " and o.active = " + active2;
         }
        if(null != organName && !"".equals(organName)){
        	PubOrganization pubOrgan = PubOrganization.findPubOrganizationByName(organName,pub);
            jpaQuery = jpaQuery + " and o.organization = " + pubOrgan.getId();
		}
        System.out.println(jpaQuery.toString());
        return entityManager().createQuery(jpaQuery.toString(), Long.class).getSingleResult();
	}
	
	public static List<PubOperator> getPubOperListByFiled(
			PubOperator pubOperator, String organName) {
		String jpaQuery = "SELECT o FROM PubOperator o where 1=1 and o.isDeleted = 1 ";
		if (null != pubOperator.getPubRole()
				&& !"".equals(pubOperator.getPubRole())) {
			jpaQuery = jpaQuery + " and o.pubRole = "
					+ pubOperator.getPubRole().getId();
		}
		if (null != pubOperator.getActive()
				&& !"".equals(pubOperator.getActive())) {
			jpaQuery = jpaQuery + " and o.active = " + pubOperator.getActive();
		}
		if (null != pubOperator.getAccount()
				&& !"".equals(pubOperator.getAccount())) {
			jpaQuery = jpaQuery + " and o.account like '%"
					+ pubOperator.getAccount() + "%'";
		}
		if (null != pubOperator.getEmail()
				&& !"".equals(pubOperator.getEmail())) {
			jpaQuery = jpaQuery + " and o.email like '%"
					+ pubOperator.getEmail() + "%'";
		}
		if (null != organName && !"".equals(organName)) {
			//PubOrganization pubOrgan = PubOrganization
			//		.findPubOrganizationByName(organName);
			//jpaQuery = jpaQuery + " and o.organization = " + pubOrgan.getId();
		}

		jpaQuery = jpaQuery + " ORDER BY o.createTime desc";
		System.out.println(jpaQuery);
		return entityManager().createQuery(jpaQuery, PubOperator.class)
				.getResultList();
	}
	
	
	public static String toJsonArray(List<PubOperator> pubOperList) {
        return new JSONSerializer()
        .exclude("*.class").serialize(pubOperList);
    }
	
	public static String toJsonArrayDto(List<PubOperatorDto> pubOperList) {
        return new JSONSerializer()
        .exclude("*.class").serialize(pubOperList);
    }


	public static String toJsonArray(Collection<PubOperator> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<PubOperator> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<PubOperator>>()
        .use("values", WccStore.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static List<PubOperator> findPubOperatorEntriesByFiled(
			int firstResult, int maxResults, String organName, String account2,
			String email2, Long pubRole2, Boolean active2,PubOperator pub,String plattype) throws Exception {
		String jpaQuery = "SELECT o FROM PubOperator o where 1=1 and o.account != '"+pub.getAccount()+"' and o.company= "+pub.getCompany().getId();
		if (null != account2 && !"".equals(account2)) {
			jpaQuery = jpaQuery + " and o.account like '%"+account2+"%'";
        }
        if (!"".equals(email2) && null != email2) {
        	jpaQuery = jpaQuery + " and o.email like '%"+email2+"%'";
        }
        if (null != pubRole2 && !"".equals(pubRole2)) {
        	jpaQuery = jpaQuery + " and o.pubRole = " + pubRole2;
        }
        if (null != active2 && !"".equals(active2)) {
        	jpaQuery = jpaQuery + " and o.active = " + active2;
        }
        if(null != organName &&!"".equals(organName)){
        	PubOrganization pubOrgan = PubOrganization.findPubOrganizationByName(organName,pub);
            jpaQuery = jpaQuery + " and o.organization = " + pubOrgan.getId();
		}
        if(null !=plattype && !"".equals(plattype)){
        	/*sql.append(" AND o.platformType = :platformType");
			if("订阅号".equals(paramMap.get("platType"))){
			//map.put("platformType", paramMap.get("platType"));
				map.put("platformType",PlatformType.订阅号);
			}
			else{
				map.put("platformType", PlatformType.服务号);
			}*/
        	if("订阅号".equals(plattype)){
        		//jpaQuery = jpaQuery + " and o.platformType = " + PlatformType.订阅号;
        		jpaQuery = jpaQuery + " and o.platformType = " + "0";
        	}else{
        		//jpaQuery = jpaQuery + " and o.platformType = " + PlatformType.服务号;
        		jpaQuery = jpaQuery + " and o.platformType = " + "1";
        	}
        }
        jpaQuery = jpaQuery + " ORDER BY o.createTime desc";
        
        System.out.println(jpaQuery);
        
        return entityManager().createQuery(jpaQuery, PubOperator.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static PubOperator findPubOperatorByAccount(String account,String companyCode) {
		String hql = "SELECT o FROM PubOperator o where  o.isDeleted = true and o.company.isDeleted = true and o.account = '"+account+"' and o.company.companyCode = '"+companyCode+"'" ;
    	try {
    		return (PubOperator) entityManager().createQuery(hql).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean findPubOperatorByAccountAndPass(String account,String password,String companyCode) {
		String hql = "SELECT o FROM PubOperator o where o.account = '"+account+"' and o.password = '"+password+"' and o.company.companyCode = '"+companyCode+"'" ;
		List<UmpOperator> UOlist = entityManager().createQuery(hql).getResultList();
		if(UOlist.size() > 0){
			return true;
		}
    	return false;
	}
	public static Long findCurrentAccountCount(UmpCompany company){
		if(company==null)return null;
		String hql = "SELECT count(o) FROM PubOperator o where o.company.id =:companyId and o.isDeleted = true" ;
		
		 TypedQuery<Long> query = entityManager().createQuery(hql, Long.class);
		 query.setParameter("companyId", company.getId());
		 return query.getSingleResult();
		
	}
	
	/**
	 * 查询坐席对应的品牌
	 * @param operatorId
	 * @return
	 */
	public static List<Long> findPubOperatorBrands(Long operatorId){
		List<Long> list = new ArrayList<Long>();
		StringBuffer sql = new StringBuffer();
		sql.append("select o.voc_shops from pub_operator_voc_shops o where o.pub_operator = :operatorId ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		 for (String key : map.keySet()) {
		   query.setParameter(key, map.get(key));
		 }
		 for(Object obj:query.getResultList()){
		   list.add(Long.parseLong(String.valueOf(obj)));
		 }
		 
		return  list;
	}
	
	/**
	 * 删除账号查询是否被引用
	 * @param operatorId
	 * @return
	 */
	public static int findPubOperToVocComment(Long operatorId){
		StringBuffer sql = new StringBuffer();
		int count = 0;
		sql.append("select count(*)  from pub_operator po where  EXISTS( select * from voc_comment vc where po.id = vc.dispatch_operator ) and po.id = :id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", operatorId);
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		 for (String key : map.keySet()) {
		   query.setParameter(key, map.get(key));
		 }
		 count =  Integer.parseInt(String.valueOf(query.getSingleResult()));
		return  count;
	}
	
	public static List<Long> findPubOperatorVocShops(Long operatorId){
		 List<Long> list = new ArrayList<Long>();
		StringBuffer sql = new StringBuffer();
		sql.append("select p.channels from pub_operator_channels p where p.pub_operator = :operatorId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		 for (String key : map.keySet()) {
		   query.setParameter(key, map.get(key));
		 }
		 
		 for(Object obj:query.getResultList()){
		   list.add(Long.parseLong(String.valueOf(obj)));
		 }
		 
		return  list;
	}
	
	public static List<Long> findPubOperatorVocAccount(Long operatorId){
		List<Long> list = new ArrayList<Long>();
		StringBuffer sql = new StringBuffer();
		sql.append("select p.voc_accounts from pub_operator_voc_accounts p where p.pub_operator :operatorId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		 for (String key : map.keySet()) {
		   query.setParameter(key, map.get(key));
		 }
		 
		 for(Object obj:query.getResultList()){
		   list.add(Long.parseLong(String.valueOf(obj)));
		 }
		 
		return  list;
	}
	public static PubOperator findAdminOperatorByCompanyId(UmpCompany company){
		StringBuffer sql = new StringBuffer();
		sql.append("select o from PubOperator o where o.company=:company and o.organization is null and o.pubRole is null");
		TypedQuery<PubOperator> query = entityManager().createQuery(sql.toString(), PubOperator.class);
		query.setParameter("company", company);
		return query.getSingleResult();
	}
	public UmpCompany getCompany() {
		return company;
	}

	public void setCompany(UmpCompany company) {
		this.company = company;
	}
	
	public PubOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(PubOrganization organization) {
		this.organization = organization;
	}
	
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public PubRole getPubRole() {
		return pubRole;
	}

	public void setPubRole(PubRole pubRole) {
		this.pubRole = pubRole;
	}

	public Set<UmpChannel> getChannels() {
		return channels;
	}

	public void setChannels(Set<UmpChannel> channels) {
		this.channels = channels;
	}

	public Set<VocShop> getVocShops() {
		return vocShops;
	}

	public void setVocShops(Set<VocShop> vocShops) {
		this.vocShops = vocShops;
	}

	public Set<VocBrand> getVocBrands() {
		return vocBrands;
	}

	public void setVocBrands(Set<VocBrand> vocBrands) {
		this.vocBrands = vocBrands;
	}

	public Set<VocAccount> getVocAccounts() {
		return vocAccounts;
	}

	public void setVocAccounts(Set<VocAccount> vocAccounts) {
		this.vocAccounts = vocAccounts;
	}

	public Set<VocComment> getReplyComment() {
		return replyComment;
	}

	public void setReplyComment(Set<VocComment> replyComment) {
		this.replyComment = replyComment;
	}

	public Set<VocComment> getDispatchComments() {
		return dispatchComments;
	}

	public void setDispatchComments(Set<VocComment> dispatchComments) {
		this.dispatchComments = dispatchComments;
	}
	
	public static List<PubOperator> getOperatorByOrg(PubOrganization organization){
		String sql="select o from PubOperator o where o.organization="+organization.getId();
		return entityManager().createQuery(sql.toString(), PubOperator.class).getResultList();
	}
	
	public static List<PubOperator> findReportByDateBetween(Long companyid,String startTime, String endTime, int firstResult, int maxResults) {
	      String jpaQuery = "select o from PubOperator o where 1=1 and o.company="+companyid;
	      if(!"".equals(startTime)){
	    	  jpaQuery+=" and  o.createTime >='"+startTime+" 00:00:00'";
	      }if(!"".equals(endTime)){
	    	  jpaQuery+=" and o.createTime <='"+endTime+" 23:59:59'";
	      }
	      jpaQuery+=" order by o.createTime  desc";
	      List<PubOperator> list=new ArrayList<PubOperator>();
	      if(firstResult!=-1&&maxResults!=-1){
	    	  list=entityManager().createQuery(jpaQuery, PubOperator.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	        }else{
	        	 list=entityManager().createQuery(jpaQuery, PubOperator.class).getResultList();
	        }
	       return list;

  }
}

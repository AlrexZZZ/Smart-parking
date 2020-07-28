package com.nineclient.model;

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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.constant.UmpCompanyStatus;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
public class UmpCompany implements java.io.Serializable {

	/**
	 * 公司名称
	 */
	@NotNull
	private String name;

	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 是否显示
	 */
	private Boolean isVisable;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	/**
	 * 公司编号
	 */
	@NotNull
	@Column(unique = true)
	private String companyCode;

	@NotNull
	@Value("0")
	private int maxAccount;

	private String remark;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date serviceStartTime;

	/**
	 * 服务结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date serviceEndTime;

	@Enumerated
	private UmpCompanyStatus status;
	@OneToMany(mappedBy = "umpCompany")
	private Set<VocBrand> vocBrands;
	@OneToMany(mappedBy = "umpCompany")
	private Set<VocSkuProperty> vocSkuProperty;
	@OneToMany(mappedBy = "umpCompany")
	private Set<VocGoods> vocGoods;
	@OneToMany(mappedBy = "umpCompany")
	private Set<WccLotteryActivity> activities;

	public Set<VocGoods> getVocGoods() {
		return vocGoods;
	}

	public void setVocGoods(Set<VocGoods> vocGoods) {
		this.vocGoods = vocGoods;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("name", "isDeleted", "isVisable", "createTime",
					"companyCode", "maxAccount", "serviceStartTime",
					"serviceEndTime", "status");

	public static final EntityManager entityManager() {
		EntityManager em = new UmpCompany().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static Long countUmpCompanys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UmpCompany o",
				Long.class).getSingleResult();
	}

	public static List<UmpCompany> findAllUmpCompanys() {
		return entityManager().createQuery("SELECT o FROM UmpCompany o",
				UmpCompany.class).getResultList();
	}

	public static List<UmpCompany> findUmpCompanysByCompanyCode(
			String companyCode) {
		String sqlStr = "FROM UmpCompany WHERE companyCode ='" + companyCode
				+ "'";
		return entityManager().createQuery(sqlStr, UmpCompany.class)
				.getResultList();
	}

	public static List<UmpCompany> findUmpCompanysByEmail(String email) {
		String sqlStr = "FROM UmpCompany WHERE email ='" + email + "'";
		return entityManager().createQuery(sqlStr, UmpCompany.class)
				.getResultList();
	}

	public static List<UmpCompany> findAllUmpCompanys(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM UmpCompany o ";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, UmpCompany.class)
				.getResultList();
	}

	/**
	 * 获取审核通过的公司
	 * 
	 * @return
	 */
	public static List<UmpCompany> findAllServiceUmpCompanys() {
		return entityManager().createQuery(
				"SELECT o FROM UmpCompany o WHERE o.status = "
						+ UmpCompanyStatus.审核通过.ordinal(), UmpCompany.class)
				.getResultList();
	}

	/**
	 * 查询不是审核通过的公司
	 * 
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
	public static List<UmpCompany> findAllRegisterUmpCompanys(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM UmpCompany o WHERE o.status != "
				+ UmpCompanyStatus.审核通过.ordinal()+" AND o.isVisable=1";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, UmpCompany.class)
				.getResultList();
	}

	public static UmpCompany findUmpCompany(Long id) {
		if (id == null)
			return null;
		return entityManager().find(UmpCompany.class, id);
	}

	public static List<UmpCompany> findUmpCompanyByIsVisable() {
		return entityManager().createQuery(
				"SELECT o FROM UmpCompany o where o.isVisable = 0", UmpCompany.class)
						.getResultList();
	}
	
	public static UmpCompany findUmpCompanyByCode(String companyCode) {
		if (companyCode == null)
			return null;
		return entityManager().createQuery(
				"SELECT o FROM UmpCompany o where o.companyCode = '"
						+ companyCode + "'", UmpCompany.class)
				.getSingleResult();
	}

	public static  List<UmpCompany> findUmpCompanyByCodes(String companyCode) {
		if (companyCode == null)
			return null;
		return entityManager().createQuery(
				"SELECT o FROM UmpCompany o where o.companyCode = '"
						+ companyCode + "'", UmpCompany.class)
				.getResultList();
	}
	/*
	 * 查询公司列表
	 */
	public static List<Map> findUmpCompanyList(int firstResult,
			int maxResults,
			Long parentBusinessId, 
			Long checkStatus, 
			Long productId,
			Long channelId,
			Long versionId,
			String companyCode,
			Date startTime,
			Date endTime
			) {
		String sql =" select o.id, o.company_code,o.create_time,p.business_name,o.name, "+
						" o.email,o.max_account,"+
						" o.status,o.address,o.url ,"+
						" o.mobile_phone,"+
						" pd.product_name,"+
						" GROUP_CONCAT(ch.channel_name) channel_name,"+
						" v.version_name"+
						" from ump_company o "+
						" left JOIN "+
						" ump_company_parent_business_type cp on o.id=cp.company "+
						" left JOIN "+
						" ump_parent_business_type p on p.id=cp.parent_business_type "+
						" LEFT JOIN ump_company_service s on s.company_code=o.company_code "+
						" Left join ump_product pd on s.product_id=pd.id "+
						" left JOIN ump_company_service_channels cch on s.id=cch.company_services "+
						" left join ump_channel ch on ch.id=cch.channels "+
						" left join ump_version v on s.version_id=v.id where 1=1 ";
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(parentBusinessId)&&parentBusinessId!=-1){
			sql+=" and p.id=:parentBusinessId";
			map.put("parentBusinessId", parentBusinessId);
		}
		if(!StringUtils.isEmpty(companyCode)){
			sql+=" and o.company_code=:companyCode";
			map.put("companyCode", companyCode);
		}
		if(!StringUtils.isEmpty(productId)&&productId!=-1){
			sql+=" and pd.id=:productId";
			map.put("productId", productId);
		}
		if(!StringUtils.isEmpty(checkStatus)&&checkStatus!=-1){
			sql+=" and o.status=:checkStatus";
			map.put("checkStatus", checkStatus);
		}else{
			sql+=" and o.status!=1";
		}
		if(!StringUtils.isEmpty(channelId)&&channelId!=-1){
			sql+=" and ch.id=:channelId";
			map.put("channelId", channelId);
		}
		if(!StringUtils.isEmpty(versionId)&&versionId!=-1){
			sql+=" and v.id=:versionId";
			map.put("versionId", versionId);
		}
		if(!StringUtils.isEmpty(startTime)){
			sql+=" and o.create_time>=:startTime";
			map.put("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			sql+=" and o.create_time<=:endTime";
			map.put("endTime", endTime);
		}
		sql+=" and o.is_visable=1";
		sql+=" group by o.id, o.company_code,o.create_time,p.business_name,o.name, o.email,o.max_account, o.mobile_phone,pd.product_name,v.version_name ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for(String key:map.keySet()){
			query.setParameter(key, map.get(key));
		}
		return query.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	public static List<UmpCompany> findUmpCompanyEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM UmpCompany o", UmpCompany.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<UmpCompany> findUmpCompanyEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM UmpCompany o ";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, UmpCompany.class)
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
			UmpCompany attached = UmpCompany.findUmpCompany(this.id);
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
	public UmpCompany merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UmpCompany merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static TypedQuery<UmpCompany> getQueryUmpCompany(UmpCompany entity,
			Map<String, Object> param) {
		EntityManager em = UmpCompany.entityManager();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder queryBuilder = new StringBuilder(
				" SELECT  o FROM UmpCompany AS o WHERE o.status = "
						+ UmpCompanyStatus.审核不通过.ordinal());

		if (null != entity.getName() && !"".equals(entity.getName())) {
			queryBuilder.append(" and o.name LIKE LOWER(:name) ");
			map.put("name", entity.getName());
		}

		if (null != entity.getCompanyCode()
				&& !"".equals(entity.getCompanyCode())) {
			queryBuilder.append(" and o.companyCode = (:companyCode) ");
			map.put("companyCode", entity.getCompanyCode());
		}

		TypedQuery<UmpCompany> q = em.createQuery(queryBuilder.toString(),
				UmpCompany.class);

		for (String key : map.keySet()) {
			q.setParameter(key, map.get(key));
		}

		int totalCount = em
				.createQuery("SELECT count(o) FROM UmpCompany as o ",
						Long.class).getSingleResult().intValue();

		System.out.println("  q.getResultList() " + q.getResultList().size()
				+ " totalCount ==>> " + totalCount);

		int limit = 0;
		int start = 0;
		if (null != param.get("limit")) {
			limit = (Integer) param.get("limit");

		}
		if (null != param.get("start")) {
			start = (Integer) param.get("start");

		}

		if (limit > 0) {
			q.setFirstResult(start);
			q.setMaxResults(limit);
		}

		return q;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsVisable() {
		return this.isVisable;
	}

	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getMaxAccount() {
		return this.maxAccount;
	}

	public void setMaxAccount(int maxAccount) {
		this.maxAccount = maxAccount;
	}

	public Date getServiceStartTime() {
		return this.serviceStartTime;
	}

	public void setServiceStartTime(Date serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public Date getServiceEndTime() {
		return this.serviceEndTime;
	}

	public void setServiceEndTime(Date serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public UmpCompanyStatus getStatus() {
		return this.status;
	}

	public void setStatus(UmpCompanyStatus status) {
		this.status = status;
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

	public String toString() {
//		return ReflectionToStringBuilder.toString(this,
//				ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
	}

	

	/**
     */
	@ManyToMany
	private Set<UmpParentBusinessType> parentBusinessType = new HashSet<UmpParentBusinessType>();

	/**
     */
	@ManyToMany
	private Set<UmpBusinessType> bussinessTypes;

	/**
     */
	private String url;

	/**
     */
	@NotNull
	private String mobilePhone;

	/**
     */
	@NotNull
	private String email;

	/**
     */
	@NotNull
	private String address;

	


	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UmpBusinessType> getBussinessTypes() {
		return this.bussinessTypes;
	}

	public void setBussinessTypes(Set<UmpBusinessType> bussinessTypes) {
		this.bussinessTypes = bussinessTypes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<WccPlatformUser> platformUsers = new HashSet<WccPlatformUser>();

	public Set<WccPlatformUser> getPlatformUsers() {
		return this.platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
	private Set<WccMassMessage> wccMassMessages;
	
	public Set<WccMassMessage> getWccMassMessages() {
		return wccMassMessages;
	}

	public void setWccMassMessages(Set<WccMassMessage> wccMassMessages) {
		this.wccMassMessages = wccMassMessages;
	}

	/**
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<UmpOperator> operators = new HashSet<UmpOperator>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<PubOperator> pubOpers = new HashSet<PubOperator>();

	public Set<WccStore> getWccStores() {
		return wccStores;
	}

	public void setWccStores(Set<WccStore> wccStores) {
		this.wccStores = wccStores;
	}

	public Set<WccQrcode> getWccQrcodes() {
		return wccQrcodes;
	}

	public void setWccQrcodes(Set<WccQrcode> wccQrcodes) {
		this.wccQrcodes = wccQrcodes;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<WccStore> wccStores = new HashSet<WccStore>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<WccQrcode> wccQrcodes = new HashSet<WccQrcode>();

	public Set<PubOperator> getPubOpers() {
		return pubOpers;
	}

	public void setPubOpers(Set<PubOperator> pubOpers) {
		this.pubOpers = pubOpers;
	}

	public Set<UmpOperator> getOperators() {
		return this.operators;
	}

	public void setOperators(Set<UmpOperator> operators) {
		this.operators = operators;
	}

	public Set<VocBrand> getVocBrands() {
		return vocBrands;
	}

	public void setVocBrands(Set<VocBrand> vocBrands) {
		this.vocBrands = vocBrands;
	}



	public Set<UmpParentBusinessType> getParentBusinessType() {
		return parentBusinessType;
	}

	public void setParentBusinessType(
			Set<UmpParentBusinessType> parentBusinessType) {
		this.parentBusinessType = parentBusinessType;
	}

	public Set<VocSkuProperty> getVocSkuProperty() {
		return vocSkuProperty;
	}

	public void setVocSkuProperty(Set<VocSkuProperty> vocSkuProperty) {
		this.vocSkuProperty = vocSkuProperty;
	}
	
	public static String toJsonArray(Collection<UmpCompany> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpCompany> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<UmpCompany> fromJsonArrayToUmpCompanys(String json) {
        return new JSONDeserializer<List<UmpCompany>>()
        .use("values", UmpCompany.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static Integer countUmpCompanys(int firstResult, int sizeNo,
			Long parentBusinessId, Long checkStatus, Long productId,
			Long channelId, Long versionId,String companyCode, Date startTime,Date endTime) {
		String sql =" select o.company_code,o.create_time, "+
				" o.email,o.max_account,"+
				" o.`status`,o.address,o.url ,"+
				" o.mobile_phone,"+
				" pd.product_name,"+
				" GROUP_CONCAT(ch.channel_name) channel_name,"+
				" v.version_name"+
				" from ump_company o"+
				" left JOIN "+
				" ump_company_parent_business_type cp on o.id=cp.company "+
				" left JOIN "+
				" ump_parent_business_type p on p.id=cp.parent_business_type "+
				" LEFT JOIN ump_company_service s on s.company_code=o.company_code "+
				" Left join ump_product pd on s.product_id=pd.id "+
				" left JOIN ump_company_service_channels cch on s.id=cch.company_services "+
				" left join ump_channel ch on ch.id=cch.channels "+
				" left join ump_version v on s.version_id=v.id where 1=1 ";
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(parentBusinessId)&&parentBusinessId!=-1){
			sql+=" and p.id=:parentBusinessId";
			map.put("parentBusinessId", parentBusinessId);
		}
		if(!StringUtils.isEmpty(companyCode)){
			sql+=" and o.company_code=:companyCode";
			map.put("companyCode", companyCode);
		}
		if(!StringUtils.isEmpty(productId)&&productId!=-1){
			sql+=" and pd.id=:productId";
			map.put("productId", productId);
		}
		if(!StringUtils.isEmpty(checkStatus)&&checkStatus!=-1){
			sql+=" and o.status=:checkStatus";
			map.put("checkStatus", checkStatus);
		}else{
			sql+=" and o.status!=1";
		}
		if(!StringUtils.isEmpty(channelId)&&channelId!=-1){
			sql+=" and ch.id=:channelId";
			map.put("channelId", channelId);
		}
		if(!StringUtils.isEmpty(versionId)&&versionId!=-1){
			sql+=" and v.id=:versionId";
			map.put("versionId", versionId);
		}
		if(!StringUtils.isEmpty(startTime)){
			sql+=" and o.create_time>=:startTime";
			map.put("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			sql+=" and o.create_time<=:endTime";
			map.put("endTime", endTime);
		}
		sql+=" and o.is_visable=1";
		sql+=" group by o.id, o.company_code,o.create_time,p.business_name,o.name, o.email,o.max_account, o.mobile_phone,pd.product_name,v.version_name ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for(String key:map.keySet()){
			query.setParameter(key, map.get(key));
		}
		List list = query.list();
		if(list!=null)
		return list.size();
		return 0;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static Object findAllUmpCompanysById() {
		return entityManager().createQuery("SELECT o FROM UmpCompany o where o.id = 1",
				UmpCompany.class).getResultList();
	}

}

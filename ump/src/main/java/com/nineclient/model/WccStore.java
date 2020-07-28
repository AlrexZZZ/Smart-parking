package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccStore {

	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 门店名称
	 */
	@NotNull
	@Size(min = 1, max = 200)
	private String storeName;

	/**
	 * 门店邮箱
	 */
	@NotNull
	@Pattern(regexp = "(^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$)")
	private String storeEmail;

	/**
	 * 门店地址
	 */
	@NotNull
	@Size(min = 1, max = 200)
	private String storeAddres;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createTime;

	/**
	 * 门店号码
	 */
	@NotNull
	@Size(min = 1, max = 100)
	private String storePhone;

	/**
	 * 是否可见
	 */
	private Boolean isVisiable;

	/**
	 * 门店备注
	 */
	@Size(max = 4000)
	private String storeText;

	/**
	 * 是否生成二维码
	 */
	private Boolean isStoreCode;

	/**
     * 
     */
	@Size(max = 4000)
	private String storeRemark;

	/**
	 * 门店精度
	 */
	@NotNull
	@Size(min = 1, max = 200)
	private String storeLngx;

	/**
	 * 门店维度
	 */
	@NotNull
	@Size(min = 1, max = 200)
	private String storeLaty;

	/**
	 * 门店地址
	 */
	private String storeUrl;

	@Size(min = 1, max = 500)
	private String storeCodeUrl;

	/**
	 * 门店大图片
	 */
	private String storeBigImageUrl;

	/**
	 * 门店小图片
	 */
	private String storeSmallImageUrl;

	/**
	 * 门店审核状态 0未审核，2审核未通过，1已审核
	 */
	private int storeAuditing;

	/**
	 * 是否发生邮件
	 */
	private boolean isSendMail;

	/**
	 * 获取二维码对应的sceneId
	 */
	private String sceneId;

	/**
	 * 关联公众号
	 */
	@ManyToMany
	private Set<WccPlatformUser> platformUsers;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	/**
	 * 关联组织
	 */
	@ManyToOne
	private PubOrganization organization;
	/**
	 * 关联公司
	 */
	@ManyToOne
	private UmpCompany company;

	private String platFormName;

	public String getPlatFormName() {
		return platFormName;
	}

	public void setPlatFormName(String platFormName) {
		this.platFormName = platFormName;
	}

	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public UmpCompany getCompany() {
		return company;
	}

	public void setCompany(UmpCompany company) {
		this.company = company;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getStoreName() {
		return this.storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddres() {
		return this.storeAddres;
	}

	public void setStoreAddres(String storeAddres) {
		this.storeAddres = storeAddres;
	}

	public String getStorePhone() {
		return this.storePhone;
	}

	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}

	public String getStoreText() {
		return this.storeText;
	}

	public void setStoreText(String storeText) {
		this.storeText = storeText;
	}

	public Boolean getIsStoreCode() {
		return isStoreCode;
	}

	public void setIsStoreCode(Boolean isStoreCode) {
		this.isStoreCode = isStoreCode;
	}

	public String getStoreRemark() {
		return this.storeRemark;
	}

	public void setStoreRemark(String storeRemark) {
		this.storeRemark = storeRemark;
	}

	public String getStoreLngx() {
		return this.storeLngx;
	}

	public void setStoreLngx(String storeLngx) {
		this.storeLngx = storeLngx;
	}

	public String getStoreLaty() {
		return this.storeLaty;
	}

	public void setStoreLaty(String storeLaty) {
		this.storeLaty = storeLaty;
	}

	public String getStoreCodeUrl() {
		return this.storeCodeUrl;
	}

	public void setStoreCodeUrl(String storeCodeUrl) {
		this.storeCodeUrl = storeCodeUrl;
	}

	public String getStoreBigImageUrl() {
		return this.storeBigImageUrl;
	}

	public void setStoreBigImageUrl(String storeBigImageUrl) {
		this.storeBigImageUrl = storeBigImageUrl;
	}

	public String getStoreSmallImageUrl() {
		return this.storeSmallImageUrl;
	}

	public void setStoreSmallImageUrl(String storeSmallImageUrl) {
		this.storeSmallImageUrl = storeSmallImageUrl;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("isDeleted", "storeName", "storeAddres", "storePhone",
					"storeText", "storeCode", "storeRemark", "storeLngx",
					"storeLaty", "storeCodeUrl", "storeBigImageUrl",
					"storeSmallImageUrl", "storeAuditing");

	public static final EntityManager entityManager() {
		EntityManager em = new WccStore().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countWccStores(PubOperator pub) {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM WccStore o where o.company="
						+ pub.getCompany().getId(), Long.class)
				.getSingleResult();
	}

	public static List<WccStore> findAllWccStores() {
		return entityManager().createQuery("SELECT o FROM WccStore o",
				WccStore.class).getResultList();
	}

	public static List<WccStore> findAllWccStoresByStatus(Long platformId) {
		return entityManager().createQuery(
				"SELECT o FROM WccStore o inner join o.platformUsers plat WHERE plat="
						+ platformId + " and o.storeAuditing = 1 ",
				WccStore.class).getResultList();
	}

	public static List<WccStore> findAllWccStores(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM WccStore o";
		/*
		 * if (fieldNames4OrderClauseFilter.contains(sortFieldName)) { jpaQuery
		 * = jpaQuery + " ORDER BY " + sortFieldName; if
		 * ("ASC".equalsIgnoreCase(sortOrder) ||
		 * "DESC".equalsIgnoreCase(sortOrder)) { jpaQuery = jpaQuery + " " +
		 * sortOrder; } }
		 */jpaQuery = jpaQuery + " ORDER BY createTime DESC";
		return entityManager().createQuery(jpaQuery, WccStore.class)
				.getResultList();
	}

	public static WccStore findWccStore(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccStore.class, id);
	}

	public static List<WccStore> findWccStoreEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM WccStore o", WccStore.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	/**
	 * 根据查询条件查询数据
	 * 
	 * @param qm
	 * @param pub
	 * @param organName
	 * @return
	 */
	public static PageModel<WccStore> findWccStoreEntries(
			QueryModel<WccStore> qm, PubOperator pub, String organName) {
		Map<String, Object> parmMap = qm.getInputMap();
		WccStore model = qm.getEntity();
		PageModel<WccStore> pageModel = new PageModel<WccStore>();
		StringBuffer sql = new StringBuffer(
				" SELECT DISTINCT o FROM WccStore o ");
		Map<String, Object> map = new HashMap<String, Object>();
		if (model.getPlatformUsers().size() > 0
				&& model.getPlatformUsers() != null) {
			sql.append(" inner join o.platformUsers plat WHERE plat in :platformUser");
			map.put("platformUser", model.getPlatformUsers());
		} else {
			sql.append("  where 1=1 and o.company = ").append(
					pub.getCompany().getId());
		}

		if (null != parmMap.get("storeName")
				&& !"".equals(parmMap.get("storeName"))) {
			sql.append(" AND o.storeName like :storeName");
			map.put("storeName", "%" + parmMap.get("storeName") + "%");
		}

		if (null != parmMap.get("storeAuditing")
				&& !"".equals(parmMap.get("storeAuditing"))) {
			sql.append(" AND o.storeAuditing = :storeAuditing");
			map.put("storeAuditing", parmMap.get("storeAuditing"));
		}

		if (null != organName && !"".equals(organName)) {
			PubOrganization pubOrgan = PubOrganization
					.findPubOrganizationByName(organName, pub);
			System.out.println(pubOrgan.getId());
			sql.append(" AND o.organization = ").append(pubOrgan.getId());
		}

		if (null != parmMap.get("isSendMail")
				&& !"".equals(parmMap.get("isSendMail"))) {
			sql.append(" AND o.isSendMail = :isSendMail");
			map.put("isSendMail", parmMap.get("isSendMail"));
		}

		if (null != parmMap.get("starTime")
				&& !"".equals(parmMap.get("starTime"))) {
			sql.append(" AND o.createTime >= :starTime");
			map.put("starTime", DateUtil.dateStringToTimestamp(parmMap
					.get("starTime") + ""));
		}
		if (null != parmMap.get("endTime")
				&& !"".equals(parmMap.get("endTime"))) {
			sql.append(" AND o.createTime <= :endTime");
			map.put("endTime",
					DateUtil.dateStringToTimestamp(parmMap.get("endTime") + ""));
		}
		sql.append(" ORDER BY o.createTime DESC ");
		TypedQuery<WccStore> query = entityManager().createQuery(
				sql.toString(), WccStore.class);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		int totalCount = 0;
		{ // 查总数
			TypedQuery<Long> countQuery = entityManager().createQuery(
					sql.toString().replaceAll(" SELECT DISTINCT o",
							"SELECT count( DISTINCT o)"), Long.class);
			for (String key : map.keySet()) {
				countQuery.setParameter(key, map.get(key));
			}
			totalCount = countQuery.getSingleResult().intValue();
		}
		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		List<WccStore> list = query.getResultList();
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());
		return pageModel;
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
			WccStore attached = WccStore.findWccStore(this.id);
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
	public WccStore merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccStore merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

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

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public String getStoreEmail() {
		return storeEmail;
	}

	public void setStoreEmail(String storeEmail) {
		this.storeEmail = storeEmail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PubOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(PubOrganization organization) {
		this.organization = organization;
	}

	public Boolean getIsVisiable() {
		return isVisiable;
	}

	public void setIsVisiable(Boolean isVisiable) {
		this.isVisiable = isVisiable;
	}

	public int getStoreAuditing() {
		return storeAuditing;
	}

	public void setStoreAuditing(int storeAuditing) {
		this.storeAuditing = storeAuditing;
	}

	public boolean getIsSendMail() {
		return isSendMail;
	}

	public void setSendMail(boolean isSendMail) {
		this.isSendMail = isSendMail;
	}

	public static List<WccStore> findAllWccStoresById(long id) {
		return entityManager().createQuery(
				"SELECT o FROM WccStore o where o.organization ='" + id + "' ",
				WccStore.class).getResultList();
	}
	
	
	//手机端页面 查询
	public static List<WccStore> getStoreByOrName(String oname){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccStore.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("organization");
	      
	      if(null != oname){
	    	  
	    	  cr.add(Restrictions.like("name",oname,MatchMode.ANYWHERE));

	      }

      return criteria.list();
	}

	/**
	 * 验证门店名称是否存在
	 * 
	 * @param storeName
	 * @return
	 */
	public static List<WccStore> findAllWccStoresByStoreName(String storeName,UmpCompany company) {
		if(storeName == null)return null;
		if(company == null)return null;
		String hql = "FROM WccStore o where o.storeName =:storeName and o.company=:company";
		TypedQuery<WccStore> query = entityManager().createQuery(hql,WccStore.class);
		query.setParameter("storeName", storeName);
		query.setParameter("company", company);
		return query.getResultList();
	}

	public static WccStore findWccStoreByScenId(String key) {
		StringBuilder sql = new StringBuilder(
				"SELECT o  FROM WccStore o.sceneId = '" + key + "'");
		return entityManager().createQuery(sql.toString(), WccStore.class)
				.getSingleResult();
	}

	@Transactional
	public static void batchPersist(Collection<WccStore> list) {
		for (WccStore wccStore : list) {
			wccStore.persist();
			System.out.println(wccStore.getId());
			if(wccStore.getSceneId() != null && !"".equals(wccStore.getSceneId())){
				String[] seneIds =wccStore.getSceneId().split(",");
				for(String id :seneIds){
					WccQrcode wccQrcode	 = WccQrcode.findWccQrcodeByScendId("2",Integer.parseInt(id));
					wccQrcode.setWccStroeId(wccStore.getId());
					wccQrcode.merge();
				}
			}
		}
	}

	public static String toJsonArray(Collection<WccStore> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<WccStore> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<WccStore> fromJsonArrayToVocWordExpressionss(
			String json) {
		return new JSONDeserializer<List<WccStore>>().use("values",
				WccStore.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static long countWccStoresByFiled(String organName, String platIds,
			String storeName2, Boolean isSendMail2, String starTime,
			String endTime, PubOperator pub) {
		String jpaQuery = "SELECT DISTINCT COUNT(o) FROM WccStore o ";
		if (null != platIds && !"".equals(platIds)) {
			jpaQuery = jpaQuery
					+ " inner join o.platformUsers plat WHERE plat in ("
					+ platIds + ")";
		}
		if (null != storeName2 && !"".equals(storeName2)) {
			jpaQuery = jpaQuery + " and o.storeName like '%" + storeName2
					+ "%'";
		}
		if (!"".equals(isSendMail2) && null != isSendMail2) {
			jpaQuery = jpaQuery + " and o.isSendMail = " + isSendMail2;
		}
		if (null != starTime && !"".equals(starTime)) {
			jpaQuery = jpaQuery + " and o.createTime >= '" + starTime + "'";
		}
		if (null != endTime && !"".equals(endTime)) {
			jpaQuery = jpaQuery + " and o.createTime <= '" + endTime + "'";
		}
		if (null != organName && !"".equals(organName)) {
			PubOrganization pubOrgan = PubOrganization
					.findPubOrganizationByName(organName, pub);
			jpaQuery = jpaQuery + " and o.organization = " + pubOrgan.getId();
		}
		System.out.println(jpaQuery.toString());
		return entityManager().createQuery(jpaQuery.toString(), Long.class)
				.getSingleResult();
	}

	public static List<WccStore> findWccStoreEntriesByFiled(int firstResult,
			int maxResults, String organName, String platIds,
			String storeName2, Boolean isSendMail2, String starTime,
			String endTime, String pks, PubOperator pub) {
		String jpaQuery = "SELECT DISTINCT o FROM WccStore o ";
		if (null != platIds && !"".equals(platIds)) {
			jpaQuery = jpaQuery
					+ " inner join o.platformUsers plat WHERE plat in ("
					+ platIds + ") ";
		}
		if (null != storeName2 && !"".equals(storeName2)) {
			jpaQuery = jpaQuery + " and o.storeName like '%" + storeName2
					+ "%'";
		}
		if (!"".equals(isSendMail2) && null != isSendMail2) {
			jpaQuery = jpaQuery + " and o.isSendMail = " + isSendMail2;
		}

		if (!"".equals(pks) && null != pks) {
			jpaQuery = jpaQuery + " and o.id in (" + pks + ")";
		} else {
			jpaQuery = jpaQuery + " and o.company =" + pub.getCompany().getId();
		}

		if (null != starTime && !"".equals(starTime)) {

			jpaQuery = jpaQuery + " and o.createTime >= '" + starTime + "'";
		}
		if (null != endTime && !"".equals(endTime)) {
			jpaQuery = jpaQuery + " and o.createTime <= '" + endTime + "'";
		}
		if (null != organName && !"".equals(organName)) {
			PubOrganization pubOrgan = PubOrganization
					.findPubOrganizationByName(organName, pub);
			jpaQuery = jpaQuery + " and o.organization = " + pubOrgan.getId();
		}
		jpaQuery = jpaQuery + " ORDER BY o.createTime desc";

		System.out.println(jpaQuery);

		return entityManager().createQuery(jpaQuery.toString(), WccStore.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	@SuppressWarnings("unchecked")
	public static List<WccStore> findAllWccStoresByStoreNameAndCompany(
			String storeName, UmpCompany company) {
		if (storeName == null)
			return null;
		if (null == company)
			return null;
		Query quer = entityManager()
				.createQuery(
						"FROM WccStore o where o.storeName =:storeName and o.company=:company ");
		quer.setParameter("storeName", storeName);
		quer.setParameter("company", company);
		return quer.getResultList();
	}

}

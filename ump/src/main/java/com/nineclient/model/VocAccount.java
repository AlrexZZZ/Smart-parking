package com.nineclient.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import com.nineclient.utils.CommonUtils;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocAccount {

	/**
     */
	@NotNull
	@Size(min = 1, max = 32)
	private String account;

	/**
     */
	@Size(max = 4000)
	private String cookie;

	/**
     */
	@Size(max = 20)
	private String password;

	/**
     */
	private long sort;

	/**
     */
	private Boolean isVisable;

	/**
     */
	@Size(max = 4000)
	private String remark;

	/**
     */
	private Boolean isDeleted;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	/**
	 * 是否已认证
	 */
	private Boolean isApprove;

	/**
     */
	@Size(max = 200)
	private String sessionKey;
	/**
	 * cookie是否有效
	 */
	@Value(value="false")
	private Boolean isValid;
	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date effectTime;
	/**
	 * session认证结果数据
	 */
	@Size(max = 4000)
	private String result;
	
	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date expiryTime;
	
	@ManyToOne
	private VocAppkey vocAppKey;
	
	@ManyToOne
	private UmpChannel umpChannel;
	
	@OneToOne
	private VocShop vocShop;

	private Long companyId;
	
	private String approveCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date approveTime;
	
	/**
	 * @return the approveTime
	 */
	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * @param approveTime the approveTime to set
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * @return the approveCode
	 */
	public String getApproveCode() {
		return approveCode;
	}

	/**
	 * @param approveCode the approveCode to set
	 */
	public void setApproveCode(String approveCode) {
		this.approveCode = approveCode;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public UmpChannel getUmpChannel() {
		return umpChannel;
	}

	public void setUmpChannel(UmpChannel umpChannel) {
		this.umpChannel = umpChannel;
	}

	public VocShop getVocShop() {
		return vocShop;
	}

	public void setVocShop(VocShop vocShop) {
		this.vocShop = vocShop;
	}

	public VocAppkey getVocAppKey() {
		return vocAppKey;
	}

	public void setVocAppKey(VocAppkey vocAppKey) {
		this.vocAppKey = vocAppKey;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getSort() {
		return this.sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public Boolean getIsVisable() {
		return this.isVisable;
	}

	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Boolean getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(Boolean isApprove) {
		this.isApprove = isApprove;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Date getEffectTime() {
		return this.effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Date getExpiryTime() {
		return this.expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("account", "cookie", "password", "sort", "isVisable",
					"remark", "isDeleted", "createTime", "isInvalid",
					"sessionKey", "effectTime", "expiryTime");

	public static final EntityManager entityManager() {
		EntityManager em = new VocAccount().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countVocAccounts(Boolean isVisable, Long channelId,
			Long vocShopId, UmpCompany compnay) {
		String sql = "SELECT count(o) FROM VocAccount o join o.umpChannel c left join o.vocShop v where 1=1 ";
		if (!StringUtils.isEmpty(isVisable)) {
			sql += " and o.isVisable=" + isVisable;
		}
		if (!StringUtils.isEmpty(channelId)) {
			sql += " and c.id=" + channelId;
		}
		if (!StringUtils.isEmpty(vocShopId)) {
			sql += " and v.id=" + vocShopId;
		}
		if (!StringUtils.isEmpty(compnay)) {
			sql += " and o.companyId=" + compnay.getId();
		}
		return entityManager().createQuery(sql, Long.class).getSingleResult();
	}

	/**
	 * 
	 * @param compnay
	 * @param account
	 * @return
	 */
	public static List<VocAccount> findAllVocAccounts(UmpCompany compnay,
			String account, Long id) {
		String sql = "SELECT o FROM VocAccount o where o.companyId="
				+ compnay.getId();
		if (!StringUtils.isEmpty(account)) {
			sql += " and o.account='" + account + "'";
		}
		if (!StringUtils.isEmpty(id)) {
			sql += " and o.id!=" + id;
		}
		sql += " order by createTime desc ";
		return entityManager().createQuery(sql, VocAccount.class)
				.getResultList();
	}

	/**
	 * 获取该公司的所有账号
	 * @param compnay
	 * @return
	 */
	public static List<VocAccount> findAllVocAccountByCompany(UmpCompany compnay) {
		String sql = "SELECT o FROM VocAccount o where o.companyId="+ compnay.getId();
		sql += " order by createTime desc ";
		return entityManager().createQuery(sql, VocAccount.class).getResultList();
	}
	
	public static List<VocAccount> findAllVocAccounts(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM VocAccount o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocAccount.class)
				.getResultList();
	}

	public static VocAccount findVocAccount(Long id) {
		if (id == null)
			return null;
		return entityManager().find(VocAccount.class, id);
	}
	
	public static List<VocAccount> findVocAccountById(Long id) {
		if (id == null)
			return null;
		 return entityManager().createQuery("SELECT o FROM VocAccount o where 1=1 and o.id = "+ id, VocAccount.class).getResultList();
	}

	public static List<VocAccount> findVocAccountEntries(Boolean isVisable,
			Long channelId, Long vocShopId, UmpCompany company,
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String sql = "SELECT o FROM VocAccount o join o.umpChannel c left join o.vocShop v where 1=1 ";
		if (!StringUtils.isEmpty(isVisable)) {
			sql += " and o.isVisable=" + isVisable;
		}
		if (!StringUtils.isEmpty(channelId)) {
			sql += " and c.id=" + channelId;
		}
		if (!StringUtils.isEmpty(vocShopId)) {
			sql += " and v.id=" + vocShopId;
		}
		if (!StringUtils.isEmpty(company)) {
			sql += " and o.companyId=" + company.getId();
		}
		sql += " order by o.createTime desc ";
		return entityManager().createQuery(sql, VocAccount.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<VocAccount> findVocAccountEntries(UmpCompany compnay,
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM VocAccount o where o.companyId="
				+ compnay.getId();
		jpaQuery += " ORDER BY createTime desc ";
		return entityManager().createQuery(jpaQuery, VocAccount.class)
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
			VocAccount attached = VocAccount.findVocAccount(this.id);
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
	public VocAccount merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		VocAccount merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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

	public static String toJsonArray(Collection<VocAccount> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<VocAccount> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<VocAccount> fromJsonArrayToVocAccounts(String json) {
		return new JSONDeserializer<List<VocAccount>>().use("values",
				VocAccount.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static List<VocAccount> findAllVocAccountByCompanyAndChannId(
			UmpCompany company, Long channId) {
		String sql = "SELECT o FROM VocAccount o where o.companyId="+ company.getId()+" and o.umpChannel="+channId;
		sql += " order by o.createTime desc ";
		return entityManager().createQuery(sql, VocAccount.class).getResultList();
	}
	/**
	 * 查询所有认证的账号
	 * @return
	 */
	public static List<VocAccount> findApproveAccount(){
		String sql="select o from VocAccount o where o.isApprove=:isApprove and o.isVisable=:isVisable";
		TypedQuery<VocAccount> query = entityManager().createQuery(sql, VocAccount.class);
		query.setParameter("isApprove", true);
		query.setParameter("isVisable", true);
		return query.getResultList();
	}

	public static List<VocAccount> findVocAccountbyCompanyAndVocShopIds(
			UmpCompany company, String ids) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id,a.account from voc_account a where a.voc_shop in(:ids) and a.company_id =:companyId ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", CommonUtils.stringArray2Set(ids));
		map.put("companyId", company.getId());
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		for (String key : map.keySet()) {
				query.setParameter(key, map.get(key));
		}
			
		List<VocAccount> vocAccount = new ArrayList<VocAccount>();
		VocAccount model = null;
		Object[] objArr = null;
		for(Object obj:query.getResultList()){
			objArr = (Object[])obj;
			model = new VocAccount();
			model.setId(Long.parseLong(String.valueOf(objArr[0])));
			model.setAccount(String.valueOf(objArr[1]));
			vocAccount.add(model);
		}
			
		return vocAccount;
	}
}

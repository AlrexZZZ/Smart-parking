package com.nineclient.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocSku {

	/**
     */
	@NotNull
	private String name;

	/**
     */
	private Boolean isDeleted;
	private Boolean isVisable;

	private String skuCode;
	@ManyToOne
	private VocSkuProperty vocSkuProperty;
	@OneToMany(mappedBy = "vocSku")
	private Set<VocGoodsPropertyValue> vocGoodsPropertyValues;
	@ManyToOne
	private VocBrand vocBrand;
	@ManyToOne
	private UmpBusinessType umpBusinessType;
	@OneToMany(mappedBy = "vocSku")
	private Set<VocGoods> vocGoodses;
	/** 型号 **/
	private String skuModel;
	/** 类型 **/
	private String skuType;
	/** 包装 **/
	private String skuPackage;
	/** 产地 **/
	private String skuOrigin;
	/** 重量 **/
	private String skuWeight;
	/**公司**/
	@NotNull
	private String companyCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSkuModel() {
		return skuModel;
	}

	public void setSkuModel(String skuModel) {
		this.skuModel = skuModel;
	}

	public String getSkuType() {
		return skuType;
	}

	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}

	public String getSkuPackage() {
		return skuPackage;
	}

	public void setSkuPackage(String skuPackage) {
		this.skuPackage = skuPackage;
	}

	public String getSkuOrigin() {
		return skuOrigin;
	}

	public void setSkuOrigin(String skuOrigin) {
		this.skuOrigin = skuOrigin;
	}

	public String getSkuWeight() {
		return skuWeight;
	}

	public void setSkuWeight(String skuWeight) {
		this.skuWeight = skuWeight;
	}

	public Set<VocGoods> getVocGoodses() {
		return vocGoodses;
	}

	public void setVocGoodses(Set<VocGoods> vocGoodses) {
		this.vocGoodses = vocGoodses;
	}

	public UmpBusinessType getUmpBusinessType() {
		return umpBusinessType;
	}

	public void setUmpBusinessType(UmpBusinessType umpBusinessType) {
		this.umpBusinessType = umpBusinessType;
	}

	public VocBrand getVocBrand() {
		return vocBrand;
	}

	public void setVocBrand(VocBrand vocBrand) {
		this.vocBrand = vocBrand;
	}

	public Set<VocGoodsPropertyValue> getVocGoodsPropertyValues() {
		return vocGoodsPropertyValues;
	}

	public void setVocGoodsPropertyValues(
			Set<VocGoodsPropertyValue> vocGoodsPropertyValues) {
		this.vocGoodsPropertyValues = vocGoodsPropertyValues;
	}

	public Boolean getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("name", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
		EntityManager em = new VocSku().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countVocSkus() {
		return entityManager().createQuery("SELECT COUNT(o) FROM VocSku o",
				Long.class).getSingleResult();
	}

	/**
	 * 查询sku总数
	 * 
	 * @param map
	 * @return
	 */
	public static long countVocSkus(Map<String, Object> map) {
		String sql = "SELECT COUNT(o) FROM VocSku o  join o.umpBusinessType p join o.vocBrand b where 1=1 ";
		String businessId = String
				.valueOf((map.get("businessTypeId") == null ? "" : map
						.get("businessTypeId")));
		String vocBrandId = String.valueOf((map.get("vocBrandId") == null ? ""
				: map.get("vocBrandId")));
		String skuName = (String) (map.get("skuName") == null ? "" : map
				.get("skuName"));
		if (!StringUtils.isEmpty(businessId)) {
			sql += " and p.id=" + businessId;
		}
		if (!StringUtils.isEmpty(vocBrandId)) {
			sql += " and b.id=" + vocBrandId;
		}
		if (!StringUtils.isEmpty(skuName)) {
			sql += " and o.name like '%" + skuName + "%'";
		}
		return entityManager().createQuery(sql, Long.class).getSingleResult();
	}

	/**
	 * 根据品牌查询sku商品
	 * 
	 * @return
	 */
	public static List<VocSku> findAllVocSkusByVocBrandId(String id) {
		return entityManager().createQuery(
				"SELECT o FROM VocSku o join o.vocBrand u where u.id in(" + id
						+ ")", VocSku.class).getResultList();
	}

	public static List<VocSku> findAllVocSkus() {
		return entityManager().createQuery("SELECT o FROM VocSku o",
				VocSku.class).getResultList();
	}

	public static List<VocSku> findAllVocSkus(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM VocSku o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocSku.class)
				.getResultList();
	}

	public static VocSku findVocSku(Long id) {
		if (id == null)
			return null;
		return entityManager().find(VocSku.class, id);
	}

	public static List<VocSku> findVocSkuEntries(int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM VocSku o", VocSku.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<VocSku> findVocSkuEntries(Map<String, Object> map,
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM VocSku o join o.umpBusinessType p join o.vocBrand b where 1=1 ";
		String businessId = String
				.valueOf((map.get("businessTypeId") == null ? "" : map
						.get("businessTypeId")));
		String skuName = (String) (map.get("skuName") == null ? "" : map
				.get("skuName"));
		String vocBrandId = String.valueOf((map.get("vocBrandId") == null ? ""
				: map.get("vocBrandId")));
		if (!StringUtils.isEmpty(businessId)) {
			jpaQuery += " and p.id=" + businessId;
		}
		if (!StringUtils.isEmpty(vocBrandId)) {
			jpaQuery += " and p.id=" + vocBrandId;
		}
		if (!StringUtils.isEmpty(skuName)) {
			jpaQuery += " and o.name like '%" + skuName + "%'";
		}

		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocSku.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<VocSku> findVocSkuEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM VocSku o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocSku.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	/**
	 * 批量保存sku
	 * 
	 * @param list
	 */
	@Transactional
	public static void batchPersist(Collection<VocSku> list) {
		for (VocSku vocSku : list) {
			vocSku.persist();
		}
	}

	@Transactional
	public void persist(Set<VocGoodsPropertyValue> list) {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
		for (VocGoodsPropertyValue vocGoodsPropertyValue : list) {
			vocGoodsPropertyValue.setVocSku(this);
			vocGoodsPropertyValue.persist();
		}
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			VocSku attached = VocSku.findVocSku(this.id);

			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void remove(List<VocGoodsPropertyValue> list) {
		if (list != null) {
			for (VocGoodsPropertyValue vocGoodsPropertyValue : list) {
				vocGoodsPropertyValue.remove();
			}
		}
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			VocSku attached = VocSku.findVocSku(this.id);
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
	public VocSku merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		VocSku merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
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
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public VocSkuProperty getVocSkuProperty() {
		return vocSkuProperty;
	}

	public void setVocSkuProperty(VocSkuProperty vocSkuProperty) {
		this.vocSkuProperty = vocSkuProperty;
	}

	public static String toJsonArray(Collection<VocSku> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<VocSku> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<VocSku> fromJsonArrayToVocSkus(String json) {
		return new JSONDeserializer<List<VocSku>>().use("values", VocSku.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static List<VocSku> findVocSkuEntries(Long businessTypeId,
			Long vocBrandId, String skuGoodsName, VocSkuProperty vocSkuProperty,
			Date startTime, Date endTime, int firstResult, int maxResults,
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM VocSku o join o.umpBusinessType p join o.vocBrand b where 1=1 ";
		Map<String,Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(businessTypeId)) {
			jpaQuery += " and p.id=:businessTypeId";
			map.put("businessTypeId", businessTypeId);
		}
		if (!StringUtils.isEmpty(vocBrandId)) {
			jpaQuery += " and b.id=:vocBrandId";
			map.put("vocBrandId", vocBrandId);
		}
		if (!StringUtils.isEmpty(skuGoodsName)) {
			jpaQuery += " and o.name like :skuGoodsName";
			map.put("skuGoodsName", "%"+skuGoodsName+"%");
		}
		if (vocSkuProperty!=null) {
			jpaQuery += " and o.vocSkuProperty=:vocSkuProperty";
			map.put("vocSkuProperty", vocSkuProperty);
		}
		if (!StringUtils.isEmpty(startTime)) {
			jpaQuery += " and o.createTime >=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			jpaQuery += " and o.createTime <=:endTime";
			map.put("endTime", endTime);
		}
		jpaQuery = jpaQuery + " ORDER BY o.createTime desc " ;
		
		TypedQuery<VocSku> query = entityManager().createQuery(jpaQuery, VocSku.class);
		for (String key:map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Long countVocSkus(Long businessTypeId, Long vocBrandId,
			String skuGoodsName, VocSkuProperty vocSkuProperty,
			Date startTime, Date endTime) {
		String jpaQuery = "SELECT count(o) FROM VocSku o join o.umpBusinessType p join o.vocBrand b where 1=1 ";
		Map<String,Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(businessTypeId)) {
			jpaQuery += " and p.id=:businessTypeId";
			map.put("businessTypeId", businessTypeId);
		}
		if (!StringUtils.isEmpty(vocBrandId)) {
			jpaQuery += " and b.id=:vocBrandId";
			map.put("vocBrandId", vocBrandId);
		}
		if (!StringUtils.isEmpty(skuGoodsName)) {
			jpaQuery += " and o.name like :skuGoodsName";
			map.put("skuGoodsName", "%"+skuGoodsName+"%");
		}
		if (vocSkuProperty!=null) {
			jpaQuery += " and o.vocSkuProperty=:vocSkuProperty";
			map.put("vocSkuProperty", vocSkuProperty);
		}
		if (!StringUtils.isEmpty(startTime)) {
			jpaQuery += " and o.createTime >=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			jpaQuery += " and o.createTime <=:endTime";
			map.put("endTime", endTime);
		}
		TypedQuery<Long> query = entityManager().createQuery(jpaQuery, Long.class);
		for (String key:map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		return query.getSingleResult();
	}
}

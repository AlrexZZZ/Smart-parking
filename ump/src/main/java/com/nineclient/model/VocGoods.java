package com.nineclient.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.constant.VocSkuCheckStatus;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocGoods {

	/**
     */
	@NotNull
	private String name;

	/**
     */
	private String goodsId;

	/**
     */
	@NotNull
	private String url;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	/**
     */
	private Boolean isDeleted;

	/**
     */
	private String goodsNumber;

	@OneToMany(mappedBy = "goods")
	private Set<VocComment> comments = new HashSet<VocComment>();

	@ManyToOne
	private UmpChannel channel;

	@ManyToOne
	private VocBrand vocBrand;

	@ManyToOne
	private VocShop shop;
	@ManyToOne
	private VocGoodsProperty vocGoodsProperty;
	/** 匹配时间 **/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date matchTime;
	/** 匹配分值 **/
	private Integer matchScore;
	/** 审核状态 **/
	private VocSkuCheckStatus status;
	/**上下架**/
	private String goodsShelvesStatus;
	/**是否删除**/
	private String goodsIsDeleted;
	/** 归属sku **/
	@ManyToOne
	private VocSku vocSku;
	/**1：人工匹配 2：智能匹配**/
	private Long matchType;
	@ManyToOne
	private UmpCompany umpCompany;
	/**子行业id**/
	private Long bussinessTypeId;
	
	
	public String getGoodsShelvesStatus() {
		return goodsShelvesStatus;
	}

	public void setGoodsShelvesStatus(String goodsShelvesStatus) {
		this.goodsShelvesStatus = goodsShelvesStatus;
	}

	public String getGoodsIsDeleted() {
		return goodsIsDeleted;
	}

	public void setGoodsIsDeleted(String goodsIsDeleted) {
		this.goodsIsDeleted = goodsIsDeleted;
	}

	public UmpCompany getUmpCompany() {
		return umpCompany;
	}

	public void setUmpCompany(UmpCompany umpCompany) {
		this.umpCompany = umpCompany;
	}

	public Long getMatchType() {
		return matchType;
	}

	public void setMatchType(Long matchType) {
		this.matchType = matchType;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public Integer getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(Integer matchScore) {
		this.matchScore = matchScore;
	}

	public VocSkuCheckStatus getStatus() {
		return status;
	}

	public void setStatus(VocSkuCheckStatus status) {
		this.status = status;
	}

	public VocSku getVocSku() {
		return vocSku;
	}

	public void setVocSku(VocSku vocSku) {
		this.vocSku = vocSku;
	}

	public VocGoodsProperty getVocGoodsProperty() {
		return vocGoodsProperty;
	}

	public void setVocGoodsProperty(VocGoodsProperty vocGoodsProperty) {
		this.vocGoodsProperty = vocGoodsProperty;
	}

	public String toString() {
//		return ReflectionToStringBuilder.toString(this,
//				ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getGoodsNumber() {
		return this.goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("name", "goodsId", "url", "createTime", "isDeleted",
					"goodsNumber");

	public static final EntityManager entityManager() {
		EntityManager em = new VocGoods().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countVocGoodses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM VocGoods o",
				Long.class).getSingleResult();
	}

	/**
	 * 查询匹配sku总数
	 * 
	 * @param vocBrandId
	 * @param vocSkuName
	 * @param firstResult
	 * @param maxResults
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
	public static Long findCountMatchGoodses(Long vocBrandId, String vocSkuName) {
		StringBuilder sql = new StringBuilder(
				"SELECT count(s) FROM  VocSku s join s.vocBrand b where 1=1 ");
		if (!StringUtils.isEmpty(vocBrandId)) {
			sql.append(" and b.id in (" + vocBrandId + ")");
		}
		if (!StringUtils.isEmpty(vocSkuName)) {
			sql.append(" and s.name like  '%" + vocSkuName + "%'");
		}
		return entityManager().createQuery(sql.toString(), Long.class)
				.getSingleResult();
	}

	/**
	 * 查询匹配sku
	 * 
	 * @param skuCodes
	 * @param firstResult
	 * @param maxResults
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
	public static List<VocSku> findAllMatchVocGoodses(Long vocBrandId,
			String vocSkuName, int firstResult, int maxResults,
			String sortFieldName, String sortOrder) {
		StringBuilder sql = new StringBuilder(
				"SELECT s FROM VocSku s join s.vocBrand b where 1=1 ");
		if (!StringUtils.isEmpty(vocBrandId)) {
			sql.append(" and b.id in (" + vocBrandId + ")");
		}
		if (!StringUtils.isEmpty(vocSkuName)) {
			sql.append(" and s.name like '%" + vocSkuName + "%'");
		}
		return entityManager().createQuery(sql.toString(), VocSku.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	/**
	 * 查询总数
	 * 
	 * @param skuCodes
	 * @param firstResult
	 * @param maxResults
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
	public static Long findCountGoodsesBySkuCodes(UmpCompany umpcomany,Long channelId,
			String goodsName, Long vocbrandId, VocSkuCheckStatus goodsStatus,
			Date startTime,
			Date endTime
			) {
		StringBuilder sql = new StringBuilder(
				"SELECT count(o) FROM VocGoods o join o.umpCompany cp join o.channel c join o.vocBrand v where 1=1 ");
		Map<String,Object> map = new HashMap<String,Object>();
		if (!StringUtils.isEmpty(channelId)) {
			sql.append(" and c.id =:channelId ");
			map.put("channelId", channelId);
		}
		if (!StringUtils.isEmpty(goodsName)) {
			sql.append(" and o.name like :goodsName");
			map.put("goodsName", "%"+goodsName+"%");
		}
		if (!StringUtils.isEmpty(vocbrandId)) {
			sql.append(" and v.id=:vocbrandId");
			map.put("vocbrandId", vocbrandId);
		}
		if (!StringUtils.isEmpty(goodsStatus)) {
			sql.append(" and o.status =:goodsStatus");
			map.put("goodsStatus", goodsStatus);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql.append(" and o.createTime >=:startTime");
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql.append(" and o.createTime <= :endTime");
			map.put("endTime", endTime);
		}
		if(umpcomany!=null){
			sql.append(" and cp.id =:umpcomanyId");
			map.put("umpcomanyId", umpcomany.getId());
		}
		 TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
		 for(String key:map.keySet()){
	        	query.setParameter(key, map.get(key));	
	        }
		return query.getSingleResult();
	}

	/**
	 * 根据商品编码查询商品
	 * 
	 * @return
	 */
	public static List<VocGoods> findAllVocGoodsesBySkuCodes(UmpCompany umpcomany,Long channelId,
			String goodsName, Long vocbrandId, VocSkuCheckStatus goodsStatus, 
			Date startTime,
			Date endTime,
			int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		StringBuilder sql = new StringBuilder(
				"SELECT o FROM VocGoods o join o.umpCompany cp join o.channel c join o.vocBrand v where 1=1 ");
		Map<String,Object> map = new HashMap<String,Object>();
		if (!StringUtils.isEmpty(channelId)) {
			sql.append(" and c.id =:channelId ");
			map.put("channelId", channelId);
		}
		if (!StringUtils.isEmpty(goodsName)) {
			sql.append(" and o.name like :goodsName");
			map.put("goodsName", "%"+goodsName+"%");
		}
		if (!StringUtils.isEmpty(vocbrandId)) {
			sql.append(" and v.id=:vocbrandId");
			map.put("vocbrandId", vocbrandId);
		}
		if (!StringUtils.isEmpty(goodsStatus)) {
			sql.append(" and o.status =:goodsStatus");
			map.put("goodsStatus", goodsStatus);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql.append(" and o.createTime >=:startTime");
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql.append(" and o.createTime <= :endTime");
			map.put("endTime", endTime);
		}
		if(umpcomany!=null){
			sql.append(" and cp.id =:umpcomanyId");
			map.put("umpcomanyId", umpcomany.getId());
		}
		sql.append(" order by  o.createTime desc");
		 TypedQuery<VocGoods> query = entityManager().createQuery(sql.toString(), VocGoods.class);
		 for(String key:map.keySet()){
	        	query.setParameter(key, map.get(key));	
	        }
		return query.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static List<VocGoods> findAllVocGoodses() {
		return entityManager().createQuery("SELECT o FROM VocGoods o",
				VocGoods.class).getResultList();
	}

	public static List<VocGoods> findAllVocGoodses(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM VocGoods o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocGoods.class)
				.getResultList();
	}

	public static VocGoods findVocGoods(Long id) {
		if (id == null)
			return null;
		return entityManager().find(VocGoods.class, id);
	}
	/**
	 * 
	 * @param ids
	 * @return
	 */
	public static List<VocGoods> findVocGoodsListById(String ids) {
		if (ids == null)
			return null;
		return entityManager()
				.createQuery("SELECT o FROM VocGoods o where o.id in ("+ids+")", VocGoods.class).getResultList();
	}
	public static List<VocGoods> findVocGoodsEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM VocGoods o", VocGoods.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<VocGoods> findVocGoodsEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM VocGoods o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocGoods.class)
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
			VocGoods attached = VocGoods.findVocGoods(this.id);
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
	public VocGoods merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		VocGoods merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
	/**
	 * 批量更新
	 * @return
	 */
	@Transactional
	public static void merge(List<VocGoods> list,Long status) {
		VocSkuCheckStatus stat=null;
		for(VocSkuCheckStatus st:VocSkuCheckStatus.values()){
			if(st.ordinal()==status){
				stat=st;
			}
		}
		for (VocGoods goods : list) {
				goods.setStatus(stat);
				goods.merge();
		}
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

	public Set<VocComment> getComments() {
		return comments;
	}

	public void setComments(Set<VocComment> comments) {
		this.comments = comments;
	}

	public UmpChannel getChannel() {
		return channel;
	}

	public void setChannel(UmpChannel channel) {
		this.channel = channel;
	}

	public VocShop getShop() {
		return shop;
	}

	public void setShop(VocShop shop) {
		this.shop = shop;
	}

	public VocBrand getVocBrand() {
		return vocBrand;
	}

	public void setVocBrand(VocBrand vocBrand) {
		this.vocBrand = vocBrand;
	}
	
	

	public Long getBussinessTypeId() {
		return bussinessTypeId;
	}

	public void setBussinessTypeId(Long bussinessTypeId) {
		this.bussinessTypeId = bussinessTypeId;
	}

	public static String toJsonArray(Collection<VocGoods> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<VocGoods> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<VocGoods> fromJsonArrayToVocGoodss(String json) {
		return new JSONDeserializer<List<VocGoods>>().use("values",
				VocGoods.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

}

package com.nineclient.cbd.wcc.model;

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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 理财产品
 * 
 * @author 9client
 *
 */
@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccFinancialProduct {

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

	private String insertTimeStr;

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

	@ManyToMany
	private Set<WccPlatformUser> platformUsers; // 公众平台

	private String financialName; // 理财产品名称

	private String themeImage; // 主题图

	@Size(max = 5000)
	private String themeImageText; // 图文信息

	private String productCode; // 产品代码

	private String riskLevel; // 风险等级

	private String productType; // 产品类型

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date saleBeginDate; // 销售起日

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date saleEndDate; // 销售止日

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date valueDate; // 起息日
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date endDate; // 到期日

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date arrivalDate; // 到账日

	private String investmentHorizon; // 投资期限（天）

	private String expectedYield; // 预期收益率

	private Long minMoney; // 产品起点金额（元）

	private Long maxMoney; // 产品上限金额（元）

	private Long increasingMoney; // 递增金额

	private Long totalMoney; // 微信预约总额（元）

	private Integer totalNumber; // 预约总人数
	
	private Boolean isUsing; // 是否启用
	
	private String ck;

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

	public String getInsertTimeStr() {
		return insertTimeStr;
	}

	public void setInsertTimeStr(String insertTimeStr) {
		this.insertTimeStr = insertTimeStr;
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

	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getFinancialName() {
		return financialName;
	}

	public void setFinancialName(String financialName) {
		this.financialName = financialName;
	}

	public String getThemeImage() {
		return themeImage;
	}

	public void setThemeImage(String themeImage) {
		this.themeImage = themeImage;
	}

	public String getThemeImageText() {
		return themeImageText;
	}

	public void setThemeImageText(String themeImageText) {
		this.themeImageText = themeImageText;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Date getSaleBeginDate() {
		return saleBeginDate;
	}

	public void setSaleBeginDate(Date saleBeginDate) {
		this.saleBeginDate = saleBeginDate;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getInvestmentHorizon() {
		return investmentHorizon;
	}

	public void setInvestmentHorizon(String investmentHorizon) {
		this.investmentHorizon = investmentHorizon;
	}

	public String getExpectedYield() {
		return expectedYield;
	}

	public void setExpectedYield(String expectedYield) {
		this.expectedYield = expectedYield;
	}

	public Long getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Long minMoney) {
		this.minMoney = minMoney;
	}

	public Long getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Long maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Long getIncreasingMoney() {
		return increasingMoney;
	}

	public void setIncreasingMoney(Long increasingMoney) {
		this.increasingMoney = increasingMoney;
	}

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Boolean getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Boolean isUsing) {
		this.isUsing = isUsing;
	}
	
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/**
	 * 查询消息记录（分页）
	 * 
	 * @param qm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageModel<WccFinancialProduct> getRecord(
			QueryModel<WccFinancialProduct> qm) {
		PageModel<WccFinancialProduct> pageModel = new PageModel<WccFinancialProduct>();
		Map parmMap = qm.getInputMap();

		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFinancialProduct o ");
		Map<String, Object> map = new HashMap<String, Object>();

		Set<WccPlatformUser> platSets = (Set<WccPlatformUser>) parmMap.get("platformUsers");
		if (platSets != null && platSets.size() > 0) {
			sql.append("  inner join o.platformUsers plat WHERE plat in:platformUsers AND o.isDelete = 0 ");
			map.put("platformUsers", platSets);
		} else {
			sql.append(" WHERE 1 = 2 ");
		}

		if (parmMap.get("financialName") != null
				&& !"".equals(parmMap.get("financialName"))) {
			sql.append(" AND o.financialName like :financialName");
			map.put("financialName", "%" + parmMap.get("financialName") + "%");
		}

		if (parmMap.get("productCode") != null
				&& !"".equals(parmMap.get("productCode"))) {
			sql.append(" AND o.productCode like :productCode");
			map.put("productCode", "%" + parmMap.get("productCode") + "%");
		}
		
		if (parmMap.get("riskLevel") != null
				&& !"".equals(parmMap.get("riskLevel"))) {
			sql.append(" AND o.riskLevel like :riskLevel");
			map.put("riskLevel", "%" + parmMap.get("riskLevel") + "%");
		}
		
		if (parmMap.get("productType") != null
				&& !"".equals(parmMap.get("productType"))) {
			sql.append(" AND o.productType like :productType");
			map.put("productType", "%" + parmMap.get("productType") + "%");
		}
		
		String beginDateStr = parmMap.get("saleBeginDate").toString();
		Date saleBeginDate = null;
		if (beginDateStr != null && !"".equals(beginDateStr)) {
			saleBeginDate = DateUtil.getDateTime(beginDateStr + " 00:00:00");
			sql.append(" AND o.saleBeginDate >= :saleBeginDate");
			map.put("saleBeginDate", saleBeginDate);
		}
		sql.append(" GROUP BY o.id ");

		sql.append(" ORDER BY o.id DESC");
		TypedQuery<WccFinancialProduct> query = null;
		try {
			query = entityManager()
					.createQuery(sql.toString(), WccFinancialProduct.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		pageModel.setTotalCount(query.getResultList().size());
		pageModel.setPageSize(qm.getLimit());
		pageModel.setDataList(query.getResultList());
		return pageModel;
	}

	public static WccFinancialProduct findWccFinancialProduct(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccFinancialProduct.class, id);
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
			WccFinancialProduct attached = WccFinancialProduct
					.findWccFinancialProduct(this.id);
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
	public WccFinancialProduct merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccFinancialProduct merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WccFinancialProduct> findFinancialProductsByDate(
			Date date,Long platId) {
		List<WccFinancialProduct> wccFinancialProducts = null;
		if (date != null) {
			Session session = (Session) entityManager().getDelegate();
			Criteria criteria = session.createCriteria(
					WccFinancialProduct.class).setResultTransformer(
					DetachedCriteria.DISTINCT_ROOT_ENTITY);
		     Criteria cr = criteria.createCriteria("platformUsers");
			   
		      if(null != platId){
		    	  cr.add(Restrictions.eq("id",platId));
		      }
			criteria.add(Restrictions.le("saleBeginDate", date));
			criteria.add(Restrictions.ge("saleEndDate", date));
			criteria.add(Restrictions.eq("isUsing", true));
			wccFinancialProducts = criteria.list();
		}
		return wccFinancialProducts;
	}
	public static List<WccFinancialProduct> findFinancialProdocts(){
		String hql=" from WccFinancialProduct";
		TypedQuery<WccFinancialProduct> query=entityManager().createQuery(hql,WccFinancialProduct.class);
		return query.getResultList();
		
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}
	
}

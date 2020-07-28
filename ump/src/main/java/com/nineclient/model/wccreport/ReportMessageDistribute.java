package com.nineclient.model.wccreport;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;

import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ReportMessageDistribute {
	private Date refDate; // 时间
	private Integer countInterval;// 当日发送消息量分布的区间，0代表
									// “0”，1代表“1-5”，2代表“6-10”，3代表“10次以上”
	private Integer msgUser;// 用户人数
	private Long platformUser;
	@Transient
	private String platformUserName;

	public String getPlatformUserName() {
		if (WccPlatformUser.findWccPlatformUser(platformUser) != null) {
			return WccPlatformUser.findWccPlatformUser(platformUser)
					.getAccount();
		}
		return "";
	}

	@Transient
	private String dateStr;

	@Transient
	private Integer aCount=0;
	@Transient
	private Integer bCount=0;
	@Transient
	private Integer cCount=0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Integer getCountInterval() {
		return countInterval;
	}

	public void setCountInterval(Integer countInterval) {
		this.countInterval = countInterval;
	}

	public Integer getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(Integer msgUser) {
		this.msgUser = msgUser;
	}

	public Long getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(Long platformUser) {
		this.platformUser = platformUser;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getaCount() {
		return aCount;
	}

	public void setaCount(Integer aCount) {
		this.aCount = aCount;
	}

	public Integer getbCount() {
		return bCount;
	}

	public void setbCount(Integer bCount) {
		this.bCount = bCount;
	}

	public Integer getcCount() {
		return cCount;
	}

	public void setcCount(Integer cCount) {
		this.cCount = cCount;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("refDate");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportMessageDistribute().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countReportMessageDistributes() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM ReportMessageDistribute o", Long.class)
				.getSingleResult();
	}

	public static List<ReportMessageDistribute> findAllReportMessageDistributes() {
		return entityManager().createQuery(
				"SELECT o FROM ReportMessageDistribute o",
				ReportMessageDistribute.class).getResultList();
	}

	public static List<ReportMessageDistribute> findAllReportMessageDistributes(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportMessageDistribute o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery,
				ReportMessageDistribute.class).getResultList();
	}

	public static ReportMessageDistribute findReportMessageDistribute(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportMessageDistribute.class, id);
	}

	public static List<ReportMessageDistribute> findReportMessageDistributeEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportMessageDistribute o",
						ReportMessageDistribute.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<ReportMessageDistribute> findReportMessageDistributeEntries(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportMessageDistribute o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager()
				.createQuery(jpaQuery, ReportMessageDistribute.class)
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
			ReportMessageDistribute attached = ReportMessageDistribute
					.findReportMessageDistribute(this.id);
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
	public ReportMessageDistribute merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportMessageDistribute merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static String toJsonArray(
			Collection<ReportMessageDistribute> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static ReportMessageDistribute checkReportMessageDistribute(
			Long platformId, String ref_date, String count_interval) {
		String sql = "SELECT o FROM ReportMessageDistribute o WHERE o.platformUser = "
				+ platformId
				+ " AND o.refDate = '"
				+ ref_date
				+ " 00:00:00'  AND countInterval = " + count_interval;
		List<ReportMessageDistribute> list = entityManager().createQuery(sql,
				ReportMessageDistribute.class).getResultList();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}
	public static List<ReportMessageDistribute> findReportMessageDistributeByDateBetween(WccPlatformUser platform, String startTime, String endTime, int firstResult, int maxResults) {
        Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(ReportMessageDistribute.class);
        if (null != platform) {
            criteria.add(Restrictions.eq("platformUser", platform.getId()));
        }
        if (null != startTime && !"".equals(startTime)) {
            criteria.add(Restrictions.between("refDate", DateUtil.formateDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),DateUtil.formateDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        criteria.addOrder(Order.asc("refDate"));
        if(firstResult!=-1&&maxResults!=-1){
        	criteria.setFirstResult(firstResult);
        	criteria.setMaxResults(maxResults);
        }
        List<ReportMessageDistribute> list = criteria.list();
        return list;
    }
}

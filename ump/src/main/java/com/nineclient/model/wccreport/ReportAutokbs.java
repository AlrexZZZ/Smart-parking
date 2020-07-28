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
import org.hibernate.criterion.Projections;
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
public class ReportAutokbs {
	private String keyWord;	//关键词
	private Date insertTime;//触发时间
	private Long platformUser;//平台id
	
	@Transient
	private String platformUserName;
	@Transient
	private Integer appearNum;
	@Transient
	private int ranking;
	
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getPlatformUserName() {
		if (WccPlatformUser.findWccPlatformUser(platformUser) != null) {
			return WccPlatformUser.findWccPlatformUser(platformUser)
					.getAccount();
		}
		return "";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(Long platformUser) {
		this.platformUser = platformUser;
	}
	
	public Integer getAppearNum() {
		return appearNum;
	}

	public void setAppearNum(Integer appearNum) {
		this.appearNum = appearNum;
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
	
	
	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList();

	public static final EntityManager entityManager() {
		EntityManager em = new ReportAutokbs().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countReportAutokbss() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM ReportAutokbs o", Long.class)
				.getSingleResult();
	}

	public static List<ReportAutokbs> findAllReportAutokbss() {
		return entityManager().createQuery(
				"SELECT o FROM ReportAutokbs o",
				ReportAutokbs.class).getResultList();
	}

	public static List<ReportAutokbs> findAllReportAutokbss(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportAutokbs o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager()
				.createQuery(jpaQuery, ReportAutokbs.class)
				.getResultList();
	}

	public static ReportAutokbs findReportAutokbs(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportAutokbs.class, id);
	}

	public static List<ReportAutokbs> findReportAutokbsEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportAutokbs o",
						ReportAutokbs.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<ReportAutokbs> findReportAutokbsEntries(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportAutokbs o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager()
				.createQuery(jpaQuery, ReportAutokbs.class)
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
			ReportAutokbs attached = ReportAutokbs
					.findReportAutokbs(this.id);
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
	public ReportAutokbs merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportAutokbs merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static String toJsonArray(Collection<ReportAutokbs> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static List findReportAutokbsByDateTop(WccPlatformUser platform,
			String startTime, String endTime) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportAutokbs.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.count("keyWord"), "countint")
				.add(Projections.groupProperty("keyWord")));
		if (null != startTime && !"".equals(startTime) && null != endTime
				&& !"".equals(endTime)) {
			criteria.add(Restrictions.between("insertTime", DateUtil.formateDate(
					startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil
					.formateDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		criteria.addOrder(Order.desc("countint"));
		List list = criteria.list();
		return list;
	}

	public static List findReportAutokbsByMonthTop(WccPlatformUser platform,
			String startTime, String endTime) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportAutokbs.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.count("keyWord"), "countint")
				.add(Projections.groupProperty("keyWord")));
		if (null != startTime && !"".equals(startTime) && null != endTime
				&& !"".equals(endTime)) {
			criteria.add(Restrictions.between("insertTime", DateUtil.formateDate(
					startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil
					.formateDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		criteria.addOrder(Order.desc("countint"));
		List list = criteria.list();
		return list;
	}
}

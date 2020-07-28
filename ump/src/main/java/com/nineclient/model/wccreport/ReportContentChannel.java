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
public class ReportContentChannel {
	private Date refDate;// 时间
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

	private Integer shareScene; // 分享场景 1代表好友转发 2代表朋友圈 3代表腾讯微博 255代表其他

	private Integer shareCount; // 分享次数

	private Integer shareUser; // 分享人数

	@Transient
	private String dateStr;

	@Transient
	private Integer forward = 0;// 好友转发
	@Transient
	private Integer circle = 0;// 朋友圈
	@Transient
	private Integer blog = 0;// 腾讯微博
	@Transient
	private Integer other = 0;// 其他

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

	public Integer getShareScene() {
		return shareScene;
	}

	public void setShareScene(Integer shareScene) {
		this.shareScene = shareScene;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getShareUser() {
		return shareUser;
	}

	public void setShareUser(Integer shareUser) {
		this.shareUser = shareUser;
	}

	public Integer getForward() {
		return forward;
	}

	public void setForward(Integer forward) {
		this.forward = forward;
	}

	public Integer getCircle() {
		return circle;
	}

	public void setCircle(Integer circle) {
		this.circle = circle;
	}

	public Integer getBlog() {
		return blog;
	}

	public void setBlog(Integer blog) {
		this.blog = blog;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList();

	public static final EntityManager entityManager() {
		EntityManager em = new ReportContentChannel().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countReportContentChannels() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM ReportContentChannel o", Long.class)
				.getSingleResult();
	}

	public static List<ReportContentChannel> findAllReportContentChannels() {
		return entityManager().createQuery(
				"SELECT o FROM ReportContentChannel o",
				ReportContentChannel.class).getResultList();
	}

	public static List<ReportContentChannel> findAllReportContentChannels(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportContentChannel o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager()
				.createQuery(jpaQuery, ReportContentChannel.class)
				.getResultList();
	}

	public static ReportContentChannel findReportContentChannel(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportContentChannel.class, id);
	}

	public static List<ReportContentChannel> findReportContentChannelEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportContentChannel o",
						ReportContentChannel.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<ReportContentChannel> findReportContentChannelEntries(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportContentChannel o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager()
				.createQuery(jpaQuery, ReportContentChannel.class)
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
			ReportContentChannel attached = ReportContentChannel
					.findReportContentChannel(this.id);
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
	public ReportContentChannel merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportContentChannel merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static List<ReportContentChannel> findReportContentChannelsByDateBetween(
			WccPlatformUser platform, String startTime, String endTime) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportContentChannel.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		if (null != startTime && !"".equals(startTime)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(
					startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil
					.formateDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		criteria.addOrder(Order.asc("refDate"));
		List<ReportContentChannel> list = criteria.list();
		return list;
	}

	public static List findReportContentChannelsByDate(
			WccPlatformUser platform, String firstdate, String lastdate) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportContentChannel.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("shareUser"))
				.add(Projections.sum("shareCount"))
				.add(Projections.groupProperty("shareScene")));
		if (null != firstdate && !"".equals(firstdate) && null != lastdate
				&& !"".equals(lastdate)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(
					firstdate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil
					.formateDate(lastdate + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		List list = criteria.list();
		return list;
	}

	public static ReportContentChannel findReportContentChannelsByDate(
			Long platformId, String time) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportContentChannel.class);
		if (null != platformId) {
			criteria.add(Restrictions.eq("platformUser", platformId));
		}
		if (null != time && !"".equals(time)) {
			criteria.add(Restrictions.eq("refDate", DateUtil.formateDate(time
					+ " 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		return (ReportContentChannel) criteria.uniqueResult();
	}

	public static ReportContentChannel findReportContentChannelsByDate2(
			Long platformId, String time, Integer type) {
		String jpaQuery = "SELECT o FROM ReportContentChannel o";
		jpaQuery += " WHERE platformUser = " + platformId;
		jpaQuery += " AND shareScene = " + type;
		jpaQuery += " AND o.refDate = '" + time + " 00:00:00'";
		List list = entityManager().createQuery(jpaQuery,
				ReportContentChannel.class).getResultList();
		if (list.size() > 0) {
			return entityManager().createQuery(jpaQuery,
					ReportContentChannel.class).getSingleResult();
		} else {
			return null;
		}
	}

	public static String toJsonArray(Collection<ReportContentChannel> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}

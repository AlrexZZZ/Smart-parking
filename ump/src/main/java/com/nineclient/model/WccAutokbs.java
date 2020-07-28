package com.nineclient.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.WccAutokbsIsReview;
import com.nineclient.constant.WccAutokbsMatchWay;
import com.nineclient.constant.WccAutokbsReplyType;
import com.nineclient.constant.WccAutokbsShowWay;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccAutokbs {

	/**
     */
	@Size(max = 300)
	private String title;

	/**
     */
	@Size(max = 4000)
	private String keyWord;

	/**
     */
	@Size(max = 4000)
	private String content;

	/**
     */
	@ManyToOne
	private PubOperator author;
	/**
     */
	@Enumerated
	private WccAutokbsShowWay showWay;

	/**
     */
	private Boolean active;

	/**
     */
	private Boolean isDeleted;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;

	/**
     */
	@Enumerated
	private WccAutokbsMatchWay matchWay;

	/**
     */
	@Enumerated
	private WccAutokbsReplyType replyType;

	/**
     */
	@Enumerated
	private WccAutokbsIsReview isReview;

	/**
     */
	@Size(max = 4000)
	private String relatedIssues;

	public String toString() {
		// return ReflectionToStringBuilder.toString(this,
		// ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
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

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("title", "keyWord", "content", "showWay", "active",
					"isDeleted", "insertTime", "matchWay", "replyType",
					"relatedIssues", "author");

	public static final EntityManager entityManager() {
		EntityManager em = new WccAutokbs().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countWccAutokbses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM WccAutokbs o",
				Long.class).getSingleResult();
	}

	public static List<WccAutokbs> findAllWccAutokbses() {
		return entityManager().createQuery("SELECT o FROM WccAutokbs o",
				WccAutokbs.class).getResultList();
	}

	public static List<WccAutokbs> findWccAutokbsesByActiveAndIsReview(
			Long platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccAutokbs o WHERE o.active = 1 AND o.isReview = 1 AND o.platformUser = "
								+ platformUserId, WccAutokbs.class)
				.getResultList();
	}

	public static List<WccAutokbs> findWccAutokbsesByPlatform(
			Long platformUserId) {
		return entityManager().createQuery(
				"SELECT o FROM WccAutokbs o WHERE o.platformUser = "
						+ platformUserId, WccAutokbs.class).getResultList();
	}

	public static List<WccAutokbs> findWccAutokbsesByShowway(int showway,
			Long platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccAutokbs o WHERE o.active = 1 AND o.isReview = 1 AND o.showWay = "
								+ showway
								+ " AND o.platformUser = "
								+ platformUserId, WccAutokbs.class)
				.getResultList();
	}

	public static List<WccAutokbs> findAllWccAutokbses(String sortFieldName,
			String sortOrder, Long companyId) {
		String jpaQuery = "SELECT o FROM WccAutokbs o WHERE 1=1 ";
		jpaQuery += " AND o.platformUser.company =" + companyId;
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccAutokbs.class)
				.getResultList();
	}

	public static WccAutokbs findWccAutokbs(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccAutokbs.class, id);
	}

	public static List<WccAutokbs> findWccAutokbsEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM WccAutokbs o", WccAutokbs.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<WccAutokbs> findWccAutokbsEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder,
			Long companyId) {
		String jpaQuery = "SELECT o FROM WccAutokbs o";
		jpaQuery += " AND o.platformUser.company =" + companyId;
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccAutokbs.class)
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
			WccAutokbs attached = WccAutokbs.findWccAutokbs(this.id);
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
	public WccAutokbs merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccAutokbs merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WccAutokbsShowWay getShowWay() {
		return this.showWay;
	}

	public void setShowWay(WccAutokbsShowWay showWay) {
		this.showWay = showWay;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public WccAutokbsMatchWay getMatchWay() {
		return this.matchWay;
	}

	public void setMatchWay(WccAutokbsMatchWay matchWay) {
		this.matchWay = matchWay;
	}

	public WccAutokbsReplyType getReplyType() {
		return this.replyType;
	}

	public void setReplyType(WccAutokbsReplyType replyType) {
		this.replyType = replyType;
	}

	public String getRelatedIssues() {
		return this.relatedIssues;
	}

	public void setRelatedIssues(String relatedIssues) {
		this.relatedIssues = relatedIssues;
	}

	@ManyToOne
	private WccPlatformUser platformUser;

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public PubOperator getAuthor() {
		return author;
	}

	public void setAuthor(PubOperator author) {
		this.author = author;
	}

	public WccAutokbsIsReview getIsReview() {
		return isReview;
	}

	public void setIsReview(WccAutokbsIsReview isReview) {
		this.isReview = isReview;
	}

	@ManyToOne
	private WccMaterials materials;

	public WccMaterials getMaterials() {
		return materials;
	}

	public void setMaterials(WccMaterials materials) {
		this.materials = materials;
	}

	public static PageModel<WccAutokbs> getQueryAutokbs(
			QueryModel<WccAutokbs> qm, String platformUser, String title,
			String keyWord, String active, String isReview, String content,
			String showWay, String commentStartTimeId, String commentEndTimeId,
			Long companyId, List<WccPlatformUser> platList) {
		PageModel<WccAutokbs> pageModel = new PageModel<WccAutokbs>();
		StringBuffer sql = new StringBuffer(
				"SELECT o FROM WccAutokbs o WHERE 1=1 ");
		sql.append(" AND o.platformUser.company =" + companyId);
		if (!"".equals(platformUser) && null != platformUser) {
			String[] platStr = platformUser.split(",");
			if (platStr.length == 1) {
				sql.append(" AND o.platformUser=" + platStr[0]);
			} else {
				sql.append(" AND (");
				for (String plat : platStr) {
					sql.append("  o.platformUser=" + plat + " OR ");
				}
				sql = new StringBuffer(sql.substring(0, sql.length() - 3));
				sql.append(") ");
			}
		}else{
			sql.append(" AND o.platformUser in (:platUsers_item)");
		}
		if (!"".equals(title) && null != title) {
			sql.append(" AND o.title LIKE '%" + title + "%'");
		}
		if (!"".equals(keyWord) && null != keyWord) {
			sql.append(" AND o.keyWord LIKE '%" + keyWord + "%'");
		}
		if (!"".equals(active) && null != active) {
			sql.append(" AND o.active=" + active);
		}
		if (!"".equals(isReview) && null != isReview) {
			sql.append(" AND o.isReview=" + isReview);
		}
		if (!"".equals(content) && null != content) {
			sql.append(" AND o.content LIKE '%" + content + "%'");
		}
		if (!"".equals(showWay) && null != showWay) {
			sql.append(" AND o.showWay=" + showWay);
		}
		if (!"".equals(commentStartTimeId) && null != commentStartTimeId) {
			sql.append(" AND o.insertTime>='" + commentStartTimeId + "'");
		}

		if (!"".equals(commentEndTimeId) && null != commentEndTimeId) {
			sql.append(" AND o.insertTime<='" + commentEndTimeId + "'");
		}

		sql.append(" ORDER BY o.insertTime DESC");
		Map<String, Object> map = new HashMap<String, Object>();
		TypedQuery<WccAutokbs> query = entityManager().createQuery(
				sql.toString(), WccAutokbs.class);

		//TODO
		if("".equals(platformUser)||null == platformUser){
		if(platList!=null&&platList.size()>0){
			query.setParameter("platUsers_item", platList);
		       }
		}
		
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}

		int totalCount = 0;
		{ // 查总数
			TypedQuery<Long> countQuery = entityManager().createQuery(
					sql.toString().replaceAll("SELECT o", "SELECT count(o)"),
					Long.class);
			//=====TODO
			if("".equals(platformUser)||null == platformUser){
			if(platList!=null&&platList.size()>0){
				countQuery.setParameter("platUsers_item", platList);
			       }
			}
			
			for (String key : map.keySet()) {
				countQuery.setParameter(key, map.get(key));
			}
			totalCount = countQuery.getSingleResult().intValue();
		}

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());

		return pageModel;
	}

	// 显示相关问题名称
	@javax.persistence.Transient
	private String relatedIssuesName;

	public String getRelatedIssuesName() {
		String relatedIssuesName = "";
		if (relatedIssues != null && !relatedIssues.equals("")) {
			String[] relatedStr = relatedIssues.split(",");
			for (String relatedId : relatedStr) {
				WccAutokbs auto = WccAutokbs.findWccAutokbs(Long
						.parseLong(relatedId));
				if (auto.getActive()
						&& auto.getIsReview().equals(WccAutokbsIsReview.审核通过)) {
					relatedIssuesName += auto.getTitle() + ",";
				}
			}
		}
		if (!relatedIssuesName.equals("")) {
			relatedIssuesName = relatedIssuesName.substring(0,
					relatedIssuesName.length() - 1);
		}
		return relatedIssuesName;
	}

	public static List<WccAutokbs> findWccAutokbsByTitle(String title,
			String platform, Long id) {
		String hql = "SELECT o FROM WccAutokbs o WHERE o.title = '" + title
				+ "' AND o.platformUser = " + platform;
		if(id!=null){
			 hql += " AND o.id<>"+id;
		}
		return entityManager().createQuery(hql, WccAutokbs.class)
				.getResultList();
	}
	public static List<WccAutokbs> findWccAutokbsByMaterial(Long id) {
		String hql = "SELECT o FROM WccAutokbs o WHERE o.materials.id = " + id;
		return entityManager().createQuery(hql, WccAutokbs.class)
				.getResultList();
	}

}

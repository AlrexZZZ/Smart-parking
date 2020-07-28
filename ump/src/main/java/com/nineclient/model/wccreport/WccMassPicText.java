package com.nineclient.model.wccreport;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccMassMessage;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMassPicText {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	private boolean isDelete;

	private String msgid;// 这里的msgid实际上是由msgid（图文消息id）和index（消息次序索引）组成，
							// 例如12003_3， 其中12003是msgid，即一次群发的id消息的；
							// 3为index，假设该次群发的图文消息共5个文章（因为可能为多图文）， 3表示5个中的第3个

	private String title;// 图文消息的标题

	private Date statDate;// 统计的日期，在getarticletotal接口中，ref_date指的是文章群发出日期，
							// 而stat_date是数据统计日期

	private Date refDate;// 数据的日期，需在begin_date和end_date之间

	private int intPageReadUser;// 图文页（点击群发图文卡片进入的页面）的阅读人数

	private int intPageReadCount;// 图文页的阅读次数

	private int oriPageReadUser;// 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0

	private int oriPageReadCount;// 原文页的阅读次数

	private int shareUser;// 分享的人数

	private int shareCount;// 分享的次数

	private int addToFavUser;// 收藏的人数

	private int addToFavCount;// 收藏的次数

	private int target_user;// 送达人数，一般约等于总粉丝数（需排除黑名单或其他异常情况下无法收到消息的粉丝）

	private int type;//

	@Transient
	private int ranking;

	@Transient
	private String platformUserName;

	public String getPlatformUserName() {
		return platformUserName;
	}

	public void setPlatformUserName(String platformUserName) {
		this.platformUserName = platformUserName;
	}

	@Transient
	private List<WccMassPicText> massPicTexts;

	public List<WccMassPicText> getMassPicTexts() {
		return massPicTexts;
	}

	public void setMassPicTexts(List<WccMassPicText> massPicTexts) {
		this.massPicTexts = massPicTexts;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private WccPlatformUser platformUser;

	private Long massId;

	public Long getMassId() {
		return massId;
	}

	public void setMassId(Long massId) {
		this.massId = massId;
	}

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public int getTarget_user() {
		return target_user;
	}

	public void setTarget_user(int target_user) {
		this.target_user = target_user;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public int getIntPageReadUser() {
		return intPageReadUser;
	}

	public void setIntPageReadUser(int intPageReadUser) {
		this.intPageReadUser = intPageReadUser;
	}

	public int getIntPageReadCount() {
		return intPageReadCount;
	}

	public void setIntPageReadCount(int intPageReadCount) {
		this.intPageReadCount = intPageReadCount;
	}

	public int getOriPageReadUser() {
		return oriPageReadUser;
	}

	public void setOriPageReadUser(int oriPageReadUser) {
		this.oriPageReadUser = oriPageReadUser;
	}

	public int getOriPageReadCount() {
		return oriPageReadCount;
	}

	public void setOriPageReadCount(int oriPageReadCount) {
		this.oriPageReadCount = oriPageReadCount;
	}

	public int getShareUser() {
		return shareUser;
	}

	public void setShareUser(int shareUser) {
		this.shareUser = shareUser;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getAddToFavUser() {
		return addToFavUser;
	}

	public void setAddToFavUser(int addToFavUser) {
		this.addToFavUser = addToFavUser;
	}

	public int getAddToFavCount() {
		return addToFavCount;
	}

	public void setAddToFavCount(int addToFavCount) {
		this.addToFavCount = addToFavCount;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccMassPicText().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
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
	public WccMassPicText merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccMassPicText merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static WccMassPicText findWccMassPicText(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccMassPicText.class, id);
	}

	public static WccMassPicText CheckWccMassByMsgIdStatTime(String msgId,
			String refTime, String statTime, int type) {
		StringBuffer sql = new StringBuffer(
				"SELECT o FROM WccMassPicText o WHERE o.isDelete = 1 AND o.msgid = '"
						+ msgId + "' AND o.type = " + type);
		if (null != statTime && !"".equals(statTime)) {
			sql.append(" AND o.statDate = '" + statTime + "'");
		}
		if (null != refTime && !"".equals(refTime)) {
			sql.append(" AND o.refDate='" + refTime + "'");
		}
		List<WccMassPicText> masss = entityManager().createQuery(
				sql.toString(), WccMassPicText.class).getResultList();
		return masss.size() > 0 ? masss.get(0) : null;
	}

	public static List<WccMassMessage> findWccMassMessages() {
		return entityManager().createQuery(
				"SELECT o FROM WccMassPicText o WHERE o.isDelete = 1",
				WccMassMessage.class).getResultList();
	}

	public static List<WccMassPicText> findWccMassPicTextByMassId(Long massId) {
		return entityManager().createQuery(
				"SELECT o FROM WccMassPicText o WHERE o.isDelete = 1 AND o.massId = "
						+ massId + " And o.type = 2 ORDER BY o.statDate",
				WccMassPicText.class).getResultList();
	}

	public static PageModel<WccMassPicText> getMassPicText(
			QueryModel<WccMassPicText> qm) {
		Map parmMap = qm.getInputMap();
		PageModel<WccMassPicText> pageModel = new PageModel<WccMassPicText>();

		StringBuffer sql = new StringBuffer(
				" SELECT o FROM WccMassPicText o WHERE o.isDelete = 1 AND o.type = 1 ");
		if (null != parmMap.get("platformId")
				&& !"".equals(parmMap.get("platformId"))) {
			sql.append(" AND o.platformUser = " + parmMap.get("platformId"));
		}
		if (null != parmMap.get("startTime")
				&& !"".equals(parmMap.get("startTime"))) {
			sql.append(" AND o.refDate >= '" + parmMap.get("startTime")
					+ " 00:00:00'");
		}
		if (null != parmMap.get("endTime")
				&& !"".equals(parmMap.get("endTime"))) {
			sql.append(" AND o.refDate <= '" + parmMap.get("endTime")
					+ " 00:00:00'");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		TypedQuery<WccMassPicText> query = entityManager().createQuery(
				sql.toString(), WccMassPicText.class);

		int totalCount = 0;

		{ // 查总数
			TypedQuery<Long> countQuery = entityManager().createQuery(
					sql.toString().replaceAll(" SELECT o", "SELECT count(o)"),
					Long.class);
			for (String key : map.keySet()) {
				countQuery.setParameter(key, map.get(key));
			}
			totalCount = countQuery.getSingleResult().intValue();
		}

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		List<WccMassPicText> list = query.getResultList();
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());
		return pageModel;
	}

	public static String toJsonArray(Collection<WccMassPicText> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static List findWccMassPicTextByDateTop(WccPlatformUser platform,
			String startTime, String endTime) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(WccMassPicText.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform));
		}
		criteria.add(Restrictions.eq("type", 3));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("intPageReadUser"), "sumint")
				.add(Projections.sum("shareUser"))
				.add(Projections.sum("addToFavUser"))
				.add(Projections.groupProperty("title")));
		if (null != startTime && !"".equals(startTime) && null != endTime
				&& !"".equals(endTime)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(
					startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil
					.formateDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		criteria.addOrder(Order.desc("sumint"));
		List list = criteria.list();
		return list;
	}

	public static List findWccMassPicTextByDate(Long platformId,
			String time) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(WccMassPicText.class);
		if (null != platformId) {
			criteria.add(Restrictions.eq("platformUser", platformId));
		}
		criteria.add(Restrictions.eq("type", 3));
		if (null != time && !"".equals(time)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(
					time + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), DateUtil
					.formateDate(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		return criteria.list();
	}

	public static WccMassPicText findWccMassPicTextByDate2(Long platformId,
			String time, String title) {
		List<WccMassPicText> list = entityManager().createQuery(
				"SELECT o FROM WccMassPicText o WHERE o.platformUser.id = "
						+ platformId + " And o.type = 3 AND o.refDate = '"
						+ time + " 00:00:00' AND o.title = '"+title+"'", WccMassPicText.class)
				.getResultList();
		if (list.size() ==1) {
			return list.get(0);
		}else {
			return null;

		}
	}
}

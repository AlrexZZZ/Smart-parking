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
public class ReportMessage {
	private Date refDate; // 时间
	private String refHour; // 小时
	private Integer msgType;// 消息类型，1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
	private Integer msgUser;// 发送消息人数
	private Integer msgCount;// 发送消息次数
	private Integer type; // 统计类型 0 按小时 1 按天 2 按月
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
	private Integer textCount=0;
	@Transient 
	private Integer imageCount=0;
	@Transient
	private Integer voiceCount=0;
	@Transient
	private Integer vodieCount=0;
	@Transient
	private Integer otherCount=0;
	
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

	public String getRefHour() {
		return refHour;
	}

	public void setRefHour(String refHour) {
		this.refHour = refHour;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(Integer msgUser) {
		this.msgUser = msgUser;
	}

	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getTextCount() {
		return textCount;
	}

	public void setTextCount(Integer textCount) {
		this.textCount = textCount;
	}

	public Integer getImageCount() {
		return imageCount;
	}

	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}

	public Integer getVoiceCount() {
		return voiceCount;
	}

	public void setVoiceCount(Integer voiceCount) {
		this.voiceCount = voiceCount;
	}

	public Integer getVodieCount() {
		return vodieCount;
	}

	public void setVodieCount(Integer vodieCount) {
		this.vodieCount = vodieCount;
	}

	public Integer getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("refDate");

	public static final EntityManager entityManager() {
		EntityManager em = new ReportMessage().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countReportMessages() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM ReportMessage o", Long.class)
				.getSingleResult();
	}

	public static List<ReportMessage> findAllReportMessages() {
		return entityManager().createQuery("SELECT o FROM ReportMessage o",
				ReportMessage.class).getResultList();
	}

	public static List<ReportMessage> findAllReportMessages(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportMessage o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportMessage.class)
				.getResultList();
	}

	public static ReportMessage findReportMessage(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ReportMessage.class, id);
	}

	public static List<ReportMessage> findReportMessageEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM ReportMessage o",
						ReportMessage.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<ReportMessage> findReportMessageEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM ReportMessage o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, ReportMessage.class)
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
			ReportMessage attached = ReportMessage.findReportMessage(this.id);
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
	public ReportMessage merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ReportMessage merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static String toJsonArray(Collection<ReportMessage> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static ReportMessage checkReportMessage(Long platformId,
			String ref_date, String ref_hour, String msg_type, Integer type) {
		String sql = "SELECT o FROM ReportMessage o WHERE o.platformUser = "
				+ platformId + " AND o.refDate = '"
				+ ref_date + " 00:00:00'  AND msgType = " + msg_type;
		if(ref_hour!=null){
			sql += " AND o.refHour = '" + ref_hour+ "'";
		}
		if(type!=null){
			sql += " And o.type = " + type;
		}
		List<ReportMessage> list = entityManager().createQuery(sql,
				ReportMessage.class).getResultList();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;

		}
	}
	public static List<ReportMessage> findReportMessageByDateBetween(WccPlatformUser platform, String startTime, String endTime, int firstResult, int maxResults, Integer queryType) {
        Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(ReportMessage.class);
        if (null != platform) {
            criteria.add(Restrictions.eq("platformUser", platform.getId()));
        }
        if (null != queryType) {
        	criteria.add(Restrictions.eq("type", queryType));
        }
        if (null != startTime && !"".equals(startTime)) {
            criteria.add(Restrictions.between("refDate", DateUtil.formateDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),DateUtil.formateDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        criteria.addOrder(Order.asc("refDate"));
        if(firstResult!=-1&&maxResults!=-1){
        	criteria.setFirstResult(firstResult);
        	criteria.setMaxResults(maxResults);
        }
        List<ReportMessage> list = criteria.list();
        return list;
    }
	
}

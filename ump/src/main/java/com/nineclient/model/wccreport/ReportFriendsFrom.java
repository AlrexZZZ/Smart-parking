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
import org.hibernate.criterion.ProjectionList;
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
public class ReportFriendsFrom {
	private Date refDate;//时间
	
	@Transient
	private String refDateStr;
	
	public String getRefDateStr() {
		if(refDate!=null){
			return DateUtil.getDayStr(refDate);
		}
		return "";
	}

	private Integer userSource;//0代表其他 30代表扫二维码(931门店二维码、932图文二维码、933其它二维码) 17代表名片分享  936公众号搜索(35代表搜号码（即微信添加朋友页的搜索） 39代表查询微信公众帐号)43代表图文页右上角菜单
	private Integer newUser;//新增数
	private Integer cancelUser;//取消数
	private Long platformUser;
	
	private Date insertTime;//添加时间
	@Transient
	private String platformUserName;
	
	public String getPlatformUserName() {
		if(WccPlatformUser.findWccPlatformUser(platformUser)!=null){
			return WccPlatformUser.findWccPlatformUser(platformUser).getAccount();
		}
		return "";
	}
	
	@Transient
	private String dateStr;
	//报表临时字段 新增数
	@Transient
	private Integer otherCount=0;//0代表其他
	@Transient
	private Integer storeCount=0;//931门店二维码
	@Transient
	private Integer imageCount=0;//932图文二维码
	@Transient
	private Integer other2Count=0;//933其它二维码
	@Transient
	private Integer shareCount=0;//17代表名片分享
	@Transient
	private Integer findNumCount=0;//936公众号搜索(35代表搜号码（即微信添加朋友页的搜索） 39代表查询微信公众帐号)
	@Transient
	private Integer menuCount=0;//43代表图文页右上角菜单
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public Long getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(Long platformUser) {
		this.platformUser = platformUser;
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

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Integer getUserSource() {
		return userSource;
	}

	public void setUserSource(Integer userSource) {
		this.userSource = userSource;
	}

	public Integer getNewUser() {
		return newUser;
	}

	public void setNewUser(Integer newUser) {
		this.newUser = newUser;
	}

	public Integer getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(Integer cancelUser) {
		this.cancelUser = cancelUser;
	}
	
	public Integer getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	public Integer getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}

	public Integer getImageCount() {
		return imageCount;
	}

	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}

	public Integer getOther2Count() {
		return other2Count;
	}

	public void setOther2Count(Integer other2Count) {
		this.other2Count = other2Count;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getFindNumCount() {
		return findNumCount;
	}

	public void setFindNumCount(Integer findNumCount) {
		this.findNumCount = findNumCount;
	}

	public Integer getMenuCount() {
		return menuCount;
	}

	public void setMenuCount(Integer menuCount) {
		this.menuCount = menuCount;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("refDate");
	
	public static final EntityManager entityManager() {
        EntityManager em = new ReportFriendsFrom().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countReportFriendsFroms() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ReportFriendsFrom o", Long.class).getSingleResult();
    }

	public static List<ReportFriendsFrom> findAllReportFriendsFroms() {
        return entityManager().createQuery("SELECT o FROM ReportFriendsFrom o", ReportFriendsFrom.class).getResultList();
    }
	
	/**
	 * 
	 * 根据时间段查询粉丝来源
	 * 
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public static List<ReportFriendsFrom> findReportFriendsFromsByDateBetween(WccPlatformUser platform, String startTime, String endTime, int firstResult, int maxResults) {
        Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(ReportFriendsFrom.class);
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
        List<ReportFriendsFrom> list = criteria.list();
        return list;
    }
	
	public static List findReportFriendsFromsByDate(WccPlatformUser platform, String firstdate, String lastdate) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportFriendsFrom.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		 criteria.setProjection(Projections.projectionList().add(Projections.sum("newUser")).add(Projections.groupProperty("userSource")));
		if (null != firstdate && !"".equals(firstdate)&&null != lastdate && !"".equals(lastdate)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(firstdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),DateUtil.formateDate(lastdate+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		List list = criteria.list();
		return list;
	}

	public static List<ReportFriendsFrom> findAllReportFriendsFroms(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ReportFriendsFrom o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ReportFriendsFrom.class).getResultList();
    }

	public static ReportFriendsFrom findReportFriendsFrom(Long id) {
        if (id == null) return null;
        return entityManager().find(ReportFriendsFrom.class, id);
    }

	public static List<ReportFriendsFrom> findReportFriendsFromEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ReportFriendsFrom o", ReportFriendsFrom.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<ReportFriendsFrom> findReportFriendsFromEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ReportFriendsFrom o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ReportFriendsFrom.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ReportFriendsFrom attached = ReportFriendsFrom.findReportFriendsFrom(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public ReportFriendsFrom merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ReportFriendsFrom merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	public static String toJsonArray(Collection<ReportFriendsFrom> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static List<ReportFriendsFrom> findReportFriendsFromsByDateAndType(
			WccPlatformUser platform, String date, Integer type) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportFriendsFrom.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		if (null != date && !"".equals(date)) {
			criteria.add(Restrictions.eq("refDate", DateUtil.formateDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		if(type!=null){
			criteria.add(Restrictions.eq("userSource", type));
		}
		return criteria.list();
	}
	public static ReportFriendsFrom findReportFriendsFromsByDateAndType2(
			WccPlatformUser platform, String date, Integer type) {
        String jpaQuery = "SELECT o FROM ReportFriendsFrom o";
        jpaQuery+=" WHERE o.platformUser = "+platform.getId();
        jpaQuery+=" AND o.refDate = '"+date+" 00:00:00'";
        jpaQuery+=" AND o.userSource = "+ type;
        List list = entityManager().createQuery(jpaQuery, ReportFriendsFrom.class).getResultList();
        if(list.size()>0){
        	return entityManager().createQuery(jpaQuery, ReportFriendsFrom.class).getSingleResult();
        }else{
        	return null;
        }
    }

	public static List findReportFriendsFromsByDay(WccPlatformUser platform,
			String startTime, String endTime) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportFriendsFrom.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		 criteria.setProjection(Projections.projectionList().add(Projections.sum("newUser")).add(Projections.sum("cancelUser")).add(Projections.groupProperty("refDate")));
		if (null != startTime && !"".equals(startTime)&&null != endTime && !"".equals(endTime)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),DateUtil.formateDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		List list = criteria.list();
		return list;
	}
	public static List findReportFriendsFromsByMonth(WccPlatformUser platform,
			String startTime, String endTime) {
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(ReportFriendsFrom.class);
		if (null != platform) {
			criteria.add(Restrictions.eq("platformUser", platform.getId()));
		}
		criteria.setProjection(Projections.projectionList().add(Projections.sum("newUser")).add(Projections.sum("cancelUser")));
		if (null != startTime && !"".equals(startTime)&&null != endTime && !"".equals(endTime)) {
			criteria.add(Restrictions.between("refDate", DateUtil.formateDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),DateUtil.formateDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		List list = criteria.list();
		return list;
	}
}

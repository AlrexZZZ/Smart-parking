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
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.UmpOperator;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.vocreport.ReportCommentMonth;
import com.nineclient.utils.DateUtil;

import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ReportFriendsSum {
	private Date refDate;
	
	@Transient
	private String refDateStr;
	
	public String getRefDateStr() {
		if(refDate!=null){
			return DateUtil.getDayStr(refDate);
		}
		return "";
	}


	private Long cumulateUser;
	
	private Long platformUser;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@Transient
	private String dateStr;
	
	@Transient
	private String platformUserName;
	
	@Transient
	private Integer newNum=0;
	
	@Transient
	private Integer cancelNum=0;
	
	
	
	public Integer getNewNum() {
		return newNum;
	}

	public void setNewNum(Integer newNum) {
		this.newNum = newNum;
	}

	public Integer getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}

	public String getPlatformUserName() {
		if(WccPlatformUser.findWccPlatformUser(platformUser)!=null){
			return WccPlatformUser.findWccPlatformUser(platformUser).getAccount();
		}
		return "";
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Long getCumulateUser() {
		return cumulateUser;
	}

	public void setCumulateUser(Long cumulateUser) {
		this.cumulateUser = cumulateUser;
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
	
	public void setPlatformUser(Long platformUser) {
		this.platformUser = platformUser;
	}


	public Long getPlatformUser() {
		return platformUser;
	}


	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("refDate");
	
	public static final EntityManager entityManager() {
        EntityManager em = new ReportFriendsSum().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countReportFriendsSums() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ReportFriendsSum o", Long.class).getSingleResult();
    }

	public static List<ReportFriendsSum> findAllReportFriendsSums() {
        return entityManager().createQuery("SELECT o FROM ReportFriendsSum o", ReportFriendsSum.class).getResultList();
    }
	
	public static List<ReportFriendsSum> findReportFriendsSumsByDateBetween(WccPlatformUser platform, String startTime, String endTime, int firstResult, int maxResults) {
        Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(ReportFriendsSum.class);
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
        List<ReportFriendsSum> list = criteria.list();
        return list;
    }
	
	public static ReportFriendsSum findReportFriendsSumsByDate(Long platformId, String time) {
		Session s = entityManager().unwrap(org.hibernate.Session.class);
		Criteria criteria = s.createCriteria(ReportFriendsSum.class);
		if (null != platformId) {
			criteria.add(Restrictions.eq("platformUser", platformId));
		}
		if (null != time && !"".equals(time)) {
			criteria.add(Restrictions.eq("refDate", DateUtil.formateDate(time+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		return (ReportFriendsSum) criteria.uniqueResult();
	}
	public static ReportFriendsSum findReportFriendsSumsByDate2(Long platformId, String time) {
        String jpaQuery = "SELECT o FROM ReportFriendsSum o WHERE 1=1";
        jpaQuery+=" AND o.platformUser = " +platformId;
        jpaQuery+=" AND o.refDate = '" +time+" 00:00:00'";
        List list = entityManager().createQuery(jpaQuery, ReportFriendsSum.class).getResultList();
        if(list.size()>0){
        	return entityManager().createQuery(jpaQuery, ReportFriendsSum.class).getSingleResult();
        }else{
        	return null;
        }
    }
	

	public static List<ReportFriendsSum> findAllReportFriendsSums(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ReportFriendsSum o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ReportFriendsSum.class).getResultList();
    }

	public static ReportFriendsSum findReportFriendsSum(Long id) {
        if (id == null) return null;
        return entityManager().find(ReportFriendsSum.class, id);
    }

	public static List<ReportFriendsSum> findReportFriendsSumEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ReportFriendsSum o", ReportFriendsSum.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<ReportFriendsSum> findReportFriendsSumEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ReportFriendsSum o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ReportFriendsSum.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ReportFriendsSum attached = ReportFriendsSum.findReportFriendsSum(this.id);
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
    public ReportFriendsSum merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ReportFriendsSum merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	public static String toJsonArray(Collection<ReportFriendsSum> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}

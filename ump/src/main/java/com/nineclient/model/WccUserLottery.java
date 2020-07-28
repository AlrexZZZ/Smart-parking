package com.nineclient.model;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccUserLottery {

    /**
     */
    private int lotteryNumber;
    
    /**
     * 每天可抽奖次数
     */
    private int lotterDayNum;

    /**
     */
    private Boolean isDeleted;
    
    /*
     * 抽奖日期
     */
    private Date lotterDay;
    
    @ManyToOne(cascade=CascadeType.ALL)
    private WccPlatformUser platformUser;
    
    @ManyToOne(cascade=CascadeType.ALL)
    private WccFriend friend;
    
    @ManyToOne(cascade=CascadeType.ALL)
    private WccLotteryActivity lotteryActivity;

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public WccFriend getFriend() {
		return friend;
	}

	public void setFriend(WccFriend friend) {
		this.friend = friend;
	}

	public WccLotteryActivity getLotteryActivity() {
		return lotteryActivity;
	}

	public void setLotteryActivity(WccLotteryActivity lotteryActivity) {
		this.lotteryActivity = lotteryActivity;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("lotteryNumber", "isDeleted");

	public static final EntityManager entityManager() {
        EntityManager em = new WccUserLottery().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccUserLotterys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccUserLottery o", Long.class).getSingleResult();
    }

	public static List<WccUserLottery> findAllWccUserLotterys() {
        return entityManager().createQuery("SELECT o FROM WccUserLottery o", WccUserLottery.class).getResultList();
    }
	
	/**
	 * 根据粉丝ID查询抽奖次数信息
	 * @return
	 */
	public static WccUserLottery findAllWccUserLotterysByFriendId(Long id,Long activityId) {
		WccUserLottery userLottery = null;
		List<WccUserLottery> userLotterys = null;
		/*if(lotterDate == null){
		}else{*/
		//userLotterys = entityManager().createQuery("SELECT o FROM WccUserLottery o where o.friend = "+id+" AND o.lotteryActivity = "+activityId+" AND o.lotterDay = "+lotterDate, WccUserLottery.class).getResultList();
		//}
		userLotterys = entityManager().createQuery("SELECT o FROM WccUserLottery o where o.friend = "+id+" AND o.lotteryActivity = "+activityId, WccUserLottery.class).getResultList();
		if(userLotterys.size()>0){
			userLottery = userLotterys.get(0);
		}
		return userLottery;
	}

	public static List<WccUserLottery> findAllWccUserLotterys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUserLottery o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUserLottery.class).getResultList();
    }

	public static WccUserLottery findWccUserLottery(Long id) {
        if (id == null) return null;
        return entityManager().find(WccUserLottery.class, id);
    }

	public static List<WccUserLottery> findWccUserLotteryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccUserLottery o", WccUserLottery.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccUserLottery> findWccUserLotteryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccUserLottery o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccUserLottery.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccUserLottery attached = WccUserLottery.findWccUserLottery(this.id);
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
    public WccUserLottery merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccUserLottery merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public int getLotteryNumber() {
        return this.lotteryNumber;
    }

	public void setLotteryNumber(int lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public static WccUserLottery getLotteryNumber(WccFriend friend ,WccLotteryActivity activity) {
		if(friend == null)return null;
		if(activity == null)return null;
		String hql=" FROM WccUserLottery o where o.friend=:friend  and o.lotteryActivity=:lotteryActivity  ";
		TypedQuery<WccUserLottery> query = entityManager().createQuery(hql,WccUserLottery.class);
		query.setParameter("friend", friend);
		query.setParameter("lotteryActivity", activity);
		return query.getSingleResult();
	}

	public Date getLotterDay() {
		return lotterDay;
	}

	public void setLotterDay(Date lotterDay) {
		this.lotterDay = lotterDay;
	}

	public int getLotterDayNum() {
		return lotterDayNum;
	}

	public void setLotterDayNum(int lotterDayNum) {
		this.lotterDayNum = lotterDayNum;
	}
	
}

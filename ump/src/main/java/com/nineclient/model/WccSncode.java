package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nineclient.constant.WccSnCodeStatus;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import javax.persistence.Enumerated;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccSncode {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @NotNull
    @Size(min = 1, max = 200)
    private String snCode;

    /**
     */
    @Enumerated
    private WccSnCodeStatus snStatus;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date awardTime;

    /**
     */
    @Size(max = 4000)
    private String snRemark;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private WccLotteryActivity lotteryActivity;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private WccAwardInfo awardInfo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private WccFriend friend;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private WccPlatformUser platformUser;
	
	private int awardLevel;
	
	private String tel;
	
	@Transient
	private int lotteryNumber;
	
	
	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public WccFriend getFriend() {
		return friend;
	}

	public void setFriend(WccFriend friend) {
		this.friend = friend;
	}

	public int getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(int awardLevel) {
		this.awardLevel = awardLevel;
	}

	public WccLotteryActivity getLotteryActivity() {
		return lotteryActivity;
	}

	public void setLotteryActivity(WccLotteryActivity lotteryActivity) {
		this.lotteryActivity = lotteryActivity;
	}

	public WccAwardInfo getAwardInfo() {
		return awardInfo;
	}

	public void setAwardInfo(WccAwardInfo awardInfo) {
		this.awardInfo = awardInfo;
	}

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

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getSnCode() {
        return this.snCode;
    }

	public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

	public WccSnCodeStatus getSnStatus() {
        return this.snStatus;
    }

	public void setSnStatus(WccSnCodeStatus snStatus) {
        this.snStatus = snStatus;
    }

	public Date getAwardTime() {
        return this.awardTime;
    }

	public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

	public String getSnRemark() {
        return this.snRemark;
    }

	public void setSnRemark(String snRemark) {
        this.snRemark = snRemark;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "snCode", "snStatus", "awardTime", "snRemark");

	public static final EntityManager entityManager() {
        EntityManager em = new WccSncode().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccSncodes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccSncode o", Long.class).getSingleResult();
    }

	public static void removeWccSncodeByActivityId(Long id){
		entityManager().createQuery("DELETE WccSncode o WHERE o.lotteryActivity="+id);
	}
	
	public static List<WccSncode> findAllWccSncodes() {
        return entityManager().createQuery("SELECT o FROM WccSncode o", WccSncode.class).getResultList();
    }
	
	public static WccSncode findAllWccSncodesBySncode(String sncode) {
		WccSncode wsncode = null;
		List<WccSncode> sncodes = entityManager().createQuery("SELECT o FROM WccSncode o WHERE o.snCode = '"+sncode+"'", WccSncode.class).getResultList();
        if(sncodes.size()>0){
        	wsncode = sncodes.get(0);
        }
		return wsncode;
	}
	
	public static List<WccSncode> findAllWccSncodesByFriendId(Long friendId,Long lotteryId) {
		return entityManager().createQuery("SELECT o FROM WccSncode o WHERE o.friend = "+friendId+" AND o.lotteryActivity = "+lotteryId, WccSncode.class).getResultList();
	}
	
	public static String findAllWccSncodesByLotteryIdLevel(String lotteryId,String level,WccFriend friend) {
		String code = "";
		WccSncode sncode = null;
		List<WccSncode> sncodes = entityManager().createQuery("SELECT o FROM WccSncode o WHERE o.lotteryActivity = "+lotteryId +" AND o.awardLevel = "+level + " And o.snStatus = "+0, WccSncode.class).getResultList();
		if(null!=sncodes&&sncodes.size()>0){
			sncode = sncodes.get(0);
			code = sncode.getSnCode();
			sncode.setFriend(friend);
			sncode.setPlatformUser(friend.getPlatformUser());
			sncode.setSnStatus(WccSnCodeStatus.已中奖);
			sncode.merge();
		}
		return code;
	}
	
	public static List<WccSncode> findAllWccSncodesByLotteryId(Long lotteryId) {
		return entityManager().createQuery("SELECT o FROM WccSncode o WHERE o.lotteryActivity = "+lotteryId, WccSncode.class).getResultList();
	}


	public static List<WccSncode> findAllWccSncodes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccSncode o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccSncode.class).getResultList();
    }

	public static WccSncode findWccSncode(Long id) {
        if (id == null) return null;
        return entityManager().find(WccSncode.class, id);
    }
	
	public static List<WccSncode> findWccSncodeBySnStatusLotteryId(Long id) {
        return entityManager().createQuery("SELECT o FROM WccSncode o WHERE o.snStatus="+1+" OR o.snStatus="+2+" AND o.lotteryActivity="+id, WccSncode.class).getResultList();
    }
	
	public static List<WccSncode> findWccSncodeBySnStatusLotteryId(Long id,String sn,String tel) {
		StringBuffer qlStr = new StringBuffer();
		qlStr.append("SELECT o FROM WccSncode o WHERE o.snStatus="+1+" OR o.snStatus="+2+" AND o.lotteryActivity="+id);
		if(null!=sn&&!"".equals(sn)){
			qlStr.append(" AND o.snCode like '%"+sn+"%'");
		}
		if(null!=tel&&!"".equals(tel)){
			qlStr.append(" AND o.tel like '%"+tel+"%'");
		}
        return entityManager().createQuery(qlStr.toString(), WccSncode.class).getResultList();
    }

	public static List<WccSncode> findWccSncodeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccSncode o", WccSncode.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccSncode> findWccSncodeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccSncode o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccSncode.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccSncode attached = WccSncode.findWccSncode(this.id);
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
    public WccSncode merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccSncode merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
public static PageModel<WccSncode> getQuerySncode(QueryModel<WccSncode> qm,List<WccPlatformUser> platformUsers){
		
//		jpaQuery = "SELECT o FROM WccPlatformUser o inner join o.pubOperators p where 1=1"
//        		+ " o.company = " + pubOper.getCompany().getId()+" and "
//        		+ " p.id = "+pubOper.getId();
		
		Map parmMap = qm.getInputMap();
		WccSncode sncode = qm.getEntity();
		PageModel<WccSncode> pageModel = new PageModel<WccSncode>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccSncode o WHERE (o.snStatus="+1+" OR o.snStatus="+2+" ");
		Map<String,Object> map = new HashMap<String, Object>();
		if(null!=platformUsers && platformUsers.size()>0){
			sql.append(") AND (");
			for(WccPlatformUser platformUser : platformUsers){
				sql.append(" o.platformUser = "+platformUser.getId() + " or");
			}
			if(sql.toString().endsWith("or")){
				sql.delete(sql.length()-2, sql.length());
			}
		}
		if(null!=parmMap.get("aid")&&!"".equals(parmMap.get("aid"))){
			sql.append(" ) AND o.snStatus !=0 AND o.lotteryActivity = "+parmMap.get("aid"));
		}
		if(null!=parmMap.get("sn")&&!"".equals(parmMap.get("sn"))){
			sql.append(" AND o.snCode like '%"+parmMap.get("sn")+"%'");
		}
		if(null!=parmMap.get("tel")&&!"".equals(parmMap.get("tel"))){
			sql.append(" AND o.tel like '%"+parmMap.get("tel")+"%'");
		}
		if(null!=parmMap.get("platformId")&&!"".equals(parmMap.get("platformId"))){
			sql.append(" AND o.platformUser = "+parmMap.get("platformId"));
		}
		
		
      TypedQuery<WccSncode> query = entityManager().createQuery(sql.toString(), WccSncode.class);
      for(String key:map.keySet()){
          query.setParameter(key, map.get(key));	
        }
        int totalCount = 0;
       { //查总数
         TypedQuery<Long> countQuery = entityManager().createQuery(sql.toString().replaceAll(" SELECT o", "SELECT count(o)"), Long.class);
         for(String key:map.keySet()){
          	countQuery.setParameter(key, map.get(key));	
          }
          totalCount = countQuery.getSingleResult().intValue();
       }
       if(qm.getLimit() > 0){ //分页截取
       	query.setFirstResult(qm.getStart());
           query.setMaxResults(qm.getLimit());
       }
       List<WccSncode> list = query.getResultList();
       pageModel.setTotalCount(totalCount);
       pageModel.setDataList(list);
       return pageModel;
	}

	public int getLotteryNumber() {
		return lotteryNumber;
	}
	
	public void setLotteryNumber(int lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
}

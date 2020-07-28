package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.nineclient.constant.WccActivityType;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONSerializer;

import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccLotteryActivity {

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 是否启用
     */
    private Boolean isVisibale;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 抽奖活动名称
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 400)
    private String activityName;

    /**
     * 开始时间
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endTime;
    
    /**
     * 添加时间
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date insertTime;

    /**
     * 活动介绍
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String activityIntroduction;

    /**
     * 未中奖提示
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String repeatAwardReply;

    /**
     * 活动类型
     */
    @Enumerated
    private WccActivityType activityType;

    /**
     * 备注
     */
    private String activityRemark;

    /**
     * 访问人数
     */
    private int visitorNumber;

    /**
     * 暂不用
     */
    private int effectiveNumber;

    /**
     * 奖品信息（暂不用）
     */
    private String awardMsg;

    /**
     * 活动图片URL(暂不用)
     */
    private String imageUrl;

    /**
     * 暂不用
     */
    private int costPoint;

    /**
     * 总抽奖次数
     */
    @NotNull
    @Value("0")
    private int lotteryNum;
    
    /**
     * 每人每日可抽奖次数
     */
    @NotNull
    @Value("0")
    private int lotteryDayNum;
    
    /**
     * 活动结束说明
     */
    private String activityEndInfo;
    
    /**
     * 活动未开始说明
     */
    private String activityNStartInfo;
    
    /**
     * 次数已满说明
     */
    private String numEndInfo;
    
    @OneToMany(mappedBy="lotteryActivity")
    private Set<WccAwardInfo> awardInfos;
    
    @OneToMany(mappedBy="lotteryActivity")
    private Set<WccSncode> sncodes;
    
  /*  @ManyToMany(cascade=CascadeType.ALL)
    private Set<WccPlatformUser> platformUsers;*/

    
    @OneToMany(mappedBy="lotteryActivity")
    private Set<WccUserLottery> userLottery;
    
    
    
    public String getActivityNStartInfo() {
		return activityNStartInfo;
	}

	public void setActivityNStartInfo(String activityNStartInfo) {
		this.activityNStartInfo = activityNStartInfo;
	}

	@ManyToOne
    private UmpCompany umpCompany;
	
	public UmpCompany getUmpCompany() {
		return umpCompany;
	}

	public void setUmpCompany(UmpCompany umpCompany) {
		this.umpCompany = umpCompany;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Set<WccUserLottery> getUserLottery() {
		return userLottery;
	}

	public void setUserLottery(Set<WccUserLottery> userLottery) {
		this.userLottery = userLottery;
	}

/*	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}*/

	public Set<WccSncode> getSncodes() {
		return sncodes;
	}

	public void setSncodes(Set<WccSncode> sncodes) {
		this.sncodes = sncodes;
	}

	public Set<WccAwardInfo> getAwardInfos() {
		return awardInfos;
	}

	public void setAwardInfos(Set<WccAwardInfo> awardInfos) {
		this.awardInfos = awardInfos;
	}

	public String getActivityEndInfo() {
		return activityEndInfo;
	}

	public void setActivityEndInfo(String activityEndInfo) {
		this.activityEndInfo = activityEndInfo;
	}

	public String getNumEndInfo() {
		return numEndInfo;
	}

	public void setNumEndInfo(String numEndInfo) {
		this.numEndInfo = numEndInfo;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "isVisibale", "keyword", "activityName", "startTime", "endTime", "activityIntroduction", "repeatAwardReply", "activityType", "activityRemark", "visitorNumber", "effectiveNumber", "awardMsg", "imageUrl", "costPoint", "lotteryNum");

	public static final EntityManager entityManager() {
        EntityManager em = new WccLotteryActivity().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	
	
	/**
	 * 活动查询
	 * @param qm
	 * @return
	 */
	public static PageModel<WccLotteryActivity> getQueryActivities(QueryModel<WccLotteryActivity> qm){
		 Map<String, Object> parmMap = qm.getInputMap();
		 WccLotteryActivity model = qm.getEntity();
		PageModel<WccLotteryActivity> pageModel = new PageModel<WccLotteryActivity>();
		
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccLotteryActivity o WHERE 1=1 AND o.isDeleted = true");
		Map<String,Object> map = new HashMap<String, Object>();
		
		/*if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
			sql.append(" inner join o.platformUsers plat WHERE plat in :platformUsers ");
			map.put("platformUsers",model.getPlatformUsers());
		}else{
			sql.append(" WHERE 1=1 ");
		}*/
		
		if(null!=parmMap.get("companyId")&&!"".equals(parmMap.get("companyId"))){
			sql.append(" AND o.umpCompany = :companyId");
			map.put("companyId", UmpCompany.findUmpCompany(Long.parseLong(parmMap.get("companyId")+"")));
		}
		
		if(null!=parmMap.get("activityName")&&!"".equals(parmMap.get("activityName"))){
			sql.append(" AND o.activityName like :activityName");
			map.put("activityName", "%"+parmMap.get("activityName")+"%");
		}
		
		if(null!=parmMap.get("startTime")&&!"".equals(parmMap.get("startTime"))){
			sql.append(" AND o.startTime >= :startTime");
			map.put("startTime", DateUtil.dateStringToTimestamp(parmMap.get("startTime")+""));
		}
		
		if(null!=parmMap.get("endTime")&&!"".equals(parmMap.get("endTime"))){
			sql.append(" AND o.endTime <= :endTime");
			map.put("endTime", DateUtil.dateStringToTimestamp(parmMap.get("endTime")+""));
		}
		
		if(null!=parmMap.get("insertTime")&&!"".equals(parmMap.get("insertTime"))){
			sql.append(" AND o.insertTime >= :insertTime");
			map.put("insertTime", DateUtil.dateStringToTimestamp(parmMap.get("insertTime")+""));
		}
		
		if(null!=parmMap.get("createEndTime")&&!"".equals(parmMap.get("createEndTime"))){
			sql.append(" AND o.insertTime <= :createEndTime");
			map.put("createEndTime", DateUtil.dateStringToTimestamp(parmMap.get("createEndTime")+""));
		}
		
		if(null!=parmMap.get("isvisible")&&!"".equals(parmMap.get("isvisible"))){
			sql.append(" AND o.isVisibale = :isvisible");
			if("0".equals(parmMap.get("isvisible")+"")){
				map.put("isvisible", false);
			}else{
				map.put("isvisible", true);
			}
		}
		
		sql.append(" ORDER BY o.insertTime DESC");
		
		System.out.println(sql.toString());
        TypedQuery<WccLotteryActivity> query = entityManager().createQuery(sql.toString(), WccLotteryActivity.class);
        
        for(String key:map.keySet()){
            query.setParameter(key, map.get(key));	
          }
        System.out.println(query.toString());
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
         List<WccLotteryActivity> list = query.getResultList();
         pageModel.setTotalCount(totalCount);
         pageModel.setDataList(query.getResultList());
         return pageModel;
	}
	
	public static List<WccLotteryActivity> findAllWccLotteryActivitys(UmpCompany company,int limit, Date date) {
		TypedQuery<WccLotteryActivity> query = entityManager().createQuery(" FROM WccLotteryActivity o WHERE 1=1 AND o.isDeleted = true and o.umpCompany=:umpCompany and o.isVisibale = true and o.endTime >= :endTime ",WccLotteryActivity.class);
		query.setParameter("umpCompany", company);
		query.setParameter("endTime", date);
		return query.getResultList();
	}
	
	/**
	 * 根据name查询抽奖活动
	 * 
	 * @param ids
	 * @return
	 */
	public static List<WccLotteryActivity> findWccLotteryByName(String name,UmpCompany company) {
		if (name == null || "".equals(name) || company == null)
			return null;
		TypedQuery<WccLotteryActivity> query = entityManager().createQuery(" FROM WccLotteryActivity o where o.activityName =:name and o.isDeleted =true and o.umpCompany=:company ",WccLotteryActivity.class);
		query.setParameter("name", name);
		query.setParameter("company", company);
		return query.getResultList();
	}

	
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public static long countWccLotteryActivitys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccLotteryActivity o", Long.class).getSingleResult();
    }

	public static List<WccLotteryActivity> findAllWccLotteryActivitys() {
        return entityManager().createQuery("SELECT o FROM WccLotteryActivity o", WccLotteryActivity.class).getResultList();
    }

	public static List<WccLotteryActivity> findAllWccLotteryActivitys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccLotteryActivity o WHERE o.isDeleted=true ";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccLotteryActivity.class).getResultList();
    }

	public static WccLotteryActivity findWccLotteryActivity(Long id) {
        if (id == null) return null;
        return entityManager().find(WccLotteryActivity.class, id);
    }

	public static List<WccLotteryActivity> findWccLotteryActivityEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccLotteryActivity o WHERE o.isDeleted=true", WccLotteryActivity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccLotteryActivity> findWccLotteryActivityEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccLotteryActivity o WHERE o.isDeleted=true ";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccLotteryActivity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccLotteryActivity attached = WccLotteryActivity.findWccLotteryActivity(this.id);
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
    public WccLotteryActivity merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccLotteryActivity merged = this.entityManager.merge(this);
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

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Boolean getIsVisibale() {
        return this.isVisibale;
    }

	public void setIsVisibale(Boolean isVisibale) {
        this.isVisibale = isVisibale;
    }

	public String getKeyword() {
        return this.keyword;
    }

	public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

	public String getActivityName() {
        return this.activityName;
    }

	public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

	public Date getStartTime() {
        return this.startTime;
    }

	public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

	public Date getEndTime() {
        return this.endTime;
    }

	public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public String getActivityIntroduction() {
        return this.activityIntroduction;
    }

	public void setActivityIntroduction(String activityIntroduction) {
        this.activityIntroduction = activityIntroduction;
    }

	public String getRepeatAwardReply() {
        return this.repeatAwardReply;
    }

	public void setRepeatAwardReply(String repeatAwardReply) {
        this.repeatAwardReply = repeatAwardReply;
    }

	public WccActivityType getActivityType() {
        return this.activityType;
    }

	public void setActivityType(WccActivityType activityType) {
        this.activityType = activityType;
    }

	public String getActivityRemark() {
        return this.activityRemark;
    }

	public void setActivityRemark(String activityRemark) {
        this.activityRemark = activityRemark;
    }

	public int getVisitorNumber() {
        return this.visitorNumber;
    }

	public void setVisitorNumber(int visitorNumber) {
        this.visitorNumber = visitorNumber;
    }

	public int getEffectiveNumber() {
        return this.effectiveNumber;
    }

	public void setEffectiveNumber(int effectiveNumber) {
        this.effectiveNumber = effectiveNumber;
    }

	public String getAwardMsg() {
        return this.awardMsg;
    }

	public void setAwardMsg(String awardMsg) {
        this.awardMsg = awardMsg;
    }

	public String getImageUrl() {
        return this.imageUrl;
    }

	public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	public int getCostPoint() {
        return this.costPoint;
    }

	public void setCostPoint(int costPoint) {
        this.costPoint = costPoint;
    }

	public int getLotteryNum() {
        return this.lotteryNum;
    }

	public void setLotteryNum(int lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

	public int getLotteryDayNum() {
		return lotteryDayNum;
	}

	public void setLotteryDayNum(int lotteryDayNum) {
		this.lotteryDayNum = lotteryDayNum;
	}

	
}

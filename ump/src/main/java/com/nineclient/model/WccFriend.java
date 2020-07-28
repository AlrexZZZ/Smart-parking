package com.nineclient.model;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.cbd.wcc.model.WccFinancialUser;
import com.nineclient.cherry.wcc.model.WccQrCode;
import com.nineclient.constant.PlatformType;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccFriendSex;
import com.nineclient.entity.MassConditionEntity;
import com.nineclient.model.wccreport.ReportFriendsSum;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccFriend {

    /**
     * 昵称
     */
    @Size(max = 500)
    private String nickName;

    /**
     * 头像图片地址
     */
    @Size(max = 4000)
    private String headImg;

    /**
     * 性别
     */
    @Enumerated
    private WccFriendSex sex;

    /**
     * 地区
     */
    @Size(max = 400)
    private String area;
    
    /**
     * 省份
     */
    private String province;
    
    /**
     * 国家
     */
    private String country;
    
    /**
     * 城市
     */
    private String city;

    /**
     * 签名
     */
    @Size(max = 4000)
    private String signature;

    /**
     * 备注名
     */
    @Size(max = 4000)
    private String remarkName;

    /**
     * 信息
     */
    @Size(max = 4000)
    private String userInfo;

    /**
     * 验证
     */
    private Boolean isValidated;

    /**
     * 是否删除
     */
    private Boolean isDeleted;
    
    private String openId;
    

    /**
     * 添加时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date insertTime;

    /**
     * 来源
     */
    @Enumerated
    private WccFriendFormType fromType;

    /**
     * 关注时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date subscribeTime;

    /**
     * 最后对话时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date messageEndTime;

    /**
     * 邮件发送时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date sendEmailTime;
    
    /**
     * 公众平台
     */
    @ManyToOne
    private WccPlatformUser platformUser;
    
    
    /**
     * 二维码活动
     */
    @ManyToOne
    private WccQrCode wccqrcode;

    /**
     * 分组
     */
    @ManyToOne
    private WccGroup wgroup;
    
    /**
     * 组织架构
     */
    @ManyToOne
    private PubOrganization organization;
    
    /**
     * 中奖
     */
    @OneToMany(mappedBy="friend")
    private Set<WccUserLottery> userLotterys;
    @OneToMany
    private Set<WccFinancialUser> wccFinancialUser;
    
    

    
    /**
     * 群发
     */
    @ManyToMany(mappedBy="Friends",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    private Set<WccMassMessage> massMessages;
    
	public Set<WccFinancialUser> getWccFinancialUser() {
		return wccFinancialUser;
	}


	public void setWccFinancialUser(Set<WccFinancialUser> wccFinancialUser) {
		this.wccFinancialUser = wccFinancialUser;
	}


	public Set<WccMassMessage> getMassMessages() {
		return massMessages;
	}


	public void setMassMessages(Set<WccMassMessage> massMessages) {
		this.massMessages = massMessages;
	}


	public Set<WccUserLottery> getUserLotterys() {
		return userLotterys;
	}


	public void setUserLotterys(Set<WccUserLottery> userLotterys) {
		this.userLotterys = userLotterys;
	}


	public PubOrganization getOrganization() {
		return organization;
	}


	public void setOrganization(PubOrganization organization) {
		this.organization = organization;
	}


	public WccGroup getWgroup() {
		return wgroup;
	}


	public void setWgroup(WccGroup wgroup) {
		this.wgroup = wgroup;
	}


	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}
	

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	
	
	public WccQrCode getWccqrcode() {
		return wccqrcode;
	}


	public void setWccqrcode(WccQrCode wccqrcode) {
		this.wccqrcode = wccqrcode;
	}



	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nickName", "headImg", "sex", "area", "signature", "remarkName", "userInfo", "isValidated", "isDeleted", "insertTime", "fromType", "subscribeTime", "messageEndTime", "sendEmailTime");

	public static final EntityManager entityManager() {
        EntityManager em = new WccFriend().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccFriends(String ids) {
		if(ids.equals("") || ids == null){
	    	   return 0l;
	    }
        return entityManager().createQuery("SELECT COUNT(o) FROM WccFriend o inner join o.platformUser plat WHERE plat in("+ids+")", Long.class).getSingleResult();
    }

	public static List<WccFriend> findAllWccFriends() {
        return entityManager().createQuery("SELECT o FROM WccFriend o", WccFriend.class).getResultList();
    }
	
	public static List<WccFriend> findWccFriendByGroup(long gid) {
		return entityManager().createQuery("SELECT o FROM WccFriend o WHERE o.wgroup = "+gid, WccFriend.class).getResultList();
	}
	
	public static List<WccFriend> findAllWccFriends(String sortFieldName, String sortOrder,String ids) {
		if(ids.equals("") || ids == null){
	    	   return null;
	    }
		String jpaQuery = "SELECT o FROM WccFriend o inner join o.platformUser plat WHERE plat in ("+ids+")";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccFriend.class).getResultList();
    }
	
	/**
	 * 该方法已不用
	 * @param nickName
	 * @param platformUserId
	 * @param area
	 * @param gender
	 * @param from
	 * @param isUserinfo
	 * @param groupId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<WccFriend> findFriendByQuery(String nickName,String platformUserId,String area,
			String gender,String from,String isUserinfo,String groupId) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("select o from WccFriend o where 1=1 ");
		if(null!=nickName && !"".equals(nickName)){
			String name = URLEncoder.encode(nickName, "utf-8");
			qlString.append(" and o.nickName like '%"+name+"%'");
		}
		if(null!=platformUserId && !"".equals(platformUserId)){
			qlString.append(" and o.platformUser = "+platformUserId);
		}
//		if(null!=groupId && !"".equals(groupId)){
//			qlString.append(" and o.wgroup = "+groupId);
//		}
		if(null!=area && !"".equals(area)){
			qlString.append(" and o.area like '%"+area+"%'");
		}
		if(null!=gender && !"".equals(gender)){
			qlString.append(" and o.sex = "+gender);
		}
		if(null!=from && !"".equals(from)){
			qlString.append(" and o.fromType = "+from);
		}
		if(null!=isUserinfo && !"".equals(isUserinfo)){
			if(isUserinfo.equals("0")){
				qlString.append(" and o.userInfo = null");
			}
		}
		if(null!=groupId && !"".equals(groupId)){
			groupId = groupId.replace(",", "");
			qlString.append(" and o.wgroup = "+groupId);
		}
		List<WccFriend> friends = entityManager().createQuery(qlString.toString(),WccFriend.class).getResultList();
		return friends;
	}
	
	public static WccFriend findWccFriend(Long id) {
        if (id == null) return null;
        return entityManager().find(WccFriend.class, id);
    }

	public static List<WccFriend> findWccFriendEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccFriend o", WccFriend.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccFriend> findWccFriendEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder,String ids) {
       if(ids.equals("") || ids == null){
    	   return null;
       }
		String jpaQuery = "SELECT o FROM WccFriend o WHERE o.platformUser in ('"+ids+"')";
//		String jpaQuery = "SELECT o FROM WccFriend o inner join o.platformUser plat WHERE plat in ("+ids+")";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccFriend.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	 /**
		 * 根据openid查询粉丝
		 * add by hunter
		 * @param ids
		 * @return
		 */
		public static List<WccFriend> findWccFriendsByOpenId(String openId) {
	        if (openId == null||"".equals(openId)) return null;
	        String qlStr = "SELECT o FROM WccFriend o where o.openId = :openId";
			TypedQuery<WccFriend> query = entityManager().createQuery(qlStr, WccFriend.class);
			query.setParameter("openId", openId);
	        return query.getResultList();
	    }
		
		public static WccFriend findWccFriendByOpenId(String openId) {
			 if (openId == null||"".equals(openId)) return null;
			 	String qlStr = "SELECT o FROM WccFriend o where o.openId = :openId";
			 	WccFriend friend =null;
				TypedQuery<WccFriend> query = entityManager().createQuery(qlStr, WccFriend.class);
				query.setParameter("openId", openId);
				try {
					friend=query.getSingleResult();
				} catch (Exception e) {
					friend=null;
				}
		        return friend;
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
            WccFriend attached = WccFriend.findWccFriend(this.id);
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
    public WccFriend merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccFriend merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	/**
	 * 粉丝查询
	 * @param qm
	 * @return
	 */
	public static PageModel<WccFriend> getQueryFriends(QueryModel<WccFriend> qm,Set<WccPlatformUser> platSets){
		 Map parmMap = qm.getInputMap();
		 WccFriend model = qm.getEntity();
		PageModel<WccFriend> pageModel = new PageModel<WccFriend>();
		
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFriend o ");
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(platSets != null && platSets.size() > 0){
			sql.append("  inner join o.platformUser plat WHERE plat in :platformUsers AND o.isDeleted = 1");
			map.put("platformUsers",platSets);
		}else{
			sql.append(" WHERE 1 = 2 ");
		}
		
		if(null!=model.getNickName()&&!"".equals(model.getNickName())){
			sql.append(" AND o.nickName like :nickName");
			try {
				map.put("nickName", "%"+URLEncoder.encode(model.getNickName(), "utf-8")+"%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(null!=parmMap.get("sex")&&!"".equals(parmMap.get("sex"))){
			int nsex = Integer.parseInt(parmMap.get("sex")+"");
			sql.append(" AND o.sex = :sex");
			if(nsex==0){
				map.put("sex",WccFriendSex.男 );
			}
			if(nsex==1){
				map.put("sex",WccFriendSex.女 );
			}
			if(nsex==2){
				map.put("sex",WccFriendSex.未知 );
			}
		}
		if(null!=parmMap.get("platformType")&&!"".equals(parmMap.get("platformType"))){
			sql.append(" AND o.platformUser.platformType = :platformType");
			if(parmMap.get("platformType").equals("服务号")){
				map.put("platformType",PlatformType.服务号);
			}
			if(parmMap.get("platformType").equals("订阅号")){
				map.put("platformType",PlatformType.订阅号);
			}
		}
		/*if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
			sql.append(" AND o.platformUser = :platformUserId");
				map.put("platformUserId",parmMap.get("platformUserId"));
			
		}*/
		if(null!=parmMap.get("activityName")&&!"".equals(parmMap.get("activityName"))){
			sql.append(" AND o.wccqrcode.activityName = :activityName");
				map.put("activityName",parmMap.get("activityName"));
		}
		
		if(null!=parmMap.get("timeStamp")&&!"".equals(parmMap.get("timeStamp"))){
			sql.append(" AND o.messageEndTime >= :timeStamp");
			map.put("timeStamp", DateUtil.getDateTime(parmMap.get("timeStamp")+""));
		}
		
		if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
			sql.append(" AND o.platformUser = :platformUserId");
			map.put("platformUserId",WccPlatformUser.findWccPlatformUser(Long.parseLong(parmMap.get("platformUserId")+"")));
		}
		
		if(null!=parmMap.get("groupId")&&!"".equals(parmMap.get("groupId"))){
			sql.append(" AND o.wgroup = :groupId");
			map.put("groupId", WccGroup.findWccGroup(Long.parseLong(parmMap.get("groupId")+"")));
		}
		
		if(null!=parmMap.get("organization") && !"".equals(parmMap.get("organization")) && !"0".equals(parmMap.get("organization"))){
			sql.append(" AND o.organization = :organization");
			map.put("organization", PubOrganization.findPubOrganization(Long.parseLong(parmMap.get("organization")+"")));
		}
		
		if(null!=parmMap.get("isUserinfo")&&!"".equals(parmMap.get("isUserinfo"))){
			if(Long.parseLong(parmMap.get("isUserinfo")+"")==0){
				sql.append(" AND o.userInfo IS NULL");
			}else{
				sql.append(" AND o.userInfo IS NOT NULL");
			}
		}
		
		if(null!=parmMap.get("isunder")&&!"".equals(parmMap.get("isunder"))){
			if(Long.parseLong(parmMap.get("isunder")+"")==0){
				sql.append(" AND o.organization IS NOT NULL");
			}else{
				sql.append(" AND o.organization IS NULL");
			}
		}
		
		if(null!=parmMap.get("area")&&!"".equals(parmMap.get("area"))){
			sql.append(" AND o.area like :area");
			map.put("area", "%"+parmMap.get("area")+"%");
		}
		
		if(null!=parmMap.get("from")&&!"".equals(parmMap.get("from"))){
			int nsex = Integer.parseInt(parmMap.get("from")+"");
			sql.append(" AND o.fromType = :fromType");
			if(nsex==0){
				map.put("fromType",WccFriendFormType.直接获取 );
			}
			if(nsex==1){
				map.put("fromType",WccFriendFormType.关注 );
			}
			if(nsex==4){
				map.put("fromType",WccFriendFormType.活动二维码 );
			}
		}
		
		if(null!=parmMap.get("startTime")&&!"".equals(parmMap.get("startTime"))){
			sql.append(" AND o.subscribeTime >= :startTime");
			map.put("startTime", DateUtil.dateStringToTimestamp(parmMap.get("startTime")+""));
		}
		
		if(null!=parmMap.get("endTime")&&!"".equals(parmMap.get("endTime"))){
			sql.append(" AND o.subscribeTime <= :endTime");
			map.put("endTime", DateUtil.dateStringToTimestamp(parmMap.get("endTime")+""));
		}
		
		sql.append(" ORDER BY o.platformUser,o.messageEndTime DESC");
        TypedQuery<WccFriend> query = entityManager().createQuery(sql.toString(), WccFriend.class);
        
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
         List<WccFriend> list = query.getResultList();
         for(WccFriend friend : list){
        	 try {
				friend.setNickName(URLDecoder.decode(friend.getNickName(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
         }
         pageModel.setTotalCount(totalCount);
         pageModel.setDataList(query.getResultList());
         return pageModel;
	}
	
	
	public static List<WccFriend> getFriends(Map<String,String> parmMap){
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFriend o where 1=1 and o.isDeleted = 1 ");
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(null!=parmMap.get("nickName")&&!"".equals(parmMap.get("nickName"))){
			sql.append(" AND o.nickName like :nickName");
			try {
				map.put("nickName", "%"+URLEncoder.encode(parmMap.get("nickName"), "utf-8")+"%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(null!=parmMap.get("sex")&&!"".equals(parmMap.get("sex"))){
			int nsex = Integer.parseInt(parmMap.get("sex")+"");
			sql.append(" AND o.sex = :sex");
			if(nsex==0){
				map.put("sex",WccFriendSex.男 );
			}
			if(nsex==1){
				map.put("sex",WccFriendSex.女 );
			}
			if(nsex==2){
				map.put("sex",WccFriendSex.未知 );
			}
		}
		if(null!=parmMap.get("platformType")&&!"".equals(parmMap.get("platformType"))){
			sql.append(" AND o.platformUser.platformType = :platformType");
			if(parmMap.get("platformType").equals("服务号")){
				map.put("platformType",PlatformType.服务号);
			}
			if(parmMap.get("platformType").equals("订阅号")){
				map.put("platformType",PlatformType.订阅号);
			}
		}
		if(null!=parmMap.get("activityName")&&!"".equals(parmMap.get("activityName"))){
			sql.append(" AND o.wccqrcode.activityName = :activityName");
				map.put("activityName",parmMap.get("activityName"));
		}
		
		if(null!=parmMap.get("timeStamp")&&!"".equals(parmMap.get("timeStamp"))){
			sql.append(" AND o.messageEndTime >= :timeStamp");
			map.put("timeStamp", DateUtil.getDateTime(parmMap.get("timeStamp")+""));
		}
		
		if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
			sql.append(" AND o.platformUser = :platformUserId");
			map.put("platformUserId",WccPlatformUser.findWccPlatformUser(Long.parseLong(parmMap.get("platformUserId")+"")));
		}
		
		if(null!=parmMap.get("groupId")&&!"".equals(parmMap.get("groupId"))){
			sql.append(" AND o.wgroup = :groupId");
			map.put("groupId", WccGroup.findWccGroup(Long.parseLong(parmMap.get("groupId")+"")));
		}
	
		
		if(null!=parmMap.get("area")&&!"".equals(parmMap.get("area"))){
			sql.append(" AND o.area like :area");
			map.put("area", "%"+parmMap.get("area")+"%");
		}
		
		if(null!=parmMap.get("from")&&!"".equals(parmMap.get("from"))){
			int nsex = Integer.parseInt(parmMap.get("from")+"");
			sql.append(" AND o.fromType = :fromType");
			if(nsex==0){
				map.put("fromType",WccFriendFormType.直接获取 );
			}
			if(nsex==1){
				map.put("fromType",WccFriendFormType.关注 );
			}
		}
		
		if(null!=parmMap.get("startTime")&&!"".equals(parmMap.get("startTime"))){
			sql.append(" AND o.subscribeTime >= :startTime");
			map.put("startTime", DateUtil.dateStringToTimestamp(parmMap.get("startTime")+""));
		}
		
		if(null!=parmMap.get("endTime")&&!"".equals(parmMap.get("endTime"))){
			sql.append(" AND o.subscribeTime <= :endTime");
			map.put("endTime", DateUtil.dateStringToTimestamp(parmMap.get("endTime")+""));
		}
		
		sql.append(" ORDER BY o.messageEndTime DESC");
       TypedQuery<WccFriend> query = entityManager().createQuery(sql.toString(), WccFriend.class);
       
       for(String key:map.keySet()){
           query.setParameter(key, map.get(key));	
         }
       System.out.println(query.toString());
        List<WccFriend> list = query.getResultList();
        return list;
	}
	
	
	 /**
	  * 查询群发粉丝
	  * @param openId
	  * @return
	  */
		public static List<WccFriend> findMassWccFriends(Long platformUserId, Long gender,
				String group,String province,String city,WccGroup bgroup) {
	        StringBuffer sql = new StringBuffer().append("SELECT o FROM WccFriend o WHERE 1 = 1 AND o.isDeleted = 1 ");
	        
	        if(null!=gender){
				sql.append(" AND o.sex = :sex");
			}
			
			if(null != platformUserId){
				sql.append(" AND o.platformUser in :platformUserId");
			}
			
			if(null != province && !"".equals(province) && !"请选择".equals(province)){
				sql.append(" AND o.province like :province");
			}
			
			if(null != bgroup){
				sql.append(" AND o.wgroup != :bgroup");
			}
			
			if(null != city && !"".equals(city) && !"请选择".equals(city)&&!"null".equals(city)){
				sql.append(" and ");
				String[] citys = city.split(",");
				boolean cflag = false;
				for(String fcity : citys){
					if(!cflag){
						sql.append(" o.city like " +"'%"+fcity.trim()+"%'");
						cflag = true;
						continue;
					}
					sql.append(" or o.city like " +"'%"+fcity.trim()+"%'");
				}
			}
			
			if(null!=group&&!"".equals(group)&&!"请选择".equals(group)&&!"null".equals(group)){
				String[] groups = group.split(",");
				boolean gflag = false;
				for (String fgroup : groups) {
					if(!gflag){
						sql.append(" AND o.wgroup = "+fgroup.trim());
						gflag = true;
						continue;
					}
					sql.append(" or o.wgroup = "+fgroup.trim());
				}
			}
			
			
			
			TypedQuery<WccFriend> query = entityManager().createQuery(sql.toString(), WccFriend.class);
			if(null != platformUserId){
				query.setParameter("platformUserId", WccPlatformUser.findWccPlatformUser(platformUserId));
			}
			if(null != gender){
				if(gender==0){
					query.setParameter("sex",WccFriendSex.男 );
				}
				if(gender==1){
					query.setParameter("sex",WccFriendSex.女 );
				}
				if(gender==2){
					query.setParameter("sex",WccFriendSex.未知 );
				}
			}
			if(null != province && !"".equals(province) && !"请选择".equals(province)){
				query.setParameter("province",province);
			}
			if(null != bgroup){
				query.setParameter("bgroup",bgroup);
			}
			return query.getResultList();
	    }
		
		public  static List<WccFriend> findMassWccFriends2(String platformUserId){
			String hql="From WccFriend where 1=1 ";
			if(null!=platformUserId && !platformUserId.equals("")){
				hql+=" and platformUser in ("+platformUserId+")";
			}
			TypedQuery<WccFriend> query = entityManager().createQuery(hql, WccFriend.class);
			return query.getResultList();
		}
		
		public  static List<WccFriend> findMassWccFriends2(Long platformUserId){
			String hql="From WccFriend where 1=1 ";
			if(null!=platformUserId && !platformUserId.equals("")){
				hql+=" and platformUser = "+platformUserId;
			}
			TypedQuery<WccFriend> query = entityManager().createQuery(hql, WccFriend.class);
			return query.getResultList();
		}
		
		public static List<WccFriend> findMassWccFriends2(String platformUserId, Long gender,
				String group,String province,String city,WccGroup bgroup) {
	        StringBuffer sql = new StringBuffer().append("SELECT o FROM WccFriend o WHERE 1 = 1 AND o.isDeleted = 1 ");
	        
	        if(null!=gender){
				sql.append(" AND o.sex = :sex");
			}
			
			if(null != platformUserId){
				sql.append(" AND o.platformUser in ( "+platformUserId+" )");
			}
			
			if(null != province && !"".equals(province) && !"请选择".equals(province)){
				sql.append(" AND o.province like :province");
			}
			
			if(null != bgroup){
				sql.append(" AND o.wgroup != :bgroup");
			}
			
			if(null != city && !"".equals(city) && !"请选择".equals(city)&&!"null".equals(city)){
				sql.append(" and ");
				String[] citys = city.split(",");
				boolean cflag = false;
				for(String fcity : citys){
					if(!cflag){
						sql.append(" o.city like " +"'%"+fcity.trim()+"%'");
						cflag = true;
						continue;
					}
					sql.append(" or o.city like " +"'%"+fcity.trim()+"%'");
				}
			}
			
			if(null!=group&&!"".equals(group)&&!"请选择".equals(group)&&!"null".equals(group)){
				String[] groups = group.split(",");
				boolean gflag = false;
				for (String fgroup : groups) {
					if(!gflag){
						sql.append(" AND o.wgroup = "+fgroup.trim());
						gflag = true;
						continue;
					}
					sql.append(" or o.wgroup = "+fgroup.trim());
				}
			}
			
			
			
			TypedQuery<WccFriend> query = entityManager().createQuery(sql.toString(), WccFriend.class);
			if(null != platformUserId){
			//	query.setParameter("platformUserId", WccPlatformUser.findWccPlatformUserById2(platformUserId));
			}
			if(null != gender){
				if(gender==0){
					query.setParameter("sex",WccFriendSex.男 );
				}
				if(gender==1){
					query.setParameter("sex",WccFriendSex.女 );
				}
				if(gender==2){
					query.setParameter("sex",WccFriendSex.未知 );
				}
			}
			if(null != province && !"".equals(province) && !"请选择".equals(province)){
				query.setParameter("province",province);
			}
			if(null != bgroup){
				query.setParameter("bgroup",bgroup);
			}
			return query.getResultList();
	    }
	
	/**
	 * 粉丝查询
	 * @param qm
	 * @return
	 */
	public static PageModel<WccFriend> getMassFriends(QueryModel<WccFriend> qm,Set<WccPlatformUser> platSets){
		 Map parmMap = qm.getInputMap();
		 WccFriend model = qm.getEntity();
		PageModel<WccFriend> pageModel = new PageModel<WccFriend>();
		
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFriend o WHERE 1 = 1 AND o.isDeleted = 1");
		Map<String,Object> map = new HashMap<String, Object>();

		if(null!=parmMap.get("sex")&&!"".equals(parmMap.get("sex"))){
			int nsex = Integer.parseInt(parmMap.get("sex")+"");
			sql.append(" AND o.sex = :sex");
			if(nsex==0){
				map.put("sex",WccFriendSex.男 );
			}
			if(nsex==1){
				map.put("sex",WccFriendSex.女 );
			}
			if(nsex==2){
				map.put("sex",WccFriendSex.未知 );
			}
		}
		
		if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
			sql.append(" AND o.platformUser = :platformUserId");
			map.put("platformUserId",WccPlatformUser.findWccPlatformUser(Long.parseLong(parmMap.get("platformUserId")+"")));
		}
		
		if(null!=parmMap.get("groupId")&&!"".equals(parmMap.get("groupId"))){
			sql.append(" AND o.wgroup = :groupId");
			map.put("groupId", WccGroup.findWccGroup(Long.parseLong(parmMap.get("groupId")+"")));
		}
		
        TypedQuery<WccFriend> query = entityManager().createQuery(sql.toString(), WccFriend.class);
        
        for(String key:map.keySet()){
            query.setParameter(key, map.get(key));	
          }
        System.out.println(query.toString());
           int totalCount = 0;
 
         List<WccFriend> list = query.getResultList();
         for(WccFriend friend : list){
        	 try {
				friend.setNickName(URLDecoder.decode(friend.getNickName(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
         }
         pageModel.setTotalCount(totalCount);
         pageModel.setDataList(query.getResultList());
         return pageModel;
	}
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getNickName() {
        return this.nickName;
    }

	public void setNickName(String nickName) {
        this.nickName = nickName;
    }

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImg() {
        return this.headImg;
    }

	public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

	public WccFriendSex getSex() {
        return this.sex;
    }

	public void setSex(WccFriendSex sex) {
        this.sex = sex;
    }

	public String getArea() {
        return this.area;
    }

	public void setArea(String area) {
        this.area = area;
    }

	public String getSignature() {
        return this.signature;
    }

	public void setSignature(String signature) {
        this.signature = signature;
    }

	public String getRemarkName() {
        return this.remarkName;
    }

	public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

	public String getUserInfo() {
        return this.userInfo;
    }

	public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

	public Boolean getIsValidated() {
        return this.isValidated;
    }

	public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
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

	public WccFriendFormType getFromType() {
        return this.fromType;
    }

	public void setFromType(WccFriendFormType fromType) {
        this.fromType = fromType;
    }

	public Date getSubscribeTime() {
        return this.subscribeTime;
    }

	public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

	public Date getMessageEndTime() {
        return this.messageEndTime;
    }

	public void setMessageEndTime(Date messageEndTime) {
        this.messageEndTime = messageEndTime;
    }

	public Date getSendEmailTime() {
        return this.sendEmailTime;
    }

	public void setSendEmailTime(Date sendEmailTime) {
        this.sendEmailTime = sendEmailTime;
    }

	

	public static Long countWccFriendsByFiled(String nickName,
			String platformUserId, String area, String gender, String from,
			String isUserinfo, String groupId) throws UnsupportedEncodingException {
		StringBuffer qlString = new StringBuffer();
		qlString.append("select o from WccFriend o where 1=1 ");
		if(null!=nickName && !"".equals(nickName)){
			String name = URLEncoder.encode(nickName, "utf-8");
			qlString.append(" and o.nickName like '%"+name+"%'");
		}
		if(null!=platformUserId && !"".equals(platformUserId)){
			qlString.append(" and o.platformUser = "+platformUserId);
		}
//		if(null!=groupId && !"".equals(groupId)){
//			qlString.append(" and o.wgroup = "+groupId);
//		}
		if(null!=area && !"".equals(area)){
			qlString.append(" and o.area like '%"+area+"%'");
		}
		if(null!=gender && !"".equals(gender)){
			qlString.append(" and o.sex = "+gender);
		}
		if(null!=from && !"".equals(from)){
			qlString.append(" and o.fromType = "+from);
		}
		if(null!=isUserinfo && !"".equals(isUserinfo)){
			if(isUserinfo.equals("0")){
				qlString.append(" and o.userInfo = null");
			}
		}
		if(null!=groupId && !"".equals(groupId)){
			groupId = groupId.replace(",", "");
			qlString.append(" and o.wgroup = "+groupId);
		}
		return entityManager().createQuery(qlString.toString(),Long.class).getSingleResult();
	}
	
	public static List<WccFriend> findFriendByNickName(String nickName,String platformUserId,String groupId) throws UnsupportedEncodingException {
		StringBuffer qlString = new StringBuffer();
		qlString.append("select o from WccFriend o where 1=1 ");
		if(null!=nickName && !"".equals(nickName)){
			String name = URLEncoder.encode(nickName, "utf-8");
			qlString.append(" and o.nickName = '"+name+"'");
		}
		if(null!=platformUserId && !"".equals(platformUserId)){
			qlString.append(" and o.platformUser = "+platformUserId);
		}
		if(null!=groupId && !"".equals(groupId)){
			qlString.append(" and o.wgroup = "+groupId);
		}
		return entityManager().createQuery(qlString.toString(),WccFriend.class).getResultList();
	}

	/**
	 *	根据平台 日期 来源类型
	 * 
	 * @param platform
	 * @param date
	 * @param type
	 * @return
	 */
	public static Integer findFriendsByPlatFromAndDateAndFromType(
			WccPlatformUser platform, String date, WccFriendFormType type) {
        Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(WccFriend.class);
        if (null != platform) {
            criteria.add(Restrictions.eq("platformUser", platform));
        }
        if (null != date && !"".equals(date)) {
            criteria.add(Restrictions.between("insertTime", DateUtil.formateDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),DateUtil.formateDate(date+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        if(type!=null){
        	criteria.add(Restrictions.eq("fromType", type));
        }
        List<ReportFriendsSum> list = criteria.list();
        return list.size();
    }
	public static Integer findFriendsByPlatFromAndDateAndFromType2(
			WccPlatformUser platform, String date, WccFriendFormType type) {
		StringBuffer qlString = new StringBuffer();
		qlString.append("select o from WccFriend o where 1=1 ");
		if(null!=platform){
			qlString.append(" and o.platformUser = '"+platform.getId()+"'");
		}
		if(null!=date && !"".equals(date)){
			qlString.append(" and o.subscribeTime >= '"+date+" 00:00:00'");
			qlString.append(" and o.subscribeTime <= '"+date+" 23:59:59'");
		}
		if(null!=type){
			qlString.append(" and o.fromType = "+type.ordinal());
		}
		List list = entityManager().createQuery(qlString.toString(),WccFriend.class).getResultList();
		return list.size();
	}

	// **群发获取发送粉丝列表
	public static List<WccFriend> getMassFriend(MassConditionEntity massConditoion) {
        StringBuffer sql = new StringBuffer().append("SELECT o FROM WccFriend o WHERE 1 = 1 AND o.isDeleted = 1 ");
        
        if(null!=massConditoion.getGender()){
			sql.append(" AND o.sex = '"+massConditoion.getGender()+"'");
		}
		
		if(null != massConditoion.getPlatformer()){
			sql.append(" AND o.platformUser ='"+massConditoion.getPlatformer().getId()+"'");
		}
		
		if(null != massConditoion.getProvince() && !"".equals(massConditoion.getProvince()) && !"全部".equals(massConditoion.getProvince())){
			sql.append(" AND o.province like '%"+massConditoion.getProvince()+"%'");
		}
		if(null != massConditoion.getCity() && !"".equals(massConditoion.getCity()) && !"全部".equals(massConditoion.getCity())&&!"null".equals(massConditoion.getCity())){
					sql.append(" and o.city like " +"'%"+ massConditoion.getCity().trim()+"%'");
		}
		
		if(null!=massConditoion.getGroups()&&!"".equals(massConditoion.getGroups())&&!"全部".equals(massConditoion.getGroups())&&!"null".equals(massConditoion.getGroups())){
			String[] groups = massConditoion.getGroups().split(",");
			boolean gflag = false;
			for (String fgroup : groups) {
				if(!gflag){
					sql.append(" AND o.wgroup = '"+fgroup.trim()+"'");
					gflag = true;
					continue;
				}
				sql.append(" or o.wgroup = '"+fgroup.trim()+"'");
			}
		}
		
		 if(null!=massConditoion.getFromType()&&!"".equals(massConditoion.getFromType()) ){
				sql.append(" AND o.fromType = '"+massConditoion.getFromType()+"'");
		 }
		 List<WccFriend>  list=entityManager().createQuery(sql.toString(), WccFriend.class).getResultList();
		return list;
	}
	
	public static Long getFriendNum(Long id){
    	String sql="SELECT COUNT(o) FROM WccFriend o where o.platformUser = " +id;
    	Long count =entityManager().createQuery(sql, Long.class).getSingleResult();
    	return count;
 }
	
}

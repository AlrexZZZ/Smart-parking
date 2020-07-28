package com.nineclient.model;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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

import com.nineclient.constant.WccFriendSex;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;



@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccRecordMsg {
   /**
   * 公司Id
   */
    private Long companyId;	
	/**
	 * 粉丝公共账号
	 */
	private String platFormId;
	/**
	 * 粉丝公共账号
	 */
	private String platFormAccount;
	
	/**粉丝昵称
     */
	
    @Size(max = 500)
    private String nickName;
    
    /**
     * 性别
     */
    @Enumerated
    private WccFriendSex sex;
    
    /**城市
     */
    @Size(max = 200)
    private String province;
   
    /**
     * 用户分组
     */
    private String friendGroup;
  
    private  Long recordFriendId;
    
    private int msgFrom;
    
    @Transient
    private WccMaterials material;
    
    @Transient
    private List<WccMaterials> materials;
    
    
    
	public Long getCompanyId() {
		return companyId;
	}

	public WccFriendSex getSex() {
		return sex;
	}

	public void setSex(WccFriendSex sex) {
		this.sex = sex;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public WccMaterials getMaterial() {
		return material;
	}

	public void setMaterial(WccMaterials material) {
		this.material = material;
	}

	public List<WccMaterials> getMaterials() {
		return materials;
	}

	public void setMaterials(List<WccMaterials> materials) {
		this.materials = materials;
	}

	public int getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(int msgFrom) {
		this.msgFrom = msgFrom;
	}

	public Long getRecordFriendId() {
		return recordFriendId;
	}

	public void setRecordFriendId(Long recordFriendId) {
		this.recordFriendId = recordFriendId;
	}

	/**回复记录内容
     */
    @Size(max = 4000)
    private String recordContent;
//自动回复内容
    @Size(max = 4000)
    private String toUserRecord;

	/**
     */
    @Size(max = 4000)
    private String remarkName;

    /**
     */
    @Size(max = 4000)
    private String userInfo;

    /**
     */
    private Boolean isValidated;

    /**
     */
    private Boolean isDeleted;
    
    private String openId;

    /**回复时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date insertTime;

    /**自动回复时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date toUserInsertTime;
	public String getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(String platFormId) {
		this.platFormId = platFormId;
	}
    public String getPlatFormAccount() {
		return platFormAccount;
	}
    public String getToUserRecord() {
		return toUserRecord;
	}

	public void setToUserRecord(String toUserRecord) {
		this.toUserRecord = toUserRecord;
	}
	public void setPlatFormAccount(String platFormAccount) {
		this.platFormAccount = platFormAccount;
	}
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getFriendGroup() {
		return friendGroup;
	}

	public void setFriendGroup(String friendGroup) {
		this.friendGroup = friendGroup;
	}

	public String getRecordContent() {
		return recordContent;
	}
   
	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}
	public Date getToUserInsertTime() {
		return toUserInsertTime;
	}

	public void setToUserInsertTime(Date toUserInsertTime) {
		this.toUserInsertTime = toUserInsertTime;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nickName", "headImg", "sex", "area", "signature", "remarkName", "userInfo", "isValidated", "isDeleted", "insertTime", "fromType", "subscribeTime", "messageEndTime", "sendEmailTime");

	public static final EntityManager entityManager() {
        EntityManager em = new WccRecordMsg().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccRecordMsg() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccRecordMsg o", Long.class).getSingleResult();
    }
	public  static long findRcordCountByQuery(String nickName,String platformUserId,String area,String msgContent,String begingTime,String endTime,
			String groupId,Long companyId) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o.openId,o.insertTime,plat.account,wfriend.nickName,wfriend.province,wwgroup.name,o.companyId FROM WccRecordMsg o,WccFriend wfriend,WccPlatformUser plat,WccGroup wwgroup ");
		qlString.append(" where 1=1 ");
		//关联粉丝表
		qlString.append("  and o.recordFriendId = wfriend.id ");
		//关联公共账号表
		qlString.append("  and wfriend.platformUser = plat.id ");
		//关联粉丝分组表
		qlString.append("  and wfriend.wgroup = wwgroup.id ");
		if(null!=nickName && !"".equals(nickName)){
			String name = URLEncoder.encode(nickName, "utf-8");
			qlString.append(" and wfriend.nickName like '%"+name+"%'");
		}
		if(null!=platformUserId && !"".equals(platformUserId)){
			if(platformUserId.contains(",")){
				String[] platStr =  platformUserId.split(",");
				qlString.append(" AND (");
				for(String plat :platStr){
					qlString.append(" wfriend.platformUser = "+plat+" OR ");
				}
				qlString.delete(qlString.length()-3, qlString.length());
				qlString.append(" )");
			}else{
				qlString.append(" and wfriend.platformUser = "+platformUserId);
			}
		}
		if(null!=msgContent && !"".equals(msgContent)){
			qlString.append(" and o.recordContent like '%"+msgContent+"%'");
		}
		
		if(null!=area && !"".equals(area)){
			qlString.append(" and wfriend.province like '%"+area+"%'");
		}

		if(null!=groupId && !"".equals(groupId)){
			groupId = groupId.replace(",", "");
			qlString.append(" and wfriend.wgroup = "+groupId);
		}
		if(null!=companyId && !"".equals(companyId)){
			qlString.append(" and o.companyId = "+companyId);
		}
		
        if (null != begingTime && !"".equals(begingTime)) {
            
        	qlString.append(" and o.insertTime >= '"+begingTime+"'");
           }
           if (null != endTime && !"".equals(endTime)) {
        	qlString.append(" and o.insertTime <= '"+endTime+"'");
            }
     
	    qlString.append(" GROUP BY o.openId, YEAR(o.insertTime),MONTH(o.insertTime),DAY(o.insertTime),plat.account,wfriend.nickName,wfriend.province,wwgroup.name,o.companyId  ");
        qlString.append("ORDER BY o.insertTime DESC  ");
		List<WccRecordMsg> reclist = new ArrayList<WccRecordMsg>(); 
		List<Object[]> objList = entityManager().createQuery(qlString.toString()).getResultList();
		if(objList.size() > 0){
			for(Object[] e:objList){
				WccRecordMsg msg = new WccRecordMsg();
				msg.setOpenId(e[0]+"");
				msg.setInsertTime((Date)e[1]);
				msg.setPlatFormAccount(e[2]+"");
				msg.setNickName(e[3]+"");
				msg.setProvince(e[4]+"");
				msg.setFriendGroup(e[5]+"");
				msg.setCompanyId(Long.parseLong(e[6]+""));
				reclist.add(msg);
			}
		}
 		long count =Long.parseLong(reclist.size()+"");  
		return count;
	}
	public static List<WccRecordMsg> findAllWccRecordMsg() {
        return entityManager().createQuery("SELECT o FROM WccRecordMsg o", WccRecordMsg.class).getResultList();
    }

	public static List<WccRecordMsg> findAllWccRecordMsg(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccRecordMsg o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccRecordMsg.class).getResultList();
    }

	public static WccRecordMsg findWccRecordMsg(Long id) {
        if (id == null) return null;
        return entityManager().find(WccRecordMsg.class, id);
    }
	public static String toJsonArray(Collection<WccRecordMsg> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
	public static String toJsonArray(Collection<WccRecordMsg> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<WccRecordMsg> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<WccRecordMsg>>()
        .use("values", WccRecordMsg.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	public static List<WccRecordMsg> findWccRecordMsgEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccRecordMsg o", WccRecordMsg.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccRecordMsg> findWccRecordMsgByToUser(Long id) {
		return entityManager().createQuery("SELECT o FROM WccRecordMsg o WHERE o.msgFrom = 4 and o.toUserRecord = "+id, WccRecordMsg.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<WccRecordMsg> findRcordByQuery(int firstResult, int maxResults,
			String nickName,String platformUserId,String area,String msgContent,String begingTime,String endTime,
			String groupId,Long companyId) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		
	qlString.append("SELECT o.openId,o.insertTime,plat.account,wfriend.nickName,wfriend.province,wwgroup.name,o.companyId FROM WccRecordMsg o,WccFriend wfriend,WccPlatformUser plat,WccGroup wwgroup ");
		qlString.append(" where 1=1 ");
		//关联粉丝表
		qlString.append("  and o.recordFriendId = wfriend.id ");
		//关联公共账号表
		qlString.append("  and wfriend.platformUser = plat.id ");
		//关联粉丝分组表
		qlString.append("  and wfriend.wgroup = wwgroup.id ");
		if(null!=nickName && !"".equals(nickName)){
			String name = URLEncoder.encode(nickName, "utf-8");
			qlString.append(" and wfriend.nickName like '%"+name+"%'");
		}
		if(null!=platformUserId && !"".equals(platformUserId)){
			if(platformUserId.contains(",")){
				String[] platStr =  platformUserId.split(",");
				qlString.append(" AND (");
				for(String plat :platStr){
					qlString.append(" wfriend.platformUser = "+plat+" OR ");
				}
				qlString.delete(qlString.length()-3, qlString.length());
				qlString.append(" )");
			}else{
				qlString.append(" and wfriend.platformUser = "+platformUserId);
			}
		}
		if(null!=msgContent && !"".equals(msgContent)){
			qlString.append(" and o.recordContent like '%"+msgContent+"%'");
		}
		
		if(null!=area && !"".equals(area)){
			qlString.append(" and wfriend.province like '%"+area+"%'");
		}

		if(null!=groupId && !"".equals(groupId)){
			groupId = groupId.replace(",", "");
			qlString.append(" and wfriend.wgroup = "+groupId);
		}
		if(null!=companyId && !"".equals(companyId)){
			qlString.append(" and o.companyId = "+companyId);
		}
		
        if (null != begingTime && !"".equals(begingTime)) {
            
        	qlString.append(" and o.insertTime >= '"+begingTime+" 00:00:00'");
           }
           if (null != endTime && !"".equals(endTime)) {
        	qlString.append(" and o.insertTime <= '"+endTime+" 23:59:59'");
            }
		//// 另一种写法  
//		CriteriaBuilder cb = entityManager().getCriteriaBuilder();
//		CriteriaQuery<WccRecordMsg> cq = cb.createQuery(WccRecordMsg.class);
//		Root<WccRecordMsg> root = cq.from(WccRecordMsg.class);
//		Join<WccRecordMsg, WccFriend> join1 = root.join("recordFriendId",JoinType.LEFT);
//		/**
//		 * 增加附表中的条件
//		 */
//		cq.select(root);
           
     ////      
	    qlString.append(" GROUP BY o.openId, YEAR(o.insertTime),MONTH(o.insertTime),DAY(o.insertTime),plat.account,wfriend.nickName,wfriend.province,wwgroup.name,o.companyId  ");
        qlString.append("ORDER BY o.insertTime DESC  ");
        System.out.println("================"+qlString.toString());
		List<WccRecordMsg> reclist = new ArrayList<WccRecordMsg>(); 
		List<Object[]> objList = entityManager().createQuery(qlString.toString()).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		if(objList.size() > 0){
			for(Object[] e:objList){
				WccRecordMsg msg = new WccRecordMsg();
				msg.setOpenId(e[0]+"");
				msg.setInsertTime((Date)e[1]);
				msg.setPlatFormAccount(e[2]+"");
				msg.setNickName(e[3]+"");
				msg.setProvince(e[4]+"");
				msg.setFriendGroup(e[5]+"");
				msg.setCompanyId(Long.parseLong(e[6]+""));
				reclist.add(msg);
			}
		}
		
		return reclist;
		
	}
	
	///
	public static List<WccRecordMsg> findRcordByQuery2(int firstResult, int maxResults,
			String nickName,String platformUserId,String area,String msgContent,String begingTime,String endTime,
			String groupId,Long companyId) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o FROM WccRecordMsg o where 1=1 ");
		if(null!=nickName && !"".equals(nickName)){
			String name = URLEncoder.encode(nickName, "utf-8");
			qlString.append(" and o.nickName like '%"+name+"%'");
		}
		if(null!=platformUserId && !"".equals(platformUserId)){
			qlString.append(" and o.platFormId = "+platformUserId);
		}
		if(null!=companyId && !"".equals(companyId)){
			qlString.append(" and o.companyId = "+companyId);
		}
		if(null!=msgContent && !"".equals(msgContent)){
			qlString.append(" and o.recordContent like '%"+msgContent+"%'");
		}
		
		if(null!=area && !"".equals(area)){
			qlString.append(" and o.province like '%"+area+"%'");
		}

		if(null!=groupId && !"".equals(groupId)){
			groupId = groupId.replace(",", "");
			qlString.append(" and o.friendGroup = "+groupId);
		}
		
		
        if (null != begingTime && !"".equals(begingTime)) {
            
           qlString.append(" and o.insertTime >= '"+begingTime+"'");
           }
           if (null != endTime && !"".equals(endTime)) {
           qlString.append(" and o.insertTime <= '"+endTime+"'");
            }
           qlString.append(" GROUP BY o.openId, YEAR(o.insertTime),MONTH(o.insertTime),DAY(o.insertTime)  ");
           qlString.append("ORDER BY o.insertTime DESC  ");
		List<WccRecordMsg> reclist = entityManager().createQuery(qlString.toString(),WccRecordMsg.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		return reclist;
	}
	//查询粉丝互动本地消息
	public static List<WccRecordMsg> findNativeRcords(String openId,String strTime,Long companyId) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT o FROM WccRecordMsg o where 1=1 ");
	
		if(null!=openId && !"".equals(openId)){
			qlString.append(" and o.openId = '"+openId+"'");
		}
//		if(null!=companyId && !"".equals(companyId)){
//			qlString.append(" and o.companyId = "+companyId);
//		}
		
        if (null != strTime && !"".equals(strTime)) {
            
           qlString.append(" and o.insertTime >= '"+strTime+" 00:00:00'");
           }
           if (null != strTime && !"".equals(strTime)) {
           qlString.append(" and o.insertTime <= '"+strTime+" 23:59:59'");
            }
           qlString.append("ORDER BY o.insertTime DESC,o.id DESC  ");
		List<WccRecordMsg> reclist = entityManager().createQuery(qlString.toString(),WccRecordMsg.class).getResultList();
		return reclist;
	}
	public static List<WccRecordMsg> findWccRecordMsgEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccRecordMsg o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccRecordMsg.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccRecordMsg attached = WccRecordMsg.findWccRecordMsg(this.id);
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
    public WccRecordMsg merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccRecordMsg merged = this.entityManager.merge(this);
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
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static PageModel<WccRecordMsg> getQueryRecord(QueryModel<WccRecordMsg> qm){
		PageModel<WccRecordMsg> pageModel = new PageModel<WccRecordMsg>();
		Map<String, Object> param = qm.getInputMap();
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccRecordMsg o WHERE o.openId = '"+param.get("openId")+"'" + "ORDER BY o.toUserInsertTime DESC");
		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccRecordMsg> query = entityManager().createQuery(sql.toString(), WccRecordMsg.class);
     
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
      List<WccRecordMsg> list = query.getResultList();
      pageModel.setTotalCount(totalCount);
      pageModel.setDataList(query.getResultList());
      return pageModel;
	}
	
	
	/**
	 * 查询公众号回复报表
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public static List<WccRecordMsg> findReplyReportByDateBetween(String platform, String startTime, String endTime, int firstResult, int maxResults) {
	      String jpaQuery = "SELECT o FROM WccRecordMsg o where 1=1 and  o.recordContent like '#%'";
	      if(null!=platform){
	    	  jpaQuery+=" and o. platFormId='"+platform+"'";
	      }if(null!=startTime){
	    	  jpaQuery+=" and  o. insertTime >='"+startTime+"00:00:00'";
	      }if(null!=endTime){
	    	  jpaQuery+=" and o. insertTime <='"+endTime+"23:59:59'";
	      }
	      jpaQuery+=" order by o. insertTime  desc";
	      List<WccRecordMsg> list=new ArrayList<WccRecordMsg>();
	      if(firstResult!=-1&&maxResults!=-1){
	    	  list=entityManager().createQuery(jpaQuery, WccRecordMsg.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	        }else{
	        	 list=entityManager().createQuery(jpaQuery, WccRecordMsg.class).getResultList();
	        }
	       return list;

    }
	
}

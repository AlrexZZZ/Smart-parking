package com.nineclient.model;

import java.util.ArrayList;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.PlatformType;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMassMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	/**
	 * 添加时间
	 */
	private Date insertTime;
	
	/**
	 * 是否删除 true：删除 	false：未删除
	 */
	private boolean isDelete;
	
	/**
	 * 发送状态 1:待审核,2:审核通过,3：审核失败,4:待发送,5: 发送成功,6:发送失败
	 */
	private int state;
	
	/**
	 * 性别 1：男 2：女 3：未知
	 */
	private int sex;
	
	/**
	 * 类型 0:文本,1:图片,2:语音,3:视频,4:图文,5:音乐,6:多图文,7:表情
	 */
	private int type;
	
	/**
	 * 文本内容
	 */
	private String content;
	
	/**
	 * 多媒体ID
	 */
	private String mediaId;
	
	/**
	 * 群发的消息ID
	 */
	private String msgId;
	
	/**
	 * 群发粉丝总数
	 */
	private int totleCount;
	
	/**
	 * 实际群发粉丝数
	 */
	private int filterCount;
	
	/**
	 * 发送成功粉丝数
	 */
	private int sendCount;
	
	/**
	 * 发送失败粉丝数
	 */
	private int errorCount;
	
	/**
	 * 版本
	 */
	private int version;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 路径
	 */
	private String path;
	//预计发送时间
	private Date expectedTime;
	//群发区域
	@Size(max=4000)
	private String regionarea;
	@Size(max=4000)
	private String label;
	@Size(max=4000)
	private String activitysn;
	@Size(max=4000)
	private String auditRemark;
	private String groupStr;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
     */
	@ManyToOne
	private UmpCompany company;
	
	public UmpCompany getCompany() {
		return company;
	}

	public void setCompany(UmpCompany company) {
		this.company = company;
	}

	/**
	 * 公众平台
	 */
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private WccPlatformUser platformUser;
	//组织架构
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private PubOrganization pubOrganization;
	/**
	 * 粉丝分组
	 */
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<WccGroup> groups;

	
	/**
	 * 素材
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private WccMaterials material;
	
	/**
	 * 粉丝
	 */
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.EAGER)
	private Set<WccFriend> Friends;

	public Set<WccFriend> getFriends() {
		return Friends;
	}

	public void setFriends(Set<WccFriend> friends) {
		Friends = friends;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getTotleCount() {
		return totleCount;
	}

	public void setTotleCount(int totleCount) {
		this.totleCount = totleCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WccPlatformUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(WccPlatformUser platformUser) {
		this.platformUser = platformUser;
	}

	public Set<WccGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<WccGroup> groups) {
		this.groups = groups;
	}


	public WccMaterials getMaterial() {
		return material;
	}

	public void setMaterial(WccMaterials material) {
		this.material = material;
	}


	public Date getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(Date expectedTime) {
		this.expectedTime = expectedTime;
	}

	public String getRegionarea() {
		return regionarea;
	}

	public void setRegionarea(String regionarea) {
		this.regionarea = regionarea;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getActivitysn() {
		return activitysn;
	}

	public void setActivitysn(String activitysn) {
		this.activitysn = activitysn;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getGroupStr() {
		return groupStr;
	}

	public void setGroupStr(String groupStr) {
		this.groupStr = groupStr;
	}

	public PubOrganization getPubOrganization() {
		return pubOrganization;
	}

	public void setPubOrganization(PubOrganization pubOrganization) {
		this.pubOrganization = pubOrganization;
	}

	@PersistenceContext
	transient EntityManager entityManager;
	
	public static final EntityManager entityManager() {
        EntityManager em = new WccMassMessage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
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
            WccMassMessage attached = WccMassMessage.findWccMassMessage(this.id);
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
    public WccMassMessage merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccMassMessage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	public static WccMassMessage findWccMassMessage(Long id) {
        if (id == null) return null;
        return entityManager().find(WccMassMessage.class, id);
    }
	
	public static List<WccMassMessage> findWccMassMessages(){
		return entityManager().createQuery("SELECT o FROM WccMassMessage o WHERE o.isDelete = 0", WccMassMessage.class).getResultList();
	}
	
	public static List<WccMassMessage> findWccMassMessagesByMaterial(Long id){
		return entityManager().createQuery("SELECT o FROM WccMassMessage o WHERE o.isDelete = 0 AND o.material.id="+id , WccMassMessage.class).getResultList();
	}
	
	public static List<WccMassMessage> findWccMassMessagesByState(int state){
		return entityManager().createQuery("SELECT o FROM WccMassMessage o WHERE o.isDelete = 0 AND o.state = "+state, WccMassMessage.class).getResultList();
	}
	
	public static WccMassMessage findWccMassMessageByMsgId(String msgId){
		WccMassMessage massMsg = null;
		List<WccMassMessage> massMsgs = entityManager().createQuery("SELECT o FROM WccMassMessage o WHERE o.isDelete = 0 and msgId = '"+msgId+"'", WccMassMessage.class).getResultList();
		if(null!=massMsgs && massMsgs.size()>0){
			massMsg = massMsgs.get(0);
		}
		return massMsg;
	}
	
	public static PageModel<WccMassMessage> getMassMessage(
			QueryModel<WccMassMessage> qm,Long companyId) {
		Map parmMap = qm.getInputMap();
		PageModel<WccMassMessage> pageModel = new PageModel<WccMassMessage>();
		
		StringBuffer sql = new StringBuffer(
				" SELECT o FROM WccMassMessage o WHERE o.isDelete = 0 AND o.company = "+companyId+"");
		
		if(null!=parmMap.get("platType")&&!"".equals(parmMap.get("platType"))){
			if("订阅号".equals(parmMap.get("platType"))){
				sql.append(" and o.platformUser.platformType=0");
			}
			else{
				sql.append(" and o.platformUser.platformType=1");
			}
			
		}if(null!=parmMap.get("platformUserId")&&!"".equals(parmMap.get("platformUserId"))){
			sql.append(" and o.platformUser="+parmMap.get("platformUserId"));
		}else{
			sql.append(" and o.platformUser in("+parmMap.get("ids")+")");
		}
		if(parmMap.get("state")!=null&&!"".equals(parmMap.get("state"))){
			sql.append(" and o.state="+parmMap.get("state"));
		}if(parmMap.get("insertStartTime")!=null&&!"".equals(parmMap.get("insertStartTime"))){
			sql.append(" and o.insertTime >='"+parmMap.get("insertStartTime")+"'");
		}if(parmMap.get("insertEndTime")!=null&&!"".equals(parmMap.get("insertEndTime"))){
			sql.append(" and o.insertTime <='"+parmMap.get("insertEndTime")+"'");
		}
		sql.append(" order by insertTime desc ");
		Map<String, Object> map = new HashMap<String, Object>();
	    TypedQuery<WccMassMessage> query = entityManager().createQuery(sql.toString(), WccMassMessage.class);

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
		List<WccMassMessage> list = query.getResultList();
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());
		return pageModel;
	}
	
	public static PageModel<WccMassMessage> getMassMessageByFriendId(
			QueryModel<WccMassMessage> qm,Long companyId,Long friendId) {
		Map parmMap = qm.getInputMap();
		PageModel<WccMassMessage> pageModel = new PageModel<WccMassMessage>();
		
		StringBuffer sql = new StringBuffer(
				" select * from wcc_mass_message where id in (select mass_messages from wcc_mass_message_friends where friends = "+ friendId +") and company = "+companyId+" and is_delete = 0 order by insert_time desc ");
		Map<String, Object> map = new HashMap<String, Object>();
		
	    Query query = entityManager().createNativeQuery(sql.toString(),WccMassMessage.class);
	   
		int totalCount = 0;

		totalCount = query.getResultList().size();

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());
		return pageModel;
	}
	/**
	 * 条件查询代理商信息（分页）
	 * 
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccMassMessage> getRecord(
			QueryModel<WccMassMessage> qm) {
		PageModel<WccMassMessage> pageModel = new PageModel<WccMassMessage>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
		Criteria criteria = s.createCriteria(WccMassMessage.class);
		criteria.add(Restrictions.eq("isDelete",false));
		String[] orgid = null;
		Long[] orgrray = null;
		//组织架构查询
		if(null != parm.get("orgids")
				&& !"".equals(parm.get("orgids") + ""))
		{
			String orgs=parm.get("orgids")+"";
			orgid = orgs.split(",");
			if (null != orgid && orgid.length > 0) {
				orgrray = new Long[orgid.length];
				for (int i = 0; i < orgid.length; i++) {
					orgrray[i] = Long.parseLong(orgid[i]);
				}
			
			}
			criteria.createAlias("pubOrganization", "pubOrganization").add(
					Restrictions.in("pubOrganization.id", orgrray));
		}
		String[] platid = null;
		Long[] platrray = null;
		// 获取公众平台上id
		
		String pltStr = parm.get("platId") + "";
		if (StringUtils.isNotBlank(pltStr)) {
			platid = pltStr.split(",");
		}
		if (null != platid && platid.length > 0) {
			platrray = new Long[platid.length];
			for (int i = 0; i < platid.length; i++) {
				platrray[i] = Long.parseLong(platid[i]);
			}
		}
		if (null != platrray && platrray.length > 0) {
			criteria.createAlias("platformUser", "platformUser").add(
					Restrictions.in("platformUser.id", platrray));
		}
       //区域条件
		if (null != parm.get("region")
				&& !"".equals(parm.get("region") + "")) {
			criteria.add(Restrictions.like("regionarea", parm.get("region")
					+ "", MatchMode.ANYWHERE));
		}
		if (null != parm.get("province")
				&& !"".equals(parm.get("province") + "")) {
			criteria.add(Restrictions.like("regionarea", parm.get("province")
					+ "", MatchMode.ANYWHERE));
		}
		if (null != parm.get("city")
				&& !"".equals(parm.get("city") + "")) {
			criteria.add(Restrictions.like("regionarea", parm.get("city")
					+ "", MatchMode.ANYWHERE));
		}
		//活动
		String  activiies=parm.get("activity")+"";
		if(StringUtils.isNotEmpty(activiies))
		{
		String[] activityarra=activiies.split(",");
		if(null != activityarra && activityarra.length > 0){
  	     	Disjunction dis=Restrictions.disjunction();  
  	      	for(String sstr:activityarra){
  	      	 dis.add(Restrictions.like("activitysn", sstr, MatchMode.ANYWHERE));
  	      	}
		}
		}
		//标签
		String  labels=parm.get("labels")+"";
		if(StringUtils.isNotEmpty(labels))
		{
		String[] labelsarray=activiies.split(",");
		if(null != labelsarray && labelsarray.length > 0){
  	     	Disjunction dis=Restrictions.disjunction();  
  	      	for(String sstr:labelsarray){
  	      	 dis.add(Restrictions.like("label", sstr, MatchMode.ANYWHERE));
  	      	}
		}
		}
		//分组
		if (null != parm.get("groups")
				&& !"".equals(parm.get("groups") + "")) {
			criteria.add(Restrictions.eq("groupStr", parm.get("groups")+ ""));
		}
		//状态
		if (null != parm.get("state")
				&& !"".equals(parm.get("state") + "")) {
			criteria.add(Restrictions.eq("state", Integer.parseInt(parm.get("state")+"")));
		}
		criteria.addOrder(Order.desc("insertTime"));// 按插入日期降序排列
		Long totalCount = 0l;
		try {
			totalCount=(Long) criteria.setProjection(
					Projections.countDistinct("id")).uniqueResult();
		} catch (Exception e) {
			totalCount=0l;
		}
		criteria.setProjection(null);
		
		pageModel.setTotalCount(Integer.parseInt(totalCount + ""));
		pageModel.setPageSize(qm.getLimit());
		List<WccMassMessage> list = null;
		if((orgrray!=null&&orgrray.length>0)||(platrray!=null&&platrray.length>0))
		{
			WccMassMessage u = null;
			list=new ArrayList<WccMassMessage>();
			List<Object[]> lis = criteria.list();
			if (null != lis && lis.size() > 0) {
				for (Object[] o : lis) {
					
					u = (WccMassMessage) o[o.length-1];
					list.add(u);
				}
			}
		}
		else {
			list= criteria.list();
		}
		pageModel.setDataList(list);

		return pageModel;

	}
}

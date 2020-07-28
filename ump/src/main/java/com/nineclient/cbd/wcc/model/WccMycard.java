package com.nineclient.cbd.wcc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 我的一卡通
 * @author 9client
 *
 */
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMycard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private Boolean isDelete;
	private String insertIp;
	private String insertPk;
	private Date insertTime;
	private String updateIp;
	private String updatePk;
	private Date updateTime;
	private String deleteIp;
	private String deletePk;
	private Date deleteTime;
	//公司ID
	private String companyId;
	private String organizationPk;
	
	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	@Size(max = 500)
	private String cardTitle;//标题

	private Date cardTime;//发布日期
	@Size(max = 500)
	private String cardPic;//图片
	@Size(max = 500)
	private String cardName;//一卡通名称
	@Size(max = 8000)
	private	String contentTitle;//内容
	@Size(max = 500)
	private String cardIntro;//一卡通介绍
	@Size(max = 500)
	private String cardUrl;//一卡通查询链接
	
	@Size(max = 500)
	private String jrUrl;//一卡通查询链接

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getInsertIp() {
		return insertIp;
	}

	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}

	public String getInsertPk() {
		return insertPk;
	}

	public void setInsertPk(String insertPk) {
		this.insertPk = insertPk;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public String getUpdatePk() {
		return updatePk;
	}

	public void setUpdatePk(String updatePk) {
		this.updatePk = updatePk;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteIp() {
		return deleteIp;
	}

	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}

	public String getDeletePk() {
		return deletePk;
	}

	public void setDeletePk(String deletePk) {
		this.deletePk = deletePk;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOrganizationPk() {
		return organizationPk;
	}

	public void setOrganizationPk(String organizationPk) {
		this.organizationPk = organizationPk;
	}

	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public Date getCardTime() {
		return cardTime;
	}

	public void setCardTime(Date cardTime) {
		this.cardTime = cardTime;
	}

	public String getCardPic() {
		return cardPic;
	}

	public void setCardPic(String cardPic) {
		this.cardPic = cardPic;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getCardIntro() {
		return cardIntro;
	}

	public void setCardIntro(String cardIntro) {
		this.cardIntro = cardIntro;
	}

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	
	
	
	public String getJrUrl() {
		return jrUrl;
	}

	public void setJrUrl(String jrUrl) {
		this.jrUrl = jrUrl;
	}

	



	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageModel<WccMycard> getRecord(QueryModel<WccMycard> qm){
		PageModel<WccMycard> pageModel=new PageModel<WccMycard>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
		  DetachedCriteria dc = DetachedCriteria.forClass(WccMycard.class).setFetchMode("platformUsers", FetchMode.SELECT);
	        Criteria criteria = dc.getExecutableCriteria(s);
    //   Criteria cr = criteria .createCriteria("platformUsers");
  //     Criteria cr2=cr.setFetchMode("platformUsers", org.hibernate.FetchMode.JOIN.LAZY);
     //  Criteria cr = criteria.createCriteria("platformUsers");
      
       String[] lid = null;
       Long[] larray = null;
       //获取公众平台上id
      String pltStr =  parm.get("platFormId")+""; 
      if(null != pltStr && !"".equals(pltStr.trim())){
     	 lid = pltStr.split(",");
      }
      if(null  != lid && lid.length > 0){
     	 larray = new Long[lid.length];
     	 for(int i=0;i<lid.length;i++){
     		 larray[i] = Long.parseLong(lid[i]); 
     	 }
      }
      if(null != larray && larray.length > 0){
     	 criteria.createAlias("platformUsers", "platformUsers")
     	.add(Restrictions.in("platformUsers.id",larray));
      }

         	if(null != parm.get("nickName") && !"".equals(parm.get("nickName")+"")){
         		criteria.add(Restrictions.like("cardName", parm.get("nickName")+"",MatchMode.ANYWHERE));
         	}
         	
 			if (null != parm.get("startTimeId") && !"".equals(parm.get("startTimeId")+"")) {
 				criteria.add(Restrictions.ge("cardTime", DateUtil.parseDateFormat(parm.get("startTimeId")+" 00:00:00")));
 			}
 			
 			if (null != parm.get("endTimeId") && !"".equals(parm.get("endTimeId")+"")) {
 				criteria.add(Restrictions.le("cardTime", DateUtil.parseDateFormat(parm.get("endTimeId")+" 23:59:59")));
 			}
     
       Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
   	   criteria.setProjection(null);
	//   criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
       pageModel.setTotalCount(Integer.parseInt(totalCount+""));
       pageModel.setPageSize(qm.getLimit());
//       criteria.setFirstResult(qm.getStart());//当前第几页
//       criteria.setMaxResults(qm.getLimit());//页的大小
       List<WccMycard> list = null;
       if(null != larray && larray.length > 0){
   		list = new ArrayList<WccMycard>();
   		WccMycard u = null;
   		Map<Long,WccMycard> mm = new HashMap<Long,WccMycard>();
   		List <Object[]> lis =  criteria.list();
   		if(null != lis && lis.size() >0 ){
   			for(Object[] o:lis){
   				u= (WccMycard) o[1];
   				mm.put(u.getId(), u);
   			}
   			if(mm.size() > 0){
   				for(Map.Entry<Long,WccMycard> mp:mm.entrySet()){
   					list.add(mp.getValue());
   				}
   			}
   			
   		}
       }else{
       	list = criteria.list();
       }
		//List<WccLifeHelper> list = criteria.list();
		pageModel.setDataList(list);
		return pageModel;
	}
	
	public static WccMycard findPubOrganization(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccMycard.class, id);
	}
	
	
	public static List<WccMycard> findMyCard(String id){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccMycard.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id && !id.equals("")){
	    	  cr.add(Restrictions.eq("id",Long.parseLong(id)));
	      }
	      return criteria.list();
	}
	//修改
		@Transactional
	    public WccMycard merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        WccMycard merged = this.entityManager.merge(this);
	        this.entityManager.flush();
	        return merged;
	    }
		
		
		//删除
		@Transactional
		public void remove() {
			if (this.entityManager == null)
				this.entityManager = entityManager();
			if (this.entityManager.contains(this)) {
				this.entityManager.remove(this);
			} else {
				WccMycard attached = WccMycard
						.findPubOrganization(this.id);
				this.entityManager.remove(attached);
			}
		}
		//添加
		@Transactional
	    public void persist() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        this.entityManager.persist(this);
	    }
	
}

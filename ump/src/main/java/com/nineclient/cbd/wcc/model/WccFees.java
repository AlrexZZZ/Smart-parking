package com.nineclient.cbd.wcc.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.cbd.wcc.dto.WccFeesDTO;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccFees {

	/**
	 * 主键
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	

	/**
	 * 关联业主信息实体类
	 */
	@ManyToOne
	private CbdWccProprietor cbdWccProprietor;
	

	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 项目id
	 */
	private Long itemPk;
	
	/**
	 *姓名
	 */
    private String userName;
    /**
	 * 电话
	 */
    private String cellphone;
    
    /**
	 *门牌号
	 */
    private String doorNum;
    
    
    /**
 	 *月份
 	 */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date month;
    /**
 	 *月份string
 	 */
 
    private String monthStr;
    
    /**
 	 *支付状态  0未支付/1支付
 	 */
    private int state;
    
    /**
 	 *金额保留两位小数
 	 */
    private float amount;
    
    /**
  	 *其他可跳转url
  	 */
    private String otherUrl;
	@Version
    @Column(name = "version")
    private Integer version;
	/**
	 * 关联公众号
     */
	@ManyToMany
    private Set<WccPlatformUser> platformUsers;
	

    /**插入时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date insertTime;

    
    
    
    

	public Long getItemPk() {
		return itemPk;
	}

	public void setItemPk(Long itemPk) {
		this.itemPk = itemPk;
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getDoorNum() {
		return doorNum;
	}

	public void setDoorNum(String doorNum) {
		this.doorNum = doorNum;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	private String insertTimeStr;
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}

	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}

	public String getInsertTimeStr() {
		return insertTimeStr;
	}

	public void setInsertTimeStr(String insertTimeStr) {
		this.insertTimeStr = insertTimeStr;
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

	public Date getInsertTime() {
        return this.insertTime;
    }

	public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

	public String getOtherUrl() {
		return otherUrl;
	}

	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	

	public CbdWccProprietor getCbdWccProprietor() {
		return cbdWccProprietor;
	}

	public void setCbdWccProprietor(CbdWccProprietor cbdWccProprietor) {
		this.cbdWccProprietor = cbdWccProprietor;
	}
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nickName", "headImg", "sex", "area", "signature", "remarkName", "userInfo", "isValidated", "isDeleted", "insertTime", "fromType", "subscribeTime", "messageEndTime", "sendEmailTime");

	public static final EntityManager entityManager() {
        EntityManager em = new WccFees().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccFees() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccFees o", Long.class).getSingleResult();
    }

	public static List<WccFees> findAllWccFees() {
        return entityManager().createQuery("SELECT o FROM WccFees o", WccFees.class).getResultList();
    }

	public static List<WccFees> findAllWccFees(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccFees o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccFees.class).getResultList();
    }

	public static WccFees findWccFees(Long id) {
        if (id == null) return null;
        return entityManager().find(WccFees.class, id);
    }
	public static String toJsonArray(Collection<WccFees> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
	public static String toJsonArray(Collection<WccFees> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<WccFees> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<WccFees>>()
        .use("values", WccFees.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	public static List<WccFees> findWccFeesEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccFees o", WccFees.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccFees> findWccFeesByToUser(Long id) {
		return entityManager().createQuery("SELECT o FROM WccFees o WHERE o.msgFrom = 4 and o.toUserRecord = "+id, WccFees.class).getResultList();
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
            WccFees attached = WccFees.findWccFees(this.id);
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
    public WccFees merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccFees merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	

	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccFees> getRecord(QueryModel<WccFees> qm){
		PageModel<WccFees> pageModel = new PageModel<WccFees>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccFees.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
      Criteria cr = criteria.createCriteria("platformUsers");
      Criteria pro = criteria.createCriteria("cbdWccProprietor");
      Criteria pj = pro.createCriteria("wccappartment");
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
    	 cr.add(Restrictions.in("id",larray));
     }      
            //月份
            if(null != parm.get("monthStr") && !"".equals(parm.get("monthStr")+"")){
 	 criteria.add(Restrictions.like("monthStr", parm.get("monthStr")+"",MatchMode.ANYWHERE));
 	         }
            //项目名称
            if(null != parm.get("itemName") && !"".equals(parm.get("itemName")+"")){
            pj.add(Restrictions.like("itemName", parm.get("itemName")+"",MatchMode.ANYWHERE));
            }
            //状态
            if(null != parm.get("state") && !"".equals(parm.get("state")+"")  && !"-1".equals(parm.get("state")+"")){
            	criteria.add(Restrictions.eq("state", Integer.parseInt(parm.get("state")+"")));
            }      
            //摘要关（键词）
            if(null != parm.get("summary") && !"".equals(parm.get("summary")+"")){
           	 criteria.add(Restrictions.like("summary", parm.get("summary")+"",MatchMode.ANYWHERE));
            } 
        	if(null != parm.get("nickName") && !"".equals(parm.get("nickName")+"")){
        		criteria.add(Restrictions.like("detailContent", parm.get("nickName")+"",MatchMode.ANYWHERE));
        	}
        	
			if (null != parm.get("beginTime") && !"".equals(parm.get("beginTime")+"")) {
				criteria.add(Restrictions.ge("insertTime", DateUtil.parseDateFormat(parm.get("beginTime")+" 00:00:00")));
			}
			
			if (null != parm.get("endTime") && !"".equals(parm.get("endTime")+"")) {
				criteria.add(Restrictions.le("insertTime", DateUtil.parseDateFormat(parm.get("endTime")+" 23:59:59")));
			}
        
      Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
    	criteria.setProjection(null);
		 criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
//        criteria.setFirstResult(qm.getStart());//当前第几页
//        criteria.setMaxResults(qm.getLimit());//页的大小
        
		//List<WccFees> list = criteria.list();
        
        
        List<WccFees> list = null;
        if(null != larray && larray.length > 0){
    		list = new ArrayList<WccFees>();
    		WccFees u = null;
    		Map<Long,WccFees> mm = new LinkedHashMap<Long,WccFees>();
    		List <Object> lis =  criteria.list();
    		if(null != lis && lis.size() >0 ){
    			for(Object o:lis){
    				u= (WccFees) o;
    				mm.put(u.getId(), u);
    			}
    			if(mm.size() > 0){
    				for(Map.Entry<Long,WccFees> mp:mm.entrySet()){
    					list.add(mp.getValue());
    				}
    			}
    			
    		}
        }else{
        	list = criteria.list();
        }
		pageModel.setDataList(list);
		
      return pageModel;
      
	}






	
	/**
	 * 查询消息记录（分页）
	 * @param qm
	 * @return
	 */
	public static PageModel<WccFees> getQueryRecord(QueryModel<WccFees> qm){
		PageModel<WccFees> pageModel = new PageModel<WccFees>();
		Map<String, Object> param = qm.getInputMap();
//		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFees o WHERE o.openId = '"+param.get("openId")+"'" + "ORDER BY o.insertTime DESC");
		StringBuffer sql = new StringBuffer(" SELECT o FROM WccFees o ");

		Map<String,Object> map = new HashMap<String, Object>();
		TypedQuery<WccFees> query = entityManager().createQuery(sql.toString(), WccFees.class).setFirstResult(qm.getStart()).setMaxResults(qm.getLimit());
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
//      List<WccFees> list = query.getResultList();
      pageModel.setTotalCount(totalCount);
      pageModel.setDataList(query.getResultList());
      return pageModel;
	}
	
	/**
	 * 查询记录
	 * @param qm
	 * @return
	 */
	public static List<WccFees> getQueryRecordList(Long id){
		Session s = (Session) entityManager().getDelegate();
	      Criteria criteria = s.createCriteria(WccFees.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	      Criteria cr = criteria.createCriteria("platformUsers");
	      if(null != id){
	    	  criteria.add(Restrictions.eq("id",id));
	      }
      return criteria.list();
	}	
	public static List<WccFeesDTO> findRcordCountByQuery(String friendPk,String dateTime) throws UnsupportedEncodingException{
		StringBuffer qlString = new StringBuffer();
		qlString.append("SELECT t1.itemName,t0.summary,t0.monthStr,t0.amount,t0.state from WccFees t0 ,WccAppartment t1, CbdWccProprietor t2");
		qlString.append("  where 1=1 ");
		qlString.append("  and t0.cbdWccProprietor = t2.id ");
		qlString.append("  and t1.id = t2.wccappartment ");
	

		if(null!=friendPk && !"".equals(friendPk)){
			qlString.append("and t2.wccFriend =  "+friendPk);
		}
		
        if (null != dateTime && !"".equals(dateTime)) {
            
        	qlString.append(" and t0.month >= '"+dateTime+"-01"+"'");
        	
        	qlString.append(" and t0.month < '"+dateTime+"-28"+"'"); 
           }
      
     
	    qlString.append(" GROUP BY t1.itemName,t2.name,t2.phone,t2.doorplate,YEAR(t0.month),MONTH(t0.month),DAY(t0.month)   ");
        qlString.append(" ORDER BY t0.month desc ");
		List<WccFeesDTO> reclist = new ArrayList<WccFeesDTO>(); 
		List<Object[]> objList = entityManager().createQuery(qlString.toString()).getResultList();
		if(objList.size() > 0){
			for(Object[] e:objList){
				WccFeesDTO msg = new WccFeesDTO();
				msg.setItemName(e[0]+""); //项目名称
				msg.setSummary(e[1]+""); //摘要
				msg.setMonthStr(e[2]+"");//年月
				msg.setAmount(Float.parseFloat(e[3]+""));//金额
				msg.setState(Integer.parseInt(e[4]+""));//年月
				reclist.add(msg);
			}
		}
 		
		return reclist;
	}
	public static List<WccFees> findFs(String id){
		String hql=" From WccFees where cbdWccProprietor="+id;
		TypedQuery<WccFees> query=entityManager().createQuery(hql,WccFees.class);
		 
		 return query.getResultList();
	}

}

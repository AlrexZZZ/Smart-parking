package com.nineclient.cbd.wcc.model;

import java.util.ArrayList;
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
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
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

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccImgSave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private	Set<WccPlatformUser>  platformUsers;
	@Size(max = 500)
	private String type;//图片所属类型 （1代表联系我们，2代表业主认证,3代表故障报修）
	@Size(max = 500)
	private String imgPath;//图片路径
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<WccPlatformUser> getPlatformUsers() {
		return platformUsers;
	}
	public void setPlatformUsers(Set<WccPlatformUser> platformUsers) {
		this.platformUsers = platformUsers;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageModel<WccImgSave> getRecord(QueryModel<WccImgSave> qm){
		

		PageModel<WccImgSave> pageModel=new PageModel<WccImgSave>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(WccImgSave.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        String[] lid = null;
        Long[] larray = null;
        String pltStr =  parm.get("platformUsers")+""; 
        if(null != pltStr && !"".equals(pltStr.trim()) && !pltStr.equals("null")){
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
    	if(null != parm.get("type") && !"".equals(parm.get("type")+"")){
    		criteria.add(Restrictions.like("type", parm.get("type")+"",MatchMode.ANYWHERE));
    	}
    	
       Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
   	   criteria.setProjection(null);
       pageModel.setTotalCount(Integer.parseInt(totalCount+""));
       pageModel.setPageSize(qm.getLimit());
//       criteria.setFirstResult(qm.getStart());//当前第几页
//       criteria.setMaxResults(qm.getLimit());//页的大小
       List<WccImgSave> list = null;
       if(null != larray && larray.length > 0){
   		list = new ArrayList<WccImgSave>();
   		WccImgSave u = null;
   		Map<Long,WccImgSave> mm = new HashMap<Long,WccImgSave>();
   		List <Object[]> lis =  criteria.list();
   		if(null != lis && lis.size() >0 ){
   			for(Object[] o:lis){
   				u= (WccImgSave) o[1];
   				mm.put(u.getId(), u);
   			}
   			if(mm.size() > 0){
   				for(Map.Entry<Long,WccImgSave> mp:mm.entrySet()){
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
	
		
		
		
		/*
		PageModel<WccImgSave> pageModel = new PageModel<WccImgSave>();
		Map parm = qm.getInputMap();
	  Session s = (Session) entityManager().getDelegate();
      Criteria criteria = s.createCriteria(WccImgSave.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
       
      Criteria cr = criteria.createCriteria("platformUsers");
      String[] lid = null;
      Long[] larray = null;
      //获取公众平台上id
     String pltStr =  parm.get("platformUsers")+""; 
     if(null != pltStr && !"".equals(pltStr.trim()) && !pltStr.equals("null")){
    	 lid = pltStr.split(",");
     }
     if(null  != lid && lid.length > 0){
    	 larray = new Long[lid.length];
    	 for(int i=0;i<lid.length;i++){
    		 larray[i] = Long.parseLong(lid[i]); 
    	 }
     }
     if(null != larray && larray.length > 0  && !larray.equals("null")){
    	 cr.add(Restrictions.in("id",larray));
     }

        	if(null != parm.get("type") && !"".equals(parm.get("type")+"")){
        		criteria.add(Restrictions.like("type", parm.get("type")+"",MatchMode.ANYWHERE));
        	}
        	
      Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
    	criteria.setProjection(null);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
        criteria.setFirstResult(qm.getStart());//当前第几页
        criteria.setMaxResults(qm.getLimit());//页的大小
		//List<WccCultureLife> list = criteria.list();
		pageModel.setDataList(criteria.list());
		return pageModel;
      
	*/}
	public static WccImgSave getById(Long id){
		if (id == null)
			return null;
		return entityManager().find(WccImgSave.class, id);
	}
	//添加
	@Transactional
	public void persist() {
	      if (this.entityManager == null) this.entityManager = entityManager();
	      this.entityManager.persist(this);
	}
	//修改
	@Transactional
    public WccImgSave merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccImgSave merged = this.entityManager.merge(this);
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
			WccImgSave attached = WccImgSave
					.getById(this.id);
			this.entityManager.remove(attached);
		}
	}
	
	public static  List<WccImgSave> findBytype(Long platformUsers,String type){
		Session s = (Session) entityManager().getDelegate();
		 Criteria criteria = s.createCriteria(WccImgSave.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	     Criteria cr = criteria.createCriteria("platformUsers");
	     if(platformUsers!=null && platformUsers>0){
	    	 cr.add(Restrictions.eq("id",platformUsers));
	     }
	     if(type != null && !type.equals("")){
	    	 criteria.add(Restrictions.eq("type",type));
	     }
	     return criteria.list();
	} 
}

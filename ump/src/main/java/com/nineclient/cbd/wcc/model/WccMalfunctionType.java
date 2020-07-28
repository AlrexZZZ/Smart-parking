package com.nineclient.cbd.wcc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMalfunctionType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	private WccAppartment itemPk;
	@Size(max = 500)
	private String name;
	@Size(max = 4000)
	private String remark;//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public WccAppartment getItemPk() {
		return itemPk;
	}
	public void setItemPk(WccAppartment itemPk) {
		this.itemPk = itemPk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	public static List<WccMalfunctionType> findByItem(WccAppartment itemPk){
		Map<String, Object> map=new HashMap<String, Object>();
		String hql=" FROM WccMalfunctionType o WHERE 1=1  ";
		
		if(itemPk!=null && !itemPk.equals("")){
			hql+="  and  o.itemPk  in :itemPk ";
			map.put("itemPk",itemPk);
		}
		 TypedQuery<WccMalfunctionType> query=entityManager().createQuery(hql,WccMalfunctionType.class);
		 for(String key:map.keySet()){
	            query.setParameter(key, map.get(key));	
	          }
		 return query.getResultList();
	}
	public static WccMalfunctionType getById(Long id){
		if (id == null)
			return null;
		return entityManager().find(WccMalfunctionType.class, id);
	}
	@SuppressWarnings("unchecked")
	public static PageModel<WccMalfunctionType> getRecord(QueryModel<WccMalfunctionType> qm){
		PageModel<WccMalfunctionType> pageModel=new PageModel<WccMalfunctionType>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(WccMalfunctionType.class);
        if(parm.get("itemPk")!=null && !parm.get("itemPk").equals("")){
        	System.out.println(parm.get("itemPk")+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //	WccAppartment wcc=WccAppartment.findPubOrganization(Long.parseLong((parm.get("itemPk")+"")));
        	String[] str=(parm.get("itemPk")+"").split(",");
        	WccAppartment[] wcc=new WccAppartment[str.length];
        	for(int i=0;i<str.length;i++){
        		wcc[i]=WccAppartment.findPubOrganization(Long.parseLong(str[i]));
        	}
        	criteria.add(Restrictions.in("itemPk", wcc ));
        }
        if(parm.get("name")!=null && !parm.get("name").equals("")){
        	System.out.println(parm.get("name"));
        	criteria.add(Restrictions.like("name",("%"+parm.get("name"))+"%"));
        }
        
        
        Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    	criteria.setProjection(null);
 	    criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
 	    System.out.println(totalCount);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
        criteria.setFirstResult(qm.getStart());//当前第几页
        criteria.setMaxResults(qm.getLimit());//页的大小
        pageModel.setDataList(criteria.list());
        return pageModel;
	}
	
	public static List<WccMalfunctionType> getBytype(String type){
		Map<String, Object> map=new HashMap<String, Object>();
			String hql=" FROM WccMalfunctionType o WHERE 1=1  ";
		
		if(type!=null && !type.equals("")){
			hql+="  and  o.name  like :name ";
			map.put("name","%"+type+"%");
		}
		 TypedQuery<WccMalfunctionType> query=entityManager().createQuery(hql,WccMalfunctionType.class);
		 for(String key:map.keySet()){
	            query.setParameter(key, map.get(key));	
	          }
		 return query.getResultList();
	}
	
	//添加
	@Transactional
	public void persist() {
	      if (this.entityManager == null) this.entityManager = entityManager();
	      this.entityManager.persist(this);
	}
	//修改
	@Transactional
    public WccMalfunctionType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccMalfunctionType merged = this.entityManager.merge(this);
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
			WccMalfunctionType attached = WccMalfunctionType
					.getById(this.id);
			this.entityManager.remove(attached);
		}
	}
	
}

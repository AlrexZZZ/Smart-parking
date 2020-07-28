package com.nineclient.cbd.wcc.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import aj.org.objectweb.asm.Type;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccAlliancestoreType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Size(max = 500)
	private String name;//名称
	@Size(max = 500)
	private String img;//图片
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccAppartment().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	public static WccAlliancestoreType getById(Long id){
		if (id == null)
			return null;
		return entityManager().find(WccAlliancestoreType.class, id);
	}
	//添加
	@Transactional
	public void persist() {
	      if (this.entityManager == null) this.entityManager = entityManager();
	      this.entityManager.persist(this);
	}
	//修改
	@Transactional
    public WccAlliancestoreType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccAlliancestoreType merged = this.entityManager.merge(this);
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
			WccAlliancestoreType attached = WccAlliancestoreType
					.getById(this.id);
			this.entityManager.remove(attached);
		}
	}
	public static PageModel<WccAlliancestoreType> getRecord(QueryModel<WccAlliancestoreType> qm){
		PageModel<WccAlliancestoreType> pageModel=new PageModel<WccAlliancestoreType>();
		Map parm = qm.getInputMap();
		Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(WccAlliancestoreType.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
        criteria.setProjection(null);
  //    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
        criteria.setFirstResult(qm.getStart());//当前第几页
        criteria.setMaxResults(qm.getLimit());//页的大小
        pageModel.setDataList(criteria.list());
		return pageModel;
	}
	
	public static List<WccAlliancestoreType> getwccAlliancestoreType(){
		String hql="from WccAlliancestoreType";
		TypedQuery<WccAlliancestoreType> query=entityManager().createQuery(hql,WccAlliancestoreType.class);
		return query.getResultList();
	}
	
}

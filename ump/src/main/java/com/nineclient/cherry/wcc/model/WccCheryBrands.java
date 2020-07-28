package com.nineclient.cherry.wcc.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.cbd.wcc.model.WccContactUs;
import com.nineclient.cbd.wcc.model.WccFees;
import com.nineclient.dto.PubOperatorDto;
import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpBrand;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccQrcode;
import com.nineclient.model.WccRecordMsg;
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
public class WccCheryBrands {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	/**
	 *品牌名称
	 */
    private String brandName;
	
	/**
	 * 创建日期
     */	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;
    
	/**
	 *启用状态
     */
	private Boolean active;
	
	/**
	 * 是否删除
     */
	private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccCheryBrands().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			WccCheryBrands attached = WccCheryBrands.findBrandById(this.id);
			this.entityManager.remove(attached);
		}
	}

	public static WccCheryBrands findBrandById(Long id) {
		if (id == null)
			return null;
	  	return entityManager().find(WccCheryBrands.class, id);
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public WccCheryBrands merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccCheryBrands merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static String toJsonArray(Collection<WccCheryBrands> collection) {
		System.out.println(collection);
		String str=new JSONSerializer().exclude("*.class").serialize(collection);
		System.out.println(str);
        return str;
    }

	
	public static List<WccCheryBrands> findBrandByFiled(
			int firstResult, int maxResults, String brandName,Boolean active) throws Exception {
		String jpaQuery = "SELECT o FROM WccCheryBrands o where 1=1 ";
		if (null != brandName && !"".equals(brandName)) {
			jpaQuery = jpaQuery + " and o.brandName like '%"+brandName+"%'";
        }
        if (null != active && !"".equals(active)) {
        	jpaQuery = jpaQuery + " and o.active = " + active;
        }
        jpaQuery = jpaQuery + " ORDER BY o.createDate desc";
        List<WccCheryBrands> list=new ArrayList<WccCheryBrands>();
        if(firstResult!=-1&&maxResults!=-1){
	    	  list=entityManager().createQuery(jpaQuery, WccCheryBrands.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	        }else{
	        	 list=entityManager().createQuery(jpaQuery, WccCheryBrands.class).getResultList();
	        }
        return list;
	}
	
	
	public static List<WccCheryBrands> findBrand(String brand) {
		if(null == brand || "".equals(brand)) return null;
		TypedQuery<WccCheryBrands> query = entityManager().createQuery(" FROM WccCheryBrands o where o.brandName =:brand ",WccCheryBrands.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	
	/**
	 * 获取所有品牌
	 * @return
	 */
	 public static List<WccCheryBrands> getBrands(){
	    	List<String> listkey=new ArrayList<String>();
	    	String jpaQuery = "SELECT o FROM WccCheryBrands o where 1=1 and o.active=true";
	    	List<WccCheryBrands> list=entityManager().createQuery(jpaQuery, WccCheryBrands.class).getResultList();
	    	if(list.size()>0){
//	    		for (WccCheryBrands wccCheryBrands : list) {
//	    			listkey.add(wccCheryBrands.getBrandName());
//				}
	    		return list;
	    	}else
	    		return null;
	    }

}

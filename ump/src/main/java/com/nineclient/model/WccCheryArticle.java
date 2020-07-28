package com.nineclient.cherry.wcc.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.nineclient.model.PubOrganization;
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
public class WccCheryArticle {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	/**
	 *品牌名称
	 */
    private String brandName;
    
    /**
     * 公众帐号
     */
	
    private String platformAccount;
    
    /**
     * 文章标题
     */
    private String title;
    
    /**
     * 文章链接
     */
    @Size(max = 1000)
    private String url;
    
    /**
     * 文章内容
     */
    
    private String articleContent;
    
	/**
	 * 创建日期
     */	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;

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

	public String getPlatformAccount() {
		return platformAccount;
	}

	public void setPlatformAccount(String platformAccount) {
		this.platformAccount = platformAccount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
	@PersistenceContext
	transient EntityManager entityManager;
	
	public static final EntityManager entityManager() {
		EntityManager em = new WccCheryArticle().entityManager;
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
	public WccCheryArticle merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccCheryArticle merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
	
	
	/**
	 * 查询官微及竞品发布内容统计表
	 * @param platform
	 * @param startTime
	 * @param endTime
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public static List<WccCheryArticle> findArticleReportByDateBetween(String brandName,String platform, String startTime, String endTime, int firstResult, int maxResults) {
	      String jpaQuery = "SELECT o FROM WccCheryArticle o where 1=1 ";
	      if(null!=platform&&!platform.equals("")&&!platform.equals("全部")){
	    	  jpaQuery+=" and o.platformAccount  = '"+platform+"'";
	      }if(null!=brandName&&!brandName.equals("")){
	    	  jpaQuery+=" and o.brandName like '%"+brandName+"%'";
	      }if(null!=startTime&&!startTime.equals("")){
	    	  jpaQuery+=" and  o.createDate >='"+startTime+" 00:00:00'";
	      }if(null!=endTime&&!endTime.equals("")){
	    	  jpaQuery+=" and o.createDate <='"+endTime+" 23:59:59'";
	      }
	      jpaQuery+=" order by o.createDate  desc";
	      List<WccCheryArticle> list=new ArrayList<WccCheryArticle>();
	      if(firstResult!=-1&&maxResults!=-1){
	    	  list=entityManager().createQuery(jpaQuery, WccCheryArticle.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	        }else{
	        	 list=entityManager().createQuery(jpaQuery, WccCheryArticle.class).getResultList();
	        }
	       return list;

    }

}

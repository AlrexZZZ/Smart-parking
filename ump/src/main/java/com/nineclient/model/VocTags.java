package com.nineclient.model;

import java.util.Collection;
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
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;
import com.nineclient.web.VocTagsController;

import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooWebJson(jsonObject = VocTagsController.class)
@RooJson
public class VocTags {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	/**
     */
	@NotNull
	private String name;

	/**
     */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date createTime;

	@ManyToOne
	private UmpBusinessType bussinessType;
	
	@ManyToMany(mappedBy="vocTags")
	private Set<VocComment> vocComments;
	
	private Long companyId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UmpBusinessType getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(UmpBusinessType bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	

	public Set<VocComment> getVocComments() {
		return vocComments;
	}

	public void setVocComments(Set<VocComment> vocComments) {
		this.vocComments = vocComments;
	}




	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new VocTemplateCategory().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("name", "createTime");

	public static List<VocTags> findVocTagsEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM VocTemplateCategory o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, VocTags.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static VocTags findVocTags(Long id) {
		if (id == null)
			return null;
		return entityManager().find(VocTags.class, id);
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
			VocTags attached = VocTags.findVocTags(this.id);
			this.entityManager.remove(attached);
		}
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
	public VocTags merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		VocTags merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static String toJsonArray(Collection<VocTags> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * 根据条件查询模板
	 * 
	 * @param qm
	 * @return
	 */
	public static PageModel<VocTags> getQueryVocTags(QueryModel<VocTags> qm) {
		Map parmMap = qm.getInputMap();
		VocTags model = qm.getEntity();
		PageModel<VocTags> pageModel = new PageModel<VocTags>();
		StringBuffer sql = new StringBuffer(
				" SELECT o FROM VocTags o where 1=1 ");
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != model.getName() && !"".equals(model.getName())) {
			sql.append(" and o.name like :name");
			map.put("name", "%"+model.getName()+"%");
		}
		
		if (null != model.getCompanyId()) {
			sql.append(" and o.companyId = :companyId");
			map.put("companyId", model.getCompanyId());
		}
		
		if(null != model.getBussinessType()){
			sql.append(" and o.bussinessType = :bussinessType");
			map.put("bussinessType", model.getBussinessType());
		}

		 String sortedSql = " order by o.createTime desc";
		TypedQuery<VocTags> query = entityManager().createQuery(sql.toString()+sortedSql,
				VocTags.class);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
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

		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());

		return pageModel;
	}
}

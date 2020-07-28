package com.nineclient.model;

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
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.constant.UmpCheckStatus;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocKeyWord {
	/**
     */
	@NotNull
	private String name;
	
	private Long companyId;
	
	private Long vocBrandId;
	/**子行业**/
	private Long businessTypeId;
    @Enumerated
    private UmpCheckStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;
    
    @PersistenceContext
    transient EntityManager entityManager;
    public static final EntityManager entityManager() {
        EntityManager em = new VocKeyWord().entityManager;
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
            VocBrand attached = VocBrand.findVocBrand(this.id);
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
    public VocKeyWord merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocKeyWord merged = this.entityManager.merge(this);
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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the vocBrandId
	 */
	public Long getVocBrandId() {
		return vocBrandId;
	}

	/**
	 * @param vocBrandId the vocBrandId to set
	 */
	public void setVocBrandId(Long vocBrandId) {
		this.vocBrandId = vocBrandId;
	}

	/**
	 * @return the status
	 */
	public UmpCheckStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(UmpCheckStatus status) {
		this.status = status;
	}

	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the businessTypeId
	 */
	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @param businessTypeId the businessTypeId to set
	 */
	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	/**
	 * 查询关键词状态不等于审核通过的
	 * @param brandId
	 * @return
	 */
	public static List<VocKeyWord> queryBrandKeyWordByBrand(Long brandId){
		String sql="select o from VocKeyWord o where o.vocBrandId="+brandId+" and o.status!=:status";
		TypedQuery<VocKeyWord> query = entityManager().createQuery(sql, VocKeyWord.class);
		query.setParameter("status", UmpCheckStatus.已审核);
		return query.getResultList();
	}

	public static List<VocKeyWord> queryBrandKeyWordByBrandId(Long brandId){
		String sql="select o from VocKeyWord o where o.vocBrandId="+brandId+"";
		TypedQuery<VocKeyWord> query = entityManager().createQuery(sql, VocKeyWord.class);
		return query.getResultList();
	}
	public static VocKeyWord findVocKeyWordById(Long id) {
		String sql="select o from VocKeyWord o where o.id="+id;
		return entityManager().createQuery(sql, VocKeyWord.class).getSingleResult();
	}
	/**
	 * 根据子行业查询关键词
	 * @param id
	 * @return
	 */
	public static List<VocKeyWord> findVocKeyWordByBusinessId(Long id) {
		String sql="select o from VocKeyWord o where o.businessTypeId="+id;
		return entityManager().createQuery(sql, VocKeyWord.class).getResultList();
	}
	public static List<VocKeyWord> findVocKeyWordEntriesByIds(String keyIds) {
		String sql="select o from VocKeyWord o where o.id in ("+keyIds+")";
		return entityManager().createQuery(sql, VocKeyWord.class).getResultList();
	}

	public static List<VocKeyWord> findVocKeyWordByBrandId(Long brandId) {
		String sql="select o from VocKeyWord o where o.vocBrandId="+brandId;
		return entityManager().createQuery(sql, VocKeyWord.class).getResultList();
	}
	/**
	 * 编辑时关键词唯一性验证
	 * @param brandId
	 * @param keyWords
	 * @param companyId
	 * @param keyIds
	 * @return
	 */
	public static Long findVocKeyWordUnique(Long brandId,
			List<String> keyWords, Long companyId,List<Long> keyIds) {
		String sql="select count(o) from VocKeyWord o where o.vocBrandId="+brandId+" and o.companyId="+companyId;
		Map<String,Object> map=new HashMap<String,Object>();
		if(!StringUtils.isEmpty(keyIds)&&keyIds.size()>0){
			sql+=" and o.id not in (:keyIds)";
			map.put("keyIds", keyIds);
		}
		 if(!StringUtils.isEmpty(keyWords)&&keyWords.size()>0){
			sql+=" and o.name in (:keyWordName)";
			map.put("keyWordName", keyWords);
		}
		TypedQuery<Long> query = entityManager().createQuery(sql, Long.class);
		for(String key:map.keySet()){
			query.setParameter(key, map.get(key));
		}
		return query.getSingleResult();
	}
	/**
	 * 根据状态查询关键词
	 * @param brandId
	 * @param status
	 * @return
	 */
	public static List<VocKeyWord> findVocKeyWordByBrandIdAndStatus(Long brandId,
			UmpCheckStatus status) {
		String sql="select o from VocKeyWord o where o.vocBrandId="+brandId+" and o.status!=:status";
		TypedQuery<VocKeyWord> query = entityManager().createQuery(sql, VocKeyWord.class);
		query.setParameter("status", status);
		return query.getResultList();
		
	}
	
}

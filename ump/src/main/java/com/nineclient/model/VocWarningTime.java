package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocWarningTime {

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    @javax.validation.constraints.NotNull
    private Long warningTime;
    @javax.validation.constraints.NotNull
    private String companyCode;
    
    
    
	public Long getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(Long warningTime) {
		this.warningTime = warningTime;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("createTime", "warningTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocWarningTime().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocWarningTimes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocWarningTime o", Long.class).getSingleResult();
    }

	public static List<VocWarningTime> findAllVocWarningTimes() {
        return entityManager().createQuery("SELECT o FROM VocWarningTime o", VocWarningTime.class).getResultList();
    }

	public static List<VocWarningTime> findAllVocWarningTimes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWarningTime o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWarningTime.class).getResultList();
    }

	public static VocWarningTime findVocWarningTime(Long id) {
        if (id == null) return null;
        return entityManager().find(VocWarningTime.class, id);
    }

	public static List<VocWarningTime> findVocWarningTimeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocWarningTime o", VocWarningTime.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocWarningTime> findVocWarningTimeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWarningTime o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWarningTime.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocWarningTime attached = VocWarningTime.findVocWarningTime(this.id);
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
    public VocWarningTime merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocWarningTime merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public static VocWarningTime findVocWarningTimeByCompanyCode(
			String companyCode) {
		if(companyCode==null)return null;
		return entityManager().createQuery("SELECT o FROM VocWarningTime o where o.companyCode='"+companyCode+"'", VocWarningTime.class).getSingleResult();
	}
}

package com.nineclient.model;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.VocWorkOrderEmergency;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocWorkOrder {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
    /**
     */
    @NotNull
    private String updateReason;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;



	@Version
    @Column(name = "version")
    private Integer version;
    
	@ManyToOne
	private VocEmailGroup vocEmailGroup;
	
	/**
	 * 优先级
	 */
	private VocWorkOrderEmergency vocWorkOrderEmergency;
	
	/**
	 * 工单类型
	 */
	@ManyToOne
	private VocWorkOrderType vocWorkOrderType;
	
	 @NotNull
	 private String companyCode;
	 
	
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
	
	

	public VocEmailGroup getVocEmailGroup() {
		return vocEmailGroup;
	}

	public void setVocEmailGroup(VocEmailGroup vocEmailGroup) {
		this.vocEmailGroup = vocEmailGroup;
	}
	
	public String getUpdateReason() {
        return this.updateReason;
    }

	public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	

	public VocWorkOrderEmergency getVocWorkOrderEmergency() {
		return vocWorkOrderEmergency;
	}

	public void setVocWorkOrderEmergency(VocWorkOrderEmergency vocWorkOrderEmergency) {
		this.vocWorkOrderEmergency = vocWorkOrderEmergency;
	}

	public VocWorkOrderType getVocWorkOrderType() {
		return vocWorkOrderType;
	}

	public void setVocWorkOrderType(VocWorkOrderType vocWorkOrderType) {
		this.vocWorkOrderType = vocWorkOrderType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("updateReason", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocWorkOrder().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocWorkOrders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocWorkOrder o", Long.class).getSingleResult();
    }

	public static List<VocWorkOrder> findAllVocWorkOrders() {
        return entityManager().createQuery("SELECT o FROM VocWorkOrder o", VocWorkOrder.class).getResultList();
    }

	public static List<VocWorkOrder> findAllVocWorkOrders(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWorkOrder o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWorkOrder.class).getResultList();
    }

	public static VocWorkOrder findVocWorkOrder(Long id) {
        if (id == null) return null;
        return entityManager().find(VocWorkOrder.class, id);
    }

	public static List<VocWorkOrder> findVocWorkOrderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocWorkOrder o", VocWorkOrder.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocWorkOrder> findVocWorkOrderEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocWorkOrder o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocWorkOrder.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocWorkOrder attached = VocWorkOrder.findVocWorkOrder(this.id);
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
    public VocWorkOrder merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocWorkOrder merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}

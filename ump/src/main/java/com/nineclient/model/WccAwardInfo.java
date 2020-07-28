package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccAwardInfo {

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 400)
    private String awardName;

    /**
     */
    @NotNull
    @Size(min = 1, max = 4000)
    private String awardInfo;

    /**
     */
    @NotNull
    private Integer winRate;

    /**
     */
    private String cdkey;

    /**
     */
    @NotNull
    @Value("0")
    private int awardNum;
    
    private int awardLevel;
    
    @OneToMany(mappedBy="awardInfo")
    private Set<WccSncode> sncodes;

	public Set<WccSncode> getSncodes() {
		return sncodes;
	}

	public void setSncodes(Set<WccSncode> sncodes) {
		this.sncodes = sncodes;
	}

	public int getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(int awardLevel) {
		this.awardLevel = awardLevel;
	}

	public WccLotteryActivity getLotteryActivity() {
		return lotteryActivity;
	}

	public void setLotteryActivity(WccLotteryActivity lotteryActivity) {
		this.lotteryActivity = lotteryActivity;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private WccLotteryActivity lotteryActivity;

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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("isDeleted", "awardName", "awardInfo", "winRate", "cdkey", "awardNum");

	public static final EntityManager entityManager() {
        EntityManager em = new WccAwardInfo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWccAwardInfoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WccAwardInfo o", Long.class).getSingleResult();
    }

	public static List<WccAwardInfo> findAllWccAwardInfoes() {
        return entityManager().createQuery("SELECT o FROM WccAwardInfo o", WccAwardInfo.class).getResultList();
    }
	
	public static void removeWccAwardInfoByActivityId(Long id){
		entityManager().createQuery("DELETE WccAwardInfo o WHERE o.lotteryActivity="+id);
	}
	
	public static List<WccAwardInfo> findAllWccAwardInfoesByActivityId(Long id) {
        return entityManager().createQuery("SELECT o FROM WccAwardInfo o WHERE o.lotteryActivity = "+ id, WccAwardInfo.class).getResultList();
    }

	public static List<WccAwardInfo> findAllWccAwardInfoes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccAwardInfo o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccAwardInfo.class).getResultList();
    }

	public static WccAwardInfo findWccAwardInfo(Long id) {
        if (id == null) return null;
        return entityManager().find(WccAwardInfo.class, id);
    }

	public static List<WccAwardInfo> findWccAwardInfoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WccAwardInfo o", WccAwardInfo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<WccAwardInfo> findWccAwardInfoEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WccAwardInfo o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WccAwardInfo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WccAwardInfo attached = WccAwardInfo.findWccAwardInfo(this.id);
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
    public WccAwardInfo merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccAwardInfo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getAwardName() {
        return this.awardName;
    }

	public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

	public String getAwardInfo() {
        return this.awardInfo;
    }

	public void setAwardInfo(String awardInfo) {
        this.awardInfo = awardInfo;
    }


	public String getCdkey() {
        return this.cdkey;
    }

	public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

	public int getAwardNum() {
        return this.awardNum;
    }

	public void setAwardNum(int awardNum) {
        this.awardNum = awardNum;
    }

	public Integer getWinRate() {
		return winRate;
	}

	public void setWinRate(Integer winRate) {
		this.winRate = winRate;
	}
	
}

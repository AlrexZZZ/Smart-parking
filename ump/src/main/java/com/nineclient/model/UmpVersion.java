package com.nineclient.model;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpVersion  implements java.io.Serializable{

    /**
     */
    private String remark;

    private String versionName;

    /**
     */
    @ManyToOne
    private UmpProduct product;
    @ManyToMany(fetch=FetchType.LAZY)
    private Set<UmpAuthority> umpAuthoritys;
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("remark", "versionName", "product", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpVersion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpVersions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpVersion o", Long.class).getSingleResult();
    }
	
	/**根据产品ID查询版本
	 * @param productId
	 * @return
	 */
	public static List<UmpVersion> findUmpVersionByProduct(Long productId) {
	        return entityManager().createQuery("SELECT o FROM UmpVersion o where o.product="+productId, UmpVersion.class).getResultList();
	    }

	public static List<UmpVersion> findAllUmpVersions() {
        return entityManager().createQuery("SELECT o FROM UmpVersion o", UmpVersion.class).getResultList();
    }
	/**
	 * 根据版本名称查询
	 * @param versionName
	 * @param productId
	 * @return
	 */
	public static List<UmpVersion> findAllUmpVersionsByName(String versionName,Long productId) {
        return entityManager().createQuery("SELECT o FROM UmpVersion o join product p where o.versionName='"+versionName+"' and p.id="+productId, UmpVersion.class).getResultList();
    }
	public static List<UmpVersion> findAllUmpVersions(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpVersion o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpVersion.class).getResultList();
    }

	public static UmpVersion findUmpVersion(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpVersion.class, id);
    }

	public static List<UmpVersion> findUmpVersionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpVersion o", UmpVersion.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpVersion> findUmpVersionEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpVersion o order by o.createTime desc ";
        return entityManager().createQuery(jpaQuery, UmpVersion.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpVersion attached = UmpVersion.findUmpVersion(this.id);
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
    public UmpVersion merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpVersion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getVersionName() {
        return this.versionName;
    }

	public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

	public UmpProduct getProduct() {
        return this.product;
    }

	public void setProduct(UmpProduct product) {
        this.product = product;
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

	
	public Set<UmpAuthority> getUmpAuthoritys() {
		return umpAuthoritys;
	}

	public void setUmpAuthoritys(Set<UmpAuthority> umpAuthoritys) {
		this.umpAuthoritys = umpAuthoritys;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public static String toJsonArray(Collection<UmpVersion> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpVersion> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<UmpVersion> fromJsonArrayToUmpBrands(String json) {
        return new JSONDeserializer<List<UmpVersion>>()
        .use("values", UmpVersion.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	/**
	 * 根据产品id查询
	 * @param productId
	 * @return
	 */
	public static List<UmpVersion> findAllVersionsbuyProductId(Long productId) {
		String sql="SELECT o FROM UmpVersion o join o.product p where p.id="+productId;
		return entityManager().createQuery(sql, UmpVersion.class).getResultList();
	}
}

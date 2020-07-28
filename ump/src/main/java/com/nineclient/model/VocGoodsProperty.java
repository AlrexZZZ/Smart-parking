package com.nineclient.model;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.VocValueType;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocGoodsProperty {

    /**
     */
    @NotNull
    private String propertyName;

    /**
     */
    private String propertyValue;
    @ManyToOne
    private UmpBusinessType umpBusinessType;
    /**
     */
    @NotNull
    @Size(min=1,max=40)
    private String valueType;
    
    private Date createTime;
    @OneToMany(mappedBy="vocGoodsProperty")
    private Set<VocGoods> vocGoods;
    /**
     * 单位
     */
    @NotNull
    @Size(min=1,max=40)
    private String unit;
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getPropertyName() {
        return this.propertyName;
    }

	public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

	public String getPropertyValue() {
        return this.propertyValue;
    }

	public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

	
	











	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
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

	public Set<VocGoods> getVocGoods() {
		return vocGoods;
	}

	public void setVocGoods(Set<VocGoods> vocGoods) {
		this.vocGoods = vocGoods;
	}














	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("propertyName", "propertyValue", "goodsId", "valueType");

	public static final EntityManager entityManager() {
        EntityManager em = new VocGoodsProperty().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocGoodsPropertys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocGoodsProperty o", Long.class).getSingleResult();
    }

	public static List<VocGoodsProperty> findAllVocGoodsPropertys() {
        return entityManager().createQuery("SELECT o FROM VocGoodsProperty o", VocGoodsProperty.class).getResultList();
    }

	public static List<VocGoodsProperty> findAllVocGoodsPropertys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocGoodsProperty o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocGoodsProperty.class).getResultList();
    }
	/**
	 * 根据行业id查询商品数据
	 * @param id
	 * @return
	 */
	public static List<VocGoodsProperty> findVocGoodsPropertyByBusinessId(Long id) {
        if (id == null) return null;
        String sql = "SELECT o FROM VocGoodsProperty o join o.umpBusinessType u where u.id ="+id;
        return entityManager().createQuery(sql, VocGoodsProperty.class).getResultList();
    }
	public static VocGoodsProperty findVocGoodsProperty(Long id) {
        if (id == null) return null;
        return entityManager().find(VocGoodsProperty.class, id);
    }

	public static List<VocGoodsProperty> findVocGoodsPropertyEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocGoodsProperty o", VocGoodsProperty.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocGoodsProperty> findVocGoodsPropertyEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocGoodsProperty o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocGoodsProperty.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocGoodsProperty attached = VocGoodsProperty.findVocGoodsProperty(this.id);
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
    public VocGoodsProperty merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocGoodsProperty merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public UmpBusinessType getUmpBusinessType() {
		return umpBusinessType;
	}

	public void setUmpBusinessType(UmpBusinessType umpBusinessType) {
		this.umpBusinessType = umpBusinessType;
	}
	public static String toJsonArray(Collection<VocGoodsProperty> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocGoodsProperty> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocGoodsProperty> fromJsonArrayToVocGoodsPropertys(String json) {
        return new JSONDeserializer<List<VocGoodsProperty>>()
        .use("values", VocGoodsProperty.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	
}

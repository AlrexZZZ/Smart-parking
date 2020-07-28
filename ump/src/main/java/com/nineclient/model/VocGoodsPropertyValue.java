package com.nineclient.model;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocGoodsPropertyValue {

    /**
     */
    private String name;

    /**
     */
    private String propertyValue;
    @ManyToOne
    private VocSku vocSku;

    
	public VocSku getVocSku() {
		return vocSku;
	}

	public void setVocSku(VocSku vocSku) {
		this.vocSku = vocSku;
	}

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static VocGoodsPropertyValue fromJsonToVocGoodsPropertyValue(String json) {
        return new JSONDeserializer<VocGoodsPropertyValue>()
        .use(null, VocGoodsPropertyValue.class).deserialize(json);
    }

	public static String toJsonArray(Collection<VocGoodsPropertyValue> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocGoodsPropertyValue> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocGoodsPropertyValue> fromJsonArrayToVocGoodsPropertyValues(String json) {
        return new JSONDeserializer<List<VocGoodsPropertyValue>>()
        .use("values", VocGoodsPropertyValue.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "propertyValue");

	public static final EntityManager entityManager() {
        EntityManager em = new VocGoodsPropertyValue().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocGoodsPropertyValues() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocGoodsPropertyValue o", Long.class).getSingleResult();
    }
	/**
	 * 根据skuid 查询
	 * @return
	 */
	public static List<VocGoodsPropertyValue> findAllVocGoodsPropertyValuesBySkuId(Long id) {
        return entityManager().createQuery("SELECT o FROM VocGoodsPropertyValue o join o.vocSku v where v.id="+id, VocGoodsPropertyValue.class).getResultList();
    }

	
	public static List<VocGoodsPropertyValue> findAllVocGoodsPropertyValues() {
        return entityManager().createQuery("SELECT o FROM VocGoodsPropertyValue o", VocGoodsPropertyValue.class).getResultList();
    }

	public static List<VocGoodsPropertyValue> findAllVocGoodsPropertyValues(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocGoodsPropertyValue o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocGoodsPropertyValue.class).getResultList();
    }

	public static VocGoodsPropertyValue findVocGoodsPropertyValue(Long id) {
        if (id == null) return null;
        return entityManager().find(VocGoodsPropertyValue.class, id);
    }

	public static List<VocGoodsPropertyValue> findVocGoodsPropertyValueEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocGoodsPropertyValue o", VocGoodsPropertyValue.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocGoodsPropertyValue> findVocGoodsPropertyValueEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocGoodsPropertyValue o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocGoodsPropertyValue.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocGoodsPropertyValue attached = VocGoodsPropertyValue.findVocGoodsPropertyValue(this.id);
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
    public VocGoodsPropertyValue merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocGoodsPropertyValue merged = this.entityManager.merge(this);
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

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getPropertyValue() {
        return this.propertyValue;
    }

	public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}

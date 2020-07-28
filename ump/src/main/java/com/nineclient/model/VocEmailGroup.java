package com.nineclient.model;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocEmailGroup {

    /**
     */
    @NotNull
    private String name;

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	@ManyToMany
	private Set<VocEmail> vocEmails;
	
//	@OneToMany(mappedBy="vocEmailGroup")
//    private Set<VocWorkOrder> workOrder; 
	
	
	public Set<VocEmail> getVocEmails() {
		return vocEmails;
	}

	public void setVocEmails(Set<VocEmail> vocEmails) {
		this.vocEmails = vocEmails;
	}

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
	
	private Long umpCompanyId;

	
	public Long getUmpCompanyId() {
		return umpCompanyId;
	}

	public void setUmpCompanyId(Long umpCompanyId) {
		this.umpCompanyId = umpCompanyId;
	}


	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("umpOperatorId", "name");

	public static final EntityManager entityManager() {
        EntityManager em = new VocEmailGroup().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocEmailGroups() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocEmailGroup o", Long.class).getSingleResult();
    }

	public static List<VocEmailGroup> findAllVocEmailGroups() {
        return entityManager().createQuery("SELECT o FROM VocEmailGroup o", VocEmailGroup.class).getResultList();
    }
	public static List<VocEmailGroup> findAllVocEmailGroupsByCompany(UmpCompany company) {
		String sql="SELECT o FROM VocEmailGroup o where o.umpCompanyId="+company.getId();
        return entityManager().createQuery(sql, VocEmailGroup.class).getResultList();
    }
	public static List<VocEmailGroup> findAllVocEmailGroups(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocEmailGroup o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocEmailGroup.class).getResultList();
    }
	/**
	 * 根据名字和公司id查询
	 * @param name
	 * @return
	 */
	public static List<VocEmailGroup> findVocEmailGroupByName(String name,Long id) {
        if (name == null&&id==null) return null;
        String sql = "SELECT o FROM VocEmailGroup o where 1=1 ";
        if(name!=null){
        	sql+=" and o.name='"+name+"'";
        }
        if(id!=null){
        	sql+=" and o.umpCompanyId="+id;
        }
        return  entityManager().createQuery(sql, VocEmailGroup.class).getResultList();
    }
	public static VocEmailGroup findVocEmailGroup(Long id) {
        if (id == null) return null;
        return entityManager().find(VocEmailGroup.class, id);
    }

	public static List<VocEmailGroup> findVocEmailGroupEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocEmailGroup o", VocEmailGroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocEmailGroup> findVocEmailGroupEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocEmailGroup o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocEmailGroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocEmailGroup attached = VocEmailGroup.findVocEmailGroup(this.id);
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
    public VocEmailGroup merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocEmailGroup merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static VocEmailGroup fromJsonToVocEmailGroup(String json) {
        return new JSONDeserializer<VocEmailGroup>()
        .use(null, VocEmailGroup.class).deserialize(json);
    }

	public static String toJsonArray(Collection<VocEmailGroup> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocEmailGroup> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocEmailGroup> fromJsonArrayToVocEmailGroups(String json) {
        return new JSONDeserializer<List<VocEmailGroup>>()
        .use("values", VocEmailGroup.class).deserialize(json);
    }
}

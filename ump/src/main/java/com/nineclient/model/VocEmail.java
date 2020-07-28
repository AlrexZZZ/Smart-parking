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
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class VocEmail {

    /**
     */
    @Size(min = 1, max = 50)
    private String name;

    /**
     */
    @Size(max = 50)
    @Pattern(regexp = "(^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$)")
    private String email;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    private Boolean isVisable;
    /**用户ID**/
    private Long operatorId;
    
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    private long parentId;
    @ManyToMany(mappedBy="vocEmails")
    private Set<VocEmailGroup> vocEmailGroups;
    
    private Long umpCompanyId;
    
    
	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getUmpCompanyId() {
		return umpCompanyId;
	}

	public void setUmpCompanyId(Long umpCompanyId) {
		this.umpCompanyId = umpCompanyId;
	}

	public Set<VocEmailGroup> getVocEmailGroups() {
		return vocEmailGroups;
	}

	public void setVocEmailGroups(Set<VocEmailGroup> vocEmailGroups) {
		this.vocEmailGroups = vocEmailGroups;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Boolean getIsVisable() {
        return this.isVisable;
    }

	public void setIsVisable(Boolean isVisable) {
        this.isVisable = isVisable;
    }

	public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public long getParentId() {
        return this.parentId;
    }

	public void setParentId(long parentId) {
        this.parentId = parentId;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "email", "remark", "isDeleted", "isVisable", "createTime", "parentId");

	public static final EntityManager entityManager() {
        EntityManager em = new VocEmail().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocEmails() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocEmail o", Long.class).getSingleResult();
    }
	/**
	 * 根据id查询邮件
	 * @return
	 */
	public static List<VocEmail> findAllVocEmailsByIds(String ids) {
        return entityManager().createQuery("SELECT o FROM VocEmail o where o.id in("+ids+")", VocEmail.class).getResultList();
    }
	public static List<VocEmail> findAllVocEmails() {
        return entityManager().createQuery("SELECT o FROM VocEmail o", VocEmail.class).getResultList();
    }
	/**
	 * 根据公司查询邮件
	 * @param company
	 * @return
	 */
	public static List<VocEmail> findAllVocEmailsByCompany(UmpCompany company) {
		if(company==null) return null;
        return entityManager().createQuery("SELECT o FROM VocEmail o where o.umpCompanyId="+company.getId(), VocEmail.class).getResultList();
    }
	/**
	 * 根据公司 和邮件名称查询邮件
	 * @param name
	 * @param company
	 * @return
	 */
	public static List<VocEmail> findAllVocEmailsByName(String name,UmpCompany company) {
		if(name==null) return null;
        return entityManager().createQuery("SELECT o FROM VocEmail o where o.name='"+name+"' and o.umpCompanyId="+company.getId(), VocEmail.class).getResultList();
    }
	public static List<VocEmail> findAllVocEmails(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocEmail o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocEmail.class).getResultList();
    }

	public static VocEmail findVocEmail(Long id) {
        if (id == null) return null;
        return entityManager().find(VocEmail.class, id);
    }
	/**
	 * 根据邮件组查询邮件
	 * @param group
	 * @return
	 */
	public static List<VocEmail> findVocEmailEntriesByGrouId(VocEmailGroup group) {
		String sql ="SELECT o FROM VocEmail o join o.vocEmailGroups g where g.id=:groupId";
		if(group==null)return null;
		TypedQuery<VocEmail> query =  entityManager().createQuery(sql, VocEmail.class);
		query.setParameter("groupId", group.getId());
        return query.getResultList();
    }
	public static List<VocEmail> findVocEmailEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocEmail o", VocEmail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocEmail> findVocEmailEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocEmail o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocEmail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            VocEmail attached = VocEmail.findVocEmail(this.id);
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
    public VocEmail merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocEmail merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static VocEmail fromJsonToVocEmailGroup(String json) {
        return new JSONDeserializer<VocEmail>()
        .use(null, VocEmail.class).deserialize(json);
    }

	public static String toJsonArray(Collection<VocEmail> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocEmail> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	public static String toJsonArrayNameAndId(Collection<VocEmail> collection) {
		String json="[";
		boolean	flag=true;
		for (VocEmail vocEmail : collection) {
			if(flag){
				flag=false;
				json+="{id:"+vocEmail.getId()+",name:\'"+vocEmail.getName()+"\'}";
			}else{
				json+=",{id:"+vocEmail.getId()+",name:\'"+vocEmail.getName()+"\'}";
			}
		}
		json+="]";
       return json;
    }
	public static Collection<VocEmail> fromJsonArrayToVocEmails(String json) {
        return new JSONDeserializer<List<VocEmail>>()
        .use("values", VocEmail.class).deserialize(json);
    }
	@Transactional
	public static void meger(List<VocEmail> listEmail) {
		if(listEmail==null) return;
		for (VocEmail vocEmail : listEmail) {
			vocEmail.merge();
		}
	}
	/**
	 * 根据用户id查询邮件
	 * @param operatorId
	 * @return
	 */
	public static VocEmail findVocEmailByOperator(Long operatorId) {
		String sql=" select o from VocEmail o where o.operatorId="+operatorId;
		 return entityManager().createQuery(sql, VocEmail.class).getSingleResult();
	}
}

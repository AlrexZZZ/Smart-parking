package com.nineclient.model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpLog implements java.io.Serializable {

    /**
     *登陆者
     */
    @NotNull
    @Size(min = 1, max = 120)
    private String account;

    /**
     * 登陆IP
     */
    @NotNull
    @Size(min = 1, max = 50)
    private String loginIp;

    /**
     * 登陆时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 退出时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginOutTime;

    /**
     * 状态
     */
    private Boolean isStatus;

    /**
     * 备注
     */
    @Size(max = 4000)
    private String remark;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public String getAccount() {
        return this.account;
    }

	public void setAccount(String account) {
        this.account = account;
    }

	public String getLoginIp() {
        return this.loginIp;
    }

	public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

	public Date getLoginTime() {
        return this.loginTime;
    }

	public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

	public Date getLoginOutTime() {
        return this.loginOutTime;
    }

	public void setLoginOutTime(Date loginOutTime) {
        this.loginOutTime = loginOutTime;
    }

	public Boolean getIsStatus() {
        return this.isStatus;
    }

	public void setIsStatus(Boolean isStatus) {
        this.isStatus = isStatus;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
    }

	/*public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }*/

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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("account", "loginIp", "loginTime", "loginOutTime", "isStatus", "remark");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpLog().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpLogs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpLog o", Long.class).getSingleResult();
    }

	public static List<UmpLog> findAllUmpLogs() {
        return entityManager().createQuery("SELECT o FROM UmpLog o", UmpLog.class).getResultList();
    }

	public static List<UmpLog> findAllUmpLogs(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpLog o";
        jpaQuery = jpaQuery + " ORDER BY loginTime DESC ";
        /* if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
           if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }*/
        return entityManager().createQuery(jpaQuery, UmpLog.class).getResultList();
    }

	public static List<UmpLog> findAllUmpLogsByFiled(int firstResult,
			int maxResults, String starTime, String endTime, String account2) {
		String jpaQuery = "SELECT o FROM UmpLog o where 1=1";
        if (null != account2 && !"".equals(account2)) {
            jpaQuery = jpaQuery + " and o.account like '%"+account2+"%'";
        }
        if (null != starTime && !"".equals(starTime)) {
        	
       	 jpaQuery = jpaQuery + " and o.loginTime >= '"+starTime+"'";
        }
        if (null != endTime && !"".equals(endTime)) {
          	 jpaQuery = jpaQuery + " and o.loginOutTime <= '"+endTime+"'";
        }
        jpaQuery = jpaQuery + " ORDER BY o.loginTime desc";
        
        System.out.println(jpaQuery);
        
        return entityManager().createQuery(jpaQuery.toString(), UmpLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}
	
	public static UmpLog findUmpLog(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpLog.class, id);
    }

	public static UmpLog findUmpLogByLogId(long umpLogId) {
		String hql = "SELECT o FROM UmpLog o where  o.id = "+ umpLogId;
    	System.out.println(hql.toString());
    	try {
    		return (UmpLog) entityManager().createQuery(hql).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<UmpLog> findUmpLogEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpLog o", UmpLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpLog> findUmpLogEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpLog o";
        jpaQuery = jpaQuery + " ORDER BY loginTime DESC ";
       /* if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }*/
        return entityManager().createQuery(jpaQuery, UmpLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpLog attached = UmpLog.findUmpLog(this.id);
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
    public UmpLog merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpLog merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static Long countUmpLogsByFiled(String starTime, String endTime,
			String account2) {
		String jpaQuery = "SELECT COUNT(o) FROM UmpLog o where 1=1";
        if (null != account2 && !"".equals(account2)) {
            jpaQuery = jpaQuery + " and o.account like '%"+account2+"%'";
        }
        if (null != starTime && !"".equals(starTime)) {
        	
       	 jpaQuery = jpaQuery + " and o.loginTime >= '"+starTime+"'";
        }
        if (null != endTime && !"".equals(endTime)) {
          	 jpaQuery = jpaQuery + " and o.loginOutTime <= '"+endTime+"'";
        }
        jpaQuery = jpaQuery + " ORDER BY o.loginTime desc";
        
        System.out.println(jpaQuery.toString());
		return entityManager().createQuery(jpaQuery.toString(), Long.class).getSingleResult();
	}

	public static String toJsonArray(Collection<UmpLog> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpLog> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<UmpLog> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<UmpLog>>()
        .use("values", UmpLog.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	
    public static Long getloginNum(String account){
    	String sql="SELECT COUNT(o) FROM UmpLog o where o.account = '" +account+"'";
    	Long count =entityManager().createQuery(sql, Long.class).getSingleResult();
    	return count;
    }


}

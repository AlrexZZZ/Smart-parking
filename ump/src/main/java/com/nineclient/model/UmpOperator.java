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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
public class UmpOperator implements java.io.Serializable {

    /**
     * 用户邮箱
     */
    @Pattern(regexp = "(^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$)")
    private String email;

    /**
     * 用户昵称
     */
    @NotNull
    private String operatorName;

    /**
     * 账号
     */
    @NotNull
    private String account;

    /**
     * 密码
     */
    @NotNull
    @Size(min =6, max = 32)
    private String password;

    /**
     * 座机
     */
    @Pattern(regexp = "((\\d{3}-|\\d{4}-)?(\\d{8}|\\d{7})?)")
    private String telephone;

    /**
     * 手机号码
     */
    @Pattern(regexp = "(^[0-9]{11}$)")
    private String mobile;

    /**
     * 状态
     */
    private Boolean active;

    /**
     * 是否自动分配
     */
    private Boolean autoAllocate;

    /**
     * 是否删除
     */
    private Boolean isDeleted;
    
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAutoAllocate() {
        return this.autoAllocate;
    }

    public void setAutoAllocate(Boolean autoAllocate) {
        this.autoAllocate = autoAllocate;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("email", "operatorName", "account", "password", "telephone", "mobile", "active", "autoAllocate", "isDeleted", "createTime");

    public static final EntityManager entityManager() {
        EntityManager em = new UmpOperator().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countUmpOperators() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpOperator o", Long.class).getSingleResult();
    }

    public static List<UmpOperator> findAllUmpOperators() {
        return entityManager().createQuery("SELECT o FROM UmpOperator o", UmpOperator.class).getResultList();
    }
    
    public static List<UmpOperator> findCompanyUmpOperators(Long companyId ) {
        return entityManager().createQuery("SELECT o FROM UmpOperator o where o.company = "+companyId+" and o.createTime = (select min(a.createTime) from UmpOperator a WHERE a.company = "+companyId+" )", UmpOperator.class).getResultList();
    }
    

    public static List<UmpOperator> findAllUmpOperators(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpOperator o ORDER BY o.createTime DESC";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpOperator.class).getResultList();
    }

    public static List<UmpOperator> findAllUmpOperatorsByFiled(UmpOperator umpOperator) {
        Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(UmpOperator.class);
        criteria.add(Restrictions.eq("isDeleted", true));
        if (null != umpOperator.getUrole() && !"".equals(umpOperator.getUrole())) {
            criteria.add(Restrictions.eq("urole", umpOperator.getUrole()));
        }
        if (null != umpOperator.getActive() && !"".equals(umpOperator.getActive())) {
            criteria.add(Restrictions.eq("active", umpOperator.getActive()));
        }
        if (null != umpOperator.getAccount() && !"".equals(umpOperator.getAccount())) {
            criteria.add(Restrictions.like("account", umpOperator.getAccount(), MatchMode.ANYWHERE));
        }
        if (null != umpOperator.getEmail() && !"".equals(umpOperator.getEmail())) {
            criteria.add(Restrictions.like("email", umpOperator.getEmail(), MatchMode.ANYWHERE));
        }
        System.out.println(criteria.list() + "--test--");
        List<UmpOperator> list = criteria.list();
        return list;
    }

    
    public static List<UmpOperator> findAllUmpOperatorsByFiled2(int firstResult,int maxResults, UmpOperator umpOperator) {
    	 String jpaQuery = "SELECT o FROM UmpOperator o where 1=1 and o.isDeleted = 1 ";
    	 if (null != umpOperator.getUrole() && !"".equals(umpOperator.getUrole())) {
           //  criteria.add(Restrictions.eq("urole", umpOperator.getUrole()));
             jpaQuery = jpaQuery + " and o.urole = " + umpOperator.getUrole();
         }
         if (null != umpOperator.getActive() && !"".equals(umpOperator.getActive())) {
            // criteria.add(Restrictions.eq("active", umpOperator.getActive()));
             jpaQuery = jpaQuery + " and o.active = " + umpOperator.getActive();
         }
         if (null != umpOperator.getAccount() && !"".equals(umpOperator.getAccount())) {
             //criteria.add(Restrictions.like("account", umpOperator.getAccount(), MatchMode.ANYWHERE));
             jpaQuery = jpaQuery + " and o.account like ' %"+umpOperator.getAccount()+"%'";
         }
         if (null != umpOperator.getEmail() && !"".equals(umpOperator.getEmail())) {
            // criteria.add(Restrictions.like("email", umpOperator.getEmail(), MatchMode.ANYWHERE));
        	 jpaQuery = jpaQuery + " and o.email like ' %"+umpOperator.getEmail()+"%'";
         }
         jpaQuery = jpaQuery + " ORDER BY o.createTime desc";
         return entityManager().createQuery(jpaQuery, UmpOperator.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}
    
    public static UmpOperator findUmpOperator(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpOperator.class, id);
    }

    public static List<UmpOperator> findUmpOperatorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpOperator o", UmpOperator.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    /**
     * 根據賬號和公司id查詢時否有該信息
     * @param account
     * @param id
     * @return
     */
    public static UmpOperator findUmpOperatorByAccount(String account,String companyCode) {
    	String hql = "SELECT o FROM UmpOperator o where  o.isDeleted = true and o.company.isDeleted = true and o.account = '"+account+"' and o.company.companyCode = '"+companyCode+"'";
    	try {
    		return (UmpOperator) entityManager().createQuery(hql).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
    
    /**
     * 判断账号密码是否正确
     * @param account
     * @param password
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean findUmpOperatorByAccountAndPass(String account,String password,String companyCode) {
    	String hql = "SELECT o FROM UmpOperator o where o.account = '"+account+"' and o.password = '"+password+"' and o.company.companyCode = '"+companyCode+"'";
		List<UmpOperator> UOlist = entityManager().createQuery(hql).getResultList();
		if(UOlist.size() > 0){
			return true;
		}
    	return false;
	}
    
    public static List<UmpOperator> findUmpOperatorEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpOperator o ORDER BY o.createTime DESC";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpOperator.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpOperator attached = UmpOperator.findUmpOperator(this.id);
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
    public UmpOperator merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpOperator merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    /**
     */
    @ManyToOne
    private UmpRole urole;

    public UmpRole getUrole() {
        return this.urole;
    }

    public void setUrole(UmpRole urole) {
        this.urole = urole;
    }

    /**
     */
    @ManyToOne
    private UmpCompany company;


	public UmpCompany getCompany() {
        return this.company;
    }

	public void setCompany(UmpCompany company) {
        this.company = company;
    }
	


	@Autowired
    private transient MailSender mailTemplate;

    public static String sendMessage(String subject, String mailTo, String message) {
    	List<UmpConfig> configs = UmpConfig.findConfigsByKeyType("emailConfig");
    	  if (configs.size() > 0) {
    	   JavaMailSenderImpl msi = new JavaMailSenderImpl();
    	   SimpleMailMessage mailMessage = new SimpleMailMessage();
    	   for (UmpConfig con : configs) {
    	    if ("HOST".equals(con.getName())) {
    	     msi.setHost(con.getKeyValue());
    	    }
    	    if ("PORT".equals(con.getName())) {
    	    	msi.setPort(Integer.parseInt(con.getKeyValue()));
    	    }
    	    if ("USERNAME".equals(con.getName())) {
    	     msi.setUsername(con.getKeyValue());
    	    }
    	    if ("PASSWORD".equals(con.getName())) {
    	     msi.setPassword(con.getKeyValue());
    	    }
    	    if ("SENDER".equals(con.getName())) {
    	    	mailMessage.setFrom(con.getKeyValue());
    	    }
    	   }
    	   mailMessage.setSubject(subject);
    	   mailMessage.setTo(mailTo);
    	   mailMessage.setText(message);
    	   try {
    		   msi.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
    			   
    	  }
		return "1";
    }

	/*public UmpCompany getUmpCompany() {
		return umpCompany;
	}

	public void setUmpCompany(UmpCompany umpCompany) {
		this.umpCompany = umpCompany;
	}*/

	public static List<UmpOperator> findUmpOperatorByAccount(String account) {
		if(null == account || "".equals(account)) return null;
		return entityManager().createQuery("SELECT o FROM UmpOperator o where o.account ='"+account+"'", UmpOperator.class).getResultList();
	}

	public static List<UmpOperator> findAllUmpOperatorByFiled(int firstResult,int maxResults, 
			String account2, String email2, Boolean active2,
			Long uroleId) {
		String jpaQuery = "SELECT o FROM UmpOperator o where 1=1";
        if (null != account2 && !"".equals(account2)) {
            jpaQuery = jpaQuery + " and o.account like '%"+account2+"%'";
        }
        if (null != email2 && !"".equals(email2)) {
            jpaQuery = jpaQuery + " and o.email like '%"+email2+"%'";
        }
        if (null != active2 && !"".equals(active2)) {
       	 jpaQuery = jpaQuery + " and o.active = "+active2;
        }
        if (uroleId !=null && uroleId >0) {
          	 jpaQuery = jpaQuery + " and o.urole ="+uroleId;
        }
        jpaQuery = jpaQuery + " ORDER BY o.createTime desc";
        
        System.out.println(jpaQuery);
        
        return entityManager().createQuery(jpaQuery.toString(), UmpOperator.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Long countUmpOperatorByFiled(String account2, String email2,
			Boolean active2, Long uroleId) {
		String jpaQuery = "SELECT COUNT(o) FROM UmpOperator o where 1=1";
        if (null != account2 && !"".equals(account2)) {
            jpaQuery = jpaQuery + " and o.account like '%"+account2+"%'";
        }
        if (null != email2 && !"".equals(email2)) {
            jpaQuery = jpaQuery + " and o.email like '%"+email2+"%'";
        }
        if (null != active2 && !"".equals(active2)) {
        	
       	 jpaQuery = jpaQuery + " and o.active = "+active2;
        }
        if (uroleId !=null && uroleId >0 ) {
          	 jpaQuery = jpaQuery + " and o.urole = "+uroleId;
        }
        jpaQuery = jpaQuery + " ORDER BY o.createTime desc";
        
        System.out.println(jpaQuery);
		return entityManager().createQuery(jpaQuery.toString(), Long.class).getSingleResult();
	}
	
	public static String toJsonArray(Collection<UmpOperator> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<UmpOperator> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
	
	public static Collection<UmpOperator> fromJsonArrayToVocWordExpressionss(String json) {
        return new JSONDeserializer<List<UmpOperator>>()
        .use("values", UmpOperator.class).deserialize(json);
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

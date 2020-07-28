package com.nineclient.model;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpConfig implements java.io.Serializable{

	/**
     */
	private String name;

	/**
     */
	private String keyValue;

	/**
     */
	private String remarks;

	/**
     */
	private String keyType;

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("name", "keyValue", "remarks", "keyType");

	public static final EntityManager entityManager() {
		EntityManager em = new UmpConfig().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countUmpConfigs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UmpConfig o",
				Long.class).getSingleResult();
	}

	public static List<UmpConfig> findAllUmpConfigs() {
		return entityManager().createQuery("SELECT o FROM UmpConfig o",
				UmpConfig.class).getResultList();
	}
	
	public static List<UmpConfig> findConfigsByKeyType(String keyType) {
		return entityManager().createQuery("SELECT o FROM UmpConfig o WHERE o.keyType='"+keyType+"'",
				UmpConfig.class).getResultList();
	}

	public static List<UmpConfig> findAllUmpConfigs(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM UmpConfig o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, UmpConfig.class)
				.getResultList();
	}
	
	public static UmpConfig findUmpConfig(Long id) {
		if (id == null)
			return null;
		return entityManager().find(UmpConfig.class, id);
	}

	public static List<UmpConfig> findUmpConfigEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM UmpConfig o", UmpConfig.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<UmpConfig> findUmpConfigEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM UmpConfig o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, UmpConfig.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			UmpConfig attached = UmpConfig.findUmpConfig(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public UmpConfig merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UmpConfig merged = this.entityManager.merge(this);
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyValue() {
		return this.keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	@Autowired
    private transient MailSender mailTemplate;

    public static String sendMessage(String subject, String mailTo, String message,List<UmpConfig> configs) {
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
			return "0";
		}
    			   
    	  }
		return "1";
    }
    

    public static String sendMessageHtml(String subject, String mailTo, String message,List<UmpConfig> configs) {
    	  if (configs.size() > 0) {
	    	   JavaMailSenderImpl msi = new JavaMailSenderImpl();
	    	   MimeMessage mailMessage = msi.createMimeMessage();
	    	   MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,"UTF-8");
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
	    	    	try {
						messageHelper.setFrom(con.getKeyValue());  // 来自  
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException("发件人邮箱地址出错！");
					}
	    	   }
	    	 }
	    	   msi.setDefaultEncoding("UTF-8");
	    	   // 配置文件对象  
	    	    Properties props = new Properties();      
	            props.put("mail.smtp.auth", "true"); // 是否进行验证  
			    Session session = Session.getInstance(props);  
			    msi.setSession(session);
	    	    
	    	    try {
					messageHelper.setSubject(subject); // 标题  
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	   try {
					messageHelper.setTo(mailTo); // 发送给谁  
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	try {
					messageHelper.setText(message,true);
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	  
	    	try {
	    		   msi.send(mailMessage);
			} catch (Exception e) {
				return "0";
			}
    	 }
    	  
		return "1";
    }
    
    public static String sendMessage(String subject, String mailTo, String message,String host,int port,String username,String password,String from) {
    		JavaMailSenderImpl msi = new JavaMailSenderImpl();
    		SimpleMailMessage mailMessage = new SimpleMailMessage();
    				msi.setHost(host);
    				msi.setPort(port);
    				msi.setUsername(username);
    				msi.setPassword(password);
    				mailMessage.setFrom(from);
    		mailMessage.setSubject(subject);
    		mailMessage.setTo(mailTo);
    		mailMessage.setText(message);
    		try {
    			msi.send(mailMessage);
    		} catch (Exception e) {
    			e.printStackTrace();
    			return "0";
    		}
    	return "1";
    }
}

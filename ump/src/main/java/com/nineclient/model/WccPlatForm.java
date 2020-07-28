package com.nineclient.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

//@Entity
//@Configurable
//@RooJavaBean
//@RooToString
//@RooJpaActiveRecord
public class WccPlatForm {
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private Boolean isDeleted;
	
	@Size(max = 4000)
	private String remark;
	
	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 200)
	private String plateType;
	
	@Size(max = 4000)
	private String authority;//公众号权限
	
	@OneToMany( targetEntity=WccPlatformUser.class, mappedBy="wccplatform",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<WccPlatformUser> platformUser = new HashSet<WccPlatformUser>();

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Set<WccPlatformUser> getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(Set<WccPlatformUser> platformUser) {
		this.platformUser = platformUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@PersistenceContext
	transient EntityManager entityManager;
	
	public static final EntityManager entityManager() {
		EntityManager em = new WccPlatForm().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	public static List<WccPlatForm> getWccPlatFormList(){
		return entityManager().createQuery("select u from WccPlatForm u",WccPlatForm.class).getResultList();
		
	}*/
}

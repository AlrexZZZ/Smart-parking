package com.nineclient.model;

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

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccProvince {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "wccProvince",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<WccCity> wccCitys;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@PersistenceContext
	transient EntityManager entityManager;
	
	public static final EntityManager entityManager() {
		EntityManager em = new WccPlatformUser().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<WccProvince> findAllWccProvinces() {
		String jpaQuery = "SELECT o FROM WccProvince o";
		return entityManager().createQuery(jpaQuery, WccProvince.class)
				.getResultList();
	}
	
	public static WccProvince findWccProvince(Long id) {
        if (id == null) return null;
        return entityManager().find(WccProvince.class, id);
    }
}

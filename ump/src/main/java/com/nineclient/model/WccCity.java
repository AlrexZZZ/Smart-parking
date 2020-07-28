package com.nineclient.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccCity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	private String name;
	
	@ManyToOne
	private WccProvince wccProvince;
	

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
	
	public WccProvince getWccProvince() {
		return wccProvince;
	}

	public void setWccProvince(WccProvince wccProvince) {
		this.wccProvince = wccProvince;
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

	public static List<WccCity> findAllWccWccCitys(WccProvince wccProvince) {
		String jpaQuery = "SELECT o FROM WccCity o where o.wccProvince = :wccProvince";
		TypedQuery<WccCity> query = entityManager().createQuery(jpaQuery, WccCity.class);
		query.setParameter("wccProvince", wccProvince);
		return query.getResultList();
	}
	
	public static WccCity findWccCity(Long id) {
        if (id == null) return null;
        return entityManager().find(WccCity.class, id);
    }
}

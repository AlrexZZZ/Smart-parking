package com.nineclient.cbd.wcc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.nineclient.interceptor.UmpInterceptor;
import com.nineclient.task.VocStandSkuTask;
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccDictionary {
	private static final Logger logger = Logger.getLogger(WccDictionary.class);
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	private Double isDelete;
	
	private String insertIp;
	
	private String insertPk;
	
	private Date insertTime;
	private String updateIp;
	private String updatePk;
	private Date updateTime;
	private String deleteIp;
	private String deletePk;
	private Date deleteTime;
	private String version;
	private String platformPk;
	private String typecode;
	private String typename;
	private String typetitle;
	private String typemark;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Double isDelete) {
		this.isDelete = isDelete;
	}
	public String getInsertIp() {
		return insertIp;
	}
	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}
	public String getInsertPk() {
		return insertPk;
	}
	public void setInsertPk(String insertPk) {
		this.insertPk = insertPk;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getUpdatePk() {
		return updatePk;
	}
	public void setUpdatePk(String updatePk) {
		this.updatePk = updatePk;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeleteIp() {
		return deleteIp;
	}
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeletePk() {
		return deletePk;
	}
	public void setDeletePk(String deletePk) {
		this.deletePk = deletePk;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPlatformPk() {
		return platformPk;
	}
	public void setPlatformPk(String platformPk) {
		this.platformPk = platformPk;
	}
	
	
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypetitle() {
		return typetitle;
	}
	public void setTypetitle(String typetitle) {
		this.typetitle = typetitle;
	}
	public String getTypemark() {
		return typemark;
	}
	public void setTypemark(String typemark) {
		this.typemark = typemark;
	}


	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new WccDictionary().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	public static List<WccDictionary> getCBD_WCC(String typecode,String typetitle ){
		 List<WccDictionary> list=null;
		 StringBuffer sql=new StringBuffer();
		 sql.append(" FROM WccDictionary o WHERE 1=1 ");
		 if(StringUtils.isNotBlank(typecode))
		 {
			 sql.append("typecode=:typecode");
		 }
		 if(StringUtils.isNotBlank(typetitle))
		 {
			 sql.append("typetitle=:typetitle");
		 }
		TypedQuery<WccDictionary> query= entityManager().createQuery(sql.toString(),WccDictionary.class);
		 if(StringUtils.isNotBlank(typecode))
		 {
				query.setParameter("typecode", typecode);
				
		 }
		 if(StringUtils.isNotBlank(typetitle))
		 {
				query.setParameter("typetitle", typetitle);
		 }
		 try {
			 list=query.getResultList();
		} catch (Exception e) {
			logger.error("Error:_________Query The Dictionayr ");
		}
		
		 return list;
	  
	}
}

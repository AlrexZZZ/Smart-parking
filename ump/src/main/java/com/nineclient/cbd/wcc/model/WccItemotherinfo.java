package com.nineclient.cbd.wcc.model;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.QueryModel;
//社区其他信息
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccItemotherinfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private Boolean isDelete;
	private String insertIp;
	private String insertPk;
	private Date insertTime;
	private String updateIp;
	private String updatePk;
	private Date updateTime;
	private String deleteIp;
	private String deletePk;
	private Date deleteTime;
	private String companyPk;
	private String organizationPk;
	
	
	/**
	 * 
	 */
	private String platformPk;
	/**
	 * 图片地址
	 */
	@Size(max = 4000)
	private String otherPic;
	/**
	 * 列表标题
	 */
	@Size(max = 500)
	private String otherTitle;
	/**
	 * 内容
	 */
	@Size(max = 4000)
	private String otherIntro;
	/**
	 * 项目PK
	 */
	@ManyToOne
	private WccAppartment itemPk;
	
	/**
	 * 类型
	 */
	@Size(max = 500)
	private String otherType;
	/**
	 * 发布日期
	 */
	private Date publicTime;

	/**
	 * 详情标题
	 */
	@Size(max = 8000)
	private String introTitle;
	/**
	 * 跳转地址
	 */
	@Size(max = 4000)
	private String url;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
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
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	public String getOrganizationPk() {
		return organizationPk;
	}
	public void setOrganizationPk(String organizationPk) {
		this.organizationPk = organizationPk;
	}
	public String getPlatformPk() {
		return platformPk;
	}
	public void setPlatformPk(String platformPk) {
		this.platformPk = platformPk;
	}
	public String getOtherPic() {
		return otherPic;
	}
	public void setOtherPic(String otherPic) {
		this.otherPic = otherPic;
	}
	public String getOtherTitle() {
		return otherTitle;
	}
	public void setOtherTitle(String otherTitle) {
		this.otherTitle = otherTitle;
	}
	public String getOtherIntro() {
		return otherIntro;
	}
	public void setOtherIntro(String otherIntro) {
		this.otherIntro = otherIntro;
	}
	public WccAppartment getItemPk() {
		return itemPk;
	}
	public void setItemPk(WccAppartment itemPk) {
		this.itemPk = itemPk;
	}
	public String getOtherType() {
		return otherType;
	}
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	public Date getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	
	public String getIntroTitle() {
		return introTitle;
	}
	public void setIntroTitle(String introTitle) {
		this.introTitle = introTitle;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	@PersistenceContext
	transient EntityManager entityManager;
	
	
	public static final EntityManager entityManager() {
		EntityManager em = new WccItemotherinfo().entityManager;
		if (em == null)
			throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	/**
	 *查询
	 */
	public static List<WccItemotherinfo> getCBD_WCC(Long itmeid){
		return entityManager().createQuery(" FROM WccItemotherinfo o WHERE 1=1   and itemPk="+itmeid,WccItemotherinfo.class).getResultList();
	}
	
	
	
	public static WccItemotherinfo findPubOrganization(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccItemotherinfo.class, id);
	}
	
	
	public static List<WccItemotherinfo> getNewCBD_WCC(Long itmeid){
		return entityManager().createQuery(" FROM WccItemotherinfo o WHERE 1=1  and itemPk="+itmeid,WccItemotherinfo.class).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public static PageModel<WccItemotherinfo> getRecord(QueryModel<WccItemotherinfo> qm,String itemPk,String otherType,String otherTitle){
		PageModel<WccItemotherinfo> pageModel=new PageModel<WccItemotherinfo>();
		Session s = (Session) entityManager().getDelegate();
        Criteria criteria = s.createCriteria(WccItemotherinfo.class);
        if(itemPk!=null &&  !itemPk.equals("")){
        	
        	String[] str=itemPk.split(",");
        	WccAppartment[] wcc=new WccAppartment[str.length];
        	for(int i=0;i<str.length;i++){
        		wcc[i]=WccAppartment.findPubOrganization(Long.parseLong(str[i]));
        	}
        	criteria.add(Restrictions.in("itemPk", wcc ));
        }
        if(otherType!=null &&  !otherType.equals("")){
        	
        	criteria.add(Restrictions.like("otherType",otherType));
        }
  
        if(otherTitle!=null &&  !otherTitle.equals("")){
  	
        	criteria.add(Restrictions.like("otherTitle", "%"+otherTitle+"%"));
        }
        
        Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    	criteria.setProjection(null);
 	    criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
 	    System.out.println(totalCount);
        pageModel.setTotalCount(Integer.parseInt(totalCount+""));
        pageModel.setPageSize(qm.getLimit());
        criteria.setFirstResult(qm.getStart());//当前第几页
        criteria.setMaxResults(qm.getLimit());//页的大小
	    
        pageModel.setDataList(criteria.list());
	   
		return pageModel;
	}
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	@Override
	public String toString() {
		return "WccItemotherinfo [id=" + id + ", isDelete=" + isDelete
				+ ", insertIp=" + insertIp + ", insertPk=" + insertPk
				+ ", insertTime=" + insertTime + ", updateIp=" + updateIp
				+ ", updatePk=" + updatePk + ", updateTime=" + updateTime
				+ ", deleteIp=" + deleteIp + ", deletePk=" + deletePk
				+ ", deleteTime=" + deleteTime + ", companyPk=" + companyPk
				+ ", organizationPk=" + organizationPk + ", platformPk="
				+ platformPk + ", otherPic=" + otherPic + ", otherTitle="
				+ otherTitle + ", otherIntro=" + otherIntro + ", itemPk="
				+ itemPk + ", otherType=" + otherType + ", publicTime="
				+ publicTime + ", introTitle=" + introTitle + ", url=" + url
				+ "]";
	}
	//删除
	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			WccItemotherinfo attached = WccItemotherinfo
					.findPubOrganization(this.id);
			this.entityManager.remove(attached);
		}
	}
	//修改
	@Transactional
    public WccItemotherinfo merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WccItemotherinfo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	public static 	int getByType(String otherTitle,String itemPkStr){
		String hql="FROM WccItemotherinfo  WHERE 1=1 ";
		hql+=" and otherTitle='"+otherTitle+"'";
		hql+=" and itemPk ="+itemPkStr;
		
		return  entityManager().createQuery(hql).getResultList().size();
		
	}	  
	
	
}

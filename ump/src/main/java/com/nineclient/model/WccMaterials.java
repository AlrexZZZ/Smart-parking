package com.nineclient.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.cbd.wcc.dto.SingleDealerToSendFrDTO;
import com.nineclient.cbd.wcc.dto.WccFeesDTO;
import com.nineclient.constant.WccMaterialAttr;
import com.nineclient.constant.WccMaterialsType;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class WccMaterials {

	private Long parent;
	
	/**
     */
	@Enumerated
	private WccMaterialsType type;

	/**
     */
	@Size(max = 400)
	private String title;

	/**
     */
	@Lob
	private String content;

	/**
     */
	@Size(max = 4000)
	private String resourceUrl;

	/**
     */
	@Size(max = 4000)
	private String thumbnailUrl;

	/**
     */
	private Boolean isDeleted;

	/**
	 */
	private String materialId;

	
	private String materialType;
	/**
	 */
	private String description;

	private String mode;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date insertTime;

	/**
	 * 微信上传时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date uploadTime;

	/**
     */
	@Size(max = 4000)
	private String remakeUrl;

	/**
     */
	private Boolean urlBoolean;

	/**
     */
	@Size(max = 200)
	private String token;

	/**
     */
	@Size(max = 100)
	private String sort;
	
	@ManyToOne
	private WccPlatformUser wccPlatformUsers;
	
	private WccMaterialAttr wccMaterialAttr;//素材属性：如新车介绍、优惠活动等
	
	private String isNecessarySend;//是否必须群发
	
	private String officeMaterialId;//总部素材的素材Id 
	
	@ManyToOne
	private PubOperator pubOperator;//经销商TODO
	
	@Size(max = 4000)
	private String sourceUrl;//原文url
	
	@Size(max = 100)
	private String flagOfficeSource;//总部素材标识
	
	@Transient
	private List<WccMaterials> children = new ArrayList<WccMaterials>(0);

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("type", "title", "content", "resourceUrl", "thumbnailUrl",
					"isDeleted", "insertTime", "remakeUrl", "urlBoolean",
					"token", "sort");

	public static final EntityManager entityManager() {
		EntityManager em = new WccMaterials().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	//=================TODO
	/*public static final EntityManager entityManagerNew() {
		EntityManager em = new WccMaterials().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}*/
	//======================

	public static long countWccMaterialses() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM WccMaterials o", Long.class)
				.getSingleResult();
	}

	public static List<WccMaterials> findAllWccMaterialses() {
		return entityManager().createQuery("SELECT o FROM WccMaterials o",
				WccMaterials.class).getResultList();
	}

	public static List<WccMaterials> findWccMaterialsesByType(int type,
			long platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.type=" + type
								+ " AND o.wccPlatformUsers = " + platformUserId +" ORDER BY o.insertTime DESC",
						WccMaterials.class).getResultList();
	}
	
	
	public static List<WccMaterials> findWccMaterialsesByType2(int type,
			long platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.type=" + type
								+ " AND o.wccPlatformUsers in ( " + platformUserId +") ORDER BY o.insertTime DESC",
						WccMaterials.class).getResultList();
	}
	
	
	public static List<WccMaterials> findWccMaterialsesByType2(int type,
			String platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.type=" + type
								+ " AND o.wccPlatformUsers in ( " + platformUserId +")  ORDER BY o.insertTime DESC",
						WccMaterials.class).setFirstResult(0).setMaxResults(5).getResultList();
	
	}
	
	public static List<WccMaterials> findWccMassMaterialsesByType(int type,
			long platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.type=" + type
								+ " AND o.content is not null AND o.content !='' AND o.wccPlatformUsers = " + platformUserId +" ORDER BY o.insertTime DESC",
						WccMaterials.class).getResultList();
	}
	
	public static List<WccMaterials> findWccMassMaterialsesByType2(int type,
			String platformUserId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.type=" + type
								+ " AND o.content is not null AND o.content !='' AND o.wccPlatformUsers in ( " + platformUserId +")  ORDER BY o.insertTime DESC",
						WccMaterials.class).getResultList();
	}
	
	public static List<WccMaterials> findWccMaterialsesByTypeAndPlatform(int type,long companyId,WccPlatformUser wccplatform){
		/*String hql = "SELECT o FROM WccMaterials o WHERE o.type=:type AND o.companyId =:companyId and o.wccPlatformUsers =:wccPlatformUsers ";
		TypedQuery<WccMaterials> query = entityManager().createQuery(hql,WccMaterials.class);
		query.setParameter("type", type);
		query.setParameter("companyId", companyId);
		query.setParameter("wccPlatformUsers", wccplatform);
		return query.getResultList();*/
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.type=" + type
								+ " AND o.companyId = " + companyId +" AND o.wccPlatformUsers = " +wccplatform.getId()+" ORDER BY o.insertTime DESC",
						WccMaterials.class).getResultList();
	}

	public static List<WccMaterials> findAllWccMaterialsesByMaterialsType(
			String sortFieldName, String sortOrder,
			WccMaterialsType materialsType) {
		String jpaQuery = "SELECT o FROM WccMaterials o WHERE o.type = :materialsType";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccMaterials.class)
				.setParameter("materialsType", materialsType).getResultList();
	}

	public static List<WccMaterials> findAllWccMaterialses(
			String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM WccMaterials o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccMaterials.class)
				.getResultList();
	}

	public static WccMaterials findWccMaterials(Long id) {
		if (id == null)
			return null;
		return entityManager().find(WccMaterials.class, id);
	}

	public static List<WccMaterials> findWccMaterialsEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM WccMaterials o", WccMaterials.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<WccMaterials> findWccMaterialsEntriesByMaterialsType(
			int firstResult, int maxResults, String sortFieldName,
			String sortOrder, WccMaterialsType materialsType,
			List<WccPlatformUser> platformUsers) {
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT o FROM WccMaterials o WHERE o.type = :materialsType AND (");

		int platUsersIndex = 0;

		for (int i = 0; i < platformUsers.size(); i++) {
			if (i > 0)
				queryBuilder.append(" OR");
				queryBuilder.append(" :platUsers_item").append(i).append(" = o.wccPlatformUsers");
		}

		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			queryBuilder.append(") ORDER BY ").append(sortFieldName);
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				queryBuilder.append(" ").append(sortOrder);
			}
		}else{
			queryBuilder.append(") ORDER BY ").append("insertTime");
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				queryBuilder.append(" ").append(sortOrder);
			}else{
				queryBuilder.append(" ").append("DESC");
			}
		}
		TypedQuery<WccMaterials> q = entityManager().createQuery(queryBuilder.toString(),
				WccMaterials.class);
		

		for (WccPlatformUser _platuser : platformUsers) {
			q.setParameter("platUsers_item" + platUsersIndex++, _platuser);
		}
		if(materialsType!=null){
			q.setParameter("materialsType", materialsType);
		}else{
			q.setParameter("materialsType", WccMaterialsType.IMAGETEXT);
		}


		return q.getResultList();
	}
	
	
	public static List<WccMaterials> findWccMaterialsEntriesByMaterialsType2(
	int firstResult, int maxResults, String sortFieldName,
	String sortOrder, WccMaterialsType materialsType,
	List<WccPlatformUser> platformUsers) {
StringBuilder queryBuilder = new StringBuilder(
		"SELECT o FROM WccMaterials o WHERE o.type = :materialsType ");

int platUsersIndex = 0;
if(platformUsers!=null&&platformUsers.size()>0){

queryBuilder.append(" AND ");
/*for (int i = 0; i < platformUsers.size(); i++) {
	if (i > 0)
		queryBuilder.append(" OR");
		queryBuilder.append(" :platUsers_item").append(i).append(" = o.wccPlatformUsers");
}*/
	queryBuilder.append("o.wccPlatformUsers").append("  in (:platUsers_item)");
}
else{
	queryBuilder.append(" AND o.wccPlatformUsers is null");
}

if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	queryBuilder.append("  ORDER BY ").append(sortFieldName);
	if ("ASC".equalsIgnoreCase(sortOrder)
			|| "DESC".equalsIgnoreCase(sortOrder)) {
		queryBuilder.append(" ").append(sortOrder);
	}
}else{
	queryBuilder.append("  ORDER BY ").append(" o.insertTime");
	if ("ASC".equalsIgnoreCase(sortOrder)
			|| "DESC".equalsIgnoreCase(sortOrder)) {
		queryBuilder.append(" ").append(sortOrder);
	}else{
		queryBuilder.append(" ").append("DESC");
	}
}
TypedQuery<WccMaterials> q = entityManager().createQuery(queryBuilder.toString(),
		WccMaterials.class);

if(platformUsers!=null&&platformUsers.size()>0){
/*for (WccPlatformUser _platuser : platformUsers) {
	q.setParameter("platUsers_item" + platUsersIndex++, _platuser);
}*/
	 q.setParameter("platUsers_item", platformUsers);
 }
if(materialsType!=null){
	q.setParameter("materialsType", materialsType);
}else{
	q.setParameter("materialsType", WccMaterialsType.IMAGETEXT);
}


return q.getResultList();
}

//==================经销商指定日期的素材查询
	public static List<WccMaterials> findWccMaterialsEntriesByMaterialsType2AndDate(
	int firstResult, int maxResults, String sortFieldName,
	String sortOrder, WccMaterialsType materialsType,
	List<WccPlatformUser> platformUsers,String currentDate,String beforeDate) {
StringBuilder queryBuilder = new StringBuilder(
		"SELECT o FROM WccMaterials o WHERE o.type = :materialsType ");

int platUsersIndex = 0;
if(platformUsers!=null&&platformUsers.size()>0){

queryBuilder.append(" AND ");
/*for (int i = 0; i < platformUsers.size(); i++) {
	if (i > 0)
		queryBuilder.append(" OR");
		queryBuilder.append(" :platUsers_item").append(i).append(" = o.wccPlatformUsers");
}*/
	queryBuilder.append("o.wccPlatformUsers").append("  in (:platUsers_item)");
}
else{
	queryBuilder.append(" AND o.wccPlatformUsers is null");
}

if(beforeDate!=null){
queryBuilder.append(" AND o.insertTime >= '"+beforeDate+"' ");
}
if(currentDate!=null){
queryBuilder.append(" AND o.insertTime <= '"+currentDate+"' ");
}

if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	queryBuilder.append("  ORDER BY ").append(sortFieldName);
	if ("ASC".equalsIgnoreCase(sortOrder)
			|| "DESC".equalsIgnoreCase(sortOrder)) {
		queryBuilder.append(" ").append(sortOrder);
	}
}else{
	queryBuilder.append("  ORDER BY ").append(" o.insertTime");
	if ("ASC".equalsIgnoreCase(sortOrder)
			|| "DESC".equalsIgnoreCase(sortOrder)) {
		queryBuilder.append(" ").append(sortOrder);
	}else{
		queryBuilder.append(" ").append("DESC");
	}
}
TypedQuery<WccMaterials> q = entityManager().createQuery(queryBuilder.toString(),
		WccMaterials.class);

if(platformUsers!=null&&platformUsers.size()>0){
/*for (WccPlatformUser _platuser : platformUsers) {
	q.setParameter("platUsers_item" + platUsersIndex++, _platuser);
}*/
	 q.setParameter("platUsers_item", platformUsers);
 }
if(materialsType!=null){
	q.setParameter("materialsType", materialsType);
}else{
	q.setParameter("materialsType", WccMaterialsType.IMAGETEXT);
}

return q.getResultList();
}

	
	
	
	//================================
	
	
	
	public static List<WccMaterials> findWccMaterialsEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM WccMaterials o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, WccMaterials.class)
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
			WccMaterials attached = WccMaterials.findWccMaterials(this.id);
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
	public WccMaterials merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		WccMaterials merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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

	public WccMaterialsType getType() {
		return this.type;
	}

	public void setType(WccMaterialsType type) {
		this.type = type;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResourceUrl() {
		return this.resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getThumbnailUrl() {
		return this.thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getRemakeUrl() {
		return this.remakeUrl;
	}

	public void setRemakeUrl(String remakeUrl) {
		this.remakeUrl = remakeUrl;
	}

	public Boolean getUrlBoolean() {
		return this.urlBoolean;
	}

	public void setUrlBoolean(Boolean urlBoolean) {
		this.urlBoolean = urlBoolean;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public List<WccMaterials> getChildren() {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.parent=" + this.id,WccMaterials.class).getResultList();
	}


	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	/**
	 * 查询图片
	 * 
	 * @param qm
	 * @return
	 */
	public static PageModel<WccMaterials> getQueryPic(
			QueryModel<WccMaterials> qm,Long companyId) {
		Map parmMap = qm.getInputMap();
		Integer type = Integer.parseInt(parmMap.get("type").toString());
		Long platFormId = Long.parseLong(parmMap.get("platFormId").toString());
		PageModel<WccMaterials> pageModel = new PageModel<WccMaterials>();
		
		StringBuffer sql = new StringBuffer(
				" SELECT o FROM WccMaterials o  WHERE 1=1 and o.type = " + type + " AND o.companyId ="+companyId+" and o.wccPlatformUsers ="+platFormId);
		if (type == 4) {
			sql.append(" AND parent is null");
		}
		Map<String, Object> map = new HashMap<String, Object>();
	    TypedQuery<WccMaterials> query = entityManager().createQuery(sql.toString(), WccMaterials.class);

		int totalCount = 0;

		{ // 查总数
			TypedQuery<Long> countQuery = entityManager().createQuery(
					sql.toString().replaceAll(" SELECT o", "SELECT count(o)"),
					Long.class);
			for (String key : map.keySet()) {
				countQuery.setParameter(key, map.get(key));
			}
			totalCount = countQuery.getSingleResult().intValue();
		}

		if (qm.getLimit() > 0) { // 分页截取
			query.setFirstResult(qm.getStart());
			query.setMaxResults(qm.getLimit());
		}
		List<WccMaterials> list = query.getResultList();
		pageModel.setTotalCount(totalCount);
		pageModel.setDataList(query.getResultList());
		return pageModel;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public WccPlatformUser getWccPlatformUsers() {
		return wccPlatformUsers;
	}

	public void setWccPlatformUsers(WccPlatformUser wccPlatformUsers) {
		this.wccPlatformUsers = wccPlatformUsers;
	}

	private Long companyId;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	private Integer codeId;

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	
	private boolean codeStatus;

	public boolean getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(boolean codeStatus) {
		this.codeStatus = codeStatus;
	}
	
	
	
	public WccMaterialAttr getWccMaterialAttr() {
		return wccMaterialAttr;
	}

	public void setWccMaterialAttr(WccMaterialAttr wccMaterialAttr) {
		this.wccMaterialAttr = wccMaterialAttr;
	}

	public String getIsNecessarySend() {
		return isNecessarySend;
	}

	public void setIsNecessarySend(String isNecessarySend) {
		this.isNecessarySend = isNecessarySend;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	

	public String getFlagOfficeSource() {
		return flagOfficeSource;
	}

	public void setFlagOfficeSource(String flagOfficeSource) {
		this.flagOfficeSource = flagOfficeSource;
	}
	
	
	public String getOfficeMaterialId() {
		return officeMaterialId;
	}

	public void setOfficeMaterialId(String officeMaterialId) {
		this.officeMaterialId = officeMaterialId;
	}
		
	
	public PubOperator getPubOperator() {
		return pubOperator;
	}

	public void setPubOperator(PubOperator pubOperator) {
		this.pubOperator = pubOperator;
	}

	public static WccMaterials findWccMaterialsByCodeId(int sceneId) {
		return entityManager()
				.createQuery(
						"SELECT o FROM WccMaterials o WHERE o.codeId=" + sceneId,WccMaterials.class).getSingleResult();
	}
	
	
	public static List<WccMaterials> findWccMaterialsByPlatAndFlag(WccPlatformUser platformuser,String flagOfficeSource){
	
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT o FROM WccMaterials o WHERE 1=1 and o.flagOfficeSource="+flagOfficeSource+" and o.parent is null");
		return entityManager().createQuery(sql.toString(), WccMaterials.class).getResultList();
		
	}
	

	//根据条件查询总部的素材信息并展示
	public static List<WccMaterials>	findWccMaterialsByCondi(WccMaterialsType materialsType,WccMaterialAttr materialAttr,String keyword,List<WccPlatformUser> platformUsers){
		
		StringBuilder queryBuilder = new StringBuilder(
				//"SELECT o FROM WccMaterials o WHERE o.type = :materialsType AND o.wccMaterialAttr =:materialAttr AND o.title like '%+keyword+%'(");
				"SELECT o FROM WccMaterials o WHERE o.type = :materialsType  ");
		int platUsersIndex = 0;

		if(materialAttr!=null&&!"".equals(materialAttr)){
			queryBuilder.append(" AND o.wccMaterialAttr = :materialAttr ");
		}
		if (StringUtils.isNotEmpty(keyword)) {
			queryBuilder.append(" AND o.title like '");
			queryBuilder.append("%"+keyword+"%"+"'");
		}
		/*if(platformUsers!=null&&platformUsers.size()>0){
		queryBuilder.append(" AND ");
		for(int i = 0;i<platformUsers.size();i++){
			if(i>0){
				queryBuilder.append(" OR");
			}
			queryBuilder.append(" :platUsers_item").append(i).append(" = o.wccPlatformUsers");
			
		}
		//queryBuilder.append(" )");
		}*/
		if(platformUsers!=null&&platformUsers.size()>0){
			
			queryBuilder.append(" AND  ");
			
				queryBuilder.append("o.wccPlatformUsers").append("  in (:platUsers_item)");
				
			}
		
		queryBuilder.append("  ORDER BY o.insertTime  DESC");
		TypedQuery<WccMaterials> q = entityManager().createQuery(queryBuilder.toString(),
				WccMaterials.class);
		
       if(platformUsers!=null&&platformUsers.size()>0)
       {
    	   /*for (WccPlatformUser _platuser : platformUsers) {
   			q.setParameter("platUsers_item" + platUsersIndex++, _platuser);
   		}*/
    	   q.setParameter("platUsers_item", platformUsers);
       }
		
		if(materialsType!=null){
			q.setParameter("materialsType", materialsType);
		}else{
			q.setParameter("materialsType", WccMaterialsType.IMAGETEXT);
		}
		
		if(materialAttr!=null){
			q.setParameter("materialAttr", materialAttr);
		}
		return q.getResultList();
		
		
		
	}
	
	
	public static List<WccMaterials> findReportByDateBetween(String startTime,String endTime,String title){
		      String jpaQuery = "SELECT o FROM WccMaterials o where 1=1 and flagOfficeSource is not null ";
		     if(null!=title&&!title.equals("")){
		    	  jpaQuery+=" and o.title like '%"+title+"%'";
		      }if(null!=startTime&&!startTime.equals("")){
		    	  jpaQuery+=" and  o.insertTime >='"+startTime+" 00:00:00'";
		      }if(null!=endTime&&!endTime.equals("")){
		    	  jpaQuery+=" and o.insertTime <='"+endTime+" 23:59:59'";
		      }
		      jpaQuery+=" order by o.insertTime desc";
		      List<WccMaterials> list=entityManager().createQuery(jpaQuery, WccMaterials.class).getResultList();
		      return list;
	}
	
		//单个经销商转发率查询
		/*public static List<WccMaterials>  findSingleDealerMaterialByDateBetween(String platform, String startTime, String endTime){
			StringBuffer jpaQuery = new StringBuffer("SELECT o FROM WccMaterials o where 1=1 ");
			 if(null!=platform&&!platform.equals("")&&!platform.equals("全部")){
		    	  jpaQuery.append(" and o.wccPlatformUsers  = '"+platform+"'");
		      }
			 if(null!=startTime&&!startTime.equals("")){
		    	  jpaQuery.append(" and  o.insertTime >='"+startTime+" 00:00:00'");
		      }if(null!=endTime&&!endTime.equals("")){
		    	  jpaQuery.append(" and o.insertTime <='"+endTime+" 23:59:59'");
		      }
		      jpaQuery.append(" and o.officeMaterialId is not null");
		      jpaQuery.append(" order by o.insertTime DESC");
		      
		      List<WccMaterials> list=new ArrayList<WccMaterials>();*/
		     /* if(firstResult!=-1&&maxResults!=-1){
		    	  list=entityManager().createQuery(jpaQuery.toString(), WccMaterials.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		        }else{
		        	 list=entityManager().createQuery(jpaQuery.toString(), WccMaterials.class).getResultList();
		        }*/
		/*      list=entityManager().createQuery(jpaQuery.toString(), WccMaterials.class).getResultList();
		      return list;
		}*/
	
	/*public static List<SingleDealerToSendDto> findSingleDealerToSendList(String platform, String startTime, String endTime){
		StringBuffer jpaQuery = new StringBuffer("SELECT count(*),o.wccPlatformUsers,o.pubOperator,o.type FROM WccMaterials o where 1=1 ");
		 if(null!=platform&&!platform.equals("")&&!platform.equals("全部")){
	    	  jpaQuery.append(" and o.wccPlatformUsers  = '"+platform+"'");
	      }
		 if(null!=startTime&&!startTime.equals("")){
	    	  jpaQuery.append(" and  o.insertTime >='"+startTime+" 00:00:00'");
	      }if(null!=endTime&&!endTime.equals("")){
	    	  jpaQuery.append(" and o.insertTime <='"+endTime+" 23:59:59'");
	      }
	      jpaQuery.append(" and o.officeMaterialId is not null and o.wccPlatformUsers is not null and o.pubOperator is not null");
	      jpaQuery.append(" GROUP BY o.wccPlatformUsers,o.pubOperator,o.type");
	      jpaQuery.append(" order by o.insertTime DESC");
	      
	      List<SingleDealerToSendDto> list = new ArrayList<SingleDealerToSendDto>();
	      List<Object[]> objList = entityManager().createQuery(jpaQuery.toString()).setFirstResult(0).setMaxResults(10).getResultList();
	      if(objList.size() > 0){
				for(Object[] e:objList){
					SingleDealerToSendDto singleSendDto = new SingleDealerToSendDto();
					singleSendDto.setCount(e[0]+"");//数量
					singleSendDto.setPlatformName(e[1]+"");//平台
					singleSendDto.setPubOperatorName(e[2]+"");//经销商
					singleSendDto.setType(e[3]+"");//素材类型
					list.add(singleSendDto);
				}
			}
	      return list;

		
	}*/
	
	public static List<Map<String, Object>> findSingleDealerToSendList(String platform, String startTime, String endTime,int firstResult,int maxResults){
		String sql = "SELECT count(*),o.wcc_Platform_Users,o.pub_Operator,o.type FROM Wcc_Materials o where 1=1  ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(platform)) {
			sql += " and o.wcc_Platform_Users=:wccPlatformUsers";
			map.put("wccPlatformUsers", platform);
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " and o.insert_time>=:startTime";
			map.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " and o.insert_time<=:endTime";
			map.put("endTime", endTime);
		}
		
		sql+=" and o.office_Material_Id is not null and o.wcc_Platform_Users is not null and o.pub_Operator is not null";
		sql+=" group by o.wcc_Platform_Users,o.pub_Operator,o.type";
		sql+=" order by o.insert_Time DESC ";
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(firstResult==-1&&maxResults==-1){
			list =  query.list();
		}else {
			list =  query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
		return list;
		
	}
	
}

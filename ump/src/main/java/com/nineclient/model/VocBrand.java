package com.nineclient.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.constant.UmpCheckStatus;
import com.nineclient.utils.CommonUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class VocBrand {

    /**
     */
    @Size(max = 500)
    private String brandName;

    /**
     */
    @Size(max = 4000)
    private String remark;

    /**
     */
    private Boolean isVisable;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    private long sort;

    /**
     */
    @Size(max = 4000)
    private String keyName;

    /**
     */
    @Enumerated
    private UmpCheckStatus status;
    /**
     * 行业
     */
    @ManyToOne
    private UmpParentBusinessType umpParentBusinessType;
   
    @ManyToOne
    private UmpCompany umpCompany;
	public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
    }
	@ManyToMany
	private Set<UmpChannel> umpChannels;
	@OneToMany(mappedBy="vocBrand")
	private Set<VocGoods> goods = new HashSet<VocGoods>();
	@ManyToMany(mappedBy="vocBrands")
	private Set<VocShop> vocShops;
	@OneToMany(mappedBy="vocBrand")
    private Set<VocSku> vocSkus;
	

	public Set<VocSku> getVocSkus() {
		return vocSkus;
	}

	public void setVocSkus(Set<VocSku> vocSkus) {
		this.vocSkus = vocSkus;
	}
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("brandName", "remark", "isVisable", "createTime", "isDeleted", "sort", "keyName", "status");

	public static final EntityManager entityManager() {
        EntityManager em = new VocBrand().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocBrands(Map<String,Object> map) {
		StringBuilder jpaQuery = new StringBuilder("SELECT COUNT(o) FROM VocBrand o ");
		String companyCode = (String) (map.get("companyCode")==null?"":map.get("companyCode"));
		if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" join o.umpCompany c ");
        }
        jpaQuery.append(" where o.isDeleted=0 ");
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" and c.companyCode= '"+companyCode+"'");
        }
        return entityManager().createQuery(jpaQuery.toString(), Long.class).getSingleResult();
    }

	public static List<VocBrand> findAllVocBrands() {
        return entityManager().createQuery("SELECT o FROM VocBrand o where o.isDeleted=0 ", VocBrand.class).getResultList();
    }

	public static List<VocBrand> findAllVocBrands(String sortFieldName, String sortOrder,Map<String,Object> map) {
		String companyCode = (String) (map.get("companyCode")==null?"":map.get("companyCode"));
		Long status =  (map.get("status")==null?null:Long.parseLong(String.valueOf(map.get("status"))));
        StringBuilder jpaQuery = new StringBuilder("SELECT o FROM VocBrand o");
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" join o.umpCompany c ");
        }
        jpaQuery.append(" where o.isDeleted=0 ");
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" and o.status="+status);
        }
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" and c.companyCode= '"+companyCode+"'");
        }
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery .append(" ORDER BY " + sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery.append(" " + sortOrder);
            }
        }
        return entityManager().createQuery(jpaQuery.toString(), VocBrand.class).getResultList();
    }

	public static VocBrand findVocBrand(Long id) {
        if (id == null) return null;
        return entityManager().find(VocBrand.class, id);
    }
	
	/*
	 * 根据id查询返回一个list
	 */
	public static List<VocBrand> findVocBrandById(Long id) {
		if (id == null) return null;
        return entityManager().createQuery("SELECT o FROM VocBrand o where 1=1 and o.id = " + id, VocBrand.class).getResultList();
    }
	/*
	 * 根据id查询返回一个list
	 */
	public static List<VocBrand> findVocBrandById(List<Long> id) {
		if (id == null) return null;
		String sql = "SELECT o FROM VocBrand o where 1=1 and o.id in (:id)";
		TypedQuery<VocBrand> query = entityManager().createQuery(sql, VocBrand.class);
		query.setParameter("id", id);
        return query.getResultList();
    }
	
	/**
	 * 根据id列表查询 待审核品牌
	 * @param ids
	 * @return
	 */
	public static List<VocBrand> findVocBrandEntriesByIds(String ids) {
        if (ids == null) return null;

		StringBuffer sql = new StringBuffer(" SELECT o FROM VocBrand o where o.isDeleted=0  and o.id in(:ids) ");
		Map<String,Object> map = new HashMap<String, Object>();
		 map.put("ids", CommonUtils.stringArray2List(ids));
       TypedQuery<VocBrand> query = entityManager().createQuery(sql.toString(), VocBrand.class);
       
        for(String key:map.keySet()){
          query.setParameter(key, map.get(key));	
        }
        return query.getResultList();

	
	
	}
	/**
	 * 根据品牌名称查询品牌
	 * @param brandName
	 * @return
	 */
	public static List<VocBrand> findVocBrandEntriesByBrandName(String brandName,Long companyId) {
        if (brandName == null) return null;
        return entityManager().createQuery("SELECT o FROM VocBrand o join o.umpCompany c where o.isDeleted=0 and o.brandName='"+brandName+"' and c.id="+companyId, VocBrand.class).getResultList();
    }
	public static List<VocBrand> findVocBrandEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocBrand o where o.isDeleted=0", VocBrand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	/**
	 * 查询待审核关键词
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public static List<Map> findVocBrandCheckStatus(int firstResult, int maxResults,Map<String,Object> map) {
			int status=UmpCheckStatus.已审核.ordinal();
			StringBuffer jpaQuery =new StringBuffer("select c.company_code companyCode,p.business_name businessName,b.brand_name brandName,k.create_time createTime,k.name name,k.id id,k.status from voc_key_word k left join voc_brand b on k.voc_brand_id=b.id and k.status!="+status);
			jpaQuery.append(" join ump_company c on c.id=k.company_id ");
			jpaQuery.append(" join ump_parent_business_type p on p.id=b.ump_parent_business_type ");
	        String brandName = (String) (map.get("brandName") == null ? "" : map.get("brandName"));
	        String businessTypeId = (String) (map.get("businessTypeId") == null ? "" : map.get("businessTypeId"));
	        String keyName = (String) (map.get("keyName") == null ? "" : map.get("keyName"));
	        String companyCode = (String) (map.get("companyCode") == null ? "" : map.get("companyCode"));
	        if (!StringUtils.isEmpty(brandName.trim())) {
	            jpaQuery.append(" and b.brand_Name like '%" + brandName + "%'");
	        }
	        if (!StringUtils.isEmpty(businessTypeId.trim())) {
	            jpaQuery.append(" and p.id=" + businessTypeId);
	        }
	        if (!StringUtils.isEmpty(keyName.trim())) {
	            jpaQuery.append(" and k.name like '%" + keyName+"%'");
	        }
	        if(!StringUtils.isEmpty(companyCode)){
	        	jpaQuery.append(" and c.company_code= '"+companyCode+"'");
	        }
	        jpaQuery.append(" order by k.create_Time desc");
			Session session = (Session) entityManager().getDelegate();
			Query query = session.createSQLQuery(jpaQuery.toString()).setResultTransformer(
					Criteria.ALIAS_TO_ENTITY_MAP);
			return query.setFirstResult(firstResult).setMaxResults(maxResults).list();
	        
    }
	/**
	 * 查询未审核总数
	 * @param map
	 * @return
	 */
	public static Integer countVocBrandCheckStatus(Map<String, Object> map) {
		int status=UmpCheckStatus.已审核.ordinal();
		StringBuffer jpaQuery =new StringBuffer("select c.company_code companyCode,p.business_name businessName,b.brand_name brandName,k.create_time createTime,k.name name,k.id id,k.status  from voc_key_word k left join voc_brand b on k.voc_brand_id=b.id and k.status!="+status);
		jpaQuery.append(" join ump_company c on c.id=k.company_id ");
		jpaQuery.append(" join ump_parent_business_type p on p.id=b.ump_parent_business_type ");
        String brandName = (String) (map.get("brandName") == null ? "" : map.get("brandName"));
        String businessTypeId = (String) (map.get("businessTypeId") == null ? "" : map.get("businessTypeId"));
        String keyName = (String) (map.get("keyName") == null ? "" : map.get("keyName"));
        String companyCode = (String) (map.get("companyCode") == null ? "" : map.get("companyCode"));
        if (!StringUtils.isEmpty(brandName.trim())) {
            jpaQuery.append(" and b.brand_Name like '%" + brandName + "%'");
        }
        if (!StringUtils.isEmpty(businessTypeId.trim())) {
            jpaQuery.append(" and p.id=" + businessTypeId);
        }
        if (!StringUtils.isEmpty(keyName.trim())) {
            jpaQuery.append(" and k.name like '%" + keyName+"%'");
        }
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" and c.company_code= '"+companyCode+"'");
        }
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(jpaQuery.toString()).setResultTransformer(
				Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list!=null){
			return list.size();
		}else{
			return 0;
		}
	}
	public static List<VocBrand> findVocBrandEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder,Map<String,Object> map) {
        StringBuilder  jpaQuery = new StringBuilder("SELECT o FROM VocBrand o ");
        String companyCode = (String) (map.get("companyCode")==null?"": map.get("companyCode"));
        Long status =  (map.get("status")==null?null:Long.parseLong(String.valueOf(map.get("status"))));
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" join o.umpCompany c ");
        }
        jpaQuery.append(" where  o.isDeleted=0 ");
        if(!StringUtils.isEmpty(status)){
        	jpaQuery.append(" and o.status="+status);
        }
        if(!StringUtils.isEmpty(companyCode)){
        	jpaQuery.append(" and c.companyCode = '"+companyCode+"'");
        }
        jpaQuery.append(" order by o.createTime desc ");
        
        return entityManager().createQuery(jpaQuery.toString(), VocBrand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	/**
	 * 根据父行业行业查询品牌
	 * @param id
	 * @param brandName
	 * @return
	 */
	public static List<VocBrand> findVocBrandsByBusinessType(Long id,String brandName){
		String sql = "SELECT o FROM VocBrand o join o.umpParentBusinessType b where o.isDeleted=0 and o.status=:status ";
		if(!StringUtils.isEmpty(id)){
			sql+=" and b.id="+id;
		}
		if(!StringUtils.isEmpty(brandName)){
			sql+=" and o.brandName ='"+brandName+"'";
		}
		TypedQuery<VocBrand> query = entityManager().createQuery(sql, VocBrand.class);
		query.setParameter("status", UmpCheckStatus.已审核);
        return query.getResultList();
		
	}
	/**
	 * 
	 * @param id
	 * @param companyId
	 * @return
	 */
	public static List<VocBrand> findVocBrandsByBusinessType(Long id,Long companyId){
		String sql = "SELECT o FROM VocBrand o join o.umpParentBusinessType b join o.umpCompany c where o.isDeleted=0 and o.status=:status ";
		if(!StringUtils.isEmpty(id)){
			sql+=" and b.id="+id;
		}
		if(!StringUtils.isEmpty(companyId)){
			sql+=" and c.id ="+companyId;
		}
		TypedQuery<VocBrand> query = entityManager().createQuery(sql, VocBrand.class);
		query.setParameter("status", UmpCheckStatus.已审核);
        return query.getResultList();
		
	}
	/**
	 * 根据公司id 和渠道查询品牌
	 * @param map
	 * @return
	 */
	public static List<VocBrand> findVocBrandsByChannelAndCompany(Map<String,Object> map){
		String channelId =String.valueOf((map.get("umpChannelId")==null?"":map.get("umpChannelId")));
		String companyId = String.valueOf((map.get("umpCompanyId")==null?"":map.get("umpCompanyId")));
		StringBuilder sql = new StringBuilder(" select o from VocBrand o ");
		if(!StringUtils.isEmpty(channelId)){
			sql.append(" join o.umpChannels c ");
		}
		if(!StringUtils.isEmpty(companyId)){
			sql.append(" join o.umpCompany p ");
		}
		sql.append(" where 1=1 and o.status =:status ");
		if(!StringUtils.isEmpty(channelId)){
			sql.append(" and c.id in ("+channelId+")");
		}
		if(!StringUtils.isEmpty(companyId)){
			sql.append(" and p.id in ("+companyId+")");
		}
		TypedQuery<VocBrand> query = entityManager().createQuery(sql.toString(),VocBrand.class);
		query.setParameter("status", UmpCheckStatus.已审核);
		return query.getResultList();
	}
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	@Transactional
    public void persist(List<VocKeyWord> list) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        for(VocKeyWord key:list){
        	key.setVocBrandId(this.id);
        	key.persist();
        }
    }
	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            VocBrand attached = VocBrand.findVocBrand(this.id);
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
    public VocBrand merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocBrand merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	@Transactional
    public VocBrand merge(List<VocKeyWord> list) {
        if (this.entityManager == null) this.entityManager = entityManager();
        if(list!=null){
	        for (VocKeyWord vocKeyWord : list) {
	        	vocKeyWord.merge();
			}
        }
        VocBrand merged = this.entityManager.merge(this);
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

	public String getBrandName() {
        return this.brandName;
    }

	public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

	public String getRemark() {
        return this.remark;
    }

	public void setRemark(String remark) {
        this.remark = remark;
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

	public Boolean getIsDeleted() {
        return this.isDeleted;
    }

	public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public long getSort() {
        return this.sort;
    }

	public void setSort(long sort) {
        this.sort = sort;
    }

	public String getKeyName() {
        return this.keyName;
    }

	public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

	public UmpCheckStatus getStatus() {
        return this.status;
    }

	public void setStatus(UmpCheckStatus status) {
        this.status = status;
    }

	


	public UmpCompany getUmpCompany() {
		return umpCompany;
	}

	public void setUmpCompany(UmpCompany umpCompany) {
		this.umpCompany = umpCompany;
	}

	public Set<UmpChannel> getUmpChannels() {
		return umpChannels;
	}

	public void setUmpChannels(Set<UmpChannel> umpChannels) {
		this.umpChannels = umpChannels;
	}

	public Set<VocShop> getVocShops() {
		return vocShops;
	}

	public void setVocShops(Set<VocShop> vocShops) {
		this.vocShops = vocShops;
	}

	public UmpParentBusinessType getUmpParentBusinessType() {
		return umpParentBusinessType;
	}

	public void setUmpParentBusinessType(UmpParentBusinessType umpParentBusinessType) {
		this.umpParentBusinessType = umpParentBusinessType;
	}

	public Set<VocGoods> getGoods() {
		return goods;
	}

	public void setGoods(Set<VocGoods> goods) {
		this.goods = goods;
	}

	public static String toJsonArray(Collection<VocBrand> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<VocBrand> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<VocBrand> fromJsonArrayToVocBrands(String json) {
        return new JSONDeserializer<List<VocBrand>>()
        .use("values", VocBrand.class).deserialize(json);
    }
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
	/**
	 * 查询品牌
	 * @param company
	 * @param brandName
	 * @return
	 */
	public static VocBrand findVocBrandbyCompanyAndName(UmpCompany company,
			String brandName) {
		String sql = " SELECT o FROM VocBrand o join o.umpCompany c where o.brandName='"+brandName+"' and c.id="+company.getId();
		return entityManager().createQuery(sql,VocBrand.class).getSingleResult();
	}
	/**
	 * 查询该公司的所有品牌
	 * @param company
	 * @return
	 */
	public static List<VocBrand> findVocBrandbyCompany(UmpCompany company) {
		String sql = " SELECT distinct o FROM VocBrand o join o.umpCompany c where  c.isVisable = 1 and c.id="+company.getId();
		return entityManager().createQuery(sql,VocBrand.class).getResultList();
	}
	/**
	 * 根据店铺查询品牌
	 * @param listShop
	 * @return
	 */
	public static List<VocBrand> findVocBrandsByVocShop(List<VocShop> shops) {
		String sql="";
		List<Long> ids=new ArrayList<Long>();
		if(shops==null||shops.size()<1)
		{
			sql="select o from VocBrand o  where o.isVisable=1 and o.isDeleted=0";
		}else{
			for (VocShop sh : shops) {
				ids.add(sh.getId());
			}
			sql="select o from VocBrand o join  o.vocShops s where s.id in (:shops) and o.isVisable=1 and o.isDeleted=0";
		}
		TypedQuery<VocBrand> query = entityManager().createQuery(sql, VocBrand.class);
		if(shops==null||shops.size()<1){
		}else{
			query.setParameter("shops", ids);
		}
		return query.getResultList();
	}
	/**
	 * 编辑时唯一验证
	 * @param id
	 * @param brandName
	 * @param companyId
	 * @return
	 */
	public static List<VocBrand> findVocBrandEntriesByBrandName(Long id,
			String brandName, Long companyId) {
		 if (brandName == null) return null;
	        return entityManager().createQuery("SELECT o FROM VocBrand o join o.umpCompany c where o.isDeleted=0 and o.status!=1 and o.id!="+id+" and o.brandName='"+brandName+"' and c.id="+companyId, VocBrand.class).getResultList();
	}

	public static List<VocBrand> findVocBrandbyCompanyAndVocShopIds(
			UmpCompany company, String ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("select vb.id,vb.brand_name from voc_brand vb,voc_shop_voc_brands vsb where"
				+ " vb.ump_company =:company and vb.id = vsb.voc_brands and vsb.voc_shops in (:ids)");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", CommonUtils.stringArray2Set(ids));
		map.put("company", company);
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
			
		List<VocBrand> vocBrand = new ArrayList<VocBrand>();
		VocBrand model = null;
		Object[] objArr = null;
		for(Object obj:query.getResultList()){
			objArr = (Object[])obj;
			model = new VocBrand();
			model.setId(Long.parseLong(String.valueOf(objArr[0])));
			model.setBrandName(String.valueOf(objArr[1]));
			vocBrand.add(model);
		}
		return vocBrand;
	}
	
	/**
	 * 查询公司下相同品牌名称数
	 * @param companyId
	 * @param shopName
	 * @return
	 */
	public static Long findVocBrandName(Long companyId,String brandName) {
		String sql = "select count(*) from voc_brand vb where vb.ump_company = "+companyId+" and vb.brand_name = '"+brandName+"' ";
       javax.persistence.Query query = entityManager().createNativeQuery(sql.toString());
        long count = 0;
        try {
			count = Long.parseLong(String.valueOf(query.getSingleResult()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return  count;
    }

}

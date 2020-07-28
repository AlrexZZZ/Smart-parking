package com.nineclient.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class VocShop {
	
	@Transient
	private String vocBrandNames;
	
    /**
     */
    @NotNull
    private String name;

    /**
     */
    @NotNull
    private String url;

    /**
     */
    private Boolean isDeleted;
    
    private String remark;
    
    /**
     * 是否启用
     */
    private Boolean isVisable;
    
    @ManyToMany
	private Set<VocBrand> vocBrands;
    
    @ManyToOne
    private UmpChannel channel;
    
    @OneToMany(mappedBy = "shop")
    private Set<VocGoods> goods = new HashSet<VocGoods>();
    
    @OneToOne(mappedBy="vocShop")
    private VocAccount vocAccount;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    private Long companyId;
    
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public  VocAccount getVocAccount() {
		return vocAccount;
	}

	public void setVocAccount(VocAccount vocAccount) {
		this.vocAccount = vocAccount;
	}

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getUrl() {
        return this.url;
    }

	public void setUrl(String url) {
        this.url = url;
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
	
	

	public String getVocBrandNames() {
		return vocBrandNames;
	}

	public void setVocBrandNames(String vocBrandNames) {
		this.vocBrandNames = vocBrandNames;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "url", "isDeleted", "createTime");

	public static final EntityManager entityManager() {
        EntityManager em = new VocShop().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVocShops() {
        return entityManager().createQuery("SELECT COUNT(o) FROM VocShop o", Long.class).getSingleResult();
    }
	
	public static List<VocShop> findAllVocShops() {
        return entityManager().createQuery("SELECT o FROM VocShop o", VocShop.class).getResultList();
    }
	/**
	 * 根据渠道加载店铺
	 * @return
	 */
	public static List<VocShop> findAllVocShopsByVocChannels(String ids) {
		 TypedQuery<VocShop> query=
         entityManager().createQuery("SELECT o FROM VocShop o join o.channel c where c.id in ("+ids+")", VocShop.class);
		return query.getResultList();
		
    }
	
	/**
	 * 根据渠道 公司 查询店铺
	 * @param channelId
	 * @param companyId
	 * @return
	 */
	public static List<VocShop> findAllVocShopsByCompanyAndChannel(Long channelId,Long companyId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select vs.id,vs.name,vs.company_id ").append(" from voc_shop vs");
		if(!StringUtils.isEmpty(channelId)){
			sql = sql.append(" inner join  ump_channel uc on vs.channel=uc.id ")
					.append(" uc.id=").append(channelId);
		}
		sql = sql.append(" where 1=1 ");
		if(!StringUtils.isEmpty(companyId)){
			sql = sql.append("  and vs.company_id =").append(companyId);
		}
		javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
		List<VocShop> vocShop = new ArrayList<VocShop>();
		VocShop model = null;
		Object[] objArr = null;
		for(Object obj:query.getResultList()){
			objArr = (Object[])obj;
			model = new VocShop();
			model.setId(Long.parseLong(String.valueOf(objArr[0])));
			model.setName(String.valueOf(objArr[1]));
			model.setCompanyId(Long.parseLong(String.valueOf(objArr[2])));
			vocShop.add(model);
		}
		return vocShop;
		/*String sql = "SELECT o FROM VocShop o join o.channel c where 1=1 ";
		if(!StringUtils.isEmpty(channelId)){
			sql+=" and c.id ="+channelId;
		}
		if(!StringUtils.isEmpty(companyId)){
			sql+=" and o.companyId ="+companyId;
		}
		TypedQuery<VocShop> query= entityManager().createQuery(sql, VocShop.class);
		return query.getResultList();*/
		
   }
	
	/**
	 * 根据渠道 公司 查询店铺
	 * @param channelId
	 * @param companyId
	 * @return
	 */
	public static Long findUniqueShopBrandByCompany(Long companyId,Long channelId,String brandId,Long shopId) {
		String sql = "select count(*) from  voc_shop vs,voc_shop_voc_brands vb ";
		 sql += " where vs.id = vb.voc_shops ";
		 sql += " and vs.company_id = "+companyId; 
		 sql += " and vs.channel = "+channelId; 
         sql += " and  vb.voc_brands in ("+brandId+")";
         if(!StringUtils.isEmpty(shopId)){
         sql += " and  vs.id!="+shopId;	 
         }
		
       javax.persistence.Query query = entityManager().createNativeQuery(sql.toString());
        long count = 0;
        try {
			count = Long.parseLong(String.valueOf(query.getSingleResult()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return  count;
    }
	
	/**
	 * 查询公司下相同店铺名称数
	 * @param companyId
	 * @param shopName
	 * @return
	 */
	public static Long findShopName(Long companyId,String shopName) {
		String sql = "select count(*) from voc_shop vs where vs.company_id = "+companyId+" and vs.`name` = '"+shopName+"'";
       javax.persistence.Query query = entityManager().createNativeQuery(sql.toString());
        long count = 0;
        try {
			count = Long.parseLong(String.valueOf(query.getSingleResult()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return  count;
    }
	
	public static List<VocShop> findAllVocShops(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocShop o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocShop.class).getResultList();
    }

	public static VocShop findVocShop(Long id) {
        if (id == null) return null;
        return entityManager().find(VocShop.class, id);
    }
	public static Long findVocShopByName(Long id,Long channelId,String name,Long companyId) {
        StringBuffer sql = new StringBuffer("select count(o) from VocShop o where o.name=:name and o.companyId=:companyId ");
        if(id!=null){
        	sql.append(" and o.id !=:id");
        }
        if(channelId != null){
        	sql.append(" and o.channel.id =:channelId");
        }
        
        TypedQuery<Long> query =  entityManager().createQuery(sql.toString(), Long.class);
        query.setParameter("name", name);
        query.setParameter("companyId", companyId);
        query.setParameter("channelId", channelId);
        if(id!=null){
        	query.setParameter("id", id);
        }
        return query.getSingleResult();
    }
	public static List<VocBrand> findVocShopIsVisable(Long id) {
        if (id == null) return null;
        String sql="select b from VocBrand b join b.vocShops o where o.id=:id and o.isVisable="+true+" and b.isVisable="+true;
        TypedQuery<VocBrand> query = entityManager().createQuery(sql, VocBrand.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
	public static List<VocShop> findVocShopEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VocShop o", VocShop.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<VocShop> findVocShopEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM VocShop o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, VocShop.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	
	/**
	 * 查询店铺
	 * @param qm
	 * @return
	 */
	public static PageModel<VocShop> getQueryShop(QueryModel<VocShop> qm){
		 Map parmMap = qm.getInputMap();
		 VocShop model = qm.getEntity();
		 
		PageModel<VocShop> pageModel = new PageModel<VocShop>();
		StringBuffer sql = new StringBuffer(" SELECT o FROM VocShop o where 1=1 ");
		Map<String,Object> map = new HashMap<String, Object>();
         if(null != model.getCompanyId()){
        	 sql.append(" and o.companyId =:companyId");
        	 map.put("companyId", model.getCompanyId());
         }
		
        TypedQuery<VocShop> query = entityManager().createQuery(sql.toString()+" order by o.createTime desc  ", VocShop.class);
        
        System.out.println(sql.toString()+"------123-----");
        
         for(String key:map.keySet()){
           query.setParameter(key, map.get(key));	
         }
         
           int totalCount = 0;{ //查总数
           TypedQuery<Long> countQuery = entityManager().createQuery(sql.toString().replaceAll(" SELECT o", "SELECT count(o)"), Long.class);
           for(String key:map.keySet()){
        	   countQuery.setParameter(key, map.get(key));	
           }
             totalCount = countQuery.getSingleResult().intValue();
           }
         
         if(qm.getLimit() > 0){ //分页截取
        	query.setFirstResult(qm.getStart());
            query.setMaxResults(qm.getLimit());
         }
         pageModel.setTotalCount(totalCount);
         pageModel.setDataList(query.getResultList());
         
         return pageModel;

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
            VocShop attached = VocShop.findVocShop(this.id);
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
    public VocShop merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        VocShop merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	/*public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }*/

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

	public UmpChannel getChannel() {
		return channel;
	}

	public void setChannel(UmpChannel channel) {
		this.channel = channel;
	}

	public Set<VocGoods> getGoods() {
		return goods;
	}

	public void setGoods(Set<VocGoods> goods) {
		this.goods = goods;
	}

	public Set<VocBrand> getVocBrands() {
		return vocBrands;
	}

	public void setVocBrands(Set<VocBrand> vocBrands) {
		this.vocBrands = vocBrands;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}

	public static List<VocShop> findAllVocShopsByVocChannelsAndCompany(
			String ids, UmpCompany company) {
		String sql = "SELECT o FROM VocShop o join o.channel c where 1=1 ";
		if(!StringUtils.isEmpty(ids)){
			sql+=" and c.id in("+ids+")";
		}
		if(!StringUtils.isEmpty(company)){
			sql+=" and o.companyId ="+company.getId();
		}
		TypedQuery<VocShop> query= entityManager().createQuery(sql, VocShop.class);
		return query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<VocShop> findAllVocShopsByChannelIdsAndCompany(
			String ids, UmpCompany company) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.id,s.name from voc_shop s where s.channel in(:ids) and company_id = :companyId ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		 map.put("ids", CommonUtils.stringArray2Set(ids));
		 map.put("companyId", company.getId());
			javax.persistence.Query	query=entityManager().createNativeQuery(sql.toString());
			for (String key : map.keySet()) {
				query.setParameter(key, map.get(key));
			}
			
			List<VocShop> vocShop = new ArrayList<VocShop>();
			VocShop model = null;
			Object[] objArr = null;
			for(Object obj:query.getResultList()){
				objArr = (Object[])obj;
				model = new VocShop();
				model.setId(Long.parseLong(String.valueOf(objArr[0])));
				model.setName(String.valueOf(objArr[1]));
				vocShop.add(model);
			}
			
		return vocShop;
	}
	
	
	
}

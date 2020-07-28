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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.constant.UmpCompanyStatus;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 公司服务
 * @author 9client
 *
 */
@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpCompanyService {
    
	@NotNull
    private String  companyCode;

    /**
     */
    @NotNull
    private Long productId;
    
    @Transient
    private String productName;
    

    /**
     */
    @NotNull
    private Long versionId;
    
    @Transient
    private String versionName;

    /**
     * 可创建账户数
     */
    private long maxAccount;
    
    /**
     * 服务状态
     */
    @Enumerated
    private UmpCompanyServiceStatus companyServiceStatus;
    
    
    /**
     * 
     */
    @ManyToMany
    private Set<UmpChannel> channels = new HashSet<UmpChannel>();

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date serviceStartTime;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date serviceEndTime;
    
    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Long getProductId() {
        return this.productId;
    }

	public void setProductId(Long productId) {
        this.productId = productId;
    }

	public Long getVersionId() {
        return this.versionId;
    }

	public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

	public long getMaxAccount() {
        return this.maxAccount;
    }

	public void setMaxAccount(long maxAccount) {
        this.maxAccount = maxAccount;
    }

	public Date getServiceStartTime() {
        return this.serviceStartTime;
    }

	public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

	public Date getServiceEndTime() {
        return this.serviceEndTime;
    }

	public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }
	
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
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
	
	
	

	public UmpCompanyServiceStatus getCompanyServiceStatus() {
		return companyServiceStatus;
	}

	public void setCompanyServiceStatus(UmpCompanyServiceStatus companyServiceStatus) {
		this.companyServiceStatus = companyServiceStatus;
	}

	public Set<UmpChannel> getChannels() {
		return channels;
	}

	public void setChannels(Set<UmpChannel> channels) {
		this.channels = channels;
	}
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("companyId", "productId", "versionId", "maxAccount", "serviceStartTime", "serviceEndTime");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpCompanyService().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpCompanyServices() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpCompanyService o", Long.class).getSingleResult();
    }

	public static List<UmpCompanyService> findAllUmpCompanyServices() {
        return entityManager().createQuery("SELECT o FROM UmpCompanyService o", UmpCompanyService.class).getResultList();
    }
	
	public static List<UmpChannel> findChannelsByCompanyServiceId(Long companyServiceId) {
        return entityManager().createQuery("SELECT o FROM UmpChannel o inner join o.companyServices companyServices where companyServices.id in("+companyServiceId+") ", UmpChannel.class).getResultList();
    }
	
	/**
	 * 根据条件查询
	 * @param model
	 * @return
	 */
	public static PageModel<UmpCompanyService> getQueryUmpCompanyServices(QueryModel<UmpCompanyService> qm) {
		StringBuffer sql = new StringBuffer(" SELECT o FROM UmpCompanyService o,UmpCompany cp ");
		Map<String,Object> map = new HashMap<String, Object>();
		UmpCompanyService model = qm.getEntity();
		PageModel<UmpCompanyService> pageModel = new PageModel<UmpCompanyService>();
        
		if(null != model.getChannels() && model.getChannels().size() > 0){
        	 sql.append(" inner join  o.channels  channels  where channels in :channels ");
         	 map.put("channels", model.getChannels());
		 }else{
			 sql.append(" WHERE 1=1 ");
		 }
		
		if(null != model.getCompanyCode() && !"".equals(model.getCompanyCode())){
		    sql.append(" and o.companyCode like :companyCode ");
		    map.put("companyCode", "%"+model.getCompanyCode()+"%");
		}
		
		if(null != model.getCompanyServiceStatus()){
			sql.append(" and o.companyServiceStatus = :companyServiceStatus ");
			map.put("companyServiceStatus", model.getCompanyServiceStatus());
		}
		
		if(null != model.getVersionId() && model.getVersionId() > 0){
			sql.append(" and o.versionId = :versionId ");
			map.put("versionId", model.getVersionId());
		}
		
		if(null != model.getProductId() && model.getProductId() > 0){
			sql.append(" and o.productId = :productId ");
			map.put("productId", model.getProductId());
		}
		
				
        if(null != model.getServiceStartTime()){
        	sql.append(" and o.serviceStartTime = :serviceStartTime ");
        	map.put("serviceStartTime", model.getServiceStartTime());
		}
        
        if(null != model.getServiceEndTime()){
        	sql.append(" and o.serviceEndTime = :serviceEndTime ");
        	map.put("serviceEndTime", model.getServiceEndTime());
		}
        
         sql.append("  and o.companyCode = cp.companyCode  and cp.status = :status");
         map.put("status", UmpCompanyStatus.审核通过);
         
         
         
         TypedQuery<UmpCompanyService> query = entityManager().createQuery(sql.toString()+" order by o.serviceStartTime", UmpCompanyService.class);
         
         for(String key:map.keySet()){
           query.setParameter(key, map.get(key));	
         }
        
          int totalCount = 0;
          
          { //查总数
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

	public static List<UmpCompanyService> findAllUmpCompanyServices(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpCompanyService o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpCompanyService.class).getResultList();
    }
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Long findUmpCompanyServiceByVersionId(Long verionsId) {
        if (verionsId == null) return null;
        String sql="select count(o) from UmpCompanyService o where o.id="+verionsId+" and o.companyServiceStatus in (:stauts)";
        List<UmpCompanyServiceStatus> list=new ArrayList<UmpCompanyServiceStatus>();
        list.add(UmpCompanyServiceStatus.试用);
        list.add(UmpCompanyServiceStatus.预到期);
        list.add(UmpCompanyServiceStatus.开通);
        TypedQuery<Long> query = entityManager().createQuery(sql, Long.class);
        query.setParameter("stauts", list);
        return query.getSingleResult();
    }
	/**
	 * 查询允许开通最大账号数
	 * @param company
	 * @return
	 */
	public static Long findMaxAccountCount(UmpCompany company){
		if (company == null) return null;
        String sql="select sum(o.maxAccount) from UmpCompanyService o where o.companyCode=:companyCode and o.companyServiceStatus in (:stauts)";
        List<UmpCompanyServiceStatus> list=new ArrayList<UmpCompanyServiceStatus>();
        list.add(UmpCompanyServiceStatus.试用);
        list.add(UmpCompanyServiceStatus.预到期);
        list.add(UmpCompanyServiceStatus.开通);
        TypedQuery<Long> query = entityManager().createQuery(sql, Long.class);
        query.setParameter("companyCode", company.getCompanyCode());
        query.setParameter("stauts", list);
        return query.getSingleResult();
	}
	/**
	 * 查询可用服务
	 * @param id
	 * @return
	 */
	public static List<UmpCompanyService> findUmpCompanyServiceByVersionId(String companyCode) {
        if (companyCode == null) return null;
        String sql="select o from UmpCompanyService o where o.companyCode=:companyCode  and o.companyServiceStatus in (:stauts)";
        List<UmpCompanyServiceStatus> list=new ArrayList<UmpCompanyServiceStatus>();
        list.add(UmpCompanyServiceStatus.试用);
        list.add(UmpCompanyServiceStatus.预到期);
        list.add(UmpCompanyServiceStatus.开通);
        TypedQuery<UmpCompanyService> query = entityManager().createQuery(sql, UmpCompanyService.class);
        query.setParameter("companyCode", companyCode);
        query.setParameter("stauts", list);
        return query.getResultList();
    }
	public static UmpCompanyService findUmpCompanyService(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpCompanyService.class, id);
    }

	public static List<UmpCompanyService> findUmpCompanyServiceEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpCompanyService o", UmpCompanyService.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpCompanyService> findUmpCompanyServiceEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpCompanyService o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpCompanyService.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void persist(UmpCompany company) {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        company.persist();
    }
	
	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UmpCompanyService attached = UmpCompanyService.findUmpCompanyService(this.id);
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
    public UmpCompanyService merge(UmpCompany company) {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpCompanyService merged = this.entityManager.merge(this);
        company.merge();
        this.entityManager.flush();
        return merged;
    }
	@Transactional
    public UmpCompanyService merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpCompanyService merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	public static List<UmpCompanyService> findUmpCompanyServiceByCode(String companyCode) {
		if (companyCode == null)
			return null;
		
		return entityManager().createQuery(
				"SELECT o FROM UmpCompanyService o where o.companyCode = '"
						+ companyCode + "'", UmpCompanyService.class)
				.getResultList();
	}
	/**
	 * 查询服务
	 * @param companyCode
	 * @param status
	 * @return
	 */
	public static List<UmpCompanyService> findUmpCompanyServiceByCodeAndServiceStatus(String companyCode,UmpCompanyServiceStatus status) {
		if (StringUtils.isEmpty(companyCode)&&companyCode.trim().equals(""))
			return null;
		String sql ="SELECT o FROM UmpCompanyService o where o.companyCode ='"+ companyCode + "' ";
		if(status!=null){
			sql+=" and o.companyServiceStatus =:status";
		}
		TypedQuery<UmpCompanyService> query = entityManager().createQuery(sql, UmpCompanyService.class);
		query.setParameter("status", status);
		return query.getResultList();
				
	}
	/**
	 * 获取该公司下的所有平台
	 * @param company
	 * @return
	 */
	public static UmpCompanyService findChannllByCode(PubOperator pubOper,Long ProductId) {
		String hql = "";
		if(pubOper.getPubRole() == null){
			hql = "SELECT o FROM UmpCompanyService o where 1=1 and o.companyCode='"+pubOper.getCompany().getCompanyCode()+"' and o.productId = "+ProductId;
		}else{
			
		}
		return entityManager().createQuery(hql.toString(), UmpCompanyService.class).getSingleResult();
	}
	
	public static List<UmpCompanyService> findUmpCompanyServiceByCodeAndProductId(Long productId, String companyCode) {
		if (companyCode == null)return null;
		return entityManager().createQuery(
				"SELECT o FROM UmpCompanyService o where o.companyCode = '"
						+ companyCode + "' and o.productId = "+productId, UmpCompanyService.class)
				.getResultList();
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 更新服务状态
	 * @param list
	 * @param status
	 */
	@Transactional
	public static void updateSerivceStatus(List<UmpCompanyService> list,
			UmpCompanyServiceStatus status) {
		for (UmpCompanyService service: list) {
			service.setCompanyServiceStatus(status);
		}
//		String sql=" update UmpCompanyService o set o.companyServiceStatus=:companyServiceStatus where o.id in (:ids)";
//		Query query = entityManager().createNativeQuery(sql);
//		query.setParameter("companyServiceStatus", status);
//		query.setParameter("ids", list);
//		query.executeUpdate();
	}
}

package com.nineclient.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.utils.CommonUtils;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UmpChannel implements java.io.Serializable {
	@ManyToMany(mappedBy="umpChannels")
	private Set<VocBrand> vocBrands;
    /**
     */
    @NotNull
    private String channelName;

  

    /**
     */
    private String remark;

    /**
     */
    private Boolean isVisable;

    /**
     */
    private Boolean isDeleted;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createTime;

    /**
     */
    @ManyToOne
    private UmpProduct product;
    
    @ManyToMany(mappedBy="channels")
    private Set<UmpCompanyService> companyServices = new HashSet<UmpCompanyService>();
    
    
    
    @OneToMany(mappedBy="channel")
    private Set<VocShop> shops = new HashSet<VocShop>();
    
    
    @OneToMany(mappedBy="channel")
    private Set<VocGoods> goods;
    @OneToMany(mappedBy="umpChannel")
    private Set<VocAccount> vocAccounts;
    
    
    public Set<VocAccount> getVocAccounts() {
		return vocAccounts;
	}

	public void setVocAccounts(Set<VocAccount> vocAccounts) {
		this.vocAccounts = vocAccounts;
	}

	/**
     * 根据渠道名称查询渠道
     * @param sortFieldName
     * @param sortOrder
     * @return
     */
    public static List<UmpChannel> findAllUmpChannelsByName(String channelName) {
        String jpaQuery = "SELECT o FROM UmpChannel o where o.channelName='"+channelName+"'";
        return entityManager().createQuery(jpaQuery, UmpChannel.class).getResultList();
    }
    
    /**
     * 根据产品ID查询渠道
     * @param productId
     * @return
     */
    public static List<UmpChannel> findUmpChannelByProduct(Long productId) {
        return entityManager().createQuery("SELECT o FROM UmpChannel o where o.isVisable = 1 AND o.product="+productId, UmpChannel.class).getResultList();
    }

	public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		return "";
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("channelName", "remark", "isVisable", "isDeleted", "createTime", "product");

	public static final EntityManager entityManager() {
        EntityManager em = new UmpChannel().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUmpChannels() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UmpChannel o", Long.class).getSingleResult();
    }

	public static List<UmpChannel> findAllUmpChannels() {
        return entityManager().createQuery("SELECT o FROM UmpChannel o", UmpChannel.class).getResultList();
    }
	
	/**
	 * 
	 * @param company
	 * @param product
	 * @return
	 */
	public static List<UmpChannel> findAllChannelsbuyProductNameAndCompanyService(UmpCompany company,UmpProduct product) {
		String sql="select o from UmpCompanyService o where o.companyServiceStatus not in (:status) and  o.companyCode='"+company.getCompanyCode()+"' and o.productId="+product.getId();
		TypedQuery<UmpCompanyService> query = entityManager().createQuery(sql, UmpCompanyService.class);
		List<UmpCompanyServiceStatus> listStatus=new ArrayList<UmpCompanyServiceStatus>();
		listStatus.add(UmpCompanyServiceStatus.关闭);
		listStatus.add(UmpCompanyServiceStatus.到期);
		query.setParameter("status", listStatus);
		UmpCompanyService serivce = query.getSingleResult();
		Set<UmpChannel> channels =  serivce.getChannels();
		if(channels==null||channels.size()<1)
			return null;
		return CommonUtils.set2List(channels);
    }
	
	
	/**
	 * 查询voc渠道
	 * @return
	 */
	public static List<UmpChannel> findAllChannelsbuyProductName(String productName) {
        return entityManager().createQuery("SELECT o FROM UmpChannel o join o.product p where p.productName ='"+productName+"'", UmpChannel.class).getResultList();
    }
	/**
	 * 查询可见渠道
	 * @param productId
	 * @return
	 */
	public static List<UmpChannel> findAllChannelsbuyProductId(Long productId) {
        return entityManager().createQuery("SELECT o FROM UmpChannel o join o.product p where o.isVisable =1 and p.id ="+productId, UmpChannel.class).getResultList();
    }
	
	/**
	 * voc产品渠道
	 * @param companyCode
	 * @return
	 */
	public static List<UmpChannel> findChannelsByCompany(String companyCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ump_channel uc where uc.id in( ");
		sql.append(" select cc.channels from ump_company_service_channels cc where cc.company_services in( ");
		sql.append(" select uu.id from ump_company_service uu where uu.company_service_status not in(4,5) ");
		sql.append(" and uu.product_id=2 ");
		sql.append(" and uu.company_code = '"+companyCode+"') ");
		sql.append(")");
		Session session = (Session) entityManager().getDelegate();
		Query query = session.createSQLQuery(sql.toString()).addEntity(UmpChannel.class);
		return query.list();
	}
	
	public static List<UmpChannel> findAllUmpChannelsByIds(String ids) {
		String sql="SELECT o FROM UmpChannel o where o.id in ("+ids+")";
		TypedQuery<UmpChannel> query = entityManager().createQuery(sql, UmpChannel.class);
        return query.getResultList();
    }
	public static List<UmpChannel> findAllUmpChannels(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpChannel o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpChannel.class).getResultList();
    }

	public static UmpChannel findUmpChannel(Long id) {
        if (id == null) return null;
        return entityManager().find(UmpChannel.class, id);
    }
	
	/**
	 * 根据id查询返回一个list
	 * @param id
	 * @return
	 */
	public static List<UmpChannel> findUmpChannelById(Long id) {
        if (id == null) return null;
        return entityManager().createQuery("SELECT o FROM UmpChannel o where 1=1 and o.id = "+ id, UmpChannel.class).getResultList();
    }

	public static List<UmpChannel> findUmpChannelEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UmpChannel o", UmpChannel.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<UmpChannel> findUmpChannelEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UmpChannel o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UmpChannel.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UmpChannel attached = UmpChannel.findUmpChannel(this.id);
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
    public UmpChannel merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UmpChannel merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getChannelName() {
        return this.channelName;
    }

	public void setChannelName(String channelName) {
        this.channelName = channelName;
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

	public UmpProduct getProduct() {
        return this.product;
    }

	public void setProduct(UmpProduct product) {
        this.product = product;
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

	public Set<UmpCompanyService> getCompanyServices() {
		return companyServices;
	}

	public void setCompanyServices(Set<UmpCompanyService> companyServices) {
		this.companyServices = companyServices;
	}

	public Set<VocShop> getShops() {
		return shops;
	}

	public void setShops(Set<VocShop> shops) {
		this.shops = shops;
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

}

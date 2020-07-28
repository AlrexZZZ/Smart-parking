package com.nineclient.qycloud.wcc.model;
import java.util.Date;
import javax.persistence.Entity; 
import javax.persistence.EntityManager; 
import org.hibernate.Session; 
import org.hibernate.Criteria; 
import org.hibernate.criterion.DetachedCriteria; 
import org.hibernate.criterion.Order; 
import org.hibernate.criterion.Projections; 
import org.springframework.beans.factory.annotation.Configurable; 
import org.springframework.roo.addon.javabean.RooJavaBean; 
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord; 
import org.springframework.roo.addon.tostring.RooToString; 
import javax.persistence.Column; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
import javax.persistence.PersistenceContext; 
import com.nineclient.utils.PageModel; 
import com.nineclient.utils.QueryModel; 
import java.util.Map; 
import java.util.List; 
import org.hibernate.criterion.MatchMode; 
import org.hibernate.criterion.Restrictions; 
import org.springframework.transaction.annotation.Transactional; 
import com.nineclient.utils.DateUtil; 

@Entity 
@Configurable 
@RooJavaBean 
@RooToString 
@RooJpaActiveRecord 
public class CopyOfScore { 
/*************arrtibute start*************/
@Id 
@GeneratedValue(strategy = GenerationType.AUTO) 
@Column(name = "id") 
private Long id; 
  private String stuNo_;//学号
private String name;
/*************arrtibute end*************/ 

 
} 

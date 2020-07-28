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
public class Score { 
/*************arrtibute start*************/
@Id 
@GeneratedValue(strategy = GenerationType.AUTO) 
@Column(name = "id") 
private Long id; 
  private String stuNo;//学号
  private String stuName;//姓名
  private int testScore;//评测分
  private int addScore;// 加分
  private int miScore;//减分
  private int score;//得分
  private int srange;//名次
  private String sstage;// 级别
  private String items;// 加减分项目
  private Date insertTime;
/*************arrtibute end*************/ 

/************* getter  and setter method start*************/ 
public Long getId() { 
  return id; 
} 
public void setId(Long id) { 
  this.id = id; 
} 
public String getStuNo() {
	return stuNo;
}
public void setStuNo(String stuNo) {
	this.stuNo = stuNo;
}
public int getTestScore() {
	return testScore;
}
public void setTestScore(int testScore) {
	this.testScore = testScore;
}
public int getAddScore() {
	return addScore;
}
public void setAddScore(int addScore) {
	this.addScore = addScore;
}
public int getMiScore() {
	return miScore;
}
public void setMiScore(int miScore) {
	this.miScore = miScore;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public int getSrange() {
	return srange;
}
public void setSrange(int srange) {
	this.srange = srange;
}
public String getSstage() {
	return sstage;
}
public void setSstage(String sstage) {
	this.sstage = sstage;
}
public String getItems() {
	return items;
}
public void setItems(String items) {
	this.items = items;
}
public String getStuName() { 
  return stuName; 
} 
public void setStuName(String stuName) { 
  this.stuName = stuName; 
} 
public Date getInsertTime() { 
  return insertTime; 
} 
public void setInsertTime(Date insertTime) { 
  this.insertTime = insertTime; 
} 
/*************getter  and setter method  end*************/

@PersistenceContext 
transient EntityManager entityManager; 
public EntityManager getEntityManager() { 
  return entityManager; 
} 
public void setEntityManager(EntityManager entityManager) { 
  this.entityManager = entityManager; 
} 

public static final EntityManager entityManager() { 
 EntityManager em = new Score().entityManager; 
 if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)"); 
 return em; 
} 

//按照id 查找    
public static Score findScore(Long id) { 
  if (id == null) return null; 
  return entityManager().find(Score.class, id);   
} 

//增加数据   
@Transactional 
public void persist()  { 
  if (this.entityManager == null) this.entityManager = entityManager(); 
  this.entityManager.persist(this);   
} 

//删除数据   
@Transactional 
public void remove() { 
  if (this.entityManager == null) this.entityManager = entityManager(); 
   if (this.entityManager.contains(this)) {   
    this.entityManager.remove(this); 
   } else { 
    Score attached = Score.findScore(this.id); 
    this.entityManager.remove(attached); 
   } 
} 

//更新数据   
@Transactional 
public Score merge() { 
   if (this.entityManager == null) this.entityManager = entityManager(); 
  Score merged = this.entityManager.merge(this);   
   this.entityManager.flush(); 
   return merged;  
} 

// 增加分页查询方法   
@SuppressWarnings({ "unchecked", "rawtypes" })public static PageModel<Score> getRecord(QueryModel<Score> qm){ 
   PageModel<Score> pageModel = new PageModel<Score>(); 
   Map parm = qm.getInputMap(); 
   Session s = (Session) entityManager().getDelegate(); 
  Criteria criteria = s.createCriteria(Score.class).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
String stuName = parm.get("stuName") == null?null:(String)parm.get("stuName"); 
if(null != stuName){  
   criteria.add(Restrictions.like("stuName", stuName, MatchMode.ANYWHERE)); 
} 
 Integer stuAge = parm.get("stuAge") == null?null:Integer.parseInt((String)parm.get("stuAge")); 
if(null != stuAge){  
  criteria.add(Restrictions.eq("stuAge", stuAge)); 
} 
 Date birth = parm.get("birth") == null?null:DateUtil.parseDateFormat((String)parm.get("birth")); 
if(null != birth){  
  criteria.add(Restrictions.eq("birth", birth)); 
} 
//固定字段 插入时间，按照插入、创建数据的时间来查询  
 Date beginTime = parm.get("beginTime") == null?null:DateUtil.parseDateFormat((String)parm.get("beginTime")); 
  if(null != beginTime){ 
  criteria.add(Restrictions.ge("insertTime", beginTime)); 
  }	  
 Date endTime = parm.get("endTime") == null?null:DateUtil.parseDateFormat((String)parm.get("endTime")); 
  if(null != endTime){ 
  criteria.add(Restrictions.le("insertTime", endTime)); 
  }	  
 
  Long totalCount = (Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult(); 
 criteria.setProjection(null); 
 criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
 criteria.setFirstResult(qm.getStart()); 
 criteria.setMaxResults(qm.getLimit()); 
 criteria.addOrder(Order.desc("insertTime")); //按照某个字段升序或者降序排列  
 pageModel.setTotalCount(Integer.parseInt(String.valueOf(totalCount))); 
 pageModel.setPageSize(qm.getLimit()); 
  List<Score> list = criteria.list(); 
  pageModel.setDataList(list); 
 return pageModel; 
 } 
 
} 

package com.nineclient.qycloud.wcc.dto;
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


public class ScoreDTO { 
/*************arrtibute start*************/

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
public ScoreDTO() {

}
public ScoreDTO(Long id, String stuNo, String stuName, int testScore,
		int addScore, int miScore, int score, int srange, String sstage,
		String items, Date insertTime) {

	this.id = id;
	this.stuNo = stuNo;
	this.stuName = stuName;
	this.testScore = testScore;
	this.addScore = addScore;
	this.miScore = miScore;
	this.score = score;
	this.srange = srange;
	this.sstage = sstage;
	this.items = items;
	this.insertTime = insertTime;
}


 
} 

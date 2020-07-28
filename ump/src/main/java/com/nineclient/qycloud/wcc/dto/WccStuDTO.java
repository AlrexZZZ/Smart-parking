package com.nineclient.qycloud.wcc.dto;

import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 泰隆产品实体类
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.utils.DateUtil;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;



public class WccStuDTO {

	private Long id;
	private String fname; // 姓名
	
	private String fphone; // 手机号
	
	private String stuNo; // 所在地区
	
	private String stuColleage;//院系
	
	private String fnickName; // 粉丝昵称
	
	private String openId; // 粉丝openid
    private Date insertTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFphone() {
		return fphone;
	}
	public void setFphone(String fphone) {
		this.fphone = fphone;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuColleage() {
		return stuColleage;
	}
	public void setStuColleage(String stuColleage) {
		this.stuColleage = stuColleage;
	}
	public String getFnickName() {
		return fnickName;
	}
	public void setFnickName(String fnickName) {
		this.fnickName = fnickName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public WccStuDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WccStuDTO(Long id, String fname, String fphone, String stuNo,
			String stuColleage, String fnickName, String openId, Date insertTime) {
		super();
		this.id = id;
		this.fname = fname;
		this.fphone = fphone;
		this.stuNo = stuNo;
		this.stuColleage = stuColleage;
		this.fnickName = fnickName;
		this.openId = openId;
		this.insertTime = insertTime;
	}



	
	
}

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



public class WccRepairRegDTO {
	private Long id;
	private String fname; // 姓名
	
	private String fphone; // 手机号
	
	private String stuNo; // 学号
	
	private String dept; // 院系
	
	private String area; // 所在区
	
	private String address; // 联系地址
	
	private String problemType; // 故障分类

	private String isBack; // 是否资料备案
	
	private String problemDes; // 故障描述
	
	private String fnickName; // 粉丝昵称
	
	private String openId; // 粉丝openid
	
	/**插入时间
     */
    private Date insertTime;

	public WccRepairRegDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WccRepairRegDTO(Long id, String fname, String fphone, String stuNo,
			String dept, String area, String address, String problemType,
			String isBack, String problemDes, String fnickName, String openId,
			Date insertTime) {
		super();
		this.id = id;
		this.fname = fname;
		this.fphone = fphone;
		this.stuNo = stuNo;
		this.dept = dept;
		this.area = area;
		this.address = address;
		this.problemType = problemType;
		this.isBack = isBack;
		this.problemDes = problemDes;
		this.fnickName = fnickName;
		this.openId = openId;
		this.insertTime = insertTime;
	}

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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getProblemDes() {
		return problemDes;
	}

	public void setProblemDes(String problemDes) {
		this.problemDes = problemDes;
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



	
	
	
}

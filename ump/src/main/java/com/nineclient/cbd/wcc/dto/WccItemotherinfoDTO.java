package com.nineclient.cbd.wcc.dto;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.nineclient.cbd.wcc.model.WccAppartment;

public class WccItemotherinfoDTO {
	
	private Long id;
	/**
	 * 图片地址
	 */
	
	private String otherPic;
	/**
	 * 列表标题
	 */
	
	private String otherTitle;
	/**
	 * 内容
	 */
	
	private String otherIntro;
	/**
	 * 项目PK
	 */
	
	private WccAppartment itemPk;
	
	/**
	 * 类型
	 */
	
	private String otherType;
	/**
	 * 发布日期
	 */
	private Date publicTime;

	/**
	 * 详情标题
	 */
	
	private String introTitle;
	/**
	 * 跳转地址
	 */
	
	private String url;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOtherPic() {
		return otherPic;
	}
	public void setOtherPic(String otherPic) {
		this.otherPic = otherPic;
	}
	public String getOtherTitle() {
		return otherTitle;
	}
	public void setOtherTitle(String otherTitle) {
		this.otherTitle = otherTitle;
	}
	public String getOtherIntro() {
		return otherIntro;
	}
	public void setOtherIntro(String otherIntro) {
		this.otherIntro = otherIntro;
	}
	public WccAppartment getItemPk() {
		return itemPk;
	}
	public void setItemPk(WccAppartment itemPk) {
		this.itemPk = itemPk;
	}
	public String getOtherType() {
		return otherType;
	}
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	public Date getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	public String getIntroTitle() {
		return introTitle;
	}
	public void setIntroTitle(String introTitle) {
		this.introTitle = introTitle;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public WccItemotherinfoDTO(Long id, String otherPic, String otherTitle,
			String otherIntro, WccAppartment itemPk, String otherType,
			Date publicTime, String introTitle, String url) {
		super();
		this.id = id;
		this.otherPic = otherPic;
		this.otherTitle = otherTitle;
		this.otherIntro = otherIntro;
		this.itemPk = itemPk;
		this.otherType = otherType;
		this.publicTime = publicTime;
		this.introTitle = introTitle;
		this.url = url;
	}
	public WccItemotherinfoDTO() {
		super();
	}
	
	
	
	
}

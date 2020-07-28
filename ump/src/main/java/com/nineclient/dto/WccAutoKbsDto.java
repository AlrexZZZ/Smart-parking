package com.nineclient.dto;

import java.io.Serializable;
import java.util.Date;

import com.nineclient.constant.WccAutokbsIsReview;
import com.nineclient.constant.WccAutokbsMatchWay;
import com.nineclient.constant.WccAutokbsReplyType;
import com.nineclient.constant.WccAutokbsShowWay;
import com.nineclient.model.PubOperator;
import com.nineclient.model.WccPlatformUser;

public class WccAutoKbsDto implements Serializable {
	
	private String keyWord;
	private String content;
	private WccAutokbsShowWay showWay;
	private Boolean active;
	private Boolean isDeleted;
	private Date insertTime;
	private WccAutokbsMatchWay matchWay;
	private WccAutokbsReplyType replyType;
	private WccAutokbsIsReview isReview;
	private String relatedIssues;
	private Long id;
	private Integer version;
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public WccAutokbsShowWay getShowWay() {
		return showWay;
	}
	public void setShowWay(WccAutokbsShowWay showWay) {
		this.showWay = showWay;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public WccAutokbsMatchWay getMatchWay() {
		return matchWay;
	}
	public void setMatchWay(WccAutokbsMatchWay matchWay) {
		this.matchWay = matchWay;
	}
	public WccAutokbsReplyType getReplyType() {
		return replyType;
	}
	public void setReplyType(WccAutokbsReplyType replyType) {
		this.replyType = replyType;
	}
	public WccAutokbsIsReview getIsReview() {
		return isReview;
	}
	public void setIsReview(WccAutokbsIsReview isReview) {
		this.isReview = isReview;
	}
	public String getRelatedIssues() {
		return relatedIssues;
	}
	public void setRelatedIssues(String relatedIssues) {
		this.relatedIssues = relatedIssues;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	
	
	

}

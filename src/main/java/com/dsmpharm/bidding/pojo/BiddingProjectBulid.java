package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@Entity
@Table(name="bidding_project_bulid")
public class BiddingProjectBulid implements Serializable {
    private static final long serialVersionUID = -8052829975594116821L;

    @Id
    private String id;

    private String docPublicTime;

    private String name;

    private String source;

    private String provinceId;

    private String cityId;

    private String productId;

    private String fileFormal;

    private String fileAsk;

    private String docInterTime;

    private String submitTime;

    private String publicTime;

    private String appealTime;

    private String noticeTime;

    private String proLabel;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getDocPublicTime() {
		return docPublicTime;
	}
    public void setDocPublicTime(String docPublicTime) {
		this.docPublicTime = docPublicTime;
	}
    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}
    public String getSource() {
		return source;
	}
    public void setSource(String source) {
		this.source = source;
	}
    public String getProvinceId() {
		return provinceId;
	}
    public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
    public String getCityId() {
		return cityId;
	}
    public void setCityId(String cityId) {
		this.cityId = cityId;
	}
    public String getProductId() {
		return productId;
	}
    public void setProductId(String productId) {
		this.productId = productId;
	}
    public String getFileFormal() {
		return fileFormal;
	}
    public void setFileFormal(String fileFormal) {
		this.fileFormal = fileFormal;
	}
    public String getFileAsk() {
		return fileAsk;
	}
    public void setFileAsk(String fileAsk) {
		this.fileAsk = fileAsk;
	}
    public String getDocInterTime() {
		return docInterTime;
	}
    public void setDocInterTime(String docInterTime) {
		this.docInterTime = docInterTime;
	}
    public String getSubmitTime() {
		return submitTime;
	}
    public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
    public String getPublicTime() {
		return publicTime;
	}
    public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}
    public String getAppealTime() {
		return appealTime;
	}
    public void setAppealTime(String appealTime) {
		this.appealTime = appealTime;
	}
    public String getNoticeTime() {
		return noticeTime;
	}
    public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
    public String getProLabel() {
		return proLabel;
	}
    public void setProLabel(String proLabel) {
		this.proLabel = proLabel;
	}
    public String getSuggestion() {
		return suggestion;
	}
    public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
    public String getGoStatus() {
		return goStatus;
	}
    public void setGoStatus(String goStatus) {
		this.goStatus = goStatus;
	}
}

package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/18
 */
@Entity
@Table(name="bidding_project_summary")
public class BiddingProjectSummary implements Serializable {
    private static final long serialVersionUID = -8481794816043003955L;

    @Id
    private String id;

    private String productId;

    private String status;

    private String lastRoundDecline;

    private String competitiveProduc;

    private String isRecord;

    private String isClinical;

    private String failureReasons;

    private String nextStep;

    private String estimatedTime;

    private String startTime;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getProductId() {
		return productId;
	}
    public void setProductId(String productId) {
		this.productId = productId;
	}
    public String getStatus() {
		return status;
	}
    public void setStatus(String status) {
		this.status = status;
	}
    public String getLastRoundDecline() {
		return lastRoundDecline;
	}
    public void setLastRoundDecline(String lastRoundDecline) {
		this.lastRoundDecline = lastRoundDecline;
	}
    public String getCompetitiveProduc() {
		return competitiveProduc;
	}
    public void setCompetitiveProduc(String competitiveProduc) {
		this.competitiveProduc = competitiveProduc;
	}
    public String getIsRecord() {
		return isRecord;
	}
    public void setIsRecord(String isRecord) {
		this.isRecord = isRecord;
	}
    public String getIsClinical() {
		return isClinical;
	}
    public void setIsClinical(String isClinical) {
		this.isClinical = isClinical;
	}
    public String getFailureReasons() {
		return failureReasons;
	}
    public void setFailureReasons(String failureReasons) {
		this.failureReasons = failureReasons;
	}
    public String getNextStep() {
		return nextStep;
	}
    public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
    public String getEstimatedTime() {
		return estimatedTime;
	}
    public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
    public String getStartTime() {
		return startTime;
	}
    public void setStartTime(String startTime) {
		this.startTime = startTime;
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

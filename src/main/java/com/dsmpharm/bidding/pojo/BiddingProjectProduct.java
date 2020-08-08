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
@Table(name="bidding_project_product")
public class BiddingProjectProduct implements Serializable {
    private static final long serialVersionUID = -6326818750957784244L;

    @Id
	private String id;
    private String projectId;

    private String projectSummaryId;

    private String productId;

    private String status;

    private String lastRoundDecline;

    private String competitiveProduc;

    private String isRecord;

    private String isClinical;

    private String failureReasons;

    private String nextStep;

    private String compeProducts;

    private String estimatedTime;

    private String startTime;

    private String delflag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}
    public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
    public String getProjectSummaryId() {
		return projectSummaryId;
	}
    public void setProjectSummaryId(String projectSummaryId) {
		this.projectSummaryId = projectSummaryId;
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
    public String getCompeProducts() {
		return compeProducts;
	}
    public void setCompeProducts(String compeProducts) {
		this.compeProducts = compeProducts;
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
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

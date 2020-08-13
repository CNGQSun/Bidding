package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@Entity
@Table(name="app_flow_approval")
public class AppFlowApproval implements Serializable {
    private static final long serialVersionUID = -8509442156873434528L;

    @Id
    private String id;

    private String projectPhaseId;

    private String applyId;

    private String flowNodeId;

    private String userId;

    private String approveResult;

    private String approveInfo;

    private String approveDate;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}

	public String getProjectPhaseId() {
		return projectPhaseId;
	}

	public void setProjectPhaseId(String projectPhaseId) {
		this.projectPhaseId = projectPhaseId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getFlowNodeId() {
		return flowNodeId;
	}
    public void setFlowNodeId(String flowNodeId) {
		this.flowNodeId = flowNodeId;
	}
    public String getUserId() {
		return userId;
	}
    public void setUserId(String userId) {
		this.userId = userId;
	}
    public String getApproveResult() {
		return approveResult;
	}
    public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
    public String getApproveInfo() {
		return approveInfo;
	}
    public void setApproveInfo(String approveInfo) {
		this.approveInfo = approveInfo;
	}
    public String getApproveDate() {
		return approveDate;
	}
    public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

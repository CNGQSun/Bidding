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
@Table(name="app_flow_apply")
public class AppFlowApply implements Serializable {
    private static final long serialVersionUID = -5563915801393435352L;

    @Id
    private String id;

    private String projectId;

    private String phaseId;

    private String userId;

    private String addDate;

    private String flowId;

    private String currentNode;

    private String status;

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
    public String getPhaseId() {
		return phaseId;
	}
    public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
    public String getUserId() {
		return userId;
	}
    public void setUserId(String userId) {
		this.userId = userId;
	}
    public String getAddDate() {
		return addDate;
	}
    public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
    public String getFlowId() {
		return flowId;
	}
    public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
    public String getCurrentNode() {
		return currentNode;
	}
    public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}
    public String getStatus() {
		return status;
	}
    public void setStatus(String status) {
		this.status = status;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

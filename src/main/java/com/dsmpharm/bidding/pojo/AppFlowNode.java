package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@Entity
@Table(name="app_flow_node")
public class AppFlowNode implements Serializable {
    private static final long serialVersionUID = -6637766682596652653L;

    private String id;

    private String flowId;

    private String flowNodeName;

    private String flowNodeRole;

    private String remark;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getFlowId() {
		return flowId;
	}
    public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
    public String getFlowNodeName() {
		return flowNodeName;
	}
    public void setFlowNodeName(String flowNodeName) {
		this.flowNodeName = flowNodeName;
	}
    public String getFlowNodeRole() {
		return flowNodeRole;
	}
    public void setFlowNodeRole(String flowNodeRole) {
		this.flowNodeRole = flowNodeRole;
	}
    public String getRemark() {
		return remark;
	}
    public void setRemark(String remark) {
		this.remark = remark;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

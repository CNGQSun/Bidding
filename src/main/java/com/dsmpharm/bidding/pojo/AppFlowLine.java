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
@Table(name="app_flow_line")
public class AppFlowLine implements Serializable {
    private static final long serialVersionUID = -7793442000893517796L;

    @Id
    private String id;

    private String flowId;

    private String prevNodeId;

    private String nextNodeId;

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
    public String getPrevNodeId() {
		return prevNodeId;
	}
    public void setPrevNodeId(String prevNodeId) {
		this.prevNodeId = prevNodeId;
	}
    public String getNextNodeId() {
		return nextNodeId;
	}
    public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
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

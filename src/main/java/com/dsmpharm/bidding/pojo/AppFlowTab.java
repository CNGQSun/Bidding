package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@Entity
@Table(name="app_flow_tab")
public class AppFlowTab implements Serializable {
    private static final long serialVersionUID = -9154005811354332129L;

    private String id;

    private String flowName;

    private String remark;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getFlowName() {
		return flowName;
	}
    public void setFlowName(String flowName) {
		this.flowName = flowName;
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

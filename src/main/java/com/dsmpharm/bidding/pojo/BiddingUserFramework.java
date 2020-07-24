package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Entity
@Table(name="bidding_user_framework")
public class BiddingUserFramework implements Serializable {
    private static final long serialVersionUID = -5216624279817367851L;

    @Id
    private String id;

    private String parentId;

    private String userId;

    private String status;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getParentId() {
		return parentId;
	}
    public void setParentId(String parentId) {
		this.parentId = parentId;
	}
    public String getUserId() {
		return userId;
	}
    public void setUserId(String userId) {
		this.userId = userId;
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

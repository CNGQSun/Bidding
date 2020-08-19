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
@Table(name="bidding_user_role")
public class BiddingUserRole implements Serializable {
    private static final long serialVersionUID = -4802186388191503785L;

    @Id
    private String id;

    private String userId;

    private String roleId;

    private String delflag;


    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getUserId() {
		return userId;
	}
    public void setUserId(String userId) {
		this.userId = userId;
	}
    public String getRoleId() {
		return roleId;
	}
    public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

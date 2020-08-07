package com.dsmpharm.bidding.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Entity
@Table(name="bidding_user")
public class BiddingLoginUser implements Serializable {
    private static final long serialVersionUID = -5807458707857459622L;

    @Id
    private String id;

    private String parentId;

    private String name;

    private String dept;

    private String email;

    private String phoneNumber;

	private String password;

    private String status;

    private String delflag;

    private String token;


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
    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}
    public String getDept() {
		return dept;
	}
    public void setDept(String dept) {
		this.dept = dept;
	}
    public String getEmail() {
		return email;
	}
    public void setEmail(String email) {
		this.email = email;
	}
    public String getPhoneNumber() {
		return phoneNumber;
	}
    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

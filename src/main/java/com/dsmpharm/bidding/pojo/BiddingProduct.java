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
@Table(name="bidding_product")
public class BiddingProduct implements Serializable {
    private static final long serialVersionUID = -6660589698245304444L;

    @Id
    private String id;

    private String name;

    private String code;

    private String commonName;

    private String enName;

    private String standards;

    private String status;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}
    public String getCode() {
		return code;
	}
    public void setCode(String code) {
		this.code = code;
	}
    public String getCommonName() {
		return commonName;
	}
    public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getStandards() {
		return standards;
	}
    public void setStandards(String standards) {
		this.standards = standards;
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

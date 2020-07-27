package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@Entity
@Table(name="bidding_province")
public class BiddingProvince implements Serializable {
    private static final long serialVersionUID = -6981395758260057455L;

    @Id
    private Integer id;

    private String proName;

    private Integer proSort;

    private String proRemark;

    public Integer getId() {
		return id;
	}
    public void setId(Integer id) {
		this.id = id;
	}
    public String getProName() {
		return proName;
	}
    public void setProName(String proName) {
		this.proName = proName;
	}
    public Integer getProSort() {
		return proSort;
	}
    public void setProSort(Integer proSort) {
		this.proSort = proSort;
	}
    public String getProRemark() {
		return proRemark;
	}
    public void setProRemark(String proRemark) {
		this.proRemark = proRemark;
	}
}

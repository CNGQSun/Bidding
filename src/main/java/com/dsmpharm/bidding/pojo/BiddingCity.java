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
@Table(name="bidding_city")
public class BiddingCity implements Serializable {
    private static final long serialVersionUID = -8457999987077739426L;

    @Id
    private Integer id;

    private String cityName;

    private Integer proId;

    private Integer citySort;

    public Integer getId() {
		return id;
	}
    public void setId(Integer id) {
		this.id = id;
	}
    public String getCityName() {
		return cityName;
	}
    public void setCityName(String cityName) {
		this.cityName = cityName;
	}
    public Integer getProId() {
		return proId;
	}
    public void setProId(Integer proId) {
		this.proId = proId;
	}
    public Integer getCitySort() {
		return citySort;
	}
    public void setCitySort(Integer citySort) {
		this.citySort = citySort;
	}
}

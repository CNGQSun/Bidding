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
@Table(name="bidding_city")
public class BiddingCity implements Serializable {
    private static final long serialVersionUID = -6839725557824329939L;

    @Id
    private String id;

    private String name;

    private String provinceId;

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
    public String getProvinceId() {
		return provinceId;
	}
    public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
}

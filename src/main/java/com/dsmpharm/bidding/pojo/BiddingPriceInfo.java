package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/21
 */
@Entity
@Table(name="bidding_price_info")
public class BiddingPriceInfo implements Serializable {
    private static final long serialVersionUID = -7818120201473350884L;

    @Id
    private String id;
    private String area;
    private String userId;
    private String userName;
    private String proId;
    private String region;
    private String productEn;

    private String bidPrice;

    private String isBid;

    private String bidTime;

    private String remarks;

    private String strikePrice;

    private String delflag;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getArea() {
		return area;
	}
    public void setArea(String area) {
		this.area = area;
	}
    public String getUserId() {
		return userId;
	}
    public void setUserId(String userId) {
		this.userId = userId;
	}
    public String getProId() {
		return proId;
	}
    public void setProId(String proId) {
		this.proId = proId;
	}
    public String getProductEn() {
		return productEn;
	}
    public void setProductEn(String productEn) {
		this.productEn = productEn;
	}
    public String getBidPrice() {
		return bidPrice;
	}
    public void setBidPrice(String bidPrice) {
		this.bidPrice = bidPrice;
	}
    public String getIsBid() {
		return isBid;
	}
    public void setIsBid(String isBid) {
		this.isBid = isBid;
	}
    public String getBidTime() {
		return bidTime;
	}
    public void setBidTime(String bidTime) {
		this.bidTime = bidTime;
	}
    public String getRemarks() {
		return remarks;
	}
    public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    public String getStrikePrice() {
		return strikePrice;
	}
    public void setStrikePrice(String strikePrice) {
		this.strikePrice = strikePrice;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

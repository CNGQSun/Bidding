package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@Entity
@Table(name="bidding_product_collection")
public class BiddingProductCollection implements Serializable {
    private static final long serialVersionUID = -8466619568712343576L;

    @Id
    private String id;

    private String fileText;

    private String suggestion;

    private String goStatus;
    private String price;
    private String historicalsSales;
    private String marketShare;
    private String qualityLevel;
    private String winningBid;



    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getFileText() {
		return fileText;
	}
    public void setFileText(String fileText) {
		this.fileText = fileText;
	}
    public String getSuggestion() {
		return suggestion;
	}
    public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
    public String getGoStatus() {
		return goStatus;
	}
    public void setGoStatus(String goStatus) {
		this.goStatus = goStatus;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getHistoricalsSales() {
		return historicalsSales;
	}

	public void setHistoricalsSales(String historicalsSales) {
		this.historicalsSales = historicalsSales;
	}

	public String getMarketShare() {
		return marketShare;
	}

	public void setMarketShare(String marketShare) {
		this.marketShare = marketShare;
	}

	public String getQualityLevel() {
		return qualityLevel;
	}

	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}

	public String getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(String winningBid) {
		this.winningBid = winningBid;
	}
}

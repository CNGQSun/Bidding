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
@Table(name="bidding_strategy_analysis")
public class BiddingStrategyAnalysis implements Serializable {
    private static final long serialVersionUID = -8381513129214834462L;

    @Id
    private String id;

    private String fileProductInfo;

    private String fileQualityLevel;

    private String fileProductGro;

    private String fileProduct;

    private String filePriceLimit;

    private String fileCompeInfo;

    private String fileQualityLevels;

    private String fileCompeGro;

    private String fileQuotationEstimate;

    private String fileQuotationOther;

    private String fileNationalPrice;

    private String fileCompetitiveLimit;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getFileProductInfo() {
		return fileProductInfo;
	}
    public void setFileProductInfo(String fileProductInfo) {
		this.fileProductInfo = fileProductInfo;
	}
    public String getFileQualityLevel() {
		return fileQualityLevel;
	}
    public void setFileQualityLevel(String fileQualityLevel) {
		this.fileQualityLevel = fileQualityLevel;
	}
    public String getFileProductGro() {
		return fileProductGro;
	}
    public void setFileProductGro(String fileProductGro) {
		this.fileProductGro = fileProductGro;
	}
    public String getFileProduct() {
		return fileProduct;
	}
    public void setFileProduct(String fileProduct) {
		this.fileProduct = fileProduct;
	}
    public String getFilePriceLimit() {
		return filePriceLimit;
	}
    public void setFilePriceLimit(String filePriceLimit) {
		this.filePriceLimit = filePriceLimit;
	}
    public String getFileCompeInfo() {
		return fileCompeInfo;
	}
    public void setFileCompeInfo(String fileCompeInfo) {
		this.fileCompeInfo = fileCompeInfo;
	}
    public String getFileQualityLevels() {
		return fileQualityLevels;
	}
    public void setFileQualityLevels(String fileQualityLevels) {
		this.fileQualityLevels = fileQualityLevels;
	}
    public String getFileCompeGro() {
		return fileCompeGro;
	}
    public void setFileCompeGro(String fileCompeGro) {
		this.fileCompeGro = fileCompeGro;
	}
    public String getFileQuotationEstimate() {
		return fileQuotationEstimate;
	}
    public void setFileQuotationEstimate(String fileQuotationEstimate) {
		this.fileQuotationEstimate = fileQuotationEstimate;
	}
    public String getFileQuotationOther() {
		return fileQuotationOther;
	}
    public void setFileQuotationOther(String fileQuotationOther) {
		this.fileQuotationOther = fileQuotationOther;
	}
    public String getFileNationalPrice() {
		return fileNationalPrice;
	}
    public void setFileNationalPrice(String fileNationalPrice) {
		this.fileNationalPrice = fileNationalPrice;
	}

	public String getFileCompetitiveLimit() {
		return fileCompetitiveLimit;
	}

	public void setFileCompetitiveLimit(String fileCompetitiveLimit) {
		this.fileCompetitiveLimit = fileCompetitiveLimit;
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
}

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
@Table(name="bidding_doc_interpretation")
public class BiddingDocInterpretation implements Serializable {
    private static final long serialVersionUID = -8647489139207882245L;

    @Id
    private String id;

    private String type;

    private String range;

    private String noReason;

    private String timeRangeStart;

    private String timeRangeEnd;

    private String commonName;

    private String standards;

    private String qualityLevel;

    private String priceLimit;

    private String priceLimitReference;

    private String priceLimitExplain;

    private String fileBuyRules;

    private String fileQuotedPrice;

    private String industryInfluence;

    private String selfInfluence;

    private String solicitingOpinions;

    private String fileSolicitingOpinions;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getType() {
		return type;
	}
    public void setType(String type) {
		this.type = type;
	}
    public String getRange() {
		return range;
	}
    public void setRange(String range) {
		this.range = range;
	}
    public String getNoReason() {
		return noReason;
	}
    public void setNoReason(String noReason) {
		this.noReason = noReason;
	}
    public String getTimeRangeStart() {
		return timeRangeStart;
	}
    public void setTimeRangeStart(String timeRangeStart) {
		this.timeRangeStart = timeRangeStart;
	}
    public String getTimeRangeEnd() {
		return timeRangeEnd;
	}
    public void setTimeRangeEnd(String timeRangeEnd) {
		this.timeRangeEnd = timeRangeEnd;
	}
    public String getCommonName() {
		return commonName;
	}
    public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
    public String getStandards() {
		return standards;
	}
    public void setStandards(String standards) {
		this.standards = standards;
	}
    public String getQualityLevel() {
		return qualityLevel;
	}
    public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}
    public String getPriceLimit() {
		return priceLimit;
	}
    public void setPriceLimit(String priceLimit) {
		this.priceLimit = priceLimit;
	}
    public String getPriceLimitReference() {
		return priceLimitReference;
	}
    public void setPriceLimitReference(String priceLimitReference) {
		this.priceLimitReference = priceLimitReference;
	}
    public String getPriceLimitExplain() {
		return priceLimitExplain;
	}
    public void setPriceLimitExplain(String priceLimitExplain) {
		this.priceLimitExplain = priceLimitExplain;
	}
    public String getFileBuyRules() {
		return fileBuyRules;
	}
    public void setFileBuyRules(String fileBuyRules) {
		this.fileBuyRules = fileBuyRules;
	}
    public String getFileQuotedPrice() {
		return fileQuotedPrice;
	}
    public void setFileQuotedPrice(String fileQuotedPrice) {
		this.fileQuotedPrice = fileQuotedPrice;
	}
    public String getIndustryInfluence() {
		return industryInfluence;
	}
    public void setIndustryInfluence(String industryInfluence) {
		this.industryInfluence = industryInfluence;
	}
    public String getSelfInfluence() {
		return selfInfluence;
	}
    public void setSelfInfluence(String selfInfluence) {
		this.selfInfluence = selfInfluence;
	}
    public String getSolicitingOpinions() {
		return solicitingOpinions;
	}
    public void setSolicitingOpinions(String solicitingOpinions) {
		this.solicitingOpinions = solicitingOpinions;
	}
    public String getFileSolicitingOpinions() {
		return fileSolicitingOpinions;
	}
    public void setFileSolicitingOpinions(String fileSolicitingOpinions) {
		this.fileSolicitingOpinions = fileSolicitingOpinions;
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

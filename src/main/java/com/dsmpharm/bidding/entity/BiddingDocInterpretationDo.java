package com.dsmpharm.bidding.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@Entity
@Table(name="bidding_doc_interpretation")
public class BiddingDocInterpretationDo implements Serializable {
    private static final long serialVersionUID = -8647489139207882245L;

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

    private String fileBuyRulestag;

    private String fileQuotedPrice;

    private String industryInfluence;

    private String selfInfluence;

    private String solicitingOpinions;

    private String fileSolicitingOpinions;

    private String suggestion;

    private String dosageForm;

	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private String value6;
	private String value7;
	private String value8;
	private String value9;
	private String value10;
	private String value11;
	private String value12;
	private String value13;
	private String value14;
	private String value15;
	private String value16;
	private String value17;
	private String value18;
	private String value19;
	private String value20;

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public String getValue6() {
		return value6;
	}

	public void setValue6(String value6) {
		this.value6 = value6;
	}

	public String getValue7() {
		return value7;
	}

	public void setValue7(String value7) {
		this.value7 = value7;
	}

	public String getValue8() {
		return value8;
	}

	public void setValue8(String value8) {
		this.value8 = value8;
	}

	public String getValue9() {
		return value9;
	}

	public void setValue9(String value9) {
		this.value9 = value9;
	}

	public String getValue10() {
		return value10;
	}

	public void setValue10(String value10) {
		this.value10 = value10;
	}

	public String getValue11() {
		return value11;
	}

	public void setValue11(String value11) {
		this.value11 = value11;
	}

	public String getValue12() {
		return value12;
	}

	public void setValue12(String value12) {
		this.value12 = value12;
	}

	public String getValue13() {
		return value13;
	}

	public void setValue13(String value13) {
		this.value13 = value13;
	}

	public String getValue14() {
		return value14;
	}

	public void setValue14(String value14) {
		this.value14 = value14;
	}

	public String getValue15() {
		return value15;
	}

	public void setValue15(String value15) {
		this.value15 = value15;
	}

	public String getValue16() {
		return value16;
	}

	public void setValue16(String value16) {
		this.value16 = value16;
	}

	public String getValue17() {
		return value17;
	}

	public void setValue17(String value17) {
		this.value17 = value17;
	}

	public String getValue18() {
		return value18;
	}

	public void setValue18(String value18) {
		this.value18 = value18;
	}

	public String getValue19() {
		return value19;
	}

	public void setValue19(String value19) {
		this.value19 = value19;
	}

	public String getValue20() {
		return value20;
	}

	public void setValue20(String value20) {
		this.value20 = value20;
	}

	public String getFileBuyRulestag() {
		return fileBuyRulestag;
	}

	public void setFileBuyRulestag(String fileBuyRulestag) {
		this.fileBuyRulestag = fileBuyRulestag;
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

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}
}

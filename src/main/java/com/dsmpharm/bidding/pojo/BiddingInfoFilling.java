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
@Table(name="bidding_info_filling")
public class BiddingInfoFilling implements Serializable {
    private static final long serialVersionUID = -8698306572181242289L;

    @Id
    private String id;

    private String fileOnlineFilling;

    private String fileApplicationSeal;

    private String fileInfoList;

    private String fileNameOa;

    private String fileDetailsSpecial;

    private String submissionTime;

    private String filePriceApp;

    private String filePriceLetter;

    private String fileInfoChange;

    private String fileOther;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getFileOnlineFilling() {
		return fileOnlineFilling;
	}
    public void setFileOnlineFilling(String fileOnlineFilling) {
		this.fileOnlineFilling = fileOnlineFilling;
	}
    public String getFileApplicationSeal() {
		return fileApplicationSeal;
	}
    public void setFileApplicationSeal(String fileApplicationSeal) {
		this.fileApplicationSeal = fileApplicationSeal;
	}
    public String getFileInfoList() {
		return fileInfoList;
	}
    public void setFileInfoList(String fileInfoList) {
		this.fileInfoList = fileInfoList;
	}
    public String getFileNameOa() {
		return fileNameOa;
	}
    public void setFileNameOa(String fileNameOa) {
		this.fileNameOa = fileNameOa;
	}
    public String getFileDetailsSpecial() {
		return fileDetailsSpecial;
	}
    public void setFileDetailsSpecial(String fileDetailsSpecial) {
		this.fileDetailsSpecial = fileDetailsSpecial;
	}
    public String getSubmissionTime() {
		return submissionTime;
	}
    public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}
    public String getFilePriceApp() {
		return filePriceApp;
	}
    public void setFilePriceApp(String filePriceApp) {
		this.filePriceApp = filePriceApp;
	}
    public String getFilePriceLetter() {
		return filePriceLetter;
	}
    public void setFilePriceLetter(String filePriceLetter) {
		this.filePriceLetter = filePriceLetter;
	}
    public String getFileInfoChange() {
		return fileInfoChange;
	}
    public void setFileInfoChange(String fileInfoChange) {
		this.fileInfoChange = fileInfoChange;
	}
    public String getFileOther() {
		return fileOther;
	}
    public void setFileOther(String fileOther) {
		this.fileOther = fileOther;
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

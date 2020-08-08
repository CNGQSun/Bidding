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
@Table(name="bidding_official_notice")
public class BiddingOfficialNotice implements Serializable {
    private static final long serialVersionUID = -5320395205493312223L;

    @Id
    private String id;

    private String fileAnnounce;

    private String distributorRequire;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getFileAnnounce() {
		return fileAnnounce;
	}
    public void setFileAnnounce(String fileAnnounce) {
		this.fileAnnounce = fileAnnounce;
	}
    public String getDistributorRequire() {
		return distributorRequire;
	}
    public void setDistributorRequire(String distributorRequire) {
		this.distributorRequire = distributorRequire;
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

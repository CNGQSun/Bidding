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
@Table(name="bidding_project_summary")
public class BiddingProjectSummary implements Serializable {
    private static final long serialVersionUID = -4689959236804343108L;

    @Id
    private String id;

    private String suggestion;

    private String goStatus;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
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

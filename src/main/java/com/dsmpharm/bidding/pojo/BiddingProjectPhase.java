package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@Entity
@Table(name="bidding_project_phase")
public class BiddingProjectPhase implements Serializable {
    private static final long serialVersionUID = -5413870398752003548L;

    @Id
    private String id;

    private String name;

    private String isNeed;

    private String delfalg;

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
    public String getIsNeed() {
		return isNeed;
	}
    public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}
    public String getDelfalg() {
		return delfalg;
	}
    public void setDelfalg(String delfalg) {
		this.delfalg = delfalg;
	}
}

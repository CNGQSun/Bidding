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
@Table(name="bidding_content_type")
public class BiddingContentType implements Serializable {
    private static final long serialVersionUID = -4936171660835305098L;

    @Id
    private String id;

    private String name;

    private String type;

    private String delflag;

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
    public String getType() {
		return type;
	}
    public void setType(String type) {
		this.type = type;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

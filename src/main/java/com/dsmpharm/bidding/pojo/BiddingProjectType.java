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
@Table(name="bidding_project_type")
public class BiddingProjectType implements Serializable {
    private static final long serialVersionUID = -6538715400178660003L;

    @Id
    private String id;

    private String name;

	private String projectPhaseId;

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

	public String getProjectPhaseId() {
		return projectPhaseId;
	}

	public void setProjectPhaseId(String projectPhaseId) {
		this.projectPhaseId = projectPhaseId;
	}

	public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

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
@Table(name="bidding_settings_extra")
public class BiddingSettingsExtra implements Serializable {
    private static final long serialVersionUID = -6210121411374836053L;

    @Id
    private String id;

    private String name;

    private String contentTypeId;

    private String isNull;

    private String value;

    private String projectId;

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
    public String getContentTypeId() {
		return contentTypeId;
	}
    public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
    public String getIsNull() {
		return isNull;
	}
    public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
    public String getValue() {
		return value;
	}
    public void setValue(String value) {
		this.value = value;
	}
    public String getProjectId() {
		return projectId;
	}
    public void setProjectId(String projectId) {
		this.projectId = projectId;
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

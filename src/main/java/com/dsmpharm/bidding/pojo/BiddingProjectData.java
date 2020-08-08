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
@Table(name="bidding_project_data")
public class BiddingProjectData implements Serializable {
    private static final long serialVersionUID = -8238987709117423792L;

    @Id
    private String id;

    private String projectId;

    private String contentSettingsId;

    private String value;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getProjectId() {
		return projectId;
	}
    public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
    public String getContentSettingsId() {
		return contentSettingsId;
	}
    public void setContentSettingsId(String contentSettingsId) {
		this.contentSettingsId = contentSettingsId;
	}
    public String getValue() {
		return value;
	}
    public void setValue(String value) {
		this.value = value;
	}
}

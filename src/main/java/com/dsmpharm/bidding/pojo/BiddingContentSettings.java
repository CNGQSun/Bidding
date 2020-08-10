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
@Table(name="bidding_content_settings")
public class BiddingContentSettings implements Serializable {
    private static final long serialVersionUID = -5491653582721413173L;

    @Id
    private String id;

    private String name;

    private String enName;

    private String isNull;

    private String contentTypeId;

    private String projectPhaseId;

    private String delflag;

    private String versionNum;

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

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getIsNull() {
		return isNull;
	}
    public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
    public String getContentTypeId() {
		return contentTypeId;
	}
    public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
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
    public String getVersionNum() {
		return versionNum;
	}
    public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
}

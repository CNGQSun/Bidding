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
@Table(name="bidding_phase_file")
public class BiddingPhaseFile implements Serializable {
    private static final long serialVersionUID = -8301316166426394768L;

    @Id
    private String id;

    private String phaseId;

    private String fieldName;

    private String fileName;

    private String fileAddress;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getPhaseId() {
		return phaseId;
	}
    public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
    public String getFieldName() {
		return fieldName;
	}
    public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
    public String getFileName() {
		return fileName;
	}
    public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    public String getFileAddress() {
		return fileAddress;
	}
    public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

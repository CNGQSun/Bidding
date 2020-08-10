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
@Table(name="bidding_project")
public class BiddingProject implements Serializable {
    private static final long serialVersionUID = -8144554726814124829L;

    @Id
    private String id;
	private String typeId;
    private String projectBulidId;

    private String docInterpretationId;

    private String productCollectionId;

    private String strategyAnalysisId;

    private String infoFillingId;

    private String officialNoticeId;

    private String projectSummaryId;

    private String versionNum;

    private String projectPhaseNow;

    private String status;

    private String suggestion;

    private String delflag;

    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}

	public String getProjectBulidId() {
		return projectBulidId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setProjectBulidId(String projectBulidId) {
		this.projectBulidId = projectBulidId;
	}

	public String getDocInterpretationId() {
		return docInterpretationId;
	}
    public void setDocInterpretationId(String docInterpretationId) {
		this.docInterpretationId = docInterpretationId;
	}
    public String getProductCollectionId() {
		return productCollectionId;
	}
    public void setProductCollectionId(String productCollectionId) {
		this.productCollectionId = productCollectionId;
	}
    public String getStrategyAnalysisId() {
		return strategyAnalysisId;
	}
    public void setStrategyAnalysisId(String strategyAnalysisId) {
		this.strategyAnalysisId = strategyAnalysisId;
	}
    public String getInfoFillingId() {
		return infoFillingId;
	}
    public void setInfoFillingId(String infoFillingId) {
		this.infoFillingId = infoFillingId;
	}
    public String getOfficialNoticeId() {
		return officialNoticeId;
	}
    public void setOfficialNoticeId(String officialNoticeId) {
		this.officialNoticeId = officialNoticeId;
	}
    public String getProjectSummaryId() {
		return projectSummaryId;
	}
    public void setProjectSummaryId(String projectSummaryId) {
		this.projectSummaryId = projectSummaryId;
	}
    public String getVersionNum() {
		return versionNum;
	}
    public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
    public String getProjectPhaseNow() {
		return projectPhaseNow;
	}
    public void setProjectPhaseNow(String projectPhaseNow) {
		this.projectPhaseNow = projectPhaseNow;
	}
    public String getStatus() {
		return status;
	}
    public void setStatus(String status) {
		this.status = status;
	}
    public String getSuggestion() {
		return suggestion;
	}
    public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
}

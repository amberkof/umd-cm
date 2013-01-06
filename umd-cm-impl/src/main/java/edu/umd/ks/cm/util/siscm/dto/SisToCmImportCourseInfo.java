package edu.umd.ks.cm.util.siscm.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SisToCmImportCourseInfo {

	@XmlElement
	private String apCrs;
	@XmlElement
	private String startTrm;
	@XmlElement
	private String endTrm;
	@XmlElement
	private String transTitle;
	@XmlElement
	private String formalTitle;
	@XmlElement
	private String txt40;
	@XmlElement
	private String minCr;
	@XmlElement
	private String maxCr;
	@XmlElement
	private String grdMeth;
	@XmlElement
	private String adminStat;
	@XmlElement
	private String core;
	@XmlElement
	private String diversity;
	@XmlElement
	private String genEdCodes;
	@XmlElement
	private String learningOutcomes;
	@XmlElement
	private String hash;
	@XmlElement
	private String diffOperation;
	@XmlElement
	private boolean last;
	
	public String getApCrs() {
		return apCrs;
	}

	public void setApCrs(String apCrs) {
		this.apCrs = apCrs;
	}

	public String getStartTrm() {
		return startTrm;
	}

	public void setStartTrm(String startTrm) {
		this.startTrm = startTrm;
	}

	public String getEndTrm() {
		return endTrm;
	}

	public void setEndTrm(String endTrm) {
		this.endTrm = endTrm;
	}

	public String getTransTitle() {
		return transTitle;
	}

	public void setTransTitle(String transTitle) {
		this.transTitle = transTitle;
	}

	public String getFormalTitle() {
		return formalTitle;
	}

	public void setFormalTitle(String formalTitle) {
		this.formalTitle = formalTitle;
	}

	public String getTxt40() {
		return txt40;
	}

	public void setTxt40(String txt40) {
		this.txt40 = txt40;
	}

	public String getMinCr() {
		return minCr;
	}

	public void setMinCr(String minCr) {
		this.minCr = minCr;
	}

	public String getMaxCr() {
		return maxCr;
	}

	public void setMaxCr(String maxCr) {
		this.maxCr = maxCr;
	}

	public String getGrdMeth() {
		return grdMeth;
	}

	public void setGrdMeth(String grdMeth) {
		this.grdMeth = grdMeth;
	}

	public String getAdminStat() {
		return adminStat;
	}

	public void setAdminStat(String adminStat) {
		this.adminStat = adminStat;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getDiversity() {
		return diversity;
	}

	public void setDiversity(String diversity) {
		this.diversity = diversity;
	}

	public String getGenEdCodes() {
		return genEdCodes;
	}

	public void setGenEdCodes(String genEdCodes) {
		this.genEdCodes = genEdCodes;
	}

	public String getLearningOutcomes() {
		return learningOutcomes;
	}

	public void setLearningOutcomes(String learningOutcomes) {
		this.learningOutcomes = learningOutcomes;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getDiffOperation() {
		return diffOperation;
	}

	public void setDiffOperation(String diffOperation) {
		this.diffOperation = diffOperation;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

}

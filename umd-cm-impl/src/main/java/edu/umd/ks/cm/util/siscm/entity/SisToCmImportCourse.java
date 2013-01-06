package edu.umd.ks.cm.util.siscm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;


@Entity
@Table(name="SISCM_IMPORT_QUEUE")
@NamedQueries( {
    @NamedQuery(name = "SisToCmImportCourse.findAllOrderedByCrsAndStartTrm", 
           query = "SELECT sc FROM SisToCmImportCourse sc ORDER BY sc.apCrs, sc.startTrm ASC")
    
})
public class SisToCmImportCourse {
	
    /**
     * This ID is generated as a GUID using the prepersist()
     * method in the class.
     */
	@Id
	@Column(name="ID")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="BATCH_JOB_ID")
	private BatchSisToCmImportJob batchJob;
	
	 	
	@Lob
	@Column(name="ERROR_LOG")
	private String errorLog;
	
	@Column(name="AP_CRS")
	private String apCrs;
	@Column(name="START_TRM")
	private String startTrm;
	@Column(name="END_TRM")
	private String endTrm;
	@Column(name="TRANS_TITLE")
	private String transTitle;
	@Column(name="FORMAL_TITLE")
	private String formalTitle;
	@Column(name="TXT40")
	private String txt40;
	@Column(name="MIN_CR")
	private String minCr;
	@Column(name="MAX_CR")
	private String maxCr;
	@Column(name="GRD_METH")
	private String grdMeth;
	@Column(name="ADMIN_STAT")
	private String adminStat;
	@Column(name="CORE")
	private String core;
	@Column(name="DIVERISTY")
	private String diversity;
	@Column(name="GENEDCODES")
	private String genEdCodes;
	@Column(name="LEARNING_OUTCOMES")
	private String learningOutcomes;
	@Column(name="OPERATION")
	private String operation;
	@Column(name="HASH")
	private String hash;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOD_DT")
    private Date modifyDate;
    
    @PrePersist
    public void prePersist(){
        //Auto generate the object id, and auto generate the ID if it's not set 
        this.id = UUIDHelper.genStringUUID(this.id);
        
        this.modifyDate = new Date();
    }
    @PreUpdate
    public void preUpdate(){
           this.modifyDate = new Date();
    }
    
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BatchSisToCmImportJob getBatchJob() {
		return batchJob;
	}
	public void setBatchJob(BatchSisToCmImportJob batchJob) {
		this.batchJob = batchJob;
	}
	 
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

}


package edu.umd.ks.cm.util.siscm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;

/**
 * @Author VG 10/20/11
 * @See https://issues.umd.edu/browse/KSCM-616
 * Used by SisCourseInfoAdvice to update SISCM.UMDCM_CRS table for MF
 */

@Entity
@Table(name = "UMDCM_CRS")
@NamedQueries( {
	@NamedQuery(name = "CmToSisExportCourse.findByCrsTrmStat", 
 		   query = "SELECT sc FROM CmToSisExportCourse sc WHERE sc.course = :course AND sc.term = :term AND sc.statusInd = :statusInd"),
	@NamedQuery(name = "CmToSisExportCourse.findByCrsTrm", 
  		   query = "SELECT sc FROM CmToSisExportCourse sc WHERE sc.course = :course AND sc.term = :term")

}) 
public class CmToSisExportCourse {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "CRS")
	private String course;

	@Column(name = "TRM")
	private String term;

	@Column(name = "TRM_END")
	private String termEnd;
	
	@Column(precision=4,scale=2,name = "MIN_CR",columnDefinition = "NUMBER(4,2)")
	private float minCredit;
	
	@Column(precision=4,scale=2,name = "MAX_CR",columnDefinition = "NUMBER(4,2)")
	private float maxCredit;
	
	@Column(name = "GRD_METH")
	private String gradeMethod;
	
	@Column(name = "CRS_TITLE")
	private String courseTitle;
	
	@Column(name = "TRANSCR_TITLE")
	private String transcrTitle;	
	
	@Column(name = "CRS_DESC")
	private String courseDesc;

	@Column(name = "CRS_REQS")
	private String courseReqs;

	@Column(name = "STATUS_IND")
	private String statusInd;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MOD_DT")
	private Date modifyDate;

	@Column(name = "MOD_ID")
	private String userId;

	@Column(name = "CRS_ID")
	private String courseId;
	
	@Column(name = "GENED")
	private String genEd;
	
	@Column(name = "DIVERSITY")
	private String diversity;
	
	@Column(name = "CORE")
	private String core;
	
	@Column(name = "ADMIN_STAT")
	private String adminStat;
	
	@Column(name = "LECTURE_HRS")
	private int lectureHours;
	
	@Column(name = "LAB_HRS")
	private int labHours;
	
	@Column(name = "EXPERIMENTAL_HRS")
	private int experimentalHours;
	
	@Column(name = "DISCUSSION_HRS")
	private int discussionHours;
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOAD_DT")
	private Date uploadDate;
		
	@PrePersist
	public void prePersist(){
		//Auto generate the object id, and auto generate the ID if it's not set 
        this.id = UUIDHelper.genStringUUID(this.id);
        this.modifyDate = new Date();
	}
	
	@PreUpdate
	public void preUpdate(){
		this.modifyDate = new Date(); 
		this.uploadDate=null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTermEnd() {
		return termEnd;
	}

	public void setTermEnd(String termEnd) {
		this.termEnd = termEnd;
	}

	public float getMinCredit() {
		return minCredit;
	}

	public void setMinCredit(float minCredit) {
		this.minCredit = minCredit;
	}

	public float getMaxCredit() {
		return maxCredit;
	}

	public void setMaxCredit(float maxCredit) {
		this.maxCredit = maxCredit;
	}

	public String getGradeMethod() {
		return gradeMethod;
	}

	public void setGradeMethod(String gradeMethod) {
		this.gradeMethod = gradeMethod;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getTranscrTitle() {
		return transcrTitle;
	}

	public void setTranscrTitle(String transcrTitle) {
		this.transcrTitle = transcrTitle;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public String getCourseReqs() {
		return courseReqs;
	}

	public void setCourseReqs(String courseReqs) {
		this.courseReqs = courseReqs;
	}

	public String getStatusInd() {
		return statusInd;
	}

	public void setStatusInd(String statusInd) {
		this.statusInd = statusInd;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getGenEd() {
		return genEd;
	}

	public void setGenEd(String genEd) {
		this.genEd = genEd;
	}

	public String getDiversity() {
		return diversity;
	}

	public void setDiversity(String diversity) {
		this.diversity = diversity;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}
	
	public String getAdminStat() {
		return adminStat;
	}

	public void setAdminStat(String adminStat) {
		this.adminStat = adminStat;
	}
	
	public int getLectureHours() {
		return lectureHours;
	}

	public void setLectureHours(int lectureHours ) {
		this.lectureHours = lectureHours;
	}

	public int getLabHours() {
		return labHours;
	}

	public void setLabHours(int labHours ) {
		this.labHours = labHours;
	}
	
	public int getExperimentalHours() {
		return experimentalHours;
	}

	public void setExperimentalHours(int experimentalHours ) {
		this.experimentalHours = experimentalHours;
	}
	
	public int getDiscussionHours() {
		return discussionHours;
	}

	public void setDiscussionHours(int discussionHours ) {
		this.discussionHours = discussionHours;
	}
	
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
}
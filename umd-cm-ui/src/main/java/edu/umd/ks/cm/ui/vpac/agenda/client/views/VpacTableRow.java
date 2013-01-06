package edu.umd.ks.cm.ui.vpac.agenda.client.views;

/**
 * UMD Custom Row Class for Vpac Agenda CellTable.
 * cluId is not visible but is used to construct the htmlLink.
 * @author mike
 *
 */
public class VpacTableRow {
		
	public VpacTableRow(String cluId, String curriculumOversightUnit,
			String courseCode, String courseTitle, String proposalType,
			String htmlLink) {
		super();
		this.cluId = cluId;
		this.curriculumOversightUnit = curriculumOversightUnit;
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.proposalType = proposalType;
		this.htmlLink = htmlLink;
	}

	private String cluId;
	private String curriculumOversightUnit;
	private String courseCode;
	private String courseTitle;
	private String proposalType;	
	private String htmlLink;
	
	public String getCluId() {
		return cluId;
	}
	public void setCluId(String cluId) {
		this.cluId = cluId;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getProposalType() {
		return this.proposalType;
	}
	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}
	public String getCurriculumOversightUnit() {
		return curriculumOversightUnit;
	}
	public void setCurriculumOversightUnit(String curriculumOversightUnit) {
		this.curriculumOversightUnit = curriculumOversightUnit;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getHtmlLink() {
		return htmlLink;
	}
	public void setHtmlLink(String htmlLink) {
		this.htmlLink = htmlLink;
	}
	
	
}

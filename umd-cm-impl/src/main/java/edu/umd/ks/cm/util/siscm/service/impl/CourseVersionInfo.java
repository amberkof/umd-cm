package edu.umd.ks.cm.util.siscm.service.impl;


public class CourseVersionInfo {
	private String courseId;
	private String courseVersionIndId;
	private String startTerm;
	private String endTerm;
	private String state;
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseVersionIndId() {
		return courseVersionIndId;
	}

	public void setCourseVersionIndId(String courseVersionIndId) {
		this.courseVersionIndId = courseVersionIndId;
	}

	public String getStartTerm() {
		return startTerm;
	}

	public void setStartTerm(String startTerm) {
		this.startTerm = startTerm;
	}

	public String getEndTerm() {
		return endTerm;
	}

	public void setEndTerm(String endTerm) {
		this.endTerm = endTerm;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

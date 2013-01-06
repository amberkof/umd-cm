package edu.umd.ks.cm.util.siscm.entity;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class SiscmDiffPK implements Serializable {
	@Column(name = "CRS")
	private String crs;

	@Column(name = "START_TRM")
	private String startTrm;

	public SiscmDiffPK() {
		super();
	}

	public String getCrs() {
		return crs;
	}

	public void setCrs(String crs) {
		this.crs = crs;
	}

	public String getStartTrm() {
		return startTrm;
	}

	public void setStartTrm(String startTrm) {
		this.startTrm = startTrm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crs == null) ? 0 : crs.hashCode());
		result = prime * result
				+ ((startTrm == null) ? 0 : startTrm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiscmDiffPK other = (SiscmDiffPK) obj;
		if (crs == null) {
			if (other.crs != null)
				return false;
		} else if (!crs.equals(other.crs))
			return false;
		if (startTrm == null) {
			if (other.startTrm != null)
				return false;
		} else if (!startTrm.equals(other.startTrm))
			return false;
		return true;
	}

}
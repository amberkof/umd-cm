package edu.umd.ks.cm.core.organization.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@NamedQueries( {
	@NamedQuery(name = "Unit.getUnitsByIdList", query = "SELECT u FROM Unit u WHERE u.id IN (:unitIdList)" )
})
@Entity
@Table(name = "UNIT")
public class Unit {
	
	@Id
	@Column(name = "UNIT_ID")
	private Long id;

	@Column(name = "UM_UNIT_CD")
	private String unitCode;

	@Column(name = "ORG_TYPE_CD")
	private Character typeCd;

	@Column(name = "ORG_TYPE")
	private String type;

	@Column(name = "UM_UNIT_INTERNAL_CD")
	private Long umInternalCd;
	
	@Column(name = "UM_UNIT_TYPE_CD")
	private String umTypeCd;
	
	@Column(name = "UM_UNIT_TYPE")
	private String umType;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "START_DT")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DT")
	private Date endDate;

	@Column(name = "LONG_NM")
	private String longName;

	@Column(name = "SHORT_NM")
	private String shortName;

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Character getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(Character typeCd) {
		this.typeCd = typeCd;
	}

	public Long getUmInternalCd() {
		return umInternalCd;
	}

	public void setUmInternalCd(Long umInternalCd) {
		this.umInternalCd = umInternalCd;
	}

	public String getUmTypeCd() {
		return umTypeCd;
	}

	public void setUmTypeCd(String umTypeCd) {
		this.umTypeCd = umTypeCd;
	}

	public String getUmType() {
		return umType;
	}

	public void setUmType(String umType) {
		this.umType = umType;
	}

}

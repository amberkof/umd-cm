package edu.umd.ks.cm.core.organization.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//These Named Queries are restricting Academic Units by restricting to those that have unit codes starting with "0120"
// They also assume colleges end in "101" and departments do not.
// In the future we will use the umType to properly identify academic colleges and departments 
@NamedQueries( {
	@NamedQuery(name = "UnitRelation.getAncestors", query = "SELECT ur.parentUnit.id FROM UnitRelation ur " +
			"WHERE ur.childUnit.id = :unitId "), 
	@NamedQuery(name = "UnitRelation.getAncestorsByIdListAndTypeList", query = "SELECT ur.parentUnit FROM UnitRelation ur " +
			"WHERE ur.childUnit.id IN(:unitIdList) AND ur.parentUnit.umType IN ('Academic College','Academic College and Department','Academic Program Home','Academic Instructional & Research Support','Academic Research Group','Academic Outreach','Academic Administrative Support','Academic Department','Center/Institute') "),
	@NamedQuery(name = "UnitRelation.getAncestorsByIdListAndTypeListDepartment", query = "SELECT ur.parentUnit FROM UnitRelation ur " +
			"WHERE ur.childUnit.id IN(:unitIdList) AND ur.parentUnit.umType IN ('Academic Program Home','Academic Instructional & Research Support','Academic Research Group','Academic Outreach','Academic Administrative Support','Academic Department','Center/Institute') "),
	@NamedQuery(name = "UnitRelation.getAncestorsByIdListAndTypeListCollege", query = "SELECT ur.parentUnit FROM UnitRelation ur " +
			"WHERE ur.childUnit.id IN(:unitIdList) AND ur.parentUnit.umType IN ('Academic College','Academic College and Department') ")
})
@Entity
@Table(name = "UNIT_REL")
public class UnitRelation {

	@Id
	@Column(name = "UNIT_REL_ID")
	private Long unitRelationId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PARENT_UNIT_ID")
	private Unit parentUnit;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CHILD_UNIT_ID")
	private Unit childUnit;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DT")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DT")
	private Date endDate;

	public Long getUnitRelationId() {
		return unitRelationId;
	}

	public void setUnitRelationId(Long unitRelationId) {
		this.unitRelationId = unitRelationId;
	}

	public Unit getParentUnit() {
		return parentUnit;
	}

	public void setParentUnit(Unit parentUnit) {
		this.parentUnit = parentUnit;
	}

	public Unit getChildUnit() {
		return childUnit;
	}

	public void setChildUnit(Unit childUnit) {
		this.childUnit = childUnit;
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

}

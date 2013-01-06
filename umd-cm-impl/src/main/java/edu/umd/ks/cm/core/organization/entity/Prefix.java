package edu.umd.ks.cm.core.organization.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PREFIX")
public class Prefix {

	@Id
	@Column(name = "PREFIX")
	private String prefix;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EFF_DT")
	private Date effectiveDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIR_DT")
	private Date expirationDate; 

	@Column(name = "NAME")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prefix")
	private List<PrefixUnitRelation> unitRelations;

	@Column(name="UM_CODE_OBSOLETE_IND")
	private String obsoleteInd;
	
	@Column(name="UM_UNIT_INTERNAL_CD")
	private Integer unitInternalCd;
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PrefixUnitRelation> getOrgRelations() {
		return unitRelations;
	}

	public void setOrgRelations(List<PrefixUnitRelation> orgRelations) {
		this.unitRelations = orgRelations;
	}

	public List<PrefixUnitRelation> getUnitRelations() {
		return unitRelations;
	}

	public void setUnitRelations(List<PrefixUnitRelation> unitRelations) {
		this.unitRelations = unitRelations;
	}

	public String getObsoleteInd() {
		return obsoleteInd;
	}

	public void setObsoleteInd(String obsoleteInd) {
		this.obsoleteInd = obsoleteInd;
	}

	public Integer getUnitInternalCd() {
		return unitInternalCd;
	}

	public void setUnitInternalCd(Integer unitInternalCd) {
		this.unitInternalCd = unitInternalCd;
	}
}

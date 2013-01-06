package edu.umd.ks.cm.core.organization.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PREFIX_REL")
public class PrefixUnitRelation {

	@Id
	@Column(name = "PREFIX_REL_ID")
	private long id;

	@ManyToOne
	@JoinColumn(name = "PREFIX")
	private Prefix prefix;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;

	@Column(name = "ACTIVE")
	private Character active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Prefix getPrefix() {
		return prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

}

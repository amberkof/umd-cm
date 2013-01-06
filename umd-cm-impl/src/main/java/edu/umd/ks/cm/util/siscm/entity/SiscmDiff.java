package edu.umd.ks.cm.util.siscm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(SiscmDiffPK.class)
@Table(name = "SISCM_DIFF")
public class SiscmDiff {

	@Id
	private String crs;

	@Id
	private String startTrm;

	@Column(name = "SIS_MD5_HASH")
	private String hash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOD_DT")
    private Date modifyDate;
    
    @PrePersist
    public void prePersist(){
           this.modifyDate = new Date();
    }
    @PreUpdate
    public void preUpdate(){
           this.modifyDate = new Date();
    }
    
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}

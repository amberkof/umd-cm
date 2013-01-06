package edu.umd.ks.cm.util.siscm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SISCM_BATCH_JOB")
@SequenceGenerator(name = "BatchSeq", allocationSize=1, sequenceName="SISCM.BATCH_SIS_TO_CM_S")
public class BatchSisToCmImportJob {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BatchSeq")
	@Column(name = "ID")
	private Long id;

	@Column(name = "EXECUTION_DT")
    @Temporal(TemporalType.TIMESTAMP)
	private Date executionDate;

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
     
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
 
}

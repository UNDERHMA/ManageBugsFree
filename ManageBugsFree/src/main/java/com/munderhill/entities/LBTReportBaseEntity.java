package com.munderhill.entities;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mason
 */

// code for dates from https://blog.octo.com/en/audit-with-jpa-creation-and-update-date/
@MappedSuperclass
public abstract class LBTReportBaseEntity {
        
        @Column(name = "last_updated", insertable = true, updatable = true)
        private Timestamp lastUpdated;
        
        @Column(name = "updated_by", insertable = true, updatable = true)
        private String updatedBy = "NA";
        
        @Column(name = "date_created", insertable = true, updatable = true)
	private Timestamp dateCreated;
        
        @Column(name = "date_all_linked_brs_closed", insertable = true, updatable = true)
	private Timestamp dateAllLinkedBRsClosed;

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }
        
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

        public Timestamp getDateAllLinkedBRsClosed() {
            return dateAllLinkedBRsClosed;
        }

        public void setDateAllLinkedBRsClosed(Timestamp dateAllLinkedBRsClosed) {
            this.dateAllLinkedBRsClosed = dateAllLinkedBRsClosed;
        }

	@PrePersist
	void onCreate() {
		this.setDateCreated(new Timestamp((new Date()).getTime()));
	}

	@PreUpdate
	void onPersistLastUpdated() {
		this.setLastUpdated(new Timestamp((new Date()).getTime()));
	}
        
}

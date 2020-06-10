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

@MappedSuperclass
public abstract class BugReportBaseEntity {
        
        @Column(name = "last_updated", insertable = true, updatable = true)
        private Timestamp lastUpdated;
        
        @Column(name = "updated_by", insertable = true, updatable = true)
        private String updatedBy = "NA";
        
        @Column(name = "date_created", insertable = true, updatable = true)
	private Timestamp dateCreated;
        
        @Column(name = "date_first_closed", insertable = true, updatable = true)
	private Timestamp dateFirstClosed;
        
        private String status;

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

        public Timestamp getDateFirstClosed() {
            return dateFirstClosed;
        }

        public void setDateFirstClosed(Timestamp dateFirstClosed) {
            this.dateFirstClosed = dateFirstClosed;
        }

        public String getStatus() {
                return status;
        }
            
        public void setStatus(String status) {
                this.status = status;
        }

	@PrePersist
	void onCreate() {
		this.setDateCreated(new Timestamp((new Date()).getTime()));
                if (this.status == null) {
                    this.status = "Open";
                }
	}

	@PreUpdate
	void onPersistLastUpdated() {
		this.setLastUpdated(new Timestamp((new Date()).getTime()));
	}
        
}

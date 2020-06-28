/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findAllNotesByBRId", resultClass = BugReportAdditionalNotes.class,
        query = "SELECT * FROM BugReportAdditionalNotes WHERE bug_report_id = #bugReportId ORDER BY entry_date")
public class BugReportAdditionalNotes implements Serializable {

    private static final long serialVersionUID = 9L;
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "bug_reports_additional_notes_sequence")
    @SequenceGenerator(name="bug_reports_additional_notes_sequence", allocationSize=1, 
            sequenceName="bug_reports_additional_notes_sequence", initialValue=1)
    @Id
    @Column(name="br_note_id")
    private Integer brNoteId;
    
    @Column(name="bug_report_id")
    @NotNull
    private int bugReportId;
    
    @NotNull
    private String note;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="entered_by_user_id")
    private Users enteredByUserId; 
   
    @Column(name="entry_date")
    @NotNull
    private Timestamp entryDate;

    public int getBrNoteId() {
        return brNoteId;
    }

    public void setBrNoteId(int brNoteId) {
        this.brNoteId = brNoteId;
    }

    public int getBugReportId() {
        return bugReportId;
    }

    public void setBugReportId(int bugReportId) {
        this.bugReportId = bugReportId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Users getEnteredByUserId() {
        return enteredByUserId;
    }

    public void setEnteredByUserId(Users enteredByUserId) {
        this.enteredByUserId = enteredByUserId;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.brNoteId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BugReportAdditionalNotes other = (BugReportAdditionalNotes) obj;
        if (!Objects.equals(this.brNoteId, other.brNoteId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BugReportAdditionalNotes{" + "brNoteId=" + brNoteId + ", bugReportId=" + bugReportId + ", note=" + note + ", enteredByUserId=" + enteredByUserId + ", entryDate=" + entryDate + '}';
    }
    
    @PrePersist
	void onCreate() {
		this.setEntryDate(new Timestamp((new Date()).getTime()));
	}
    
}

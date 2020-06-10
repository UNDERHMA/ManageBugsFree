/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mason
 */
@Entity
@Cacheable(false)
@Table(name = "brsimilarbugreports")
public class BRSimilarBugReports implements Serializable {

    private static final long serialVersionUID = 12L;
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "br_similar_bug_reports_sequence")
    @SequenceGenerator(name="br_similar_bug_reports_sequence", allocationSize=1, 
            sequenceName="br_similar_bug_reports_sequence")
    @Column(name="br_similar_bug_reports_id")
    @Id
    private Long brSimilarBugReportsId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bug_report_id", nullable=false)
    private BugReport bugReport;
    
    @Column(name="similar_bug_report_id")
    private Integer similarBugReportId;

    public Long getBrSimilarBugReportsId() {
        return brSimilarBugReportsId;
    }

    public void setBrSimilarBugReportsId(Long brSimilarBugReportsId) {
        this.brSimilarBugReportsId = brSimilarBugReportsId;
    }

    public BugReport getBugReport() {
        return bugReport;
    }

    public void setBugReport(BugReport bugReport) {
        this.bugReport = bugReport;
    }

    public Integer getSimilarBugReportId() {
        return similarBugReportId;
    }

    public void setSimilarBugReportId(Integer similarBugReportId) {
        this.similarBugReportId = similarBugReportId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.brSimilarBugReportsId);
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
        final BRSimilarBugReports other = (BRSimilarBugReports) obj;
        if (!Objects.equals(this.brSimilarBugReportsId, other.brSimilarBugReportsId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BRSimilarBugReports{" + "brSimilarBugReportsId=" + brSimilarBugReportsId + ", bugReport=" + bugReport.getBugReportId() + ", similarBugReportId=" + similarBugReportId + '}';
    }
   
    
}

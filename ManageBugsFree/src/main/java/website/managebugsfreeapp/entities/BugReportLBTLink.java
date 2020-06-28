/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.entities;

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
@Table(name = "bridlbtlink")
public class BugReportLBTLink implements Serializable {

    private static final long serialVersionUID = 11L;
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "bridlbtlink_sequence")
    @SequenceGenerator(name="bridlbtlink_sequence", allocationSize=1, 
            sequenceName="bridlbtlink_sequence", initialValue=1)
    @Column(name="brid_lbt_link_id")
    @Id
    private Integer bridLbtLinkId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bug_report_id", nullable=false)
    private BugReport bugReport;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lbt_report_id", nullable=false)
    private LBTReport lbtReport;

    public Integer getBridLbtLinkId() {
        return bridLbtLinkId;
    }

    public void setBridLbtLinkId(Integer bridLbtLinkId) {
        this.bridLbtLinkId = bridLbtLinkId;
    }

    public BugReport getBugReport() {
        return bugReport;
    }

    public void setBugReport(BugReport bugReport) {
        this.bugReport = bugReport;
    }

    public LBTReport getLbtReport() {
        return lbtReport;
    }

    public void setLbtReport(LBTReport lbtReport) {
        this.lbtReport = lbtReport;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.bridLbtLinkId);
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
        final BugReportLBTLink other = (BugReportLBTLink) obj;
        if (!Objects.equals(this.bridLbtLinkId, other.bridLbtLinkId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BugReportLBTLink{" + "bridLbtLinkId=" + bridLbtLinkId + ", bugReport=" + bugReport + ", lbtReport=" + lbtReport + '}';
    }

}

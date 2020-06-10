/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author mason
 */
@Entity
@Customizer(com.munderhill.eclipselink.history.LBTReportHistoryCustomizer.class)
@Table(name = "LBTReports")
@NamedNativeQuery(name = "findLBTReportById", resultClass = LBTReport.class, query = "SELECT * \n" +
                "FROM LBTReports WHERE lbt_report_id = #lbtReportId ")
//modified from response by ruffin and jonVD https://stackoverflow.com/questions/4193705/sql-server-select-last-n-rows
@NamedNativeQuery(name = "queryLBTReports", resultClass = LBTReport.class, query = "SELECT * \n" +
                "FROM\n" +
                "(\n" +
                "    SELECT ROW_NUMBER() OVER (PARTITION BY lbt_report_id ORDER BY lbt_report_id DESC) AS lbt_reports_1,*\n" +
                "    FROM LBTReports \n" +
                "    WHERE (lbt_report_id = #lbtReportId Or #lbtReportId = 0) \n" +
                "    AND LOWER(title) like LOWER(#title) AND LOWER(topic) like LOWER(#topic) AND (#linkedBugReportId = 0 AND\n" +
                "    (lbt_report_id in (Select lbt_report_id from lbtreports AS lbtreportstemp WHERE lbt_report_id not in (\n" +
                "    select lbt_report_id from BRIDLBTLink))) OR (lbt_report_id IN \n" +
                "    (select lbt_report_id from BRIDLBTLink AS brreportstemp WHERE bug_report_id = #linkedBugReportId)) \n" +
                "    OR #linkedBugReportId = -1) AND ((length(#developmentTeam) > 1 AND development_team like #developmentTeam) \n" +
                "    OR (length(#developmentTeam) = 1 OR development_team IS NULL))\n" +
                "    AND ((length(#customerSupportTeam) > 1 AND customer_support_team like #customerSupportTeam) \n" +
                "    OR (length(#customerSupportTeam) = 1 OR customer_support_team IS NULL))\n" +
                "    AND LOWER(user_assigned) like LOWER(#userAssigned)\n" +
                "    AND LOWER(user_submitted) like LOWER(#userSubmitted)\n" +
                "    AND severity_estimate like #severityEstimate AND ((date_created >  #startdate\n" +
                "    AND date_created < #enddate) OR (date_created = #startdate\n" +
                "    AND date_created < #enddate) OR (date_created > #startdate\n" +
                "    AND date_created = #enddate) OR (date_created = #startdate\n" +
                "    AND date_created = #enddate))\n" +
                "    AND ((date_all_linked_brs_closed >  #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed < #enddateallbrsclosed) OR (date_all_linked_brs_closed = #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed < #enddateallbrsclosed) OR (date_all_linked_brs_closed > #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed = #enddateallbrsclosed) OR (date_all_linked_brs_closed = #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed = #enddateallbrsclosed) OR (date_all_linked_brs_closed ISNULL AND #search = 0))\n" +
                "    AND software_version like #softwareVersion\n" +
                "    AND (lbt_report_id IN (select lbt_report_id from LBTSimilarBugReports AS SimilarReports WHERE similar_bug_report_id = #similarBugReportId)\n" +
                "    OR #similarBugReportId = -1) \n" +
                "    AND LOWER(abbreviated_stack_trace) like LOWER(#abbreviatedStackTrace) AND LOWER(bug_description) like LOWER(#bugDescription)\n" +
                ") as lbt_reports_2 ORDER BY lbt_report_id DESC LIMIT 1000")
//modified from response by ruffin and jonVD https://stackoverflow.com/questions/4193705/sql-server-select-last-n-rows
@NamedNativeQuery(name = "queryMyLBTReports", resultClass = LBTReport.class, query = "SELECT * \n" +
                "FROM\n" +
                "(\n" +
                "    SELECT ROW_NUMBER() OVER (PARTITION BY lbt_report_id ORDER BY lbt_report_id DESC) AS lbt_reports_1,*\n" +
                "    FROM LBTReports \n" +
                "    WHERE (lbt_report_id = #lbtReportId Or #lbtReportId = 0) \n" +
                "    AND LOWER(title) like LOWER(#title) AND LOWER(topic) like LOWER(#topic) AND (#linkedBugReportId = 0 AND\n" +
                "    (lbt_report_id in (Select lbt_report_id from lbtreports AS lbtreportstemp WHERE lbt_report_id not in (\n" +
                "    select lbt_report_id from BRIDLBTLink))) OR (lbt_report_id IN \n" +
                "    (select lbt_report_id from BRIDLBTLink AS brreportstemp WHERE bug_report_id = #linkedBugReportId)) \n" +
                "    OR #linkedBugReportId = -1) AND ((length(#developmentTeam) > 1 AND development_team like #developmentTeam) \n" +
                "    OR (length(#developmentTeam) = 1 OR development_team IS NULL))\n" +
                "    AND ((length(#customerSupportTeam) > 1 AND customer_support_team like #customerSupportTeam) \n" +
                "    OR (length(#customerSupportTeam) = 1 OR customer_support_team IS NULL))\n" +
                "    AND (LOWER(user_assigned) like LOWER(#userAssigned)\n" +
                "    OR LOWER(user_submitted) like LOWER(#userSubmitted))\n" +
                "    AND severity_estimate like #severityEstimate AND ((date_created >  #startdate\n" +
                "    AND date_created < #enddate) OR (date_created = #startdate\n" +
                "    AND date_created < #enddate) OR (date_created > #startdate\n" +
                "    AND date_created = #enddate) OR (date_created = #startdate\n" +
                "    AND date_created = #enddate))\n" +
                "    AND ((date_all_linked_brs_closed >  #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed < #enddateallbrsclosed) OR (date_all_linked_brs_closed = #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed < #enddateallbrsclosed) OR (date_all_linked_brs_closed > #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed = #enddateallbrsclosed) OR (date_all_linked_brs_closed = #startdateallbrsclosed\n" +
                "    AND date_all_linked_brs_closed = #enddateallbrsclosed) OR (date_all_linked_brs_closed ISNULL AND #search = 0))\n" +
                "    AND software_version like #softwareVersion\n" +
                "    AND (lbt_report_id IN (select lbt_report_id from LBTSimilarBugReports AS SimilarReports WHERE similar_bug_report_id = #similarBugReportId)\n" +
                "    OR #similarBugReportId = -1) \n" +
                "    AND LOWER(abbreviated_stack_trace) like LOWER(#abbreviatedStackTrace) AND LOWER(bug_description) like LOWER(#bugDescription)\n" +
                ") as lbt_reports_2 ORDER BY lbt_report_id DESC LIMIT 1000")
@NamedNativeQuery(name = "findActiveLBTs", query = "SELECT count(*) as count \n" +
                "FROM LBTReports WHERE customer_support_team like #team AND date_all_linked_brs_closed ISNULL ")
@NamedNativeQuery(name = "findCriticalActiveLBTs", query = "SELECT count(*) as count \n" +
                "FROM LBTReports WHERE customer_support_team like #team AND severity_estimate like '5' AND date_all_linked_brs_closed isNull")
@NamedNativeQuery(name = "findLBTsCompletedCurrentMonth", query = "SELECT count(*) \n" +
                "FROM LBTReports WHERE customer_support_team like #team AND date_all_linked_brs_closed > date_trunc('month', CURRENT_DATE)")
@NamedNativeQuery(name = "findLBTsCompletedYTD", query = "SELECT count(*) \n" +
                "FROM LBTReports WHERE customer_support_team like #team AND date_all_linked_brs_closed > date_trunc('year', CURRENT_DATE)")
@NamedNativeQuery(name = "findLBTTimeCompletedCurrentMonth", query = "SELECT AVG(date_all_linked_brs_closed - date_created) \n" +
                "FROM LBTReports WHERE customer_support_team like #team AND date_all_linked_brs_closed > date_trunc('month', CURRENT_DATE)")
@NamedNativeQuery(name = "findLBTTimeCompletedYTD", query = "SELECT AVG(date_all_linked_brs_closed - date_created) \n" +
                "FROM LBTReports WHERE customer_support_team like #team AND date_all_linked_brs_closed > date_trunc('year', CURRENT_DATE)")
public class LBTReport extends LBTReportBaseEntity implements Serializable {

    private static final long serialVersionUID = 5L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lbt_reports_sequence"
    )
    @SequenceGenerator(name = "lbt_reports_sequence", sequenceName = "lbt_reports_sequence",
                       allocationSize = 1, initialValue=1)
    @Column(name="lbt_report_id")
    private Integer lbtReportId;
    
    @NotNull
    private String title;
    
    @NotNull
    private String topic;
    
    @Column(name="development_team")
    private String developmentTeam;
    
    @Column(name="customer_support_team")
    private String customerSupportTeam;
    
    @Column(name="user_submitted")
    private String userSubmitted = "";
    
    @Column(name="user_assigned")
    private String userAssigned = "";
    
    @Column(name="severity_estimate")
    @NotNull 
    private String severityEstimate;
    
    @Column(name="software_version")
    @NotNull
    private String softwareVersion;
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, mappedBy="lbtReport", orphanRemoval=true)
    @JoinColumn(name="lbt_report_id")
    private List<LBTSimilarBugReports> similarBugReportIds = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, mappedBy="lbtReport", orphanRemoval=true)
    @JoinColumn(name="lbt_report_id", nullable=false)
    private List<BugReportLBTLink> linkedBugReportIds = new ArrayList<>();
    
    @Column(name="abbreviated_stack_trace")
    @NotNull
    private String abbreviatedStackTrace;
    
    @Column(name="bug_description")
    @NotNull
    private String bugDescription;
    
    public Integer getLbtReportId() {
        return lbtReportId;
    }

    public void setLbtReportId(Integer lbtReportId) {
        this.lbtReportId = lbtReportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDevelopmentTeam() {
        return developmentTeam;
    }

    public void setDevelopmentTeam(String developmentTeam) {
        this.developmentTeam = developmentTeam;
    }

    public String getCustomerSupportTeam() {
        return customerSupportTeam;
    }

    public void setCustomerSupportTeam(String customerSupportTeam) {
        this.customerSupportTeam = customerSupportTeam;
    }

    public String getUserSubmitted() {
        return userSubmitted;
    }

    public void setUserSubmitted(String userSubmitted) {
        this.userSubmitted = userSubmitted;
    }

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public String getSeverityEstimate() {
        return severityEstimate;
    }

    public void setSeverityEstimate(String severityEstimate) {
        this.severityEstimate = severityEstimate;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public List<LBTSimilarBugReports> getSimilarBugReportIds() {
        return similarBugReportIds;
    }

    public void setSimilarBugReportIds(List<LBTSimilarBugReports> similarBugReportIds) {
        this.similarBugReportIds = similarBugReportIds;
    }

    public List<BugReportLBTLink> getLinkedBugReportIds() {
        return linkedBugReportIds;
    }

    public void setLinkedBugReportIds(List<BugReportLBTLink> linkedBugReportIds) {
        this.linkedBugReportIds = linkedBugReportIds;
    }

    public String getAbbreviatedStackTrace() {
        return abbreviatedStackTrace;
    }

    public void setAbbreviatedStackTrace(String abbreviatedStackTrace) {
        this.abbreviatedStackTrace = abbreviatedStackTrace;
    }

    public String getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    // Used to determine whether a linked LBTReport link displays in JSF page
    public boolean hasLinkedBugReports() {
        if(this.getLinkedBugReportIds().isEmpty()) {
            return false;
        }
        return true;
    }
    
    // Used to determine whether a similar Bug Report displays in JSF page
    public boolean hasSimilarBugReport() {
        if(this.getSimilarBugReportIds().isEmpty()) {
            return false;
        }
        return true;
    }
        
    // Used to add BugReportLBTLink
    public void addBugReportLBTLink (BugReportLBTLink bugReportLBTLink) {
        bugReportLBTLink.setLbtReport(this);
        linkedBugReportIds.add(bugReportLBTLink);
    }
    
    // Used to add Similar Bug Report
    public void addLBTSimilarBugReport(LBTSimilarBugReports lBTimilarBugReports) {
        lBTimilarBugReports.setLbtReport(this);
        similarBugReportIds.add(lBTimilarBugReports);
    }
    
    // Used to remove similar bug reports on BugReports.xhtml page
    public void removeSimilarBugReportById(int id) {
        for(int i = 0; i < similarBugReportIds.size(); i++) {
            if(getSimilarBugReportIds().get(i).getSimilarBugReportId() == id) {
                getSimilarBugReportIds().remove(i);
            }///// MOVEEEEE THIS TO EJB!!!
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.lbtReportId);
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
        final LBTReport other = (LBTReport) obj;
        if (!Objects.equals(this.lbtReportId, other.lbtReportId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LBTReport{" + "lbtReportId=" + lbtReportId + ", title=" + title + ", topic=" + topic + ", developmentTeam=" + developmentTeam + ", customerSupportTeam=" + customerSupportTeam + ", userSubmitted=" + userSubmitted + ", userAssigned=" + userAssigned + ", severityEstimate=" + severityEstimate + ", softwareVersion=" + softwareVersion + ", similarBugReportIds=" + similarBugReportIds + ", linkedBugReportIds=" + linkedBugReportIds + ", abbreviatedStackTrace=" + abbreviatedStackTrace + ", bugDescription=" + bugDescription + '}';
    }
}

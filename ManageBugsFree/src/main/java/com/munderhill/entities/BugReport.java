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
@Customizer(com.munderhill.eclipselink.history.BugReportHistoryCustomizer.class)
@Table(name = "BugReports")
@NamedNativeQuery(name = "AnyLinkedBugReportsNotClosed", query = "SELECT COUNT(*) \n" +
                "FROM BugReports b INNER JOIN BRIDLBTLink l on l.bug_report_id = b.bug_report_id \n" +
                "WHERE l.lbt_report_id = #lbtReportId AND (b.status not like 'Closed/Fix Deployed'" +       
                "AND b.bug_report_id <> #bugReportId)")
@NamedNativeQuery(name = "findBugReportById", resultClass = BugReport.class, query = "SELECT * \n" +
                "FROM BugReports WHERE bug_report_id = #bugReportId ")
//modified from response by ruffin and jonVD https://stackoverflow.com/questions/4193705/sql-server-select-last-n-rows
@NamedNativeQuery(name = "queryBugReports", resultClass = BugReport.class, query = "SELECT * \n" +
                "FROM\n" +
                "(\n" +
                "    SELECT ROW_NUMBER() OVER (PARTITION BY bug_report_id ORDER BY bug_report_id DESC) AS bug_reports_1,*\n" +
                "    FROM BugReports  \n" +
                "    WHERE (bug_report_id = #bugReportId Or #bugReportId = 0) \n" +
                "    AND LOWER(title) like LOWER(#title) AND LOWER(topic) like LOWER(#topic) \n" +
                "    AND (bug_report_id IN (select bug_report_id from BRIDLBTLink AS LBTReports WHERE lbt_report_id = #lbtReportId) OR #lbtReportId = -1 OR #lbtReportId = 0)\n" +
                "    AND ((length(#teamName) > 1 AND team_name like #teamName) OR (length(#teamName) = 1 OR team_name IS NULL))\n" +
                "    AND LOWER(user_assigned) like LOWER(#userAssigned)\n" +
                "    AND LOWER(opened_by) like LOWER(#openedBy)\n" +
                "    AND priority like #priority AND status like #status AND ((date_created >  #startdate\n" +
                "    AND date_created < #enddate) OR (date_created = #startdate\n" +
                "    AND date_created < #enddate) OR (date_created > #startdate\n" +
                "    AND date_created = #enddate) OR (date_created = #startdate\n" +
                "    AND date_created = #enddate))\n" +
                "    AND ((date_first_closed >  #startdateclosed\n" +
                "    AND date_first_closed < #enddateclosed) OR (date_first_closed = #startdateclosed\n" +
                "    AND date_first_closed < #enddateclosed) OR (date_first_closed > #startdateclosed\n" +
                "    AND date_first_closed = #enddateclosed) OR (date_first_closed = #startdateclosed\n" +
                "    AND date_first_closed = #enddateclosed) OR (date_first_closed ISNULL AND #search = 0))\n" +
                "    AND software_version like #softwareVersion\n" +
                "    AND (bug_report_id IN (select bug_report_id from BRSimilarBugReports AS SimilarReports WHERE similar_bug_report_id = #similarBugReportId)\n" +
                "    OR #similarBugReportId = -1) \n" +
                "    AND LOWER(abbreviated_stack_trace) like LOWER(#abbreviatedStackTrace) AND LOWER(bug_description) like LOWER(#bugDescription)\n" +
                ") as bug_reports_2 ORDER BY bug_report_id DESC LIMIT 1000")
//modified from response by ruffin and jonVD https://stackoverflow.com/questions/4193705/sql-server-select-last-n-rows
@NamedNativeQuery(name = "queryMyBugReports", resultClass = BugReport.class, query = "SELECT * \n" +
                "FROM\n" +
                "(\n" +
                "    SELECT ROW_NUMBER() OVER (PARTITION BY bug_report_id ORDER BY bug_report_id DESC) AS bug_reports_1,*\n" +
                "    FROM BugReports  \n" +
                "    WHERE (bug_report_id = #bugReportId Or #bugReportId = 0) \n" +
                "    AND LOWER(title) like LOWER(#title) AND LOWER(topic) like LOWER(#topic) \n" +
                "    AND (bug_report_id IN (select bug_report_id from BRIDLBTLink AS LBTReports WHERE lbt_report_id = #lbtReportId) OR #lbtReportId = -1 OR #lbtReportId = 0)\n" +
                "    AND ((length(#teamName) > 1 AND team_name like #teamName) OR (length(#teamName) = 1 OR team_name IS NULL))\n" +
                "    AND (LOWER(user_assigned) like LOWER(#userAssigned)\n" +
                "    OR LOWER(opened_by) like LOWER(#openedBy))\n" +
                "    AND priority like #priority AND status like #status AND ((date_created >  #startdate\n" +
                "    AND date_created < #enddate) OR (date_created = #startdate\n" +
                "    AND date_created < #enddate) OR (date_created > #startdate\n" +
                "    AND date_created = #enddate) OR (date_created = #startdate\n" +
                "    AND date_created = #enddate))\n" +
                "    AND ((date_first_closed >  #startdateclosed\n" +
                "    AND date_first_closed < #enddateclosed) OR (date_first_closed = #startdateclosed\n" +
                "    AND date_first_closed < #enddateclosed) OR (date_first_closed > #startdateclosed\n" +
                "    AND date_first_closed = #enddateclosed) OR (date_first_closed = #startdateclosed\n" +
                "    AND date_first_closed = #enddateclosed) OR (date_first_closed ISNULL AND #search = 0))\n" +
                "    AND software_version like #softwareVersion\n" +
                "    AND (bug_report_id IN (select bug_report_id from BRSimilarBugReports AS SimilarReports WHERE similar_bug_report_id = #similarBugReportId)\n" +
                "    OR #similarBugReportId = -1) \n" +
                "    AND LOWER(abbreviated_stack_trace) like LOWER(#abbreviatedStackTrace) AND LOWER(bug_description) like LOWER(#bugDescription)\n" +
                ") as bug_reports_2 ORDER BY bug_report_id DESC LIMIT 1000")
@NamedNativeQuery(name = "findBRsCompletedCurrentMonth", query = "SELECT count(*) \n" +
                "FROM BugReports WHERE team_name like #team AND date_first_closed > date_trunc('month', CURRENT_DATE)")
@NamedNativeQuery(name = "findBRsCompletedYTD", query = "SELECT count(*) \n" +
                "FROM BugReports WHERE team_name like #team AND date_first_closed > date_trunc('year', CURRENT_DATE)")
@NamedNativeQuery(name = "findBRTimeCompletedCurrentMonth", query = "SELECT AVG(date_first_closed - date_created) \n" +
                "FROM BugReports WHERE team_name like #team AND date_first_closed > date_trunc('month', CURRENT_DATE)")
@NamedNativeQuery(name = "findBRTimeCompletedYTD", query = "SELECT AVG(date_first_closed - date_created) \n" +
                "FROM BugReports WHERE team_name like #team AND date_first_closed > date_trunc('year', CURRENT_DATE)")
public class BugReport extends BugReportBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bug_reports_sequence"
    )
    @SequenceGenerator(name = "bug_reports_sequence", sequenceName = "bug_reports_sequence",
                       allocationSize = 1,initialValue=1)
    @Column(name="bug_report_id")
    private Integer bugReportId;
    
    @NotNull
    private String title;
    
    @NotNull
    private String topic;
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, mappedBy="bugReport", orphanRemoval=true)
    @JoinColumn(name="bug_report_id", nullable=false)
    private List<BugReportLBTLink> linkedLbtReportIds = new ArrayList<>();
    
    @Column(name="team_name")
    private String teamName;
    
    @Column(name="user_assigned")
    private String userAssigned = "Unassigned";
    
    @Column(name="opened_by")
    private String openedBy = "Unassigned";
    
    @NotNull 
    private String priority;
    
    @Column(name="software_version")
    @NotNull
    private String softwareVersion;
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, mappedBy="bugReport", orphanRemoval=true)
    private List<BRSimilarBugReports> similarBugReportIds = new ArrayList<>();
    
    @Column(name="abbreviated_stack_trace")
    @NotNull
    private String abbreviatedStackTrace;
    
    @Column(name="bug_description")
    @NotNull
    private String bugDescription;
    
    public Integer getBugReportId() {
        return bugReportId;
    }

    public void setBugReportId(Integer bugReportId) {
        this.bugReportId = bugReportId;
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

    public List<BugReportLBTLink> getLinkedLbtReportIds() {
        return linkedLbtReportIds;
    }

    public void setLinkedLbtReportIds(List<BugReportLBTLink> linkedLbtReportIds) {
        this.linkedLbtReportIds = linkedLbtReportIds;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public String getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public List<BRSimilarBugReports> getSimilarBugReportIds() {
        return similarBugReportIds;
    }

    public void setSimilarBugReportIds(List<BRSimilarBugReports> similarBugReportIds) {
        this.similarBugReportIds = similarBugReportIds;
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
    public boolean hasLinkedLbtReports() {
        if(this.getLinkedLbtReportIds().isEmpty()) {
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
        bugReportLBTLink.setBugReport(this);
        linkedLbtReportIds.add(bugReportLBTLink);
    }
    
    // Used to add Similar Bug Report
    public void addBRSimilarBugReport(BRSimilarBugReports bRSimilarBugReports) {
        bRSimilarBugReports.setBugReport(this);
        similarBugReportIds.add(bRSimilarBugReports);
    }

    // Used to remove similar bug reports on BugReports.xhtml page
    public void removeSimilarBugReportById(int id) {
        for(int i = 0; i < similarBugReportIds.size(); i++) {
            if(getSimilarBugReportIds().get(i).getSimilarBugReportId() == id) {
                getSimilarBugReportIds().remove(i);
            } ///// MOVEEEEE THIS TO EJB!!!
        }
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.bugReportId);
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
        final BugReport other = (BugReport) obj;
        if (!Objects.equals(this.bugReportId, other.bugReportId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BugReport{" + "bugReportId=" + bugReportId + ", title=" + title + ", topic=" + topic + ", linkedLbtReportIds=" + linkedLbtReportIds + ", teamName=" + teamName + ", userAssigned=" + userAssigned + ", openedBy=" + openedBy + ", priority=" + priority + ", softwareVersion=" + softwareVersion + ", similarBugReportIds=" + similarBugReportIds + ", abbreviatedStackTrace=" + abbreviatedStackTrace + ", bugDescription=" + bugDescription + '}';
    }
   
}

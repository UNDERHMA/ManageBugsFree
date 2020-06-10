/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mason
 */
@Entity
@Table(name = "BugReportHistory")
@NamedNativeQuery(name = "findBugReportChanges", resultClass = BugReportHistory.class, query = "SELECT * \n" +
                "FROM BugReportHistory WHERE bug_report_id = #bugReportId ORDER BY start_Date ASC ")
public class BugReportHistory implements Serializable {

    private static final long serialVersionUID = 15L;
    
    @Column(name="bug_report_id")
    private Integer bugReportId;
    
    @NotNull
    private String title;
    
    private String topic;
    
    @Column(name="team_name")
    private String teamName;
    
    @Column(name="user_assigned")
    private String userAssigned = "Unassigned";
    
    @Column(name="opened_by")
    private String openedBy = "Unassigned";
    
    @NotNull 
    private String priority;
    
    @Column(name = "last_updated", insertable = true, updatable = true)
    private Timestamp lastUpdated;
    
    @Column(name = "updated_by", insertable = true, updatable = true)
    private String updatedBy;
        
    @Column(name = "date_created", insertable = true, updatable = true)
    private Timestamp dateCreated;
        
    private String status;
    
    @Column(name="software_version")
    @NotNull
    private String softwareVersion;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private List<BRSimilarBugReports> similarBugReportIds = new ArrayList<>();
    
    @Column(name="abbreviated_stack_trace")
    @NotNull
    private String abbreviatedStackTrace;
    
    @Column(name="bug_description")
    @NotNull
    private String bugDescription;
    
    @Id
    @Column(name="start_date")
    private Timestamp startDate;
    
    @Column(name="end_date")
    private Timestamp endDate;
    
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.bugReportId);
        hash = 11 * hash + Objects.hashCode(this.startDate);
        hash = 11 * hash + Objects.hashCode(this.endDate);
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
        final BugReportHistory other = (BugReportHistory) obj;
        if (!Objects.equals(this.bugReportId, other.bugReportId)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BugReportHistory{" + "bugReportId=" + bugReportId + ", title=" + title + ", topic=" + topic + ", teamName=" + teamName + ", userAssigned=" + userAssigned + ", openedBy=" + openedBy + ", priority=" + priority + ", lastUpdated=" + lastUpdated + ", updatedBy=" + updatedBy + ", dateCreated=" + dateCreated + ", status=" + status + ", softwareVersion=" + softwareVersion + ", similarBugReportIds=" + similarBugReportIds + ", abbreviatedStackTrace=" + abbreviatedStackTrace + ", bugDescription=" + bugDescription + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }
}


package website.managebugsfreeapp.entities;

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
@Table(name = "LBTReportHistory")
@NamedNativeQuery(name = "findLBTReportChanges", resultClass = LBTReportHistory.class, query = "SELECT * \n" +
                "FROM LBTReportHistory WHERE lbt_report_id = #lbtReportId ORDER BY start_Date ASC ")
public class LBTReportHistory implements Serializable {

    private static final long serialVersionUID = 16L;
    
    @Column(name="lbt_report_id")
    private int lbtReportId;
    
    @NotNull
    private String title;
    
    private String topic;
    
    @Column(name="development_team")
    private String developmentTeam;
    
    @Column(name="user_submitted")
    private String userSubmitted = "";
    
    @Column(name="user_assigned")
    private String userAssigned = "";
    
    @Column(name="customer_support_team")
    private String customerSupportTeam;
    
    @Column(name="severity_estimate")
    @NotNull 
    private String severityEstimate;
    
    @Column(name = "last_updated", insertable = true, updatable = true)
    private Timestamp lastUpdated;
    
    @Column(name = "updated_by", insertable = true, updatable = true)
    private String updatedBy;
        
    @Column(name = "date_created", insertable = true, updatable = true)
    private Timestamp dateCreated; 
    
    @Column(name="software_version")
    @NotNull
    private String softwareVersion;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private List<LBTSimilarBugReports> similarBugReportIds = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private List<BugReportLBTLink> linkedBugReportIds = new ArrayList<>();
    
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

    public int getLbtReportId() {
        return lbtReportId;
    }

    public void setLbtReportId(int lbtReportId) {
        this.lbtReportId = lbtReportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDevelopmentTeam() {
        return developmentTeam;
    }

    public void setDevelopmentTeam(String developmentTeam) {
        this.developmentTeam = developmentTeam;
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

    public String getCustomerSupportTeam() {
        return customerSupportTeam;
    }

    public void setCustomerSupportTeam(String customerSupportTeam) {
        this.customerSupportTeam = customerSupportTeam;
    }

    public String getSeverityEstimate() {
        return severityEstimate;
    }

    public void setSeverityEstimate(String severityEstimate) {
        this.severityEstimate = severityEstimate;
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
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.lbtReportId);
        hash = 53 * hash + Objects.hashCode(this.startDate);
        hash = 53 * hash + Objects.hashCode(this.endDate);
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
        final LBTReportHistory other = (LBTReportHistory) obj;
        if (!Objects.equals(this.lbtReportId, other.lbtReportId)) {
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
        return "LBTReportHistory{" + "lbtReportId=" + lbtReportId + ", title=" + title + ", topic=" + topic + ", developmentTeam=" + developmentTeam + ", userSubmitted=" + userSubmitted + ", userAssigned=" + userAssigned + ", customerSupportTeam=" + customerSupportTeam + ", severityEstimate=" + severityEstimate + ", lastUpdated=" + lastUpdated + ", updatedBy=" + updatedBy + ", dateCreated=" + dateCreated + ", softwareVersion=" + softwareVersion + ", similarBugReportIds=" + similarBugReportIds + ", linkedBugReportIds=" + linkedBugReportIds + ", abbreviatedStackTrace=" + abbreviatedStackTrace + ", bugDescription=" + bugDescription + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }
}

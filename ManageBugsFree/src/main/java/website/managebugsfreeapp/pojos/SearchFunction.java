
package website.managebugsfreeapp.pojos;

/**
 *
 * @author mason
 */
public abstract class SearchFunction {
    
    private String title = "";
    private String topic = "";
    private String developmentTeam = "";
    private String customerSupportTeam = "";
    private java.util.Date startDate;
    private java.util.Date endDate;
    private String softwareVersion = "";
    private Integer similarBugReportId;
    private String abbreviatedStackTrace = "";
    private String bugDescription = "";
    private String userAssigned = "";

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

    public java.util.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public Integer getSimilarBugReportId() {
        return similarBugReportId;
    }

    public void setSimilarBugReportId(Integer similarBugReportId) {
        this.similarBugReportId = similarBugReportId;
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

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }
    
    //Convert java.util.Date to java.util.Date for JPA queries
    public java.sql.Date dateConverter(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
}

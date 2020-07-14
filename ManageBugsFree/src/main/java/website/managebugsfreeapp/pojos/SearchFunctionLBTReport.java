
package website.managebugsfreeapp.pojos;

import java.util.Date;


/**
 *
 * @author mason
 */
public class SearchFunctionLBTReport extends SearchFunction {
    
    private Integer lbtReportId;
    private Integer bugReportId;
    private String userSubmitted = "";
    private String severityEstimate = "";
    private String myLBTReports = "";
    private java.util.Date startDateAllBRsClosed;
    private java.util.Date endDateAllBRsClosed;
    private boolean activeReports = false;

    public Integer getLbtReportId() {
        return lbtReportId;
    }

    public void setLbtReportId(Integer lbtReportId) {
        this.lbtReportId = lbtReportId;
    }
    
    public Integer getBugReportId() {
        return bugReportId;
    }

    public void setBugReportId(Integer bugReportId) {
        this.bugReportId = bugReportId;
    }

    public String getUserSubmitted() {
        return userSubmitted;
    }

    public void setUserSubmitted(String userSubmitted) {
        if(!this.getMyLBTReports().equals("")) {
            if(userSubmitted.equals(this.getMyLBTReports())) {
                this.userSubmitted = userSubmitted;
            }
            else if(userSubmitted.equals("-----") && 
                    (this.getUserAssigned().equals(this.getMyLBTReports())
                    || this.getUserAssigned().equals(("-----")))) {
                this.userSubmitted = userSubmitted;
            }
            else if(!userSubmitted.equals("-----") && !userSubmitted.equals(this.getMyLBTReports())) {
                this.userSubmitted = userSubmitted;
                super.setUserAssigned(this.getMyLBTReports());
            }
            else if (this.getUserAssigned().equals((this.getMyLBTReports()))) {
                this.userSubmitted = userSubmitted;
            }
        }
        else if (this.getMyLBTReports().equals("")) {
            this.userSubmitted = userSubmitted;
        }
    }
    
    @Override
    public void setUserAssigned(String userAssigned) {
        if(!this.getMyLBTReports().equals("")) {
            if(userAssigned.equals(this.getMyLBTReports())) {
                super.setUserAssigned(userAssigned);
            }
            else if(userAssigned.equals("-----") && 
                    (this.getUserSubmitted().equals(this.getMyLBTReports()) 
                || this.getUserSubmitted().equals(("-----")))) {
                super.setUserAssigned(userAssigned);
            }
            else if(!userAssigned.equals("-----") && !userAssigned.equals(this.getMyLBTReports())) {
                super.setUserAssigned(userAssigned);
                this.setUserSubmitted(this.getMyLBTReports());
            }
            else if (this.getUserSubmitted().equals((this.getMyLBTReports()))) {
                super.setUserAssigned(userAssigned);
            }
        }
        else if (this.getMyLBTReports().equals("")) {
            super.setUserAssigned(userAssigned);
        }
    }
      
    public String getSeverityEstimate() {
        return severityEstimate;
    }

    public void setSeverityEstimate(String severityEstimate) {
        this.severityEstimate = severityEstimate;
    }

    public String getMyLBTReports() {
        return myLBTReports;
    }

    public void setMyLBTReports(String myLBTReports) {
        this.myLBTReports = myLBTReports;
    }

    public Date getStartDateAllBRsClosed() {
        return startDateAllBRsClosed;
    }

    public void setStartDateAllBRsClosed(Date startDateAllBRsClosed) {
        this.startDateAllBRsClosed = startDateAllBRsClosed;
    }

    public Date getEndDateAllBRsClosed() {
        return endDateAllBRsClosed;
    }

    public void setEndDateAllBRsClosed(Date endDateAllBRsClosed) {
        this.endDateAllBRsClosed = endDateAllBRsClosed;
    }

    public boolean isActiveReports() {
        return activeReports;
    }

    public void setActiveReports(boolean activeReports) {
        this.activeReports = activeReports;
    }
     
}

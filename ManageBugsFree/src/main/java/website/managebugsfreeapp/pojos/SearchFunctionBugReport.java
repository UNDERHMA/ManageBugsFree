
package website.managebugsfreeapp.pojos;

import java.util.Date;

/**
 *
 * @author mason
 */
public class SearchFunctionBugReport extends SearchFunction {
    
    private Integer bugReportId;
    private Integer lbtReportId;
    private String priority = "";
    private String status = "";
    private String openedBy = "";
    private String myBugReports = "";
    private java.util.Date startDateClosed;
    private java.util.Date endDateClosed;
    private boolean activeReports = false;

    public Integer getBugReportId() {
        return bugReportId;
    }

    public void setBugReportId(Integer bugReportId) {
        this.bugReportId = bugReportId;
    }

    public Integer getLbtReportId() {
        return lbtReportId;
    }

    public void setLbtReportId(Integer lbtReportId) {
        this.lbtReportId = lbtReportId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(String openedBy) {
        if(!this.getMyBugReports().equals("")) {
            if(openedBy.equals(this.getMyBugReports())) {
                this.openedBy = openedBy;
            }
            else if(openedBy.equals("-----") && 
                    (this.getUserAssigned().equals(this.getMyBugReports())
                    || this.getUserAssigned().equals(("-----")))) {
                this.openedBy = openedBy;
            }
            else if(!openedBy.equals("-----") && !openedBy.equals(this.getMyBugReports())) {
                this.openedBy = openedBy;
                super.setUserAssigned(this.getMyBugReports());
            }
            else if (this.getUserAssigned().equals((this.getMyBugReports()))) {
                this.openedBy = openedBy;
            }
        }
        else if (this.getMyBugReports().equals("")) {
            this.openedBy = openedBy;
        }
    }
    
    @Override
    public void setUserAssigned(String userAssigned) {
        if(!this.getMyBugReports().equals("")) {
            if(userAssigned.equals(this.getMyBugReports())) {
                super.setUserAssigned(userAssigned);
            }
            else if(userAssigned.equals("-----") && 
                    (this.getOpenedBy().equals(this.getMyBugReports()) 
                || this.getOpenedBy().equals(("-----")))) {
                super.setUserAssigned(userAssigned);
            }
            else if(!userAssigned.equals("-----") && !userAssigned.equals(this.getMyBugReports())) {
                super.setUserAssigned(userAssigned);
                this.setOpenedBy(this.getMyBugReports());
            }
            else if (this.getOpenedBy().equals((this.getMyBugReports()))) {
                super.setUserAssigned(userAssigned);
            }
        }
        else if (this.getMyBugReports().equals("")) {
            super.setUserAssigned(userAssigned);
        }
    }

    public String getMyBugReports() {
        return myBugReports;
    }

    public void setMyBugReports(String myBugReports) {
        this.myBugReports = myBugReports;
    }

    public Date getStartDateClosed() {
        return startDateClosed;
    }

    public void setStartDateClosed(Date startDateClosed) {
        this.startDateClosed = startDateClosed;
    }

    public Date getEndDateClosed() {
        return endDateClosed;
    }

    public void setEndDateClosed(Date endDateClosed) {
        this.endDateClosed = endDateClosed;
    }

    public boolean isActiveReports() {
        return activeReports;
    }

    public void setActiveReports(boolean activeReports) {
        this.activeReports = activeReports;
    }

}

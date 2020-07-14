
package website.managebugsfreeapp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import website.managebugsfreeapp.ejb.BugReportEJB;
import website.managebugsfreeapp.ejb.LBTReportEJB;
import website.managebugsfreeapp.ejb.TeamsEJB;
import website.managebugsfreeapp.ejb.UsersEJB;
import website.managebugsfreeapp.entities.BRSimilarBugReports;
import website.managebugsfreeapp.entities.BugReport;
import website.managebugsfreeapp.entities.BugReportAdditionalNotes;
import website.managebugsfreeapp.entities.BugReportLBTLink;
import website.managebugsfreeapp.entities.LBTReport;
import website.managebugsfreeapp.entities.LBTReportAdditionalNotes;
import website.managebugsfreeapp.entities.LBTSimilarBugReports;
import website.managebugsfreeapp.entities.Users;
import website.managebugsfreeapp.pojos.ChangeRecord;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mason
 */
@FacesConfig(
        // Activates CDI build-in beans
        // CC BY-SA 4.0 License, available in package folder. Code snippet not changed in any way.
        // Tadas B. https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
        version = JSF_2_3
)

@Named
@RequestScoped
public class BugReportController {
    
    @EJB
    private BugReportEJB bugReportEJB;
    @EJB
    private LBTReportEJB lbtReportEJB;
    @EJB
    private UsersEJB usersEJB;
    @EJB
    private TeamsEJB teamsEJB;
    private BugReport bugReport;
    private int bugReportId;
    private String justCreated;
    private String updated;
    private BugReportAdditionalNotes note = new BugReportAdditionalNotes();
    private List<BugReportAdditionalNotes> notesList = new ArrayList<>();
    private List<String> developmentTeams;
    private List<String> priorities;   
    private List<String> softwareVersions;  
    private List<String> statuses;
    private List<String> topics;
    private List<String> users;  
    private List<ChangeRecord> changeRecord;
    private List<BRSimilarBugReports> similarSelectedValues;
    private List<BugReportLBTLink> linkedSelectedValues;
    private Integer jsfSimilarBugReport;
    private Integer jsfLinkedLbtReport;

    @PostConstruct
    public void init() {
        // Get BugReportID and justCreated number from url
        // CC BY-SA 4.0 License, available in package folder. Code snippet not changed in any way.
        // adapted from post by Erick, https://stackoverflow.com/questions/34031175/get-parameter-not-present-in-request-parameter-map
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        bugReportId = Integer.parseInt(parameterMap.get("bugReportId"));
        if(parameterMap.containsKey("justCreated")) {
            justCreated = " Created";
        }
        if(parameterMap.containsKey("updated")) {
            updated = "Update Successful!";
        }
        
        // Get bug report from HTTP query string
        bugReport =  bugReportEJB.findBugReportById(bugReportId);
        
        // Get change record for bug report
        changeRecord = bugReportEJB.findBugReportChanges(bugReportId);
        
        // Get additional notes for Bug Report
        notesList = bugReportEJB.notesList(bugReportId);
        
        // Initializing note bugreportid and enteredbyuserid
        note.setBugReportId(bugReportId);
        Users user = usersEJB.getUser(usersEJB.checkUser());
        note.setEnteredByUserId(user);
        
        // initializing dropdowns
        developmentTeams = teamsEJB.teamsByType("Software Development");
        priorities = bugReportEJB.prioritiesList();
        softwareVersions = bugReportEJB.softwareVersionsList();
        statuses = bugReportEJB.statusesList();
        topics = bugReportEJB.topicsList();
        users = usersEJB.usersByType("Software Development");
        
        developmentTeams.add(0,"Unassigned");
        users.add(0,"Unassigned");
    }
   
    public BugReport getBugReport() {
        return bugReport;
    }

    public void setBugReport(BugReport bugReport) {
        this.bugReport = bugReport;
    }

    public int getBugReportId() {
        return bugReportId;
    }

    public void setBugReportId(int bugReportId) {
        this.bugReportId = bugReportId;
    }

    public String getJustCreated() {
        return justCreated;
    }

    public void setJustCreated(String justCreated) {
        this.justCreated = justCreated;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public BugReportAdditionalNotes getNote() {
        return note;
    }

    public void setNote(BugReportAdditionalNotes note) {
        this.note = note;
    }

    public List<BugReportAdditionalNotes> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<BugReportAdditionalNotes> notesList) {
        this.notesList = notesList;
    }

    public List<String> getDevelopmentTeams() {
        return developmentTeams;
    }

    public void setDevelopmentTeams(List<String> developmentTeams) {
        this.developmentTeams = developmentTeams;
    }

    public List<String> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<String> priorities) {
        this.priorities = priorities;
    }

    public List<String> getSoftwareVersions() {
        return softwareVersions;
    }

    public void setSoftwareVersions(List<String> softwareVersions) {
        this.softwareVersions = softwareVersions;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    
    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<ChangeRecord> getChangeRecord() {
        return changeRecord;
    }

    public void setChangeRecord(List<ChangeRecord> changeRecord) {
        this.changeRecord = changeRecord;
    }

    public List<BRSimilarBugReports> getSimilarSelectedValues() {
        return similarSelectedValues;
    }

    public void setSimilarSelectedValues(List<BRSimilarBugReports> similarSelectedValues) {
        this.similarSelectedValues = similarSelectedValues;
    }

    public List<BugReportLBTLink> getLinkedSelectedValues() {
        return linkedSelectedValues;
    }

    public void setLinkedSelectedValues(List<BugReportLBTLink> linkedSelectedValues) {
        this.linkedSelectedValues = linkedSelectedValues;
    }

    public Integer getJsfSimilarBugReport() {
        return jsfSimilarBugReport;
    }

    public void setJsfSimilarBugReport(Integer jsfSimilarBugReport) {
        this.jsfSimilarBugReport = jsfSimilarBugReport;
    }

    public Integer getJsfLinkedLbtReport() {
        return jsfLinkedLbtReport;
    }

    public void setJsfLinkedLbtReport(Integer jsfLinkedLbtReport) {
        this.jsfLinkedLbtReport = jsfLinkedLbtReport;
    }
    
    
    
     // Used to remove similar bug reports on BugReports.xhtml page
    public void removeSimilarBugReportById() {
        bugReport.removeSimilarBugReportById(this.getJsfLinkedLbtReport()); //fix this
    }
    
    // Used to add similar bug reports on BugReports.xhtml page
    public void addSimilarBugReportById() {
        //check for valid input before calling EJB
        if(getJsfSimilarBugReport() == null ) {
            setJsfSimilarBugReport(0);
        }
        if(this.getJsfSimilarBugReport() > 0 && this.getJsfSimilarBugReport() < 10000000
                && this.getJsfSimilarBugReport() != this.getBugReportId()) {
            
            for(BRSimilarBugReports b : this.bugReport.getSimilarBugReportIds()) {
                if(this.getJsfSimilarBugReport().equals(b.getSimilarBugReportId())) {
                    return;
                }
            }
        // add new similar bug report id if necessary{
            BRSimilarBugReports newBRSimilarBugReport = new BRSimilarBugReports();
            newBRSimilarBugReport.setSimilarBugReportId(getJsfSimilarBugReport());
            bugReport.addBRSimilarBugReport(newBRSimilarBugReport);
        }
    }
    
    // Used to add similar bug reports on BugReports.xhtml page
    public void addLinkedLbtReportById() {
        //check for valid input before calling EJB
        if(getJsfLinkedLbtReport() == null ) {
            setJsfLinkedLbtReport(0);
        }
        if(this.getJsfLinkedLbtReport() > 0 && this.getJsfLinkedLbtReport() < 10000000) {
            for(BugReportLBTLink bl : this.bugReport.getLinkedLbtReportIds()) {
                if(this.getJsfLinkedLbtReport().equals(bl.getLbtReport().getLbtReportId())) {
                    return;
                }
            }
        // add new linked lbt report id if necessary
            BugReportLBTLink newBugReportLBTLink = new BugReportLBTLink();
            LBTReport lbtReport = lbtReportEJB.findLBTReportById(getJsfLinkedLbtReport());
            newBugReportLBTLink.setLbtReport(lbtReport);
            bugReport.addBugReportLBTLink(newBugReportLBTLink);
        }
    }
    
    public boolean isStatusClosed() {
        if(bugReport.getStatus().equals("Closed/Fix Deployed")) {
            return true;
        }
        return false;
    }
    
    public void reload() throws IOException {
         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
         String urlString = ((HttpServletRequest) context.getRequest()).getRequestURL() +
                             "?bugReportId="+bugReport.getBugReportId()+"&updated=1";
         context.redirect(urlString);
    } 
    
    public void update() {
        addSimilarBugReportById();
        addLinkedLbtReportById();
        // Update Development Team to match user assigned if user assigned 
        if(!bugReport.getUserAssigned().equals("Unassigned")) {
            bugReport.setTeamName(usersEJB.getUser(bugReport.getUserAssigned()).getTeamName());
        }
        if(bugReport.getDateFirstClosed() == null && bugReport.getStatus().equals("Closed/Fix Deployed")
                || bugReport.getDateFirstClosed() != null) {
            if(bugReport.getDateFirstClosed() == null) {
                bugReport.setDateFirstClosed(new Timestamp((new Date()).getTime()));
            }
            List<BugReportLBTLink> linkedLBTs = bugReport.getLinkedLbtReportIds();
            for(BugReportLBTLink link : linkedLBTs) {
                LBTReport lbtReport = link.getLbtReport();
                if(bugReportEJB.AllLinkedBugReportsClosed(lbtReport.getLbtReportId(), bugReportId)) {
                    lbtReport.setDateAllLinkedBRsClosed(bugReport.getDateFirstClosed());
                    lbtReportEJB.updateLBTReport(lbtReport, new LBTReportAdditionalNotes(),
                            new ArrayList<LBTSimilarBugReports>(), new ArrayList<BugReportLBTLink>());
                }
            }
        }
        // Update bug report after setting updatedBy to current user
        bugReport.setUpdatedBy(usersEJB.checkUser());
        bugReportEJB.updateBugReport(this.getBugReport(), note, 
                     getSimilarSelectedValues(), getLinkedSelectedValues());
        try {
            this.reload();
        } catch (IOException e) {
            //e.printStackTrace(); not necessary for IOException
        }
    }
    
    public void validateSimilarBRId(FacesContext context, UIComponent component, Integer input) {
        if(input != null) {
            if(!bugReportEJB.validateSimilarBRId(input)) {
                // MIT License in package folder
                // Pankaj Kumar https://github.com/journaldev/journaldev/blob/master/JSF/JSF_ValidationModel/src/main/java/com/journaldev/jsf/bean/Mobile.java
                ((UIInput) component).setValid(false);
                FacesMessage message = new FacesMessage("This BugReportId does not exists");
                context.addMessage(component.getClientId(context), message);
            }
        }
    }
    
    public void validateLinkedLBTId(FacesContext context, UIComponent component, Integer input) {
        if(input != null) {
            if(!bugReportEJB.validateLinkedLBTId(input)) {
                // MIT License in package folder
                // Pankaj Kumar https://github.com/journaldev/journaldev/blob/master/JSF/JSF_ValidationModel/src/main/java/com/journaldev/jsf/bean/Mobile.java
                ((UIInput) component).setValid(false);
                FacesMessage message = new FacesMessage("This LbtReportId does not exists");
                context.addMessage(component.getClientId(context), message);
            }
        }
    }
    
    public void validatePermissions(FacesContext context, UIComponent component, String input) {
        // Checking authorization token for permissions - return with faces message if invalid
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String accessToken = (String) session.getAttribute("accessToken");
        DecodedJWT accessJWT = JWT.decode(accessToken);
        List<String> permissionList = accessJWT.getClaim("permissions").asList(String.class);
        boolean hasValidPermissions = false;
        for (String s : permissionList) {
            if(s.equals("update:bugreport")) {
                hasValidPermissions = true;
            }
        }
        if(!hasValidPermissions) {
            // MIT License in package folder
            // Pankaj Kumar https://github.com/journaldev/journaldev/blob/master/JSF/JSF_ValidationModel/src/main/java/com/journaldev/jsf/bean/Mobile.java
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage("You do not have permission to modify a Bug Report. Only Software Developers can.");
            context.addMessage(component.getClientId(context), message);
        }       
    }
    
     
}

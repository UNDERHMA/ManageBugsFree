
package website.managebugsfreeapp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import website.managebugsfreeapp.ejb.BugReportEJB;
import website.managebugsfreeapp.ejb.LBTReportEJB;
import website.managebugsfreeapp.ejb.TeamsEJB;
import website.managebugsfreeapp.ejb.UsersEJB;
import website.managebugsfreeapp.entities.BRSimilarBugReports;
import website.managebugsfreeapp.entities.BugReport;
import website.managebugsfreeapp.entities.BugReportLBTLink;
import website.managebugsfreeapp.entities.LBTReport;
import java.io.IOException;
import java.util.List;
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
public class NewBugReportController {
    
    @EJB
    private BugReportEJB bugReportEJB;
    @EJB
    private LBTReportEJB lbtReportEJB;
    @EJB 
    private TeamsEJB teamsEJB;
    @EJB
    private UsersEJB usersEJB;
    private BugReport bugReport = new BugReport();
    private Integer similarBugReportId;
    private Integer linkedLbtReportId;
    private List<String> teams;    
    private List<String> priorities;   
    private List<String> softwareVersions; 
    private List<String> topics;
    
    @PostConstruct
    public void init() {
        teams = teamsEJB.teamsByType("Software Development");
        priorities = bugReportEJB.prioritiesList();
        softwareVersions = bugReportEJB.softwareVersionsList();
        topics = bugReportEJB.topicsList();
        
        for(String s: topics) {
            if (s.equals("Unassigned")) {
                topics.remove(s); //remove Unassigned
            }
        }
        topics.add(0,"Unassigned"); //add to front so it defaults to Unassigned
        
        teams.add(0,"Unassigned");
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

    public Integer getLinkedLbtReportId() {
        return linkedLbtReportId;
    }

    public void setLinkedLbtReportId(Integer linkedLbtReportId) {
        this.linkedLbtReportId = linkedLbtReportId;
    }
    
    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
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

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    
    public void addBugReport() {
        //Add userAssigned and openedBy value from idToken in the session
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idToken = (String) session.getAttribute("idToken");
        DecodedJWT idJWT = JWT.decode(idToken);
        String userName = idJWT.getClaim("name").asString();
        bugReport.setOpenedBy(userName);
        if(bugReport.getTeamName().equals("Unassigned")) {
            bugReport.setUserAssigned(userName);
            //Add Development Team value based on userName
            bugReport.setTeamName(usersEJB.getUser(userName).getTeamName());
        }
        else if(usersEJB.getUser(userName).getTeamName().equals(bugReport.getTeamName())) {
            bugReport.setUserAssigned(userName);
        }
        // add new similar bug report id if necessary
        if(getSimilarBugReportId() != null) {
            BRSimilarBugReports newBRSimilarBugReport = new BRSimilarBugReports();
            newBRSimilarBugReport.setSimilarBugReportId(getSimilarBugReportId());
            bugReport.addBRSimilarBugReport(newBRSimilarBugReport);
        }
        // add new linked lbt report id if necessary
        if(getLinkedLbtReportId() != null) {
            BugReportLBTLink newBugReportLBTLink = new BugReportLBTLink();
            LBTReport lbtReport = lbtReportEJB.findLBTReportById(getLinkedLbtReportId());
            newBugReportLBTLink.setLbtReport(lbtReport);
            bugReport.addBugReportLBTLink(newBugReportLBTLink);
        }
        // call EJB method to persist bugReport entity
        bugReportEJB.createBugReport(bugReport);
        ExternalContext context = facesContext.getExternalContext();
        String urlString = "BugReport.xhtml"+"?bugReportId="+bugReport.getBugReportId()+""
                + "&justCreated=1";
        try {
            context.redirect(urlString);
        } catch (IOException e) {
            e.printStackTrace();
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
            if(s.equals("create:bugreport")) {
                hasValidPermissions = true;
            }
        }
        if(!hasValidPermissions) {
            // MIT License in package folder
            // Pankaj Kumar https://github.com/journaldev/journaldev/blob/master/JSF/JSF_ValidationModel/src/main/java/com/journaldev/jsf/bean/Mobile.java
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage("You do not have permission to add a New Bug Report. Only Software Developers do.");
            context.addMessage(component.getClientId(context), message);
        }       
    }
       
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.munderhill.ejb.BugReportEJB;
import com.munderhill.ejb.LBTReportEJB;
import com.munderhill.ejb.TeamsEJB;
import com.munderhill.ejb.UsersEJB;
import com.munderhill.entities.BugReport;
import com.munderhill.entities.BugReportLBTLink;
import com.munderhill.entities.LBTReport;
import com.munderhill.entities.LBTReportAdditionalNotes;
import com.munderhill.entities.LBTSimilarBugReports;
import com.munderhill.entities.Users;
import com.munderhill.pojos.ChangeRecord;
import java.io.IOException;
import java.util.ArrayList;
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
        version = JSF_2_3
)

@Named
@RequestScoped
public class LBTReportController {
    
    @EJB
    private LBTReportEJB lbtReportEJB;
    @EJB
    private BugReportEJB bugReportEJB;
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private UsersEJB usersEJB;
    private LBTReport lbtReport;
    private int lbtReportId;
    private String justCreated;
    private String updated;
    private LBTReportAdditionalNotes note = new LBTReportAdditionalNotes();
    private List<LBTReportAdditionalNotes> notesList = new ArrayList<>();
    private List<String> developmentTeams;    
    private List<String> customerSupportTeams; 
    private List<String> priorities;   
    private List<String> softwareVersions;  
    private List<String> statuses; 
    private List<String> topics;
    private List<String> users; 
    private List<ChangeRecord> changeRecord;
    private List<LBTSimilarBugReports> similarSelectedValues;
    private List<BugReportLBTLink> linkedSelectedValues;
    private Integer jsfSimilarBugReport;
    private Integer jsfLinkedBugReport;

    @PostConstruct
    public void init() {
        // Get BugReportID and justCreated number from url
        //adapted from post by Tobias Amon, https://liferay.dev/forums/-/message_boards/message/1992225
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> parameterMap = context.getExternalContext().getRequestParameterMap();
        lbtReportId = Integer.parseInt(parameterMap.get("lbtReportId"));
        if(parameterMap.containsKey("justCreated")) {
            justCreated = " Created";
        }
        if(parameterMap.containsKey("updated")) {
            updated = "Updated Successful!";
        }
        
        // Get bug report from HTTP query string
        lbtReport =  lbtReportEJB.findLBTReportById(lbtReportId);
        
        // Get change record for bug report
        changeRecord = lbtReportEJB.findLBTReportChanges(lbtReportId);
        
        // Get additional notes for Bug Report
        notesList = lbtReportEJB.notesList(lbtReportId);
        
         // Initializing note bugreportid and enteredbyuserid
        note.setLbtReportId(lbtReportId);
        Users user = usersEJB.getUser(usersEJB.checkUser());
        note.setEnteredByUserId(user);
        
        // initializing dropdowns
        developmentTeams = teamsEJB.teamsByType("Software Development");
        customerSupportTeams = teamsEJB.teamsByType("Customer Support");
        priorities = lbtReportEJB.prioritiesList();
        softwareVersions = lbtReportEJB.softwareVersionsList();
        statuses = lbtReportEJB.statusesList();
        topics = lbtReportEJB.topicsList();
        users = usersEJB.usersByType("Customer Support");
        
        developmentTeams.add(0,"Unassigned");
        customerSupportTeams.add(0,"Unassigned");
        users.add(0,"Unassigned");
    }

    public LBTReport getLbtReport() {
        return lbtReport;
    }

    public void setLbtReport(LBTReport lbtReport) {
        this.lbtReport = lbtReport;
    }

    public int getLbtReportId() {
        return lbtReportId;
    }

    public void setLbtReportId(int lbtReportId) {
        this.lbtReportId = lbtReportId;
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

    public LBTReportAdditionalNotes getNote() {
        return note;
    }

    public void setNote(LBTReportAdditionalNotes note) {
        this.note = note;
    }

    public List<LBTReportAdditionalNotes> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<LBTReportAdditionalNotes> notesList) {
        this.notesList = notesList;
    }

    public List<String> getDevelopmentTeams() {
        return developmentTeams;
    }

    public void setDevelopmentTeams(List<String> developmentTeams) {
        this.developmentTeams = developmentTeams;
    }

    public List<String> getCustomerSupportTeams() {
        return customerSupportTeams;
    }

    public void setCustomerSupportTeams(List<String> customerSupportTeams) {
        this.customerSupportTeams = customerSupportTeams;
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

    public List<LBTSimilarBugReports> getSimilarSelectedValues() {
        return similarSelectedValues;
    }

    public void setSimilarSelectedValues(List<LBTSimilarBugReports> similarSelectedValues) {
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

    public Integer getJsfLinkedBugReport() {
        return jsfLinkedBugReport;
    }

    public void setJsfLinkedBugReport(Integer jsfLinkedBugReport) {
        this.jsfLinkedBugReport = jsfLinkedBugReport;
    }
    
    // Used to remove similar bug reports on BugReports.xhtml page
    public void removeSimilarBugReportById() {
        lbtReport.removeSimilarBugReportById(this.getJsfLinkedBugReport()); //fix this
    }
    
    // Used to add similar bug reports on BugReports.xhtml page
    public void addSimilarBugReportById() {
        //check for valid input before calling EJB
        if(getJsfSimilarBugReport() == null ) {
            setJsfSimilarBugReport(0);
        }
        if(this.getJsfSimilarBugReport() > 0 && this.getJsfSimilarBugReport() < 10000000) {
            for(LBTSimilarBugReports lbt : this.lbtReport.getSimilarBugReportIds()) {
                if(this.getJsfSimilarBugReport().equals(lbt.getSimilarBugReportId())) {
                    return;
                }
            }
        // add new similar bug report id if necessary{
            LBTSimilarBugReports newBRSimilarBugReport = new LBTSimilarBugReports();
            newBRSimilarBugReport.setSimilarBugReportId(getJsfSimilarBugReport());
            lbtReport.addLBTSimilarBugReport(newBRSimilarBugReport);
        }
    }
    
    // Used to add similar bug reports on BugReports.xhtml page
    public void addLinkedBugReportById() {
        //check for valid input before calling EJB
        if(getJsfLinkedBugReport() == null ) {
            setJsfLinkedBugReport(0);
        }
        if(this.getJsfLinkedBugReport() > 0 && this.getJsfLinkedBugReport() < 10000000) {
            for(BugReportLBTLink bl : this.lbtReport.getLinkedBugReportIds()) {
                if(this.getJsfLinkedBugReport().equals(bl.getBugReport().getBugReportId())) {
                    return;
                }
            }
        // add new linked bug report id if necessary
            BugReportLBTLink newBugReportLBTLink = new BugReportLBTLink();
            BugReport bugReport = bugReportEJB.findBugReportById(getJsfLinkedBugReport());
            newBugReportLBTLink.setBugReport(bugReport);
            lbtReport.addBugReportLBTLink(newBugReportLBTLink);
        }
    }
    
    public boolean isStatusClosed() {
        if(!lbtReport.getLinkedBugReportIds().isEmpty()) {
            if(bugReportEJB.AllLinkedBugReportsClosed(lbtReportId, -1)) {
                return true;
            }
        }
        return false;
    }
    
    public void reload() throws IOException {
         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
         String urlString = ((HttpServletRequest) context.getRequest()).getRequestURL() +
                             "?lbtReportId="+lbtReport.getLbtReportId()+"&updated=1";
         context.redirect(urlString);
    }   
    
    public void update() {
        addSimilarBugReportById();
        addLinkedBugReportById();
        // Update Customer Support Team to match user assigned if user assigned 
        if(!lbtReport.getUserAssigned().equals("Unassigned")) {
        lbtReport.setCustomerSupportTeam(usersEJB.getUser(lbtReport.getUserAssigned()).getTeamName());
        }
        // Update lbt report after setting updatedBy to current user
        lbtReport.setUpdatedBy(usersEJB.checkUser());
        lbtReportEJB.updateLBTReport(lbtReport, note, getSimilarSelectedValues(), getLinkedSelectedValues());
        try {
            this.reload();
        } catch (IOException e) {
            //e.printStackTrace(); not necessary for IOException
        }
    }

    // used Pankaj article for reference https://www.journaldev.com/7035/jsf-validation-example-tutorial-validator-tag-custom-validator
    public void validateSimilarBRId(FacesContext context, UIComponent component, Integer input) {
        if(input != null) {
            if(!lbtReportEJB.validateSimilarBRId(input)) {
                ((UIInput) component).setValid(false);
                FacesMessage message = new FacesMessage("This BugReportId does not exists");
                context.addMessage(component.getClientId(context), message);
            }
        }
    }
    
    // used Pankaj article for reference https://www.journaldev.com/7035/jsf-validation-example-tutorial-validator-tag-custom-validator
    public void validateLinkedBRId(FacesContext context, UIComponent component, Integer input) {
        if(input != null) {
            if(!lbtReportEJB.validateLinkedBRId(input)) {
                ((UIInput) component).setValid(false);
                FacesMessage message = new FacesMessage("This BugReportId does not exists");
                context.addMessage(component.getClientId(context), message);
            }
        }
    }
    
    // used Pankaj article for reference https://www.journaldev.com/7035/jsf-validation-example-tutorial-validator-tag-custom-validator
    public void validatePermissions(FacesContext context, UIComponent component, String input) {
        // Checking authorization token for permissions - return with faces message if invalid
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String accessToken = (String) session.getAttribute("accessToken");
        DecodedJWT accessJWT = JWT.decode(accessToken);
        List<String> permissionList = accessJWT.getClaim("permissions").asList(String.class);
        boolean hasValidPermissions = false;
        for (String s : permissionList) {
            if(s.equals("update:lbtreport")) {
                hasValidPermissions = true;
            }
        }
        if(!hasValidPermissions) {
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage("You do not have permission to modify an LBT Report. Only Customer Support Representatives can.");
            context.addMessage(component.getClientId(context), message);
        }       
    }
}

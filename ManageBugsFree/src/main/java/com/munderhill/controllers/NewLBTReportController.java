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
import com.munderhill.entities.LBTSimilarBugReports;
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
        version = JSF_2_3
)

@Named
@RequestScoped
public class NewLBTReportController {
    
    @EJB
    private LBTReportEJB lBTReportEJB;
    @EJB
    private BugReportEJB bugReportEJB;
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private UsersEJB usersEJB;
    private LBTReport lbtReport = new LBTReport();
    private Integer jsfSimilarBugReportId;
    private Integer jsfLinkedBugReportId;
    private List<String> developmentTeams;      
    private List<String> priorities;     
    private List<String> softwareVersions;   
    private List<String> topics;
    
    @PostConstruct
    public void init() {
        // initializing dropdowns
        developmentTeams = teamsEJB.teamsByType("Software Development");
        priorities = lBTReportEJB.prioritiesList();
        softwareVersions = lBTReportEJB.softwareVersionsList();
        topics = lBTReportEJB.topicsList();
        
        for(String s: topics) {
            if (s.equals("Unassigned")) {
                topics.remove(s); //remove Unassigned
            }
        }
        topics.add(0,"Unassigned"); //add to front so it defaults to Unassigned
        
        
        developmentTeams.add(0,"Unassigned");
    }

    public LBTReport getLbtReport() {
        return lbtReport;
    }

    public void setLbtReport(LBTReport lbtReport) {
        this.lbtReport = lbtReport;
    }

    public Integer getJsfSimilarBugReportId() {
        return jsfSimilarBugReportId;
    }

    public void setJsfSimilarBugReportId(Integer jsfSimilarBugReportId) {
        this.jsfSimilarBugReportId = jsfSimilarBugReportId;
    }

    public Integer getJsfLinkedBugReportId() {
        return jsfLinkedBugReportId;
    }

    public void setJsfLinkedBugReportId(Integer jsfLinkedBugReportId) {
        this.jsfLinkedBugReportId = jsfLinkedBugReportId;
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

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public void addLBTReport() {
        //Add userSubmitted and userAssigned value from idToken in the session
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idToken = (String) session.getAttribute("idToken");
        DecodedJWT idJWT = JWT.decode(idToken);
        String userName = idJWT.getClaim("name").asString();
        lbtReport.setUserSubmitted(userName);
        lbtReport.setUserAssigned(userName);
        //Add Customer Support Team value based on userName
        lbtReport.setCustomerSupportTeam(usersEJB.getUser(userName).getTeamName());
        // add new similar bug report id if necessary
        if(getJsfSimilarBugReportId() != null) {
            LBTSimilarBugReports newBRSimilarBugReport = new LBTSimilarBugReports();
            newBRSimilarBugReport.setSimilarBugReportId(getJsfSimilarBugReportId());
            lbtReport.addLBTSimilarBugReport(newBRSimilarBugReport);
        }
        // add new linked lbt report id if necessary
        if(getJsfLinkedBugReportId() != null ) {
            BugReportLBTLink newBugReportLBTLink = new BugReportLBTLink();
            BugReport bugReport = bugReportEJB.findBugReportById(getJsfLinkedBugReportId());
            newBugReportLBTLink.setBugReport(bugReport);
            lbtReport.addBugReportLBTLink(newBugReportLBTLink);
        }
        // call EJB method to persist bugReport entity
        lBTReportEJB.createLBTReport(lbtReport);
        ExternalContext context = facesContext.getExternalContext();
        String urlString = "LBTReport.xhtml"+"?lbtReportId="+lbtReport.getLbtReportId()+""
                + "&justCreated=1";
        try {
            context.redirect(urlString);
        } catch (IOException e) {
            e.printStackTrace();
            }
    }
    
    // used Pankaj article for reference https://www.journaldev.com/7035/jsf-validation-example-tutorial-validator-tag-custom-validator
    public void validateSimilarBRId(FacesContext context, UIComponent component, Integer input) {
        if(input != null) {
            if(!lBTReportEJB.validateSimilarBRId(input)) {
                ((UIInput) component).setValid(false);
                FacesMessage message = new FacesMessage("This BugReportId does not exists");
                context.addMessage(component.getClientId(context), message);
            }
        }
    }
    
    // used Pankaj article for reference https://www.journaldev.com/7035/jsf-validation-example-tutorial-validator-tag-custom-validator
    public void validateLinkedBRId(FacesContext context, UIComponent component, Integer input) {
        if(input != null) {
            if(!lBTReportEJB.validateLinkedBRId(input)) {
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
            if(s.equals("create:lbtreport")) {
                hasValidPermissions = true;
            }
        }
        if(!hasValidPermissions) {
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage("You do not have permission to add a New LBT Report. Only Customer Support Representatives do.");
            context.addMessage(component.getClientId(context), message);
        }       
    }
}

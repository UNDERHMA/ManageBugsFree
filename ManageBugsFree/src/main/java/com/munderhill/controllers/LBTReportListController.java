/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.munderhill.ejb.LBTReportEJB;
import com.munderhill.ejb.TeamsEJB;
import com.munderhill.ejb.UsersEJB;
import com.munderhill.entities.LBTReport;
import com.munderhill.pagination.LazyLBTReportDataModel;
import com.munderhill.pojos.SearchFunctionLBTReport;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
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
public class LBTReportListController {
    
    @EJB
    private LBTReportEJB lBTReportEJB;
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private UsersEJB usersEJB;
    private SearchFunctionLBTReport searchFunction = new SearchFunctionLBTReport();
    private LBTReport lbtReport = new LBTReport();
    private Integer jsfSimilarBugReportId;
    private Integer jsfLinkedBugReportId;
    private LazyLBTReportDataModel lbtReportModel;
    private List<String> developmentTeams;    
    private List<String> customerSupportTeams; 
    private List<String> priorities;   
    private List<String> softwareVersions;  
    private List<String> statuses;
    private List<String> topics;
    private List<String> users; 
    private List<String> users2;

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String s = request.getServletPath();
        if(s.equals("/UnlinkedLBTReports.xhtml")) {
            searchFunction.setBugReportId(0);
            lbtReportModel = new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction, false));
        }
        else if(s.equals("/MyLBTReports.xhtml")) {
            //Add userAssigned from session
            HttpSession session = (HttpSession) request.getSession(false);
            String idToken = (String) session.getAttribute("idToken");
            DecodedJWT idJWT = JWT.decode(idToken);
            String userName = idJWT.getClaim("name").asString();
            searchFunction.setUserSubmitted(userName);
            searchFunction.setUserAssigned(userName);
            searchFunction.setMyLBTReports(userName);
            lbtReportModel = new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction, true));
            // set values back so GUI makes sense
            searchFunction.setUserAssigned("-----");
            searchFunction.setUserSubmitted("-----");
        }
        else {
            lbtReportModel = new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction, false));
        }
        
        // initializing dropdowns
        developmentTeams = teamsEJB.teamsByType("Software Development");
        customerSupportTeams = teamsEJB.teamsByType("Customer Support");
        priorities = lBTReportEJB.prioritiesList();
        softwareVersions = lBTReportEJB.softwareVersionsList();
        statuses = lBTReportEJB.statusesList();
        topics = lBTReportEJB.topicsList();
        users = usersEJB.usersByType("Customer Support");
        developmentTeams.add(0,"-----");
        developmentTeams.add(1,"Unassigned");
        customerSupportTeams.add(0,"-----");
        customerSupportTeams.add(1,"Unassigned");
        priorities.add(0,"-----");
        softwareVersions.add(0,"-----");
        statuses.add(0,"-----");
        topics.add(0,"-----");
        users.add(0,"-----");
        users2 = new ArrayList<>(users);
        users.add(1,"Unassigned");
    }

    public SearchFunctionLBTReport getSearchFunction() {
        return searchFunction;
    }

    public void setSearchFunction(SearchFunctionLBTReport searchFunction) {
        this.searchFunction = searchFunction;
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

    public LazyLBTReportDataModel getLbtReportModel() {
        return lbtReportModel;
    }

    public void setLbtReportModel(LazyLBTReportDataModel lbtReportModel) {
        this.lbtReportModel = lbtReportModel;
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

    // used for users list without unassigned value
    public List<String> getUsers2() {
        return users2;
    }

    public void setUsers2(List<String> users2) {
        this.users2 = users2;
    }
    
     public void search(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String s = request.getServletPath();
        if(s.equals("/UnlinkedLBTReports.xhtml")) {
            searchFunction.setBugReportId(0);
            lbtReportModel = new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction, false));
        }
        else if(s.equals("/MyLBTReports.xhtml")) {
                //Add userAssigned from session
                HttpSession session = (HttpSession) request.getSession(false);
                String idToken = (String) session.getAttribute("idToken");
                DecodedJWT idJWT = JWT.decode(idToken);
                String userName = idJWT.getClaim("name").asString();
                /* assigned user values from session when needed and perform queryLBTReports method
                    with the true value so that the query returns reports with userAssigned
                    or userSubmitted = the username when the user enters "-----" in both fields */
                if(searchFunction.getUserAssigned().equals(searchFunction.getMyLBTReports())
                    || searchFunction.getUserSubmitted().equals(searchFunction.getMyLBTReports())) {
                    lbtReportModel =  new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction,false));
                }
                else if(searchFunction.getUserAssigned().equals("-----")) {
                    searchFunction.setUserAssigned(userName);
                    lbtReportModel =  new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction,false));
                }
                else if(searchFunction.getUserSubmitted().equals("-----")) {
                    searchFunction.setUserSubmitted(userName);
                    lbtReportModel =  new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction,false));
                }
                else {
                    searchFunction.setUserAssigned(userName);
                    searchFunction.setUserSubmitted(userName);
                    lbtReportModel = new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction,true));
                }
        }
        else {
            lbtReportModel = new LazyLBTReportDataModel(lBTReportEJB.queryLBTReports(searchFunction, false));
        }
    }
}

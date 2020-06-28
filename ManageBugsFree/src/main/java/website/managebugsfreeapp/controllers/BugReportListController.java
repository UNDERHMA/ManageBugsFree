/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package website.managebugsfreeapp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import website.managebugsfreeapp.ejb.BugReportEJB;
import website.managebugsfreeapp.ejb.TeamsEJB;
import website.managebugsfreeapp.ejb.UsersEJB;
import website.managebugsfreeapp.pagination.LazyBugReportDataModel;
import website.managebugsfreeapp.pojos.SearchFunctionBugReport;
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
public class BugReportListController {

    @EJB
    private BugReportEJB bugReportEJB;
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private UsersEJB usersEJB;
    private SearchFunctionBugReport searchFunction = new SearchFunctionBugReport();
    private LazyBugReportDataModel bugReportModel;
    private List<String> teams;    
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
        if(s.equals("/UnassignedBugReports.xhtml")) {
            searchFunction.setUserAssigned("Unassigned");
            bugReportModel = new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
        }
        else if(s.equals("/CriticalBugReports.xhtml")) {
            searchFunction.setPriority("5");
            bugReportModel = new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
        }
        else if(s.equals("/MyBugReports.xhtml")) {
            //Add userAssigned from session
            HttpSession session = (HttpSession) request.getSession(false);
            String idToken = (String) session.getAttribute("idToken");
            DecodedJWT idJWT = JWT.decode(idToken);
            String userName = idJWT.getClaim("name").asString();
            searchFunction.setUserAssigned(userName);
            searchFunction.setOpenedBy(userName);
            searchFunction.setMyBugReports(userName);
            bugReportModel = new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,true));
            // set values back so GUI makes sense
            searchFunction.setUserAssigned("-----");
            searchFunction.setOpenedBy("-----");
        }
        else {
            bugReportModel =  new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
        }
        
        // initializing dropdowns
        teams = teamsEJB.teamsByType("Software Development");
        priorities = bugReportEJB.prioritiesList();
        softwareVersions = bugReportEJB.softwareVersionsList();
        statuses = bugReportEJB.statusesList();
        topics = bugReportEJB.topicsList();
        users = usersEJB.usersByType("Software Development");
        teams.add(0,"-----");
        teams.add(1,"Unassigned");
        topics.add(0,"-----");
        priorities.add(0,"-----");
        softwareVersions.add(0,"-----");
        statuses.add(0,"-----");
        users.add(0,"-----");
        users2 = new ArrayList<>(users);
        users.add(1,"Unassigned");
    }

    public SearchFunctionBugReport getSearchFunction() {
        return searchFunction;
    }

    public void setSearchFunction(SearchFunctionBugReport searchFunction) {
        this.searchFunction = searchFunction;
    }

    public LazyBugReportDataModel getBugReportModel() {
        return bugReportModel;
    }

    public void setBugReportModel(LazyBugReportDataModel bugReportModel) {
        this.bugReportModel = bugReportModel;
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

    public List<String> getUsers2() {
        return users2;
    }

    public void setUsers2(List<String> users2) {
        this.users2 = users2;
    }
    
    public void search(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String s = request.getServletPath();
        if(s.equals("/UnassignedBugReports.xhtml")) {
            searchFunction.setUserAssigned("Unassigned");
            bugReportModel = new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
        }
        else if(s.equals("/CriticalBugReports.xhtml")) {
            searchFunction.setPriority("5");
            bugReportModel = new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
        }
        else if(s.equals("/MyBugReports.xhtml")) {
            //Add userAssigned from session
            HttpSession session = (HttpSession) request.getSession(false);
            String idToken = (String) session.getAttribute("idToken");
            DecodedJWT idJWT = JWT.decode(idToken);
            String userName = idJWT.getClaim("name").asString();
            /* assigned user values from session when needed and perform queryBugReports method
                    with the true value so that the query returns reports with userAssigned
                    or OpenedBy = the username when the user enters "-----" in both fields */
                if(searchFunction.getUserAssigned().equals(searchFunction.getMyBugReports())
                    || searchFunction.getOpenedBy().equals(searchFunction.getMyBugReports())) {
                    bugReportModel =  new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
                }
                else if(searchFunction.getUserAssigned().equals("-----")) {
                    searchFunction.setUserAssigned(userName);
                    bugReportModel =  new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
                }
                else if(searchFunction.getOpenedBy().equals("-----")) {
                    searchFunction.setOpenedBy(userName);
                    bugReportModel =  new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
                }
                else {
                    searchFunction.setUserAssigned(userName);
                    searchFunction.setOpenedBy(userName);
                    bugReportModel = new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,true));
                }
        }
        else {
            bugReportModel =  new LazyBugReportDataModel(bugReportEJB.queryBugReports(searchFunction,false));
        }
    }
    
}

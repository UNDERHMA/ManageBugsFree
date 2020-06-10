/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.munderhill.ejb.TeamsEJB;
import com.munderhill.ejb.UsersEJB;
import com.munderhill.entities.Users;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
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
public class UserSettingsController {
    
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private UsersEJB usersEJB;
    private List<String> teams;
    private Users user;
    private String teamName;
    private String role;
    private String updated;

    
    @PostConstruct
    public void init() {
        // Get userName value from idToken in the session
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idToken = (String) session.getAttribute("idToken");
        DecodedJWT idJWT = JWT.decode(idToken);
        String userName = idJWT.getClaim("name").asString();
        
        // Get team and role given userName
        user = usersEJB.getUser(userName);
        teamName = user.getTeamName();
        role = user.getRole();
                
        // Get list of teams
        String listType = "does nothing";
        if(role.equals("customersupportrepresentative")) {
            listType = "Customer Support";
        }
        else if (role.equals("softwaredeveloper")) {
            listType = "Software Development";
        }
        else if (role.equals("admin")) {
            listType = "Admin";
        }
        teams = teamsEJB.teamsByType(listType);

        // Update successful displays on update
        //adapted from post by Tobias Amon, https://liferay.dev/forums/-/message_boards/message/1992225
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> parameterMap = context.getExternalContext().getRequestParameterMap();
        if(parameterMap.containsKey("updated")) {
            updated = "Update Successful!";
        }
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
        user.setTeamName(teamName);
    }

    public String getRole() {
        if(role.equals("customersupportrepresentative")) {
            role = role.substring(0,1).toUpperCase() + role.substring(1,8) + " " + 
                    role.substring(8,9).toUpperCase() + role.substring(9,15) +
                    " " + role.substring(15,16).toUpperCase() + role.substring(16);
        }
        if(role.equals("softwaredeveloper")){
            role = role.substring(0,1).toUpperCase() + role.substring(1,8) + " " + 
                    role.substring(8,9).toUpperCase() + role.substring(9);
        }
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
    
    public void reload() throws IOException {
         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
         String urlString = ((HttpServletRequest) context.getRequest()).getRequestURL() + "?updated=1";
         context.redirect(urlString);
    }   

    public void save() {
        teamsEJB.saveSettings(this.user);
        try {
            this.reload();
        } catch (IOException e) {
            //e.printStackTrace(); not necessary for IOException
        }
    }
}

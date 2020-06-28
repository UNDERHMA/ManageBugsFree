/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.controllers;

import website.managebugsfreeapp.ejb.UsersEJB;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import javax.inject.Named;

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
public class UserController {
    
    @EJB
    private UsersEJB userEJB;
    private String userName;
            
    // check if user exists in database and add user if user does not exist
    @PostConstruct
    public void init() {
     this.setUserName(userEJB.checkUser());   
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}

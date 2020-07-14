
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
        // CC BY-SA 4.0 License, available in package folder. Code snippet not changed in any way.
        // Tadas B. https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
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

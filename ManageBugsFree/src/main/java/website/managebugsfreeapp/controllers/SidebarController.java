
package website.managebugsfreeapp.controllers;

import website.managebugsfreeapp.ejb.SidebarEJB;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
public class SidebarController implements Serializable {
    
    @EJB
    private SidebarEJB sidebarEJB;
    private boolean SoftwareDeveloperPermissions = false;
    private boolean CustomerSupportRepresentativePermissions = false;
    
    @PostConstruct
    public void init() {
        checkSideBarScope();
    }
    
    public void checkSideBarScope() {
        String result = sidebarEJB.checkSideBarScope();
        if(result.equals("view:SDSidebar")) {
            this.SoftwareDeveloperPermissions = true;
        }
        else if(result.equals("view:CSRSidebar")) {
            this.CustomerSupportRepresentativePermissions = true;
        }
        else if(result.equals("view:AdminSideBar")) {
            this.SoftwareDeveloperPermissions = true;
            this.CustomerSupportRepresentativePermissions = true;
        }
    }

    public boolean isSoftwareDeveloperPermissions() {
        return SoftwareDeveloperPermissions;
    }

    public void setSoftwareDeveloperPermissions(boolean SoftwareDeveloperPermissions) {
        this.SoftwareDeveloperPermissions = SoftwareDeveloperPermissions;
    }

    public boolean isCustomerSupportRepresentativePermissions() {
        return CustomerSupportRepresentativePermissions;
    }

    public void setCustomerSupportRepresentativePermissions(boolean CustomerSupportRepresentativePermissions) {
        this.CustomerSupportRepresentativePermissions = CustomerSupportRepresentativePermissions;
    }
    
}

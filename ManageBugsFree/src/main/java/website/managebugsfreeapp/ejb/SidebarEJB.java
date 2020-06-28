/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.ejb;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mason
 */
@Stateless
public class SidebarEJB {
    
    public String checkSideBarScope() {
        // Checking authorization token for permissions - return with faces message if invalid
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String accessToken = (String) session.getAttribute("accessToken");
        DecodedJWT accessJWT = JWT.decode(accessToken);
        List<String> permissionList = accessJWT.getClaim("permissions").asList(String.class);
        for (String s : permissionList) {   
            if(s.equals("view:SDSidebar")) {
                return "view:SDSidebar";
            }
            else if(s.equals("view:CSRSidebar")) {
                return "view:CSRSidebar";
            }
            else if(s.equals("view:AdminSideBar")) {
                return "view:AdminSideBar";
            }
        }
        // return error if no appropriate permission was found
        return "error";
    }
}

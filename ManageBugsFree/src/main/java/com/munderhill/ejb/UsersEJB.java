/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.ejb;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.munderhill.entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mason
 */
@Stateless
public class UsersEJB {

    @PersistenceContext
    private EntityManager em;
    
    public String checkUser() {
        // Get userName value from idToken in the session
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idToken = (String) session.getAttribute("idToken");
        DecodedJWT idJWT = JWT.decode(idToken);
        String userName = idJWT.getClaim("name").asString();
        
        // check if user exist in Users database table
        TypedQuery<String> query = em.createNamedQuery("findUser", String.class);
        query.setParameter("userName", userName);
         List<String> result = query.getResultList();
        
        // create user in database Users table if they don't exist
        if(result.isEmpty()) {
            Users user = new Users();
            user.setUserName(userName);
            user.setTeamName("Unassigned");
            
            // get user's role from session attributes 
            String accessToken = (String) session.getAttribute("accessToken");
            DecodedJWT accessJWT = JWT.decode(accessToken);
            List<String> permissionList = accessJWT.getClaim("permissions").asList(String.class);
            for (String s : permissionList) {
                if(s.equals("role:softwaredeveloper")) {
                    user.setRole("softwaredeveloper");
                }
                else if(s.equals("role:customersupportrepresentative")) {
                    user.setRole("customersupportrepresentative");
                }
                else if(s.equals("role:admin")) {
                    user.setRole("admin");
                }
            }
            // default to customersupportrepresentative permissions just in case permissions are blank
            if(user.getTeamName().equals("")) {
                user.setRole("customersupportrepresentative");
            }
            em.persist(user);
            return user.getUserName();
        }
        return result.get(0);
    }
    
    public Users getUser(String userName) {     
        // check if user has assigned team in Teams database table
        TypedQuery<Users> query = em.createNamedQuery("findAllUserData", Users.class);
        query.setParameter("userName", userName);
        List<Users> userObject = query.getResultList();
        return userObject.get(0);
    }
    
    public List<String> usersByType(String teamType){
        TypedQuery<String> query = em.createNamedQuery("findAllUsersByType", String.class);
        query.setParameter("teamType", teamType);
        return query.getResultList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.authenticationfilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mason
 */

@WebFilter("/faces/*")
public class AuthenticationFilter implements Filter {

    
    /* Code by Kasun Dharmadasa used as reference -
    https://medium.com/@kasunpdh/session-management-in-java-using-servlet-filters-and-cookies-7c536b40448f
    */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Creating HttpServletRequest, HttpServletResponse and userActive flag for processing
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        boolean userActive = false;
        
        // Dealing with session, only validated sessions get userActive = true
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            if (session.getAttribute("accessToken") != null || session.getAttribute("idToken") != null
                    || session.getAttribute("User") != null) {
                userActive = true;          
            }
        }
        
       
        
        //redirecting based on userActive flag or if it is the callback
        if (userActive) {
            // Continue to original request destination
            chain.doFilter(request, response);
        }
        else {
            // Redirect to login URL
            String redirectUrl = String.format(
                "%s://localhost:%s/ManageBugsFree/login",
                request.getScheme(),
                request.getServerPort()
            );
            try {
                httpResponse.sendRedirect(redirectUrl);
            } catch (Exception exc) {
                System.out.println("Faces redirect failed");
            }
        }
    }
    
    @Override
     public void destroy() {
    }
 
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }
}

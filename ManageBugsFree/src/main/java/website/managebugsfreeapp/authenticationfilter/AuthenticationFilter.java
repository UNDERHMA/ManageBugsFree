
package website.managebugsfreeapp.authenticationfilter;

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

@WebFilter("*.xhtml")
public class AuthenticationFilter implements Filter {

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
                "%s://www.managebugsfree-app.website/login",
                request.getScheme()
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

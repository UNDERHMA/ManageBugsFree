/**
MIT License in this package:
code taken from https://github.com/auth0-samples/auth0-java-ee-sample
 */
package website.managebugsfreeapp.security.auth0javaeesample;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/callback"})
public class CallbackServlet extends HttpServlet {
           
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = (String) request.getSession().getAttribute("Referer");
        String redirectTo = referer != null ? 
                referer : "/Home.xhtml";
        
        // Redirect page
        try {
            response.sendRedirect(redirectTo);
        } catch (Exception exc) {
            System.out.println("Faces redirect failed");
        }
    }
}

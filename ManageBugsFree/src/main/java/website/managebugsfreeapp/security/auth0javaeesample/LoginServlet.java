/**
MIT License in this package:
code taken from https://github.com/auth0-samples/auth0-java-ee-sample
 */
package website.managebugsfreeapp.security.auth0javaeesample;

import com.auth0.AuthenticationController;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final Auth0AuthenticationConfig config;
    private final AuthenticationController authenticationController;
    
    @Inject
    LoginServlet(Auth0AuthenticationConfig config, AuthenticationController authenticationController) {
        this.config = config;
        this.authenticationController = authenticationController;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // URL where the application will receive the authorization code (e.g., http://localhost:3000/callback)
        String callbackUrl = String.format(
                "%s://www.managebugsfree-app.website/callback",
                request.getScheme()
        );

        // Create the authorization URL to redirect the user to, to begin the authentication flow.
        String authURL = authenticationController.buildAuthorizeUrl(request, response, callbackUrl)
                .withScope(config.getScope())
                .build();
                
        response.sendRedirect(authURL);
    }
}

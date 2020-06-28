/**
MIT License in this package:
code taken from https://github.com/auth0-samples/auth0-java-ee-sample
 */

package website.managebugsfreeapp.security.auth0javaeesample;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@ApplicationScoped
@AutoApplySession
public class Auth0AuthenticationMechanism implements HttpAuthenticationMechanism {
    private final AuthenticationController authenticationController;
    private final IdentityStoreHandler identityStoreHandler;

    @Inject
    Auth0AuthenticationMechanism(AuthenticationController authenticationController, IdentityStoreHandler identityStoreHandler) {
        this.authenticationController = authenticationController;
        this.identityStoreHandler = identityStoreHandler;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws RuntimeException {

        // Exchange the code for the ID token, and notify container of result.
        if (isCallbackRequest(httpServletRequest)) {
            try {
                // Creating session and com.auth0.state attribute for state validation
                HttpSession session = httpServletRequest.getSession(true);
                //set state if it exists in httpServlet request else check cookies for state and set it
                if(httpServletRequest.getParameter("state") != null) {
                    session.setAttribute("com.auth0.state", httpServletRequest.getParameter("state"));
                }
                else {
                    Cookie[] requestCookies = httpServletRequest.getCookies();
                    for (Cookie c : requestCookies) {
                        if(c.getName().equals("com.auth0.state")) {
                            // if state not null when user logs in
                            if(httpServletRequest.getParameter("state") != null) {
                                session.setAttribute("com.auth0.state", httpServletRequest.getParameter("state"));
                                c.setValue(httpServletRequest.getParameter("state"));
                            }
                            // if state is null when user returns with valid cookie
                            else {
                            session.setAttribute("com.auth0.state", c.getValue());
                            }
                        }
                    }
                }
                
                // Perform validation, get and get tokens
                Tokens tokens = authenticationController.handle(httpServletRequest, httpServletResponse);
                Auth0JwtCredential auth0JwtCredential = new Auth0JwtCredential(tokens.getIdToken());
                CredentialValidationResult result = identityStoreHandler.validate(auth0JwtCredential);
                
                // Set session attributes for logged in state
                session.setAttribute("accessToken", tokens.getAccessToken());
                session.setAttribute("idToken", tokens.getIdToken());
                session.setAttribute("User", "validUser");
                
                return httpMessageContext.notifyContainerAboutLogin(result);
            } catch (IdentityVerificationException e) {
                e.printStackTrace();
                return httpMessageContext.responseUnauthorized();
            }
        }
        return httpMessageContext.doNothing();
    }

    private boolean isCallbackRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/callback") && request.getParameter("code") != null;
    }
}

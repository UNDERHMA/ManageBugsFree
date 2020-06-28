/**
MIT License in this package:
code taken from https://github.com/auth0-samples/auth0-java-ee-sample
 */
package com.munderhill.security.auth0javaeesample;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    private final Auth0AuthenticationConfig config;

    @Inject
    LogoutServlet(Auth0AuthenticationConfig config) {
        this.config = config;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        clearSession(request);
        response.sendRedirect(getLogoutUrl(request));
    }

    private void clearSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

    private String getLogoutUrl(HttpServletRequest request) {
        String returnUrl = String.format("%s://www.managebugsfree-app.website", request.getScheme());
        int port = request.getServerPort();
        String scheme = request.getScheme();

        if (("http".equals(scheme) && port != 80) ||
                ("https".equals(scheme) && port != 443)) {
            returnUrl += ":" + port;
        }

        returnUrl += "/login";

        // Build logout URL like:
        // https://{YOUR-DOMAIN}/v2/logout?client_id={YOUR-CLIENT-ID}&returnTo=http://localhost:8080/
        String logoutUrl = String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                config.getDomain(),
                config.getClientId(),
                returnUrl
        );

        return logoutUrl;
    }
}

/**
MIT License in this package:
code taken from https://github.com/auth0-samples/auth0-java-ee-sample
 */
package website.managebugsfreeapp.security.auth0javaeesample;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import javax.security.enterprise.credential.Credential;


class Auth0JwtCredential implements Credential {
    private Auth0JwtPrincipal auth0JwtPrincipal;

    Auth0JwtCredential(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        this.auth0JwtPrincipal = new Auth0JwtPrincipal(decodedJWT);
    }

    Auth0JwtPrincipal getAuth0JwtPrincipal() {
        return auth0JwtPrincipal;
    }
}

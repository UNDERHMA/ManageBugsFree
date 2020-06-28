/**
MIT License in this package:
code taken from https://github.com/auth0-samples/auth0-java-ee-sample
 */
package website.managebugsfreeapp.security.auth0javaeesample;

import com.auth0.jwt.interfaces.DecodedJWT;
import javax.security.enterprise.CallerPrincipal;


public class Auth0JwtPrincipal extends CallerPrincipal {
    private final DecodedJWT idToken;

    Auth0JwtPrincipal(DecodedJWT idToken) {
        super(idToken.getClaim("name").asString());
        this.idToken = idToken;
    }

    public DecodedJWT getIdToken() {
        return this.idToken;
    }
}

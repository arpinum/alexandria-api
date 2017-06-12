package arpinum.infrastructure.security;


import arpinum.configuration.Secured;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    public JwtFilter(JwtVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        try {
            String token = token(authorizationHeader);
            requestContext.setSecurityContext(new SimpleSecurityContext(validateToken(token)));
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String token(String authorizationHeader) {
        return authorizationHeader.substring("Bearer".length()).trim();
    }

    private String validateToken(String token) {
        return verifier.verify(token);
    }

    private JwtVerifier verifier;

    public static class SimpleSecurityContext implements SecurityContext {

        public SimpleSecurityContext(String id) {
            this.id = id;
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> id;
        }

        @Override
        public boolean isUserInRole(String role) {
            return true;
        }

        @Override
        public boolean isSecure() {
            return true;
        }

        @Override
        public String getAuthenticationScheme() {
            return "Bearer";
        }

        private final String id;
    }
}

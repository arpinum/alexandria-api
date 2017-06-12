package arpinum.web;


import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public final class SecurityContextBuilder {

    public static final SecurityContext forId(String id) {
        return new SecurityContext() {
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
                return "FAKE";
            }
        };
    }
}

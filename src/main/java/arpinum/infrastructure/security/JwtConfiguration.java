package arpinum.infrastructure.security;


public interface JwtConfiguration {

    String secret();

    String issuer();
}

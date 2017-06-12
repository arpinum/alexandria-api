package arpinum.infrastructure.security;


public interface JwtVerifier {

    String verify(String token);

}

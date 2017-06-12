package arpinum.infrastructure.security;


import io.vavr.collection.Map;

public interface JwtBuilder {

    String build(String id, Map<String, String> claims);
}

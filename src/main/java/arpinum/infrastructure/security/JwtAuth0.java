package arpinum.infrastructure.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.vavr.collection.Map;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

public class JwtAuth0 implements JwtBuilder, JwtVerifier {

    @Inject
    public JwtAuth0(JwtConfiguration configuration) throws UnsupportedEncodingException {
        this.algorithm = Algorithm.HMAC256(configuration.secret());
        this.issuer = configuration.issuer();
        jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    @Override
    public String build(String id, Map<String, String> claims) {
        return claims.foldLeft(JWT.create()
                        .withIssuer(issuer)
                        .withSubject(id)
                , (b, t) -> t.apply((s, v) -> b.withClaim(s, v)))
                .sign(algorithm);
    }

    @Override
    public String verify(String token) {
        return jwtVerifier.verify(token).getSubject();
    }

    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    private final String issuer;
}

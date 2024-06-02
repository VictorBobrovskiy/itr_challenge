package com.itr.challenge.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtUtil(@Value("${jwt_secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm)
                .withSubject("User details")
                .withIssuer("itr")
                .build();
    }

    public String generateToken(String username) {

        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(30).toInstant());

        String token = JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("itr")
                .withExpiresAt(expirationDate)
                .sign(algorithm);

        log.debug("JWT Token generated successfully");

        return token;

    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {

        DecodedJWT jwt = verifier.verify(token);

        log.debug("JWT Token verified successfully");

        return jwt.getClaim("username").asString();
    }
}
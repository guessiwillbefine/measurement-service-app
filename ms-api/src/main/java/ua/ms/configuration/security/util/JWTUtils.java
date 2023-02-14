package ua.ms.configuration.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtils {
    @Value("${security.jwt.days-to-expire}")
    private int daysToExpire;
    @Value("${security.jwt.secret}")
    private String secret;
    public String generateToken(String username) {
        Date date = Date.from(ZonedDateTime.now().plusDays(daysToExpire).toInstant());
        return JWT.create()
                .withSubject("user details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(date)
                .withIssuer("ms-api")
                .sign(Algorithm.HMAC256(secret));
    }

    public String getClaimFromToken(String token) throws JWTVerificationException {
        JWTVerifier tokenVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user details")
                .withIssuer("ms-api")
                .build();
        DecodedJWT verifiedToken = tokenVerifier.verify(token);
        return verifiedToken.getClaim("username").asString();
    }
}

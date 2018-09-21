package com.smoothprogramming.JWTToken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class JwtTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtTokenApplication.class, args);
        String jwtToken = generateJWTToken();
        verifyJWTToken(jwtToken);
    }

    public static String generateJWTToken() {
        String signatureSecret = "SECRET_VALUE_FOR_SIGNATURE";
        Algorithm algorithm = Algorithm.HMAC256(signatureSecret);

        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();

        c.add(Calendar.HOUR, 24);
        Date expireDate = c.getTime();

        String jwtToken = JWT.create()
                .withIssuer("smoothprogramming")
                .withSubject("demo")
                .withAudience("techgeeks")
                .withIssuedAt(currentDate)
                .withExpiresAt(expireDate)
                .withClaim("Claim1", "Value1")
                .withClaim("Claim2", "Value2")
                .sign(algorithm);

        return jwtToken;
    }

    public static void verifyJWTToken(String jwtToken) {
        String signatureSecret = "SECRET_VALUE_FOR_SIGNATURE";
        Algorithm algorithm = Algorithm.HMAC256(signatureSecret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("smoothprogramming")
                .withSubject("demo")
                .build();

        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        System.out.println("Claim1 is "+ decodedJWT.getClaim("Claim1").asString());
        System.out.println("Claim2 is "+ decodedJWT.getClaim("Claim2").asString());
    }
}

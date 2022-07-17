package vip.testops.account.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public static String createToken(String secret, Map<String, ?> claim) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("springdemo")
                .withClaim("userInfo", claim)
                .sign(algorithm);
        return token;
    }

    public static Map<String, ?> verify(String secret, String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("springdemo")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        Claim claim = jwt.getClaim("userInfo");
        return claim.asMap();
    }

}

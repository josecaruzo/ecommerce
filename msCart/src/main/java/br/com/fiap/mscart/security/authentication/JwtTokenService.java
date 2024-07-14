package br.com.fiap.mscart.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;
    @Value("${spring.security.oauth2.authorizationserver.issuer}")
    private String issuer;

    public String getSubjectFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }
}

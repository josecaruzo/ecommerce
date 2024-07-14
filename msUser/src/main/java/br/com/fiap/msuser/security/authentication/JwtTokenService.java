package br.com.fiap.msuser.security.authentication;

import br.com.fiap.msuser.security.userdetails.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class JwtTokenService {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;
    @Value("${spring.security.oauth2.authorizationserver.issuer}")
    private String issuer;

    public String generateToken(UserDetailsImpl userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        Instant issuedAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        Instant expiresAt = issuedAt.atZone(ZoneId.systemDefault()).plusMinutes(30).toInstant();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("user", userDetails.getUser().getId())
                .withClaim("username", userDetails.getUsername())
                .withArrayClaim("roles", convertAuthorities(userDetails))
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getSubjectFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }

    private String[] convertAuthorities(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

}

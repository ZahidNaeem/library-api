package org.zahid.apps.web.pos.security.jwt;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.pos.security.service.UserPrincipal;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger LOG = LogManager.getLogger(JwtProvider.class);

    @Value("${pos.app.jwtSecret}")
    private String jwtSecret;

    @Value("${pos.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            LOG.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            LOG.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }
}

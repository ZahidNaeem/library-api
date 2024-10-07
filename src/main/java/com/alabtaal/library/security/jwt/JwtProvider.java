package com.alabtaal.library.security.jwt;

import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.model.UserPrincipal;
import com.alabtaal.library.util.Miscellaneous;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);
  private static final String LIBRARY_SECRET_KEY = System.getenv("LIBRARY_SECRET_KEY");
  private static final byte[] keyBytes = LIBRARY_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
  private static final SecretKey secret = Keys.hmacShaKeyFor(keyBytes);

  public String generateJwt(final Authentication authentication, final int jwtExpiration) {
    final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    return generateJwt(userPrincipal.getUsername(), jwtExpiration);
  }

  public String generateJwt(final String username, final int jwtExpiration) {
    return Jwts
        .builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(secret, SIG.HS256)
        .compact();
  }

  public String getUsernameFromJwt(final String jwt) {

    return Jwts
        .parser()
        .verifyWith(secret)
        .build()
        .parseSignedClaims(jwt)
        .getPayload()
        .getSubject();
  }

  public boolean validateJwtToken(final String jwt) throws InternalServerErrorException {
    try {
      Jwts
          .parser()
          .verifyWith(secret)
          .build()
          .parse(jwt);
      return true;
    } catch (SignatureException e) {
      Miscellaneous.logException(LOG, e);
      throw new SignatureException("Invalid JWT Signature");
    } catch (MalformedJwtException e) {
      Miscellaneous.logException(LOG, e);
      throw new MalformedJwtException("Invalid JWT");
    } catch (ExpiredJwtException e) {
      Miscellaneous.logException(LOG, e);
      throw new ExpiredJwtException(null, null, "Expired JWT");
    } catch (UnsupportedJwtException e) {
      Miscellaneous.logException(LOG, e);
      throw new UnsupportedJwtException("Unsupported JWT");
    } catch (IllegalArgumentException e) {
      Miscellaneous.logException(LOG, e);
      throw new IllegalArgumentException("JWT claims are invalid");
    } catch (Exception e) {
      Miscellaneous.logException(LOG, e);
      throw new InternalServerErrorException("Unexpected error");
    }
  }

  public LocalDateTime getExpiryDateFromToken(String token) {
    return toLocalDateTime(
        Jwts
            .parser()
            .verifyWith(secret)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration()
    );
  }

  private LocalDateTime toLocalDateTime(Date date) {
    ZoneOffset zoneOffset = ZoneOffset.UTC;
    return date.toInstant().atOffset(zoneOffset).toLocalDateTime();
  }
}

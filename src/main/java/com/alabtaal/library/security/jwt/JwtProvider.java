package com.alabtaal.library.security.jwt;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.model.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);
  private static final String PG_SECRET_KEY = System.getenv("PG_SECRET_KEY");
  private static final byte[] keyBytes = PG_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
  private static final SecretKey secret = Keys.hmacShaKeyFor(keyBytes);

//  Set token expiry as 1 hour from issuance date
  @Value("${abt.app.jwtExpiration:#{1*60*60*1000}}")
  private int jwtExpiration;

  public String generateJwt(final Authentication authentication) {
    final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    return Jwts
        .builder()
        .subject(userPrincipal.getUsername())
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

  public boolean validateJwtToken(final String jwt) throws InternalServerErrorException, BadRequestException {
    try {
      Jwts
          .parser()
          .verifyWith(secret)
          .build()
          .parse(jwt);
      return true;
    } catch (SignatureException e) {
      Miscellaneous.logException(LOG, e);
      throw new BadRequestException("Invalid JWT Signature");
    } catch (MalformedJwtException e) {
      Miscellaneous.logException(LOG, e);
      throw new BadRequestException("Invalid JWT");
    } catch (ExpiredJwtException e) {
      Miscellaneous.logException(LOG, e);
      throw new BadRequestException("Expired JWT");
    } catch (UnsupportedJwtException e) {
      Miscellaneous.logException(LOG, e);
      throw new BadRequestException("Unsupported JWT");
    } catch (IllegalArgumentException e) {
      Miscellaneous.logException(LOG, e);
      throw new BadRequestException("JWT claims are invalid");
    } catch (Exception e) {
      Miscellaneous.logException(LOG, e);
      throw new InternalServerErrorException("Unexpected error");
    }
  }
}

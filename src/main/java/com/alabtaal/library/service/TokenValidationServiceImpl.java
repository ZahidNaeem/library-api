package com.alabtaal.library.service;

import com.alabtaal.library.entity.TokenValidationEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.repo.TokenValidationRepo;
import com.alabtaal.library.util.Miscellaneous;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {

  private final TokenValidationRepo tokenValidationRepo;

  @Value("${abt.app.accessCookieName:access_token}")
  private String accessCookieName;
  @Value("${abt.app.refreshCookieName:refresh_token}")
  private String refreshCookieName;
  @Value("${abt.app.tokenCookieExpiration:#{24*60*60}}")
  private int cookieMaxAge;

  @Override
  public boolean isTokenDisabled(String token) {
    return tokenValidationRepo.findByToken(token)
        .map(entity -> entity.getDisabled() == 1)
        .orElse(true);
  }

  @Override
  public void disableAllUserTokens(String username) {
    final List<TokenValidationEntity> allUserTokens = tokenValidationRepo.findAllByUsername(username);
    allUserTokens
        .forEach(token -> token.setDisabled(1));
    tokenValidationRepo.saveAllAndFlush(allUserTokens);
  }

  @Override
  public boolean tokenExists(String token) {
    return tokenValidationRepo.findByToken(token).isPresent();
  }

  @Override
  public TokenValidationEntity add(String username, String token) throws BadRequestException {
    if (tokenExists(token)) {
      throw new BadRequestException("Token already exists");
    }

    TokenValidationEntity entity = TokenValidationEntity
        .builder()
        .username(username)
        .token(token)
        .build();
    return save(entity);
  }

  private TokenValidationEntity save(TokenValidationEntity entity) throws BadRequestException {
    Miscellaneous.constraintViolation(entity);
    return tokenValidationRepo.saveAndFlush(entity);
  }

  @Override
  public ResponseCookie createAccessTokenCookie(String accessToken) {
    return ResponseCookie.fromClientResponse(accessCookieName, accessToken)
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .maxAge(cookieMaxAge)
        .build();
  }

  @Override
  public ResponseCookie createRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.fromClientResponse(refreshCookieName, refreshToken)
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .maxAge(cookieMaxAge)
        .build();
  }

  @Override
  public void addAccessTokenCookie(HttpHeaders httpHeaders, String accessToken) {
    httpHeaders.add(HttpHeaders.SET_COOKIE, String.valueOf(createAccessTokenCookie(accessToken)));
  }

  @Override
  public void addRefreshTokenCookie(HttpHeaders httpHeaders, String refreshToken) {
    httpHeaders.add(HttpHeaders.SET_COOKIE, String.valueOf(createRefreshTokenCookie(refreshToken)));
  }

  @Override
  public void deleteAccessTokenCookie(HttpHeaders httpHeaders) {
    ResponseCookie cookie = ResponseCookie.fromClientResponse(accessCookieName, "")
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .maxAge(0)
        .build();
    httpHeaders.add(HttpHeaders.SET_COOKIE, String.valueOf(cookie));
  }

  @Override
  public void deleteRefreshTokenCookie(HttpHeaders httpHeaders) {
    ResponseCookie cookie = ResponseCookie.fromClientResponse(refreshCookieName, "")
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .maxAge(0)
        .build();
    httpHeaders.add(HttpHeaders.SET_COOKIE, String.valueOf(cookie));
  }
}

package com.alabtaal.library.service;

import com.alabtaal.library.entity.TokenValidationEntity;
import com.alabtaal.library.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public interface TokenValidationService {

  boolean isTokenDisabled(String token);

  void disableAllUserTokens(String username);

  boolean tokenExists(String token);

  TokenValidationEntity add(String username, String token) throws BadRequestException;

  ResponseCookie createAccessTokenCookie(String accessToken);

  ResponseCookie createRefreshTokenCookie(String refreshToken);

  void addAccessTokenCookie(HttpHeaders httpHeaders, String accessToken);

  void addRefreshTokenCookie(HttpHeaders httpHeaders, String refreshToken);

  void deleteAccessTokenCookie(HttpHeaders httpHeaders);

  void deleteRefreshTokenCookie(HttpHeaders httpHeaders);
}

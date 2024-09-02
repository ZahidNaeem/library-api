package com.alabtaal.library.controller;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.mapper.UserMapper;
import com.alabtaal.library.payload.request.LoginRequest;
import com.alabtaal.library.payload.request.SignupRequest;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.JwtAuthenticationResponse;
import com.alabtaal.library.repo.UserRepo;
import com.alabtaal.library.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final UserRepo userRepo;

  private final UserMapper userMapper;

  private final JwtProvider jwtProvider;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    final String jwt = jwtProvider.generateJwt(authentication);
    return ResponseEntity.ok(
        ApiResponse
            .<JwtAuthenticationResponse>builder()
            .success(true)
            .message("Login successfull")
            .entity(new JwtAuthenticationResponse(jwt))
            .build()
    );
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<Boolean>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws BadRequestException {
    if (userRepo.existsByUsername(signUpRequest.getUsername())) {
      throw new BadRequestException("Username is already taken!");
    }

    if (userRepo.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException("Email Address already in use!");
    }

    // Creating user's account
    final UserEntity user = userMapper.toEntity(signUpRequest);

    final UserEntity result = userRepo.saveAndFlush(user);

    final URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/users/{username}")
        .buildAndExpand(result.getUsername()).toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse
                .<Boolean>builder()
                .success(true)
                .message("Thank you! You're successfully registered. Please Login to continue!")
                .entity(true)
                .build()
        );
  }

  @GetMapping("validate-token")
  public ResponseEntity<ApiResponse<Boolean>> validateJwtToken(
      @RequestParam(value = "jwt") final String jwt) throws InternalServerErrorException, BadRequestException {
    final boolean isTokenValid = jwtProvider.validateJwtToken(jwt);
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(isTokenValid)
            .message("Token is valid")
            .entity(isTokenValid)
            .build()
    );
  }

  @GetMapping("/checkEmailExists")
  public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(@RequestParam(value = "email") String email) {
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(userRepo.existsByEmail(email))
            .message("Email already exists")
            .entity(userRepo.existsByEmail(email))
            .build()
    );
  }

  @GetMapping("/recoverPassword")
  public ResponseEntity<ApiResponse<Boolean>> recoverPassword(@RequestParam(value = "email") String email) {
//    TODO: functionality will be implemented later
    return null;
  }
}

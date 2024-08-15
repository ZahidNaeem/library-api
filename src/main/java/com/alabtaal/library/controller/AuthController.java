package com.alabtaal.library.controller;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.enumeration.RoleName;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.RoleNameNotFoundException;
import com.alabtaal.library.mapper.UserMapper;
import com.alabtaal.library.model.UserModel;
import com.alabtaal.library.payload.request.LoginRequest;
import com.alabtaal.library.payload.request.SignUpRequest;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.JwtAuthenticationResponse;
import com.alabtaal.library.repo.RoleRepo;
import com.alabtaal.library.repo.UserRepo;
import com.alabtaal.library.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

  private final RoleRepo roleRepo;

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  private final JwtProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    final String jwt = tokenProvider.generateJwt(authentication);
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
  public ResponseEntity<ApiResponse<Boolean>> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws BadRequestException {
    if (userRepo.existsByUsername(signUpRequest.getUsername())) {
      throw new BadRequestException("Username is already taken!");
    }

    if (userRepo.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException("Email Address already in use!");
    }

    // Creating userModel's account
    final UserModel userModel = UserModel.builder()
        .name(signUpRequest.getName())
        .username(signUpRequest.getUsername())
        .password(passwordEncoder.encode(signUpRequest.getPassword()))
        .email(signUpRequest.getEmail())
        .organization(signUpRequest.getOrganization())
        .build();

    Set<String> strRoles = CollectionUtils.isNotEmpty(signUpRequest.getRole()) ? signUpRequest.getRole() : Set.of("user");
    Set<RoleName> roles = new HashSet<>();

    strRoles.forEach(role -> {
      try {
        roles.add(RoleName.fromValue(role));
      } catch (RoleNameNotFoundException e) {
        throw new RuntimeException(e);
      }
    });

    userModel.setRoles(roles);

    final UserEntity result = userRepo.saveAndFlush(userMapper.toEntity(userModel));

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
    final ResponseEntity<ApiResponse<Boolean>> responseEntity = checkEmailExists(email);

    if (responseEntity.getBody().equals(true)) {
      try {
        final boolean mailSent = gmailService.sendMessage("Welcome to Library Application",
            "To reset you account password, please click on below link:\nhttp://localhost:3000", email);
        if (mailSent) {
          return ResponseEntity.ok(
              ApiResponse
                  .<Boolean>builder()
                  .success(true)
                  .message("Recovery email sent")
                  .entity(null)
                  .build()
          );
        } else {
          throw new BadRequestException("Unknown error occurred");
        }
      } catch (MessagingException | IOException e) {
        e.printStackTrace();
        throw new InternalServerErrorException(e.getMessage());
      } catch (Exception e) {
        e.printStackTrace();
        throw new InternalServerErrorException(e.getMessage());
      }
    } else {
      throw new BadRequestException("Email not registered");
    }
  }
}

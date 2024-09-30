package com.alabtaal.library.controller;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.UserPrincipal;
import com.alabtaal.library.model.UserSummary;
import com.alabtaal.library.payload.request.ChangePasswordRequest;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.repo.UserRepo;
import com.alabtaal.library.security.CurrentUser;
import com.alabtaal.library.util.Miscellaneous;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private final UserRepo userRepo;

  private final PasswordEncoder passwordEncoder;

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<ApiResponse<UserSummary>> getCurrentUser(@CurrentUser UserPrincipal currentUser) {

    final Set<String> authorities = currentUser.getAuthorities().stream()
        .map(r -> r.getAuthority()).collect(Collectors.toSet());

    UserSummary userSummary = UserSummary.builder()
        .id(currentUser.getId())
        .name(currentUser.getName())
        .username(currentUser.getUsername())
        .roles(authorities)
        .build();
    return ResponseEntity.ok(
        ApiResponse
            .<UserSummary>builder()
            .success(true)
            .message("getCurrentUser response")
            .entity(userSummary)
            .build()
    );
  }

  @GetMapping("/user/checkUsernameAvailability")
  public ResponseEntity<ApiResponse<Boolean>> checkUsernameAvailability(@RequestParam(value = "username") String username) {
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("checkUsernameAvailability response")
            .entity(!userRepo.existsByUsername(username))
            .build()
    );
  }

  @GetMapping("/user/checkEmailAvailability")
  public ResponseEntity<ApiResponse<Boolean>> checkEmailAvailability(@RequestParam(value = "email") String email) {
    return ResponseEntity.ok(
        ApiResponse
            .<Boolean>builder()
            .success(true)
            .message("checkEmailAvailability response")
            .entity(!userRepo.existsByEmail(email))
            .build()
    );
  }

  @GetMapping("/users/{username}")
  public ResponseEntity<ApiResponse<UserSummary>> getUserProfile(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
    final UserEntity user = userRepo.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    final Set<String> roles = user.getRoles().stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());

    final UserSummary userSummary = UserSummary.builder()
        .id(user.getId())
        .name(user.getName())
        .username(user.getUsername())
        .roles(roles)
        .build();

    return ResponseEntity.ok(
        ApiResponse
            .<UserSummary>builder()
            .success(true)
            .message("getUserProfile response")
            .entity(userSummary)
            .build()
    );
  }

  @PostMapping("/user/changePassword")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse<Boolean>> changeUserPassword(@CurrentUser final UserPrincipal currentUserPrincipal,
      @RequestBody final ChangePasswordRequest request) throws InternalServerErrorException {
    try {
      final UserEntity currentUser = userRepo.findById(currentUserPrincipal.getId())
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserPrincipal.getId()));

      if (StringUtils.isEmpty(request.getCurrentPassword())) {
        throw new BadRequestException("Current password is empty");
      } else if (StringUtils.isEmpty(request.getNewPassword())) {
        throw new BadRequestException("New password is empty");
      } else if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
        throw new BadRequestException("Current password is not same as actual password");
      } else if (request.getCurrentPassword().equals(request.getNewPassword())) {
        throw new BadRequestException("New password is same as current password");
      } else if (request.getNewPassword().length() < 6) {
        throw new BadRequestException("New password does not meet complexity requirements");
      } else {
        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.saveAndFlush(currentUser);
        return ResponseEntity.ok(
            ApiResponse
                .<Boolean>builder()
                .success(true)
                .message("Password changed successfully")
                .entity(true)
                .build()
        );
      }
    } catch (Exception e) {
      Miscellaneous.logException(LOG, e);
      throw new InternalServerErrorException(e.getMessage());
    }
  }
}

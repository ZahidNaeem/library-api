package org.zahid.apps.web.library.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.User;
import org.zahid.apps.web.library.exception.ResourceNotFoundException;
import org.zahid.apps.web.library.model.UserSummary;
import org.zahid.apps.web.library.payload.request.ChangePasswordRequest;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.repo.RoleRepo;
import org.zahid.apps.web.library.repo.UserRepo;
import org.zahid.apps.web.library.security.CurrentUser;
import org.zahid.apps.web.library.security.service.UserPrincipal;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<UserSummary>> getCurrentUser(@CurrentUser UserPrincipal currentUser) {

        final Set<String> authorities = currentUser.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        UserSummary userSummary = UserSummary.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .username(currentUser.getUsername())
                .organizationCode(currentUser.getOrganization().getOrganizationCode())
                .organizationName(currentUser.getOrganization().getOrganizationName())
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
    public ResponseEntity<ApiResponse<UserSummary>> getUserProfile(@PathVariable(value = "username") String username) {
        final User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        final Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        final UserSummary userSummary = UserSummary.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .organizationCode(user.getOrganization().getOrganizationCode())
                .organizationName(user.getOrganization().getOrganizationName())
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
    public ResponseEntity<ApiResponse<Boolean>> changeUserPassword(@CurrentUser final UserPrincipal currentUserPrincipal, @RequestBody final ChangePasswordRequest request) {
        try {
            final User currentUser = userRepo.findById(currentUserPrincipal.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserPrincipal.getId()));
            String currentPassword = null;
            String newPassword = null;
            Boolean isPasswordSame = false;
            if (null != request && StringUtils.isNotEmpty(request.getCurrentPassword())) {
                currentPassword = request.getCurrentPassword();
                newPassword = request.getNewPassword();
                isPasswordSame = passwordEncoder.matches(currentPassword, currentUser.getPassword());
                LOG.debug("Is password same: {}", isPasswordSame);
            }
            if (StringUtils.isEmpty(currentPassword)) {
                throw new IllegalArgumentException("Current password is empty");
            } else if (StringUtils.isEmpty(newPassword)) {
                throw new IllegalArgumentException("New password is empty");
            } else if (!isPasswordSame) {
                throw new IllegalArgumentException("Current password does not match to actual password");
            } else if (currentPassword.equals(newPassword)) {
                throw new IllegalArgumentException("New password is same as current password");
            } else if (newPassword.length() < 6) {
                throw new IllegalArgumentException("New password does not meet complexity requirements");
            } else {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(currentUser);
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("changeUserPassword response")
                                .entity(true)
                                .build()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.ok(
                ApiResponse
                        .<Boolean>builder()
                        .success(false)
                        .message("changeUserPassword response")
                        .entity(false)
                        .build()
        );
    }
}

package org.zahid.apps.web.pos.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.exception.ResourceNotFoundException;
import org.zahid.apps.web.pos.model.UserSummary;
import org.zahid.apps.web.pos.repo.UserRepo;
import org.zahid.apps.web.pos.security.CurrentUser;
import org.zahid.apps.web.pos.security.payload.request.ChangePasswordRequest;
import org.zahid.apps.web.pos.security.service.UserPrincipal;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = UserSummary.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .username(currentUser.getUsername())
                .organizationCode(currentUser.getOrganization().getOrganizationCode())
                .organizationName(currentUser.getOrganization().getOrganizationName())
                .build();
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public Boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return !userRepo.existsByUsername(username);
    }

    @GetMapping("/user/checkEmailAvailability")
    public Boolean checkEmailAvailability(@RequestParam(value = "email") String email) {
        return !userRepo.existsByEmail(email);
    }

    @GetMapping("/users/{username}")
    public UserSummary getUserProfile(@PathVariable(value = "username") String username) {
        final User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        final UserSummary userSummary = UserSummary.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .organizationCode(user.getOrganization().getOrganizationCode())
                .organizationName(user.getOrganization().getOrganizationName())
                .build();

        return userSummary;
    }

    @PostMapping("/user/changePassword")
    @PreAuthorize("hasRole('USER')")
    public Boolean changeUserPassword(@CurrentUser final UserPrincipal currentUserPrincipal, @RequestBody final ChangePasswordRequest request) {
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
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

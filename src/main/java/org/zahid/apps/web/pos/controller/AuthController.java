package org.zahid.apps.web.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zahid.apps.web.pos.entity.Role;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.enumeration.RoleName;
import org.zahid.apps.web.pos.mapper.UserMapper;
import org.zahid.apps.web.pos.model.UserModel;
import org.zahid.apps.web.pos.repo.RoleRepo;
import org.zahid.apps.web.pos.repo.UserRepo;
import org.zahid.apps.web.pos.security.jwt.JwtProvider;
import org.zahid.apps.web.pos.security.payload.request.LoginRequest;
import org.zahid.apps.web.pos.security.payload.request.SignUpRequest;
import org.zahid.apps.web.pos.security.payload.response.ApiResponse;
import org.zahid.apps.web.pos.security.payload.response.JwtAuthenticationResponse;
import org.zahid.apps.web.pos.service.GmailService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private GmailService gmailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = tokenProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating userModel's account
        final UserModel userModel = UserModel.builder()
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .organization(signUpRequest.getOrganization())
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(
                                    "Fail! -> Cause: Role " + RoleName.ROLE_ADMIN.getValue() + " not found."));
                    roles.add(adminRole);
                    break;

                case "pm":
                    Role pmRole = roleRepo.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException(
                                    "Fail! -> Cause: Role " + RoleName.ROLE_PM.getValue() + " not found."));
                    roles.add(pmRole);
                    break;

        /*case "userModel":
          Role userRole = roleRepo.findByName(RoleName.ROLE_USER)
              .orElseThrow(() -> new RuntimeException(
                  "Fail! -> Cause: Role " + RoleName.ROLE_USER.getValue() + " not found."));
          roles.add(userRole);
          break;*/

                default:
                    Role userRole = roleRepo.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(
                                    "Fail! -> Cause: Role " + RoleName.ROLE_USER.getValue() + " not found."));
                    roles.add(userRole);
            }
        });

        userModel.setRoles(roles);

        final User result = userRepo.save(userMapper.toUser(userModel));

        final URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/checkEmailExists")
    public ResponseEntity<?> checkEmailExists(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(userRepo.existsByEmail(email));
    }

    @GetMapping("/recoverPassword")
    public ResponseEntity<?> recoverPassword(@RequestParam(value = "email") String email) {
        final ResponseEntity<?> responseEntity = checkEmailExists(email);

        if (responseEntity != null && responseEntity.getBody().equals(true)) {
            try {
                final boolean mailSent = gmailService.sendMessage("Welcome to Point of Sale Application", "To reset you account password, please click on below link:\nhttp://localhost:3000", email);
                if (mailSent) {
                    return ResponseEntity.ok("Recovery email sent");
                } else {
                    return new ResponseEntity(new ApiResponse(false, "Unknown error occurred"),
                            HttpStatus.BAD_REQUEST);
                }
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
                return new ResponseEntity(new ApiResponse(false, e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(new ApiResponse(false, e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(new ApiResponse(false, "Email not registered"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}

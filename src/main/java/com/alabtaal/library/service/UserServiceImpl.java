package com.alabtaal.library.service;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.UserMapper;
import com.alabtaal.library.payload.request.SignupRequest;
import com.alabtaal.library.repo.UserRepo;
import com.alabtaal.library.util.Miscellaneous;
import com.alabtaal.library.util.RelationshipHandler;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
  private final UserRepo userRepo;
  private final UserMapper userMapper;

  @Override
  public List<UserEntity> findAll() {
    return userRepo.findAll();
  }

  @Override
  public UserEntity findById(final UUID id) throws ResourceNotFoundException {
    return userRepo.findById(id)
        .orElse(null);
  }

  @Override
  public UserEntity findByUsername(final String username) throws ResourceNotFoundException {
    return userRepo.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
  }

  @Override
  public List<UserEntity> findByIdIn(final List<UUID> userIds) {
    return userRepo.findByIdIn(userIds);
  }

  @Override
  public Boolean existsByUsername(final String username) {
    return userRepo.existsByUsername(username);
  }

  @Override
  public Boolean existsByEmail(final String email) {
    return userRepo.existsByEmail(email);
  }

  @Override
  public UserEntity save(final UserEntity entity) throws BadRequestException {
    RelationshipHandler.setParentForChildren(entity);
Miscellaneous.constraintViolation(entity);
    return userRepo.saveAndFlush(entity);
  }

  @Override
  public UserEntity save(final SignupRequest signupRequest) throws BadRequestException {
    Miscellaneous.constraintViolation(signupRequest);

    if (existsByUsername(signupRequest.getUsername())) {
      throw new BadRequestException("Username is already taken!");
    }

    if (existsByEmail(signupRequest.getEmail())) {
      throw new BadRequestException("Email is already taken!");
    }

    // Creating user account
    final UserEntity userEntity = userMapper.toEntity(signupRequest);
    return userRepo.saveAndFlush(userEntity);
  }
}

package com.alabtaal.library.service;


import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.payload.request.SignupRequest;
import java.util.List;
import java.util.UUID;

public interface UserService {

  List<UserEntity> findAll();

  UserEntity findById(final UUID id) throws ResourceNotFoundException;

  UserEntity findByUsername(final String username) throws ResourceNotFoundException;

  List<UserEntity> findByIdIn(final List<UUID> userIds);

  Boolean existsByUsername(final String username);

  Boolean existsByEmail(String email);

  UserEntity save(final UserEntity entity) throws BadRequestException;

  UserEntity save(final SignupRequest signupRequest) throws BadRequestException;
}

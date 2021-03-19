package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.mapper.UserMapper;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.validators.TempRoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;

/** The type User service. */
@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  /**
   * Get all users list.
   *
   * @return the list
   */
  public List<User> getAllUsers() {
    return UserMapper.INSTANCE.toDto(userRepository.findAll());
  }

  /**
   * Gets user by id.
   *
   * @param id the user id
   * @return the user by id
   * @throws Exception the exception
   */
  public User getUserById(Integer id) throws EntityNotFoundException {
    return UserMapper.INSTANCE.toDto(
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found")));
  }

  /**
   * Create user user.
   *
   * @param user the user
   * @return the user
   */
  public User createUser(User user) {
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
    Errors errors = new BeanPropertyBindingResult(userEntity, "userEntity");
    ValidationUtils.invokeValidator(
        new TempRoleValidator(userEntity), userEntity.getRole(), errors);
    if (errors.hasErrors()) {
      throw new ValidationException();
    }
    UserEntity savedUserEntity = userRepository.save(userEntity);
    User userDto = UserMapper.INSTANCE.toDto(savedUserEntity);
    return userDto;
    // return UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(user)));
  }

  /**
   * Update user response entity.
   *
   * @param id the user id
   * @param userDetails the user details
   * @return user
   * @throws Exception the exception
   */
  public User updateUser(Integer id, User userDetails) throws EntityNotFoundException {
    User user =
        UserMapper.INSTANCE.toDto(
            userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found")));

    if (userDetails.getName() != null) {
      user.setName(userDetails.getName());
    }

    if (userDetails.getEmail() != null) {
      user.setEmail(userDetails.getEmail());
    }

    if (userDetails.getPassword() != null) {
      user.setPassword(userDetails.getPassword());
    }

    if (userDetails.getRole() != null) {
      user.setRole(userDetails.getRole());
    }

    return UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(user)));
  }

  /**
   * Delete user map.
   *
   * @param id the user id
   * @return
   * @throws Exception the exception
   */
  public void deleteUser(Integer id) throws EntityNotFoundException {
    userRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
    userRepository.deleteById(id);
  }
}

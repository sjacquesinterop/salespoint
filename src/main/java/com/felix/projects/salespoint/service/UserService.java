package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import com.felix.projects.salespoint.mapper.UserMapper;
import com.felix.projects.salespoint.mapper.WishlistMapper;
import com.felix.projects.salespoint.repository.RoleRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.repository.WishlistRepository;
import com.felix.projects.salespoint.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/** The type User service. */
@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private WishlistRepository wishlistRepository;

  @Autowired private ItemService itemService;

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
   * @throws EntityNotFoundException the exception
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
    UserEntity newUser = UserMapper.INSTANCE.toEntity(user);
    Errors errors = new BeanPropertyBindingResult(newUser, "userEntity");
    ValidationUtils.invokeValidator(new RoleValidator(roleRepository), newUser.getRole(), errors);
    if (errors.hasErrors()) {
      throw new CustomValidationException("Error creating the user : Role invalid.", errors);
    }
    UserEntity savedUserEntity = userRepository.save(newUser);
    return UserMapper.INSTANCE.toDto(savedUserEntity);
  }

  /**
   * Update user response entity.
   *
   * @param id the user id
   * @param userDetails the user details
   * @return user
   * @throws EntityNotFoundException the exception
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
   * @throws EntityNotFoundException the exception
   */
  public void deleteUser(Integer id) throws EntityNotFoundException {
    UserEntity userEntity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

    List<ItemEntity> listOfItems = userEntity.getListOfItems();

    if (listOfItems.size() > 0) {
      for (ItemEntity item : listOfItems) { // TODO CHANGE THIS TO NOT USE DELETEITEM
        itemService.deleteItem(item.getId());
      }
    }

    if (wishlistRepository.findWishListEntityByIdUserIdEquals(id).size() > 0) {

      List<WishListEntity> list = wishlistRepository.findWishListEntityByIdUserIdEquals(id);

      for (WishListEntity wishListEntity : list) {

        WishListId currentId = WishlistMapper.INSTANCE.toId(wishListEntity);
        wishlistRepository.deleteById(currentId);
      }
    }
    userRepository.deleteById(id);
  }
}

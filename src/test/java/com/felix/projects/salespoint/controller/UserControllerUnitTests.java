package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.mapper.UserMapper;
import com.felix.projects.salespoint.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/** The type User controller unit tests. */
@ActiveProfiles("userControllerTest")
@RunWith(MockitoJUnitRunner.class)
public class UserControllerUnitTests {

  private final UserEntity testUser1 = new UserEntity();
  private final UserEntity testUser2 = new UserEntity();
  private final UserEntity testUser3 = new UserEntity();
  private final RoleEntity role = new RoleEntity();
  /** The User controller. */
  @InjectMocks UserController userController;

  /** The User service. */
  @Mock UserService userService;

  /** Init. */
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    role.setId(1);

    testUser1.setId(1);
    testUser1.setName("Legolas");
    testUser1.setEmail("elf@gmail.com");
    testUser1.setPassword("!Password123");
    testUser1.setRole(role);

    testUser2.setId(2);
    testUser2.setName("Gimli");
    testUser2.setEmail("dwarf@gmail.com");
    testUser2.setPassword("!Password123");
    testUser2.setRole(role);

    testUser3.setId(3);
    testUser3.setName("Aragorn");
    testUser3.setEmail("human@gmail.com");
    testUser3.setPassword("!Password123");
    testUser3.setRole(role);
  }

  /** Gets all users test. */
  @Test
  public void getAllUsersTest() {

    List<UserEntity> list = new ArrayList<>();

    list.add(testUser1);
    list.add(testUser2);
    list.add(testUser3);

    when(userService.getAllUsers()).thenReturn(UserMapper.INSTANCE.toDto(list));

    ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().size(), list.size());
  }

  /** Gets user by id test. */
  @Test
  public void getUserByIdTest() {

    when(userService.getUserById(2)).thenReturn(UserMapper.INSTANCE.toDto(testUser2));

    ResponseEntity<User> responseEntity = userController.getUserById(2);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().getId(), testUser2.getId());
  }

  /** Create user test. */
  @Test
  public void createUserTest() {
    User user = UserMapper.INSTANCE.toDto(testUser2);

    when(userService.createUser(user)).thenReturn(user);

    ResponseEntity<User> responseEntity = userController.createUser(user);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CREATED.value());
    assertEquals(responseEntity.getBody().getId(), testUser2.getId());
  }

  /** Update user test. */
  @Test
  public void updateUserTest() {
    String updatedName = "Frodo";
    String updatedPassword = "Sauron$ucks123";
    String updatedEmail = "hobbit@gmail.com";
    RoleEntity newRole = new RoleEntity();
    newRole.setId(2);

    User tempUser = new User();
    tempUser.setName(updatedName);
    tempUser.setPassword(updatedPassword);
    tempUser.setEmail(updatedEmail);
    tempUser.setRole(2);

    testUser2.setName(updatedName);
    testUser2.setName(updatedName);
    testUser2.setPassword(updatedPassword);
    testUser2.setEmail(updatedEmail);
    testUser2.setRole(newRole);

    when(userService.updateUser(2, tempUser)).thenReturn(UserMapper.INSTANCE.toDto(testUser2));

    ResponseEntity<User> responseEntity = userController.updateUser(2, tempUser);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().getName(), updatedName);
    assertEquals(responseEntity.getBody().getId(), testUser2.getId());
    assertEquals(responseEntity.getBody().getPassword(), updatedPassword);
    assertEquals(responseEntity.getBody().getEmail(), updatedEmail);
    assertEquals(responseEntity.getBody().getRole(), newRole.getId());
  }

  @Test
  public void deleteUserTest() {
    ResponseEntity<Map<String, Boolean>> responseEntity = userController.deleteUser(2);

    verify(userService, times(1)).deleteUser(2);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
  }
}

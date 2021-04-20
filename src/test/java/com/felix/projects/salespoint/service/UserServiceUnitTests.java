package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import com.felix.projects.salespoint.mapper.UserMapper;
import com.felix.projects.salespoint.repository.RoleRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.repository.WishlistRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ActiveProfiles("userTest")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTests {

  private final UserEntity testUser1 = new UserEntity();
  private final UserEntity testUser2 = new UserEntity();
  private final UserEntity testUser3 = new UserEntity();
  private final RoleEntity role = new RoleEntity();

  @InjectMocks private UserService userService;
  @Mock private UserRepository userRepository;
  @Mock private RoleRepository roleRepository;
  @Mock private WishlistRepository wishlistRepository;

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

  @Test
  public void getAllUsersTest() {

    List<UserEntity> list = new ArrayList<>();

    list.add(testUser1);
    list.add(testUser2);
    list.add(testUser3);

    when(userRepository.findAll()).thenReturn(list);

    List<User> empList = userService.getAllUsers();

    assertNotNull(empList);
    assertEquals(3, empList.size());

    verify(userRepository, times(1)).findAll();
  }

  @Test
  public void getUserByIdTest() {

    when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser1));

    User retrievedUser = userService.getUserById(1);

    assertNotNull(retrievedUser);
    assertEquals((Integer) 1, retrievedUser.getId());
  }

  @Test
  public void createUserTest() {

    User user = UserMapper.INSTANCE.toDto(testUser1);

    when(roleRepository.existsById(role.getId())).thenReturn(Boolean.TRUE);

    when(userRepository.save(any(UserEntity.class))).thenReturn(testUser1);

    User createdUser = userService.createUser(user);

    assertNotNull(createdUser);
    assertEquals(createdUser.getName(), testUser1.getName());
  }

  @Test(expected = CustomValidationException.class)
  public void createNotValidUserTest() {

    User badUser = new User();

    userService.createUser(badUser);
  }

  @Test
  public void updateUserTest() {
    String updatedName = "Bilbon";
    String updatedPassword = "Sauron$ucks123";
    String updatedEmail = "hobbit@gmail.com";
    RoleEntity newRole = new RoleEntity();
    newRole.setId(2);

    User tempUser = new User();
    tempUser.setName(updatedName);
    tempUser.setName(updatedName);
    tempUser.setPassword(updatedPassword);
    tempUser.setEmail(updatedEmail);
    tempUser.setRole(2);

    testUser1.setName(updatedName);
    testUser1.setName(updatedName);
    testUser1.setName(updatedName);
    testUser1.setPassword(updatedPassword);
    testUser1.setEmail(updatedEmail);
    testUser1.setRole(newRole);

    when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser1));

    when(userRepository.save(any(UserEntity.class))).thenReturn(testUser1);

    User updatedUser = userService.updateUser(1, tempUser);

    assertNotNull(updatedUser);
    assertEquals(updatedUser.getName(), updatedName);
    assertEquals(updatedUser.getEmail(), updatedEmail);
    assertEquals(updatedUser.getPassword(), updatedPassword);
    assertEquals(updatedUser.getRole(), newRole.getId());
    assertEquals(updatedUser.getId(), testUser1.getId());
  }

  @Test
  public void deleteUserTest() {

    List<ItemEntity> itemList = new ArrayList<>();

    testUser1.setListOfItems(itemList);

    when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser1));

    List<WishListEntity> list = new ArrayList<>();

    when(wishlistRepository.findWishListEntityByIdUserIdEquals(1)).thenReturn(list);

    userService.deleteUser(1);

    verify(userRepository, times(1)).deleteById(1);
  }
}

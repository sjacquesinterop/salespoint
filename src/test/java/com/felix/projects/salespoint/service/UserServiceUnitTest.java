package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.mapper.UserMapper;
import com.felix.projects.salespoint.repository.RoleRepository;
import com.felix.projects.salespoint.repository.UserRepository;
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
public class UserServiceUnitTest { // TODO add at least a test per method

  @InjectMocks private UserService userService;

  @Mock private UserRepository userRepository;

  @Mock private RoleRepository roleRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void
      getAllUsersTest() { // TODO kinda confused with the types and shit so not sure if this is
    // right

    UserEntity testUser1 = new UserEntity();
    UserEntity testUser2 = new UserEntity();
    UserEntity testUser3 = new UserEntity();

    RoleEntity role = new RoleEntity();
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

    UserEntity testUser1 = new UserEntity();

    RoleEntity role = new RoleEntity();
    role.setId(1);

    testUser1.setId(1);
    testUser1.setName("Legolas");
    testUser1.setEmail("elf@gmail.com");
    testUser1.setPassword("!Password123");
    testUser1.setRole(role);

    when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser1));

    User retrievedUser = userService.getUserById(1);

    assertNotNull(retrievedUser);
    assertEquals((Integer) 1, retrievedUser.getId());
  }

  @Test
  public void
      createUserTest() { // TODO I'm cheesing this waaaay too fucking hard, need to look more into
    // it

    UserEntity testUser1 = new UserEntity();

    RoleEntity role = new RoleEntity();
    role.setId(1);

    testUser1.setId(1);
    testUser1.setName("Legolas");
    testUser1.setEmail("elf@gmail.com");
    testUser1.setPassword("!Password123");
    testUser1.setRole(role);

    User user = UserMapper.INSTANCE.toDto(testUser1);

    when(roleRepository.existsById(role.getId())).thenReturn(Boolean.TRUE);

    when(userRepository.save(any(UserEntity.class))).thenReturn(testUser1);

    User createdUser = userService.createUser(user);

    assertNotNull(createdUser);
    assertEquals(createdUser.getName(), testUser1.getName());
  }

  @Test
  public void updateUserTest() {

    UserEntity testUser1 = new UserEntity();

    RoleEntity role = new RoleEntity();
    role.setId(1);

    testUser1.setId(1);
    testUser1.setName("Legolas");
    testUser1.setEmail("elf@gmail.com");
    testUser1.setPassword("!Password123");
    testUser1.setRole(role);

    User tempUser = new User();
    tempUser.setName("Bilbon");

    when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser1));

    User updatedUser =
        userService.updateUser(1, tempUser); // TODO find out why tf this returns null

    assertNotNull(updatedUser);
    assertEquals(updatedUser.getName(), "Bilbon");
  }
}

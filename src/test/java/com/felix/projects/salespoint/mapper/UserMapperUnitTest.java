package com.felix.projects.salespoint.mapper;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("userMapperTest")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = UserMapperImpl.class)
public class UserMapperUnitTest {

  private final UserEntity testUser1 = new UserEntity();
  private final User testUser2 = new User();
  private final UserEntity testUser3 = new UserEntity();
  private final RoleEntity role = new RoleEntity();
  @Autowired public UserMapper userMapper = new UserMapperImpl();

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
    testUser2.setRole(1);

    testUser3.setId(3);
    testUser3.setName("Aragorn");
    testUser3.setEmail("human@gmail.com");
    testUser3.setPassword("!Password123");
    testUser3.setRole(role);
  }

  @Test
  public void userToDtoTest() {

    User returnedUser = userMapper.toDto(testUser1);

    assertNotNull(returnedUser);
    assertEquals(testUser1.getId(), returnedUser.getId());
    assertEquals(testUser1.getName(), returnedUser.getName());
    assertEquals(testUser1.getEmail(), returnedUser.getEmail());
    assertEquals(testUser1.getPassword(), returnedUser.getPassword());
    assertEquals(testUser1.getRole().getId(), returnedUser.getRole());
  }

  @Test
  public void userToEntityTest() {

    UserEntity returnedEntity = userMapper.toEntity(testUser2);

    assertNotNull(returnedEntity);
    assertEquals(testUser2.getId(), returnedEntity.getId());
    assertEquals(testUser2.getName(), returnedEntity.getName());
    assertEquals(testUser2.getEmail(), returnedEntity.getEmail());
    assertEquals(testUser2.getPassword(), returnedEntity.getPassword());
    assertEquals(testUser2.getRole(), returnedEntity.getRole().getId());
  }

  @Test
  public void listToDtoTest() {
    List<UserEntity> list = new ArrayList<>();

    list.add(testUser1);
    list.add(testUser3);

    List<User> retrievedList = userMapper.toDto(list);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.size(), 2);
    assertEquals(retrievedList.get(0).getId(), testUser1.getId());
    assertEquals(retrievedList.get(0).getPassword(), testUser1.getPassword());
    assertEquals(retrievedList.get(0).getRole(), testUser1.getRole().getId());
    assertEquals(retrievedList.get(0).getEmail(), testUser1.getEmail());
    assertEquals(retrievedList.get(0).getName(), testUser1.getName());

    assertEquals(retrievedList.get(1).getId(), testUser3.getId());
    assertEquals(retrievedList.get(1).getPassword(), testUser3.getPassword());
    assertEquals(retrievedList.get(1).getRole(), testUser3.getRole().getId());
    assertEquals(retrievedList.get(1).getEmail(), testUser3.getEmail());
    assertEquals(retrievedList.get(1).getName(), testUser3.getName());
  }

  @Test
  public void listToEntityTest() {
    List<User> list = new ArrayList<>();

    list.add(testUser2);

    List<UserEntity> retrievedList = userMapper.toEntity(list);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.size(), 1);
    assertEquals(retrievedList.get(0).getId(), testUser2.getId());
    assertEquals(retrievedList.get(0).getPassword(), testUser2.getPassword());
    assertEquals(retrievedList.get(0).getRole().getId(), testUser2.getRole());
    assertEquals(retrievedList.get(0).getEmail(), testUser2.getEmail());
    assertEquals(retrievedList.get(0).getName(), testUser2.getName());
  }
}

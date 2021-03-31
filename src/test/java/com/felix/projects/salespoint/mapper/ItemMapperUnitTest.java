package com.felix.projects.salespoint.mapper;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
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

@ActiveProfiles("itemMapperTest")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = ItemMapperImpl.class)
public class ItemMapperUnitTest {

  private final ItemEntity testItem1 = new ItemEntity();
  private final Item testItem2 = new Item();
  private final ItemEntity testItem3 = new ItemEntity();
  private final RoleEntity role = new RoleEntity();

  @Autowired public ItemMapper itemMapper = new ItemMapperImpl();

  @Before
  public void init() {

    MockitoAnnotations.initMocks(this);

    role.setId(1);

    UserEntity user = new UserEntity();
    user.setId(1);
    user.setName("Goku");
    user.setPassword("!Password123");
    user.setEmail("dragonballz@gmail.com");
    user.setRole(role);

    testItem1.setName("dragon ball 1");
    testItem1.setDescription("One of the dragon balls!");
    testItem1.setId(1);
    testItem1.setOwner(user);
    testItem1.setPrice(10.0000f);
    testItem1.setStock(9);

    testItem2.setName("dragon ball 2");
    testItem2.setDescription("One of the dragon balls!");
    testItem2.setId(2);
    testItem2.setOwner(1);
    testItem2.setPrice(10.0000f);
    testItem2.setStock(9);

    testItem3.setName("dragon ball 3");
    testItem3.setDescription("One of the dragon balls!");
    testItem3.setId(3);
    testItem3.setOwner(user);
    testItem3.setPrice(10.0000f);
    testItem3.setStock(9);
  }

  @Test
  public void itemToDtoTest() {

    Item returnedItem = itemMapper.toDto(testItem1);

    assertNotNull(returnedItem);
    assertEquals(testItem1.getId(), returnedItem.getId());
    assertEquals(testItem1.getName(), returnedItem.getName());
    assertEquals(testItem1.getOwner().getId(), returnedItem.getOwner());
    assertEquals(testItem1.getDescription(), returnedItem.getDescription());
    assertEquals(testItem1.getPrice(), returnedItem.getPrice());
    assertEquals(testItem1.getStock(), returnedItem.getStock());
  }

  @Test
  public void itemToEntityTest() {

    ItemEntity returnedItem = itemMapper.toEntity(testItem2);

    assertNotNull(returnedItem);
    assertEquals(testItem2.getId(), returnedItem.getId());
    assertEquals(testItem2.getName(), returnedItem.getName());
    assertEquals(testItem2.getOwner(), returnedItem.getOwner().getId());
    assertEquals(testItem2.getDescription(), returnedItem.getDescription());
    assertEquals(testItem2.getPrice(), returnedItem.getPrice());
    assertEquals(testItem2.getStock(), returnedItem.getStock());
  }

  @Test
  public void listToDtoTest() {
    List<ItemEntity> list = new ArrayList<>();

    list.add(testItem1);

    List<Item> retrievedList = itemMapper.toDto(list);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.size(), 1);
    assertEquals(retrievedList.get(0).getId(), testItem1.getId());
    assertEquals(retrievedList.get(0).getPrice(), testItem1.getPrice());
    assertEquals(retrievedList.get(0).getStock(), testItem1.getStock());
    assertEquals(retrievedList.get(0).getOwner(), testItem1.getOwner().getId());
    assertEquals(retrievedList.get(0).getDescription(), testItem1.getDescription());
    assertEquals(retrievedList.get(0).getName(), testItem1.getName());
  }

  @Test
  public void listToEntityTest() {
    List<Item> list = new ArrayList<>();

    list.add(testItem2);

    List<ItemEntity> retrievedList = itemMapper.toEntity(list);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.size(), 1);
    assertEquals(retrievedList.get(0).getId(), testItem2.getId());
    assertEquals(retrievedList.get(0).getPrice(), testItem2.getPrice());
    assertEquals(retrievedList.get(0).getStock(), testItem2.getStock());
    assertEquals(retrievedList.get(0).getOwner().getId(), testItem2.getOwner());
    assertEquals(retrievedList.get(0).getDescription(), testItem2.getDescription());
    assertEquals(retrievedList.get(0).getName(), testItem2.getName());
  }
}

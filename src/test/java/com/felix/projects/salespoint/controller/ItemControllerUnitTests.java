package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.mapper.ItemMapper;
import com.felix.projects.salespoint.service.ItemService;
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

@ActiveProfiles("itemControllerTest")
@RunWith(MockitoJUnitRunner.class)
public class ItemControllerUnitTests {

  private final ItemEntity testItem1 = new ItemEntity();
  private final ItemEntity testItem2 = new ItemEntity();
  private final ItemEntity testItem3 = new ItemEntity();
  private final RoleEntity role = new RoleEntity();

  @InjectMocks private ItemController itemController;

  @Mock private ItemService itemService;

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
    testItem2.setOwner(user);
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
  public void getAllItemsTest() {
    List<ItemEntity> list = new ArrayList<>();

    list.add(testItem1);
    list.add(testItem2);
    list.add(testItem3);

    when(itemService.getAllItems()).thenReturn(ItemMapper.INSTANCE.toDto(list));

    ResponseEntity<List<Item>> responseEntity = itemController.getAllItems();

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.ACCEPTED.value());
    assertEquals(responseEntity.getBody().size(), list.size());
  }

  @Test
  public void getItemByIdTest() {

    when(itemService.getItemById(2)).thenReturn(ItemMapper.INSTANCE.toDto(testItem2));

    ResponseEntity<Item> responseEntity = itemController.getItemById(2);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().getId(), testItem2.getId());
  }

  @Test
  public void createItemTest() {
    Item item = ItemMapper.INSTANCE.toDto(testItem2);

    when(itemService.createItem(item)).thenReturn(item);

    ResponseEntity<Item> responseEntity = itemController.createItem(item);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CREATED.value());
    assertEquals(responseEntity.getBody().getId(), testItem2.getId());
  }

  @Test
  public void updateItemTest() {
    String updatedName = "4th dragon ball!";
    String updateDescription = "A more powerful version!";

    role.setId(0);
    UserEntity newUser = new UserEntity();
    newUser.setId(2);
    newUser.setName("Jiren");
    newUser.setPassword("Goku$ucks444");
    newUser.setEmail("Power@gmail.com");
    newUser.setRole(role);

    Integer updatedStock = 4;
    Float updatedPrice = 1f;

    Item tempItem = new Item();
    tempItem.setName(updatedName);
    tempItem.setDescription(updateDescription);
    tempItem.setOwner(2);
    tempItem.setPrice(updatedPrice);
    tempItem.setStock(updatedStock);

    testItem2.setName(updatedName);
    testItem2.setName(updatedName);
    testItem2.setDescription(updateDescription);
    testItem2.setOwner(newUser);
    testItem2.setPrice(updatedPrice);
    testItem2.setStock(updatedStock);

    when(itemService.updateItem(2, tempItem)).thenReturn(ItemMapper.INSTANCE.toDto(testItem2));

    ResponseEntity<Item> responseEntity = itemController.updateItem(2, tempItem);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().getName(), updatedName);
    assertEquals(responseEntity.getBody().getId(), testItem2.getId());
    assertEquals(responseEntity.getBody().getDescription(), updateDescription);
    assertEquals(responseEntity.getBody().getOwner(), newUser.getId());
    assertEquals(responseEntity.getBody().getPrice(), updatedPrice);
    assertEquals(responseEntity.getBody().getStock(), updatedStock);
  }

  @Test
  public void deleteItemTest() {

    ResponseEntity<Map<String, Boolean>> responseEntity = itemController.deleteItem(1);

    verify(itemService, times(1)).deleteItem(1);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
  }
}

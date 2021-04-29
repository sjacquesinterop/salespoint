package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import com.felix.projects.salespoint.mapper.ItemMapper;
import com.felix.projects.salespoint.repository.ItemRepository;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("itemTest")
@RunWith(MockitoJUnitRunner.class)
public class ItemServiceUnitTests {

  private final ItemEntity testItem1 = new ItemEntity();
  private final ItemEntity testItem2 = new ItemEntity();
  private final ItemEntity testItem3 = new ItemEntity();
  private final RoleEntity role = new RoleEntity();
  @Mock private ItemRepository itemRepository;
  @Mock private UserRepository userRepository;
  @Mock private WishlistRepository wishlistRepository;
  @InjectMocks private ItemService itemService;

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

    when(itemRepository.findAll()).thenReturn(list);

    List<Item> tempList = itemService.getAllItems();

    assertNotNull(tempList);
    assertEquals(3, tempList.size());

    verify(itemRepository, times(1)).findAll();
  }

  @Test
  public void getItemByIdTest() {

    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));

    Item retrievedItem = itemService.getItemById(1);

    assertNotNull(retrievedItem);
    assertEquals((Integer) 1, retrievedItem.getId());
  }

  @Test
  public void createItemTest() {

    Item item = ItemMapper.INSTANCE.toDto(testItem1);

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);

    when(itemRepository.save(any(ItemEntity.class))).thenReturn(testItem1);

    Item createdItem = itemService.createItem(item);

    assertNotNull(createdItem);
    assertEquals(createdItem.getName(), testItem1.getName());
  }

  @Test(expected = CustomValidationException.class)
  public void createNotValidItemTest() {

    Item badItem = new Item();

    itemService.createItem(badItem);
  }

  @Test
  public void updateItemTest() {
    String updatedName = "2nd dragon ball!";

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

    testItem1.setName(updatedName);
    testItem1.setName(updatedName);
    testItem1.setName(updatedName);
    testItem1.setDescription(updateDescription);
    testItem1.setOwner(newUser);
    testItem1.setPrice(updatedPrice);
    testItem1.setStock(updatedStock);

    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));

    when(itemRepository.save(any(ItemEntity.class))).thenReturn(testItem1);

    Item updatedItem = itemService.updateItem(1, tempItem);

    assertNotNull(updatedItem);
    assertEquals(updatedItem.getName(), updatedName);
    assertEquals(updatedItem.getDescription(), updateDescription);
    assertEquals(updatedItem.getOwner(), newUser.getId());
    assertEquals(updatedItem.getStock(), updatedStock);
    assertEquals(updatedItem.getPrice(), updatedPrice);
    assertEquals(updatedItem.getId(), testItem1.getId());
  }

  @Test
  public void deleteItemTest() {

    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));

    List<WishListEntity> list = new ArrayList<>();

    when(wishlistRepository.findWishListEntityByIdItemIdEquals(1)).thenReturn(list);

    itemService.deleteItem(1);

    verify(itemRepository, times(1)).deleteById(1);
  }

  @Test
  public void NiceTest() {
    assertTrue(true);
  }
}

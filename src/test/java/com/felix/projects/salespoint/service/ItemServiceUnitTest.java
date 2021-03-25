package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.mapper.ItemMapper;
import com.felix.projects.salespoint.repository.ItemRepository;
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

@ActiveProfiles("itemTest")
@RunWith(MockitoJUnitRunner.class)
public class ItemServiceUnitTest {

  @Mock private ItemRepository itemRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private ItemService itemService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAllItemsTest() {

    ItemEntity testItem1 = new ItemEntity();
    ItemEntity testItem2 = new ItemEntity();
    ItemEntity testItem3 = new ItemEntity();

    RoleEntity role = new RoleEntity();
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

    testItem2.setName("dragon ball 1");
    testItem2.setDescription("One of the dragon balls!");
    testItem2.setId(2);
    testItem2.setOwner(user);
    testItem2.setPrice(10.0000f);
    testItem2.setStock(9);

    testItem3.setName("dragon ball 1");
    testItem3.setDescription("One of the dragon balls!");
    testItem3.setId(3);
    testItem3.setOwner(user);
    testItem3.setPrice(10.0000f);
    testItem3.setStock(9);

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

    ItemEntity testItem1 = new ItemEntity();

    RoleEntity role = new RoleEntity();
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

    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));

    Item retrievedItem = itemService.getItemById(1);

    assertNotNull(retrievedItem);
    assertEquals((Integer) 1, retrievedItem.getId());
  }

  @Test
  public void
      createItemTest() { // TODO I'm cheesing this waaaay too fucking hard, need to look more into
    // it

    ItemEntity testItem1 = new ItemEntity();

    RoleEntity role = new RoleEntity();
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

    Item item = ItemMapper.INSTANCE.toDto(testItem1);

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);

    when(itemRepository.save(any(ItemEntity.class))).thenReturn(testItem1);

    Item createdItem = itemService.createItem(item); // TODO currently returns null

    assertNotNull(createdItem);
    assertEquals(createdItem.getName(), testItem1.getName());
  }

  @Test
  public void updateItemTest() {

    ItemEntity testItem1 = new ItemEntity();

    RoleEntity role = new RoleEntity();
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

    Item tempItem = new Item();
    tempItem.setName("2nd dragon ball!");

    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));

    Item updatedItem =
        itemService.updateItem(
            1, tempItem); // TODO need to find a way to mock correctly the repositories

    assertNotNull(updatedItem);
    assertEquals(updatedItem.getName(), "2nd dragon ball!");
  }
}

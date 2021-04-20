package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.dto.WishList;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import com.felix.projects.salespoint.mapper.WishlistMapper;
import com.felix.projects.salespoint.service.WishlistService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ActiveProfiles("wishListControllerTest")
@RunWith(MockitoJUnitRunner.class)
public class WishListControllerUnitTests {

  private final WishListEntity testList1 = new WishListEntity();
  private final WishListEntity testList2 = new WishListEntity();
  private final WishListEntity testList3 = new WishListEntity();
  private final WishListId wishListId1 = new WishListId();
  private final WishListId wishListId2 = new WishListId();
  private final WishListId wishListId3 = new WishListId();
  @InjectMocks WishListController wishListController;
  @Mock WishlistService wishlistService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    wishListId1.setItemId(1);
    wishListId1.setUserId(1);

    wishListId2.setItemId(2);
    wishListId2.setUserId(2);

    wishListId3.setItemId(3);
    wishListId3.setUserId(3);

    testList1.setId(wishListId1);
    testList2.setId(wishListId2);
    testList3.setId(wishListId3);
  }

  @Test
  public void getAllWishListsTest() {

    List<WishListEntity> list = new ArrayList<>();

    list.add(testList1);
    list.add(testList2);
    list.add(testList3);

    when(wishlistService.getAllWishLists()).thenReturn(WishlistMapper.INSTANCE.toDto(list));

    ResponseEntity<List<WishList>> responseEntity = wishListController.getAllWishLists();

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().size(), list.size());
  }

  @Test
  public void getAllItemsWishedByUser() {
    Item item1 = new Item();

    item1.setId(1);

    List<Item> items = new ArrayList<>();

    items.add(item1);

    when(wishlistService.getAllItemsWishedByUser(1)).thenReturn(items);

    ResponseEntity<List<Item>> responseEntity = wishListController.getAllItemsWishedByUser(1);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().get(0).getId(), item1.getId());
  }

  @Test
  public void getAllUserWishingForItem() {
    User user1 = new User();

    user1.setId(1);

    List<User> list = new ArrayList<>();
    list.add(user1);

    when(wishlistService.getAllUserWishingForItem(1)).thenReturn(list);

    ResponseEntity<List<User>> responseEntity = wishListController.getAllUserWishingForItem(1);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value());
    assertEquals(responseEntity.getBody().get(0).getId(), user1.getId());
  }

  @Test
  public void addToWishListTest() {
    WishList wishList = WishlistMapper.INSTANCE.toDto(testList1);

    when(wishlistService.addToWishList(wishList)).thenReturn(wishList);

    ResponseEntity<WishList> responseEntity = wishListController.addToWishList(wishList);

    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CREATED.value());
    assertEquals(responseEntity.getBody().getUser(), testList1.getId().getUserId());
    assertEquals(responseEntity.getBody().getItem(), testList1.getId().getItemId());
  }

  @Test
  public void removeFromWishList() {
    ResponseEntity<Map<String, Boolean>> responseEntity =
        wishListController.removeFromWishList(wishListId3);

    verify(wishlistService, times(1)).removeFromWishList(wishListId3);
    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode().value(), HttpStatus.NO_CONTENT.value());
  }
}

package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.dto.WishList;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import com.felix.projects.salespoint.mapper.WishlistMapper;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ActiveProfiles("wishListServiceTest")
@RunWith(MockitoJUnitRunner.class)
public class WishListServiceUnitTest {

  private final WishListEntity testList1 = new WishListEntity();
  private final WishListEntity testList2 = new WishListEntity();
  private final WishListEntity testList3 = new WishListEntity();
  private final WishList testList4 = new WishList();
  private final WishListId wishListId1 = new WishListId();
  private final WishListId wishListId2 = new WishListId();
  private final WishListId wishListId3 = new WishListId();
  @InjectMocks private WishlistService wishlistService;
  @Mock private WishlistRepository wishlistRepository;
  @Mock private UserRepository userRepository;
  @Mock private ItemRepository itemRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    wishListId1.setItemId(1);
    wishListId1.setUserId(1);

    wishListId2.setItemId(2);
    wishListId2.setUserId(2);

    wishListId3.setItemId(3);
    wishListId3.setUserId(3);

    testList4.setItem(1);
    testList4.setUser(1);

    testList1.setId(wishListId1);
    testList2.setId(wishListId2);
    testList3.setId(wishListId3);
  }

  @Test
  public void getAllWWishListsTest() {

    List<WishListEntity> list = new ArrayList<>();

    list.add(testList1);
    list.add(testList2);
    list.add(testList3);

    when(wishlistRepository.findAll()).thenReturn(list);

    List<WishList> returnedList = wishlistService.getAllWishLists();

    assertNotNull(returnedList);
    assertEquals(3, returnedList.size());
    verify(wishlistRepository, times(1)).findAll();
  }

  @Test
  public void getAllItemsWishedByUserTest() {
    List<WishListEntity> list = new ArrayList<>();

    ItemEntity wishedItem = new ItemEntity();

    wishedItem.setId(1);

    list.add(testList1);

    when(wishlistRepository.findWishListEntityByIdUserIdEquals(1)).thenReturn(list);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(wishedItem));

    List<Item> retrievedList = wishlistService.getAllItemsWishedByUser(1);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.get(0).getId(), wishedItem.getId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void getAllItemsWishedByUserButDoesntExist() {

    List<WishListEntity> list = new ArrayList<>();

    ItemEntity wishedItem = new ItemEntity();

    wishedItem.setId(1);

    list.add(testList1);

    when(wishlistRepository.findWishListEntityByIdUserIdEquals(1)).thenReturn(list);

    wishlistService.getAllItemsWishedByUser(1);
  }

  @Test
  public void getAllUserWishingForItemTest() {
    List<WishListEntity> list = new ArrayList<>();

    UserEntity user = new UserEntity();

    user.setId(1);

    list.add(testList1);

    when(wishlistRepository.findWishListEntityByIdItemIdEquals(1)).thenReturn(list);
    when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));

    List<User> retrievedList = wishlistService.getAllUserWishingForItem(1);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.get(0).getId(), user.getId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void getAllUserWishingForItemButDoesntExist() {

    List<WishListEntity> list = new ArrayList<>();

    UserEntity user = new UserEntity();

    user.setId(1);

    list.add(testList1);

    when(wishlistRepository.findWishListEntityByIdItemIdEquals(1)).thenReturn(list);
    wishlistService.getAllUserWishingForItem(1);
  }

  @Test
  public void addToWishListTest() {

    WishList wishList = WishlistMapper.INSTANCE.toDto(testList1);

    ItemEntity item = new ItemEntity();

    UserEntity userEntity = new UserEntity();
    userEntity.setId(2);

    item.setId(1);
    item.setStock(5);
    item.setOwner(userEntity);

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(item));
    when(wishlistRepository.save(any(WishListEntity.class))).thenReturn(testList1);

    WishList returnedWishList = wishlistService.addToWishList(wishList);

    assertNotNull(returnedWishList);
    assertEquals(returnedWishList.getItem(), testList1.getId().getItemId());
    assertEquals(returnedWishList.getUser(), testList1.getId().getUserId());
  }

  @Test(expected = CustomValidationException.class)
  public void addANotValidWishListTest() {

    WishList wishList = WishlistMapper.INSTANCE.toDto(testList1);

    ItemEntity item = new ItemEntity();

    UserEntity userEntity = new UserEntity();
    userEntity.setId(2);

    item.setId(1);
    item.setStock(5);
    item.setOwner(userEntity);

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.FALSE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(item));

    wishlistService.addToWishList(wishList);
  }

  @Test
  public void removeWishListTest() {

    ItemEntity item = new ItemEntity();

    UserEntity userEntity = new UserEntity();
    userEntity.setId(2);

    item.setId(1);
    item.setStock(5);
    item.setOwner(userEntity);

    when(wishlistRepository.findById(wishListId1)).thenReturn(java.util.Optional.of(testList1));
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(item));

    wishlistService.removeFromWishList(wishListId1);

    verify(wishlistRepository, times(1)).deleteById(wishListId1);
    verify(itemRepository, times(1)).save(item);
  }

  @Test(expected = EntityNotFoundException.class)
  public void removeWishListTestItemNotFound() {
    ItemEntity item = new ItemEntity();

    UserEntity userEntity = new UserEntity();
    userEntity.setId(2);

    item.setId(1);
    item.setStock(5);
    item.setOwner(userEntity);

    when(wishlistRepository.findById(wishListId1)).thenReturn(java.util.Optional.of(testList1));
    wishlistService.removeFromWishList(wishListId1);
  }
}

package com.felix.projects.salespoint.mapper;

import com.felix.projects.salespoint.dto.WishList;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
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

@ActiveProfiles("wishListMapperTest")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = WishlistMapperImpl.class)
public class WishListMapperUnitTests {
  private final WishListEntity testList1 = new WishListEntity();
  private final WishListEntity testList2 = new WishListEntity();
  private final WishListEntity testList3 = new WishListEntity();
  private final WishList testList4 = new WishList();
  private final WishListId wishListId1 = new WishListId();
  private final WishListId wishListId2 = new WishListId();
  private final WishListId wishListId3 = new WishListId();
  @Autowired public WishlistMapper wishlistMapper = new WishlistMapperImpl();

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
  public void wishListToDtoTest() {

    WishList wishList = wishlistMapper.toDto(testList1);

    assertNotNull(wishList);
    assertEquals(wishList.getItem(), testList1.getId().getItemId());
    assertEquals(wishList.getUser(), testList1.getId().getUserId());
  }

  @Test
  public void wishListToEntityTest() {
    WishListEntity wishList = wishlistMapper.toEntity(testList4);

    assertNotNull(wishList);
    assertEquals(wishList.getId().getItemId(), testList4.getItem());
    assertEquals(wishList.getId().getUserId(), testList4.getUser());
  }

  @Test
  public void wishListToWishListIdTest() {
    WishList wishList = wishlistMapper.toDto(wishListId1);

    assertNotNull(wishList);
    assertEquals(wishList.getItem(), wishListId1.getItemId());
    assertEquals(wishList.getUser(), wishListId1.getUserId());
  }

  @Test
  public void listToDtoTest() {
    List<WishListEntity> list = new ArrayList<>();

    list.add(testList1);
    list.add(testList2);

    List<WishList> retrievedList = wishlistMapper.toDto(list);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.get(0).getUser(), testList1.getId().getUserId());
    assertEquals(retrievedList.get(0).getItem(), testList1.getId().getItemId());
    assertEquals(retrievedList.get(1).getUser(), testList2.getId().getUserId());
    assertEquals(retrievedList.get(1).getItem(), testList2.getId().getItemId());
  }

  @Test
  public void listToEntityTest() {
    List<WishList> list = new ArrayList<>();

    list.add(testList4);

    List<WishListEntity> retrievedList = wishlistMapper.toEntity(list);

    assertNotNull(retrievedList);
    assertEquals(retrievedList.get(0).getId().getUserId(), testList4.getUser());
    assertEquals(retrievedList.get(0).getId().getItemId(), testList4.getItem());
  }
}

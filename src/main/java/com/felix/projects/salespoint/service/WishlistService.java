package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.dto.WishList;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import com.felix.projects.salespoint.mapper.ItemMapper;
import com.felix.projects.salespoint.mapper.UserMapper;
import com.felix.projects.salespoint.mapper.WishlistMapper;
import com.felix.projects.salespoint.repository.ItemRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.repository.WishlistRepository;
import com.felix.projects.salespoint.validators.WishListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/** The type Wishlist service. */
@Service
public class WishlistService {

  @Autowired private WishlistRepository wishlistRepository;

  @Autowired private ItemRepository itemRepository;

  @Autowired private UserRepository userRepository;

  /**
   * Gets all wish lists.
   *
   * @return the all wish lists
   */
  public List<WishList> getAllWishLists() {
    return WishlistMapper.INSTANCE.toDto(wishlistRepository.findAll());
  }

  /**
   * Gets all items wished by user.
   *
   * @param userId the user id
   * @return the all items wished by user
   */
  public List<Item> getAllItemsWishedByUser(Integer userId) throws EntityNotFoundException {

    List<WishListEntity> listOfIds = wishlistRepository.findWishListEntityByIdUserIdEquals(userId);

    List<Item> items = new ArrayList<>();

    if (!listOfIds.isEmpty()) {
      for (WishListEntity id : listOfIds) {
        items.add(
            ItemMapper.INSTANCE.toDto(
                itemRepository
                    .findById(id.getId().getItemId())
                    .orElseThrow(
                        () ->
                            new EntityNotFoundException(
                                "Item " + id.getId().getItemId() + " not found"))));
      }
    }
    return items;
  }

  /**
   * Gets all user wishing for item.
   *
   * @param itemId the item id
   * @return the all user wishing for item
   */
  public List<User> getAllUserWishingForItem(Integer itemId) throws EntityNotFoundException {

    List<WishListEntity> listOfIds = wishlistRepository.findWishListEntityByIdItemIdEquals(itemId);

    List<User> users = new ArrayList<>();

    for (WishListEntity id : listOfIds) {

      users.add(
          UserMapper.INSTANCE.toDto(
              userRepository
                  .findById(id.getId().getUserId())
                  .orElseThrow(
                      () ->
                          new EntityNotFoundException(
                              "User " + id.getId().getUserId() + " not found"))));
    }
    return users;
  }

  /**
   * Add to wish list wish list.
   *
   * @param wishList the wish list
   * @return the wish list
   */
  public WishList addToWishList(WishList wishList)
      throws CustomValidationException, EntityNotFoundException {

    WishListEntity wishListEntity = WishlistMapper.INSTANCE.toEntity(wishList);

    Errors errors = new BeanPropertyBindingResult(wishListEntity, "wishListEntity");
    ValidationUtils.invokeValidator(
        new WishListValidator(userRepository, itemRepository, wishlistRepository),
        wishListEntity,
        errors);

    if (errors.hasErrors()) {
      throw new CustomValidationException("Error creating the wishlist.", errors);
    }

    WishListEntity savedWishListEntity = wishlistRepository.save(wishListEntity);

    ItemEntity wantedItem =
        itemRepository
            .findById(wishList.getItem())
            .orElseThrow(
                () -> new EntityNotFoundException("Item " + wishList.getItem() + " not found"));

    Integer newStock = wantedItem.getStock() - 1;

    wantedItem.setStock(newStock);

    itemRepository.save(wantedItem);

    return WishlistMapper.INSTANCE.toDto(savedWishListEntity);
  }

  /**
   * Remove from wish list.
   *
   * @param id the id
   */
  public void removeFromWishList(WishListId id) throws EntityNotFoundException {

    wishlistRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("WishList" + id + "not found"));

    wishlistRepository.deleteById(id);

    ItemEntity wantedItem =
        itemRepository
            .findById(id.getItemId())
            .orElseThrow(
                () -> new EntityNotFoundException("Item " + id.getItemId() + " not found"));

    Integer newStock = wantedItem.getStock() + 1;

    wantedItem.setStock(newStock);

    itemRepository.save(wantedItem);
  }
}

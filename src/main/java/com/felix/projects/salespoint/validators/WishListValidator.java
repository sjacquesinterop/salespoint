package com.felix.projects.salespoint.validators;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.mapper.ItemMapper;
import com.felix.projects.salespoint.repository.ItemRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.repository.WishlistRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class WishListValidator implements Validator {

  private final UserRepository userRepository;

  private final ItemRepository itemRepository;

  private final WishlistRepository wishlistRepository;

  public WishListValidator(
      UserRepository userRepository,
      ItemRepository itemRepository,
      WishlistRepository wishlistRepository) {
    this.itemRepository = itemRepository;
    this.userRepository = userRepository;
    this.wishlistRepository = wishlistRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return WishListEntity.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    WishListEntity wishListEntity = (WishListEntity) o;

    Item wantedItem = null;

    if (wishListEntity.getId().getUserId() == null
        || !userRepository.existsById(wishListEntity.getId().getUserId())) {
      errors.rejectValue(
          "id",
          "User does not exist.",
          "User " + wishListEntity.getId().getUserId() + " does not exist.");
    }

    if (wishListEntity.getId().getItemId() == null
        || !itemRepository.existsById(wishListEntity.getId().getItemId())) {
      errors.rejectValue(
          "id",
          "Item does not exist.",
          "Item " + wishListEntity.getId().getItemId() + " does not exist.");
    }

    Optional<ItemEntity> itemEntity = itemRepository.findById(wishListEntity.getId().getItemId());

    if (itemEntity.isPresent()) {
      ItemEntity newItem = itemEntity.get();
      wantedItem = ItemMapper.INSTANCE.toDto(newItem);
    }

    if (wantedItem != null && wantedItem.getStock() <= 0) {
      errors.rejectValue(
          "id",
          "This item is not available for purchase.",
          "This item is not available for purchase.");
    }

    if (wantedItem != null && wantedItem.getOwner().equals(wishListEntity.getId().getUserId())) {
      errors.rejectValue(
          "id",
          "User cannot wish for an item he already owns.",
          "User "
              + wishListEntity.getId().getUserId()
              + " cannot wish for an item he already owns.");
    }

    if (wantedItem != null && wishlistRepository.existsById(wishListEntity.getId())) {
      errors.rejectValue(
          "id",
          "User/Item combination already exists.",
          "User " + wishListEntity.getId().getUserId() + " already has this item in his wishlist.");
    }
  }
}

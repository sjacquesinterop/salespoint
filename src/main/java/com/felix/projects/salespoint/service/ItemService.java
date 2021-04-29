package com.felix.projects.salespoint.service;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import com.felix.projects.salespoint.mapper.ItemMapper;
import com.felix.projects.salespoint.mapper.WishlistMapper;
import com.felix.projects.salespoint.repository.ItemRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.repository.WishlistRepository;
import com.felix.projects.salespoint.validators.OwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/** The type Item service. */
@Service
public class ItemService {

  @Autowired private ItemRepository itemRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private WishlistRepository wishlistRepository;

  /**
   * Get all items list.
   *
   * @return the list
   */
  public List<Item> getAllItems() {
    return ItemMapper.INSTANCE.INSTANCE.toDto(itemRepository.findAll());
  }

  /**
   * Gets item by id.
   *
   * @param id the item id
   * @return the item by id
   * @throws EntityNotFoundException the exception
   */
  public Item getItemById(Integer id) throws EntityNotFoundException {

    return ItemMapper.INSTANCE.toDto(
        itemRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Item " + id + " not found")));
  }

  /**
   * Create item item.
   *
   * @param item the item
   * @return the item
   */
  public Item createItem(Item item) {
    ItemEntity newItem = ItemMapper.INSTANCE.toEntity(item);
    Errors errors = new BeanPropertyBindingResult(newItem, "itemEntity");
    ValidationUtils.invokeValidator(new OwnerValidator(userRepository), newItem.getOwner(), errors);
    if (errors.hasErrors()) {
      throw new CustomValidationException("Error creating the item : Owner invalid.", errors);
    }
    ItemEntity savedItemEntity = itemRepository.save(newItem);
    return ItemMapper.INSTANCE.toDto(savedItemEntity);
  }

  /**
   * Update item response entity.
   *
   * @param id the item id
   * @param itemDetails the item details
   * @return the item
   * @throws EntityNotFoundException the exception
   */
  public Item updateItem(Integer id, Item itemDetails) throws EntityNotFoundException {
    Item item =
        ItemMapper.INSTANCE.toDto(
            itemRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item " + id + " not found")));

    if (itemDetails.getDescription() != null) {
      item.setDescription(itemDetails.getDescription());
    }

    if (itemDetails.getName() != null) {
      item.setName(itemDetails.getName());
    }

    if (itemDetails.getOwner() != null) {
      item.setOwner(itemDetails.getOwner());
    }

    if (itemDetails.getPrice() != null) {
      item.setPrice(itemDetails.getPrice());
    }

    if (itemDetails.getStock() != null) {
      item.setStock(itemDetails.getStock());
    }

    return ItemMapper.INSTANCE.toDto(itemRepository.save(ItemMapper.INSTANCE.toEntity(item)));
  }

  /**
   * Delete item map.
   *
   * @param id the item id
   * @return
   * @throws EntityNotFoundException the exception
   */
  public void deleteItem(Integer id) throws EntityNotFoundException {
    itemRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Item " + id + " not found"));

    if (wishlistRepository.findWishListEntityByIdItemIdEquals(id).size() > 0) {

      List<WishListEntity> list = wishlistRepository.findWishListEntityByIdItemIdEquals(id);

      for (WishListEntity wishListEntity : list) {

        WishListId currentId = WishlistMapper.INSTANCE.toId(wishListEntity);
        wishlistRepository.deleteById(currentId);
      }
    }
    itemRepository.deleteById(id);
  }
}

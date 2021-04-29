package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.dto.WishList;
import com.felix.projects.salespoint.entities.WishListId;
import com.felix.projects.salespoint.service.WishlistService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class WishListController {

  @Autowired private WishlistService wishlistService;

  @GetMapping("/wishlists")
  @ApiResponses(
      value = {@ApiResponse(code = 202, message = "Fetched list of wishlists successfully. :)")})
  public ResponseEntity<List<WishList>> getAllWishLists() {
    return new ResponseEntity<>(wishlistService.getAllWishLists(), HttpStatus.OK);
  }

  @GetMapping("/wishlists/users/{userId}")
  @ApiResponses(
      value = {@ApiResponse(code = 202, message = "List of items found successfully. :)")})
  public ResponseEntity<List<Item>> getAllItemsWishedByUser(
      @PathVariable(value = "userId") Integer userId) throws EntityNotFoundException {
    return new ResponseEntity<>(wishlistService.getAllItemsWishedByUser(userId), HttpStatus.OK);
  }

  @GetMapping("/wishlists/items/{itemId}")
  @ApiResponses(
      value = {@ApiResponse(code = 202, message = "List of users found successfully. :)")})
  public ResponseEntity<List<User>> getAllUserWishingForItem(
      @PathVariable(value = "itemId") Integer itemId) throws EntityNotFoundException {
    return new ResponseEntity<>(wishlistService.getAllUserWishingForItem(itemId), HttpStatus.OK);
  }

  @PostMapping("/wishlists")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "Wishlist created successfully. :)")})
  public ResponseEntity<WishList> addToWishList(@Valid @RequestBody WishList wishList) {
    return new ResponseEntity<>(wishlistService.addToWishList(wishList), HttpStatus.CREATED);
  }

  @DeleteMapping("/wishlists")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "Wishlist deleted successfully. :)")})
  public ResponseEntity<Map<String, Boolean>> removeFromWishList(@RequestBody WishListId id)
      throws EntityNotFoundException {
    wishlistService.removeFromWishList(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

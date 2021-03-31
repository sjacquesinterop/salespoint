package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.service.ItemService;
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
public class ItemController {

  @Autowired private ItemService itemService;

  @GetMapping("/items")
  @ApiResponses(
      value = {@ApiResponse(code = 202, message = "Fetched list of items successfully. :)")})
  public ResponseEntity<List<Item>> getAllItems() {
    return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.ACCEPTED);
  }

  @GetMapping("/items/{id}")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User found successfully. :)")})
  public ResponseEntity<Item> getItemById(@PathVariable(value = "id") Integer id)
      throws EntityNotFoundException {
    return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
  }

  @PostMapping("/items")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User created successfully. :)")})
  public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
    return new ResponseEntity<>(itemService.createItem(item), HttpStatus.CREATED);
  }

  @PutMapping("/items/{id}")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User updated successfully. :)")})
  public ResponseEntity<Item> updateItem(
      @PathVariable(value = "id") Integer id, @Valid @RequestBody Item itemDetails)
      throws EntityNotFoundException {
    return new ResponseEntity<>(itemService.updateItem(id, itemDetails), HttpStatus.OK);
  }

  @DeleteMapping("/items/{id}")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User deleted successfully. :)")})
  public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable(value = "id") Integer id)
      throws EntityNotFoundException {
    itemService.deleteItem(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

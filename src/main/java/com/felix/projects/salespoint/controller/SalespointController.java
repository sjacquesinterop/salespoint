package com.felix.projects.salespoint.controller;


import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.repository.ItemRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Salespoint controller.
 */
@Controller
@RequestMapping("/api/v1")//Is this the right thing?
public class SalespointController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Get all items list.
     *
     * @return the list
     */
    @GetMapping("/items")
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     * @throws Exception the exception
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") Long userId) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User " + userId + " not found"));
        return ResponseEntity.ok().body(user);
    }

    /**
     * Gets item by id.
     *
     * @param itemId the item id
     * @return the item by id
     * @throws Exception the exception
     */
    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable(value = "itemId") Long itemId) throws Exception{
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new Exception("User " + itemId + " not found"));
        return ResponseEntity.ok().body(item);
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping("/users")
    public User createUser(@Validated @RequestBody User user){//in the doc it says @Valid??
        return userRepository.save(user);
    }

    /**
     * Create item item.
     *
     * @param item the item
     * @return the item
     */
    @PostMapping("/items")
    public Item createItem(@Validated @RequestBody Item item){//in the doc it says @Valid??
        return itemRepository.save(item);
    }

    /**
     * Update user response entity.
     *
     * @param userId      the user id
     * @param userDetails the user details
     * @return the response entity
     * @throws Exception the exception
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "userId") Long userId, @Validated @RequestBody User userDetails) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User " + userId + " not found"));

        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Update item response entity.
     *
     * @param itemId      the item id
     * @param itemDetails the item details
     * @return the response entity
     * @throws Exception the exception
     */
    @PutMapping("/item/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "itemId") Long itemId, @Validated @RequestBody Item itemDetails) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new Exception("Item " + itemId + " not found"));

        item.setDescription(itemDetails.getDescription());
        item.setName(itemDetails.getName());
        item.setOwner(itemDetails.getOwner());
        item.setPrice(itemDetails.getPrice());
        item.setStock(itemDetails.getStock());

        final Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * Delete user map.
     *
     * @param userId the user id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/user/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "userId") Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User " + userId + " not found"));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * Delete item map.
     *
     * @param itemId the item id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/item/{itemId}")
    public Map<String, Boolean> deleteItem(@PathVariable(value = "itemId") Long itemId) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new Exception("item " + itemId + " not found"));

        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}

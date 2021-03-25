package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.service.UserService;
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
public class UserController {

  @Autowired private UserService userService;

  @GetMapping("/users")
  @ApiResponses(
      value = {@ApiResponse(code = 202, message = "Fetched list of users successfully. :)")})
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.ACCEPTED);
  }

  @GetMapping("/users/{id}")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User found successfully. :)")})
  public ResponseEntity<User> getUserById(@PathVariable(value = "id") Integer id)
      throws EntityNotFoundException {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @PostMapping("/users")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User created successfully. :)")})
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PutMapping("/users/{id}")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User updated successfully. :)")})
  public ResponseEntity<User> updateUser(
      @PathVariable(value = "id") Integer id, @Valid @RequestBody User userDetails)
      throws EntityNotFoundException {
    return new ResponseEntity<>(userService.updateUser(id, userDetails), HttpStatus.OK);
  }

  @DeleteMapping("/user/{id}")
  @ApiResponses(value = {@ApiResponse(code = 202, message = "User deleted successfully. :)")})
  public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") Integer id)
      throws EntityNotFoundException {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

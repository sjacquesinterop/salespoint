package com.felix.projects.salespoint.validator;

import com.felix.projects.salespoint.entities.*;
import com.felix.projects.salespoint.repository.ItemRepository;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.repository.WishlistRepository;
import com.felix.projects.salespoint.validators.WishListValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ActiveProfiles("wishListValidatorTest")
@RunWith(MockitoJUnitRunner.class)
public class WishListValidatorUnitTests {

  private final WishListEntity testList1 = new WishListEntity();
  private final WishListId wishListId1 = new WishListId();
  private final ItemEntity testItem1 = new ItemEntity();
  private final RoleEntity role = new RoleEntity();
  private final UserEntity user = new UserEntity();
  @InjectMocks private WishListValidator wishListValidator;
  @Mock private WishlistRepository wishlistRepository;
  @Mock private UserRepository userRepository;
  @Mock private ItemRepository itemRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    wishListValidator = new WishListValidator(userRepository, itemRepository, wishlistRepository);

    wishListId1.setItemId(1);
    wishListId1.setUserId(1);

    testList1.setId(wishListId1);

    role.setId(1);

    user.setId(3);
    user.setName("Goku");
    user.setPassword("!Password123");
    user.setEmail("dragonballz@gmail.com");
    user.setRole(role);

    testItem1.setName("dragon ball 1");
    testItem1.setDescription("One of the dragon balls!");
    testItem1.setId(1);
    testItem1.setOwner(user);
    testItem1.setPrice(10.0000f);
    testItem1.setStock(9);
  }

  @Test
  public void supportsTest() {
    WishListEntity wishListEntity = new WishListEntity();

    assertTrue(wishListValidator.supports(wishListEntity.getClass()));
  }

  @Test
  public void validateTestIsValid() {
    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));
    when(wishlistRepository.existsById(wishListId1)).thenReturn(Boolean.FALSE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);

    assertFalse(errors.hasErrors());
  }

  @Test
  public void validateTestWithBadUser() {

    when(userRepository.existsById(1)).thenReturn(Boolean.FALSE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));
    when(wishlistRepository.existsById(wishListId1)).thenReturn(Boolean.FALSE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);

    assertTrue(errors.hasErrors());
  }

  @Test
  public void validateTestWithBadItem() {
    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.FALSE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));
    when(wishlistRepository.existsById(wishListId1)).thenReturn(Boolean.FALSE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);

    assertTrue(errors.hasErrors());
  }

  @Test
  public void validateTestWithNoStock() {
    testItem1.setStock(0);

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));
    when(wishlistRepository.existsById(wishListId1)).thenReturn(Boolean.FALSE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);

    assertTrue(errors.hasErrors());
  }

  @Test
  public void validateTestWithAlreadyOwned() {
    user.setId(1);

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));
    when(wishlistRepository.existsById(wishListId1)).thenReturn(Boolean.FALSE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);

    assertTrue(errors.hasErrors());
  }

  @Test
  public void validateTestWithAlreadyInWishList() {
    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(testItem1));
    when(wishlistRepository.existsById(wishListId1)).thenReturn(Boolean.TRUE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);

    assertTrue(errors.hasErrors());
  }

  @Test
  public void validateTestWithItemNotFound() {

    when(userRepository.existsById(1)).thenReturn(Boolean.TRUE);
    when(itemRepository.existsById(1)).thenReturn(Boolean.FALSE);

    Errors errors = new BeanPropertyBindingResult(testList1, "testList1");

    wishListValidator.validate(testList1, errors);
    assertTrue(errors.hasErrors());
  }
}

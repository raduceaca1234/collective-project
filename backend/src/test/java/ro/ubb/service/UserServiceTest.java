package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;
import ro.ubb.validator.UserValidator;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTest {

  @Mock UserRepository userRepository;

  @Mock UserValidator userValidator;

  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    initMocks(this);
    userService = new UserServiceImpl();
    userService.setUserRepository(userRepository);
    userService.setUserValidator(userValidator);
  }

  @Test
  void testUserExistsById_exists() {
    when(userRepository.existsById(1)).thenReturn(true);
    Assertions.assertTrue(userService.existsById(1));
  }

  @Test
  void testUserExistsById_nonExists() {
    when(userRepository.existsById(-1)).thenReturn(false);
    Assertions.assertFalse(userService.existsById(-1));
  }
}

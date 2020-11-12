package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.ubb.model.User;
import ro.ubb.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
    }

    @Test
    public void testUserExistsById_exists(){
        when(userRepository.findById(1))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .firstName("fname1")
                        .lastName("lname1")
                        .build()
                ));
        Assertions.assertEquals(userRepository.findById(1).get().getId(),1);
    }

    @Test
    public void testUserExistsById_nonExists(){
        when(userRepository.findById(-1)).thenReturn(Optional.empty());
        Assertions.assertTrue(userRepository.findById(-1).isEmpty());
    }
}

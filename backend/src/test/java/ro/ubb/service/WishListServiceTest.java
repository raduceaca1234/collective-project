package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.ubb.model.User;
import ro.ubb.model.Wishlist;
import ro.ubb.repository.WishlistRepository;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WishListServiceTest {

    @Mock
    WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void testWishListAdd() {
        Wishlist wishlistToAdd = Wishlist.builder()
                .owner(User.builder().id(1).build())
                .build();
        Wishlist afterAdd = wishlistToAdd;
        afterAdd.setId(1);
        when(wishlistRepository.save(wishlistToAdd)).thenReturn(afterAdd);
        Assertions.assertEquals(wishlistRepository.save(wishlistToAdd).getId(), 1);
    }
}

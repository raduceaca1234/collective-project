package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.Wishlist;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.repository.WishlistRepository;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void testWishListItemAdd() {
        Wishlist wishlist = Wishlist.builder()
                .id(1)
                .owner(User.builder().id(1).build())
                .build();
        Announcement announcementToAdd = Announcement.builder().id(1)
                .user(User.builder()
                        .id(2).build())
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category(Category.AGRICULTURE)
                .duration(30)
                .pricePerDay(50)
                .status(Status.OPEN)
                .build();
        Set<Announcement> announcementSet = wishlist.getWantedAnnouncements();
        if (announcementSet == null) {
            announcementSet = new HashSet<>();
        }
        announcementSet.add(announcementToAdd);
        wishlist.setWantedAnnouncements(announcementSet);
        Assertions.assertEquals(wishlist.getWantedAnnouncements().size(), 1);
    }
}

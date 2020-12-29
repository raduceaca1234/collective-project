package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
     Wishlist getWishlistByOwnerId(int id);
}

package ro.ubb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.ubb.model.Announcement;
import ro.ubb.model.Wishlist;

import java.util.List;

public interface WishListService {
    Wishlist add(Wishlist wishlist);
    List<Wishlist> getAll();
    Page<Wishlist> getAllPaged(Pageable pageable);

    Wishlist getById(int id);

    boolean existsById(int id);
}

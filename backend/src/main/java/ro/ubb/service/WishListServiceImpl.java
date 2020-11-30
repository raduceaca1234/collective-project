package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.ubb.model.Wishlist;
import ro.ubb.repository.WishlistRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WishListServiceImpl implements WishListService {

    private WishlistRepository wishlistRepository;

    @Override
    public Wishlist add(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> getAll() {
        List<Wishlist> wishlists = new ArrayList<>();
        wishlistRepository.findAll().forEach(wishlists::add);
        return wishlists;
    }

    @Override
    public Page<Wishlist> getAllPaged(Pageable pageable) {
        return wishlistRepository.findAll(pageable);
    }

    @Override
    public Wishlist getById(int id) {
        return wishlistRepository.findById(id).orElse(Wishlist.builder().id(-1).build());
    }

    @Override
    public boolean existsById(int id) {
        return wishlistRepository.existsById(id);
    }

    @Autowired
    public void setWishlistRepository(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }
}

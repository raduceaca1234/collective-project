package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.ubb.model.Announcement;
import ro.ubb.model.Wishlist;
import ro.ubb.repository.WishlistRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.StrictMath.toIntExact;

@Slf4j
@Service
public class WishListServiceImpl implements WishListService {

    private WishlistRepository wishlistRepository;
    private AnnouncementService announcementService;

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

    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Override
    public Wishlist getWishListByOwnerId(int id) {
        return wishlistRepository.getWishlistByOwnerId(id);
    }

    @Override
    public Wishlist addItem(int ownerId, int announcementId) {
        Announcement announcement = announcementService.getById(announcementId);
        Wishlist wishlist = getWishListByOwnerId(ownerId);
        Set<Announcement> announcementSet = getWishListByOwnerId(ownerId).getWantedAnnouncements();
        if (announcementSet == null) {
            announcementSet = new HashSet<>();
        }
        announcementSet.add(announcement);
        wishlist.setWantedAnnouncements(announcementSet);
        add(wishlist);
        return wishlist;
    }

    @Override
    public Page<Announcement> getAllAnnouncementPaged(Pageable pageable, int ownerId) {
        Set<Announcement> announcementsOfWishlist = getWishListByOwnerId(ownerId).getWantedAnnouncements();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), announcementsOfWishlist.size());

        List<Announcement> output = new ArrayList<>();

        if (start <= end) {
            output = announcementsOfWishlist.stream().collect(Collectors.toList()).subList(start, end);
        }
        Page<Announcement> announcementsPage = new PageImpl<Announcement>(output, pageable,announcementsOfWishlist.size());
        return announcementsPage;
    }
}

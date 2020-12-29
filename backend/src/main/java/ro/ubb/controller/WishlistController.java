package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.PagedAnnouncementDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.Wishlist;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.UserService;
import ro.ubb.service.WishListService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@Slf4j
public class WishlistController {

    private WishListService wishListService;
    private UserService userService;
    private JWTUtil jwtUtil;
    private DtoConverter dtoConverter;

    @Autowired
    public void setWishListService(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtUtil(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setDtoConverter(DtoConverter dtoConverter) {
        this.dtoConverter = dtoConverter;
    }


    @PostMapping(value = "/{ownerToken}/{announcementId}")
    ResponseEntity<?> postWishList(@PathVariable String ownerToken, @PathVariable Integer announcementId) {
        int ownerId = Integer.parseInt(jwtUtil.decodeJWT(ownerToken).getId());
        Wishlist wishlist = new Wishlist();
        if (!userService.existsById(ownerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            log.info("calling wishlistService add ...");
             wishlist = wishListService.addItem(ownerId, announcementId);
            log.info("wishlistService add finished...");
        } catch (Exception e) {
            log.error("Exception encountered-" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerToken}/{pageNo}/{pageSize}")
    ResponseEntity<List<PagedAnnouncementDto>> getAnnouncementsOfWishlist(@PathVariable String ownerToken,
                                                                          @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        int ownerId = Integer.parseInt(jwtUtil.decodeJWT(ownerToken).getId());
        if (!userService.existsById(ownerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("pageNo = {}", pageNo);
        log.info("pageSize = {}", pageSize);
        log.info("calling wishListService get...");
        Page<Announcement> announcementsOfWishlistPage = wishListService.getAllAnnouncementPaged(pageable, ownerId);
        log.info("wishListService get finished...");
        log.info("page = {}", announcementsOfWishlistPage);

        List<PagedAnnouncementDto> pagedAnnouncementDtos = new ArrayList<>();
        for (Announcement a : announcementsOfWishlistPage) {
            PagedAnnouncementDto pad = dtoConverter.convertAnnouncementForGetPaginated(a);
            pad.setPageNumber(pageNo);
            pagedAnnouncementDtos.add(pad);
        }
        return ResponseEntity.ok(pagedAnnouncementDtos);
    }

}

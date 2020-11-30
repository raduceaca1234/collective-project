package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.UserService;
import ro.ubb.service.WishListService;

@RestController
@RequestMapping("/api/wishlist")
@Slf4j
public class WishlistController {

    private WishListService wishListService;
    private UserService userService;
    private JWTUtil jwtUtil;

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

    @PostMapping(value = "/{ownerToken}/{announcementId}")
    ResponseEntity<?> postWishList(@PathVariable String ownerToken, @PathVariable Integer announcementId) {
        int ownerId = Integer.parseInt(jwtUtil.decodeJWT(ownerToken).getId());
        if (!userService.existsById(ownerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            log.info("calling wishlistService add ...");
            wishListService.addItem(ownerId, announcementId);
            log.info("wishlistService add finished...");
        } catch (Exception e) {
            log.error("Exception encountered-" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

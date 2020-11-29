package ro.ubb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.dto.WishListDto;
import ro.ubb.service.WishListService;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private WishListService wishListService;

    @PostMapping
    ResponseEntity<?> postWishList(@ModelAttribute WishListDto wishListDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

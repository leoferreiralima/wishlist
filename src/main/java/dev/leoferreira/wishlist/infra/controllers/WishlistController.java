package dev.leoferreira.wishlist.infra.controllers;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public List<Wishlist> getAllWishlists(@PathVariable String userId){
        return wishlistService.getAllWishlists(userId);
    }

    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Wishlist createWishlist(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        return wishlistService.createWishlist(
                userId, productId
        );
    }

    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Wishlist getWishlistByProduct(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        return wishlistService.getWishlistByProduct(
                userId,
                productId
        );
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeWishlist(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        wishlistService.removeWishlistByProduct(userId, productId);
    }
}

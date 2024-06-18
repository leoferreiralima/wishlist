package dev.leoferreira.wishlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController {

    @GetMapping
    public String getAllWishlist(){
        return "hello world";
    }
}

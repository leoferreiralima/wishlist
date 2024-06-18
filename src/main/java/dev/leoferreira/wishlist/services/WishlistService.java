package dev.leoferreira.wishlist.services;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.exceptions.WishlistAlreadyExistsException;
import dev.leoferreira.wishlist.exceptions.WishlistNotFoundException;

import java.util.List;

public interface WishlistService {

    Wishlist createWishlist(String userId, String productId) throws WishlistAlreadyExistsException;

    List<Wishlist> getAllWishlists(String userId);

    Wishlist getWishlistByProduct(String userId, String productId) throws WishlistNotFoundException;

    void removeWishlistByProduct(String userId, String productId) throws WishlistNotFoundException;
}

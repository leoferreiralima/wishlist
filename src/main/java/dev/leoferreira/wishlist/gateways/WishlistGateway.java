package dev.leoferreira.wishlist.gateways;

import dev.leoferreira.wishlist.domain.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistGateway {

    long getWishlistCountByUser(String userId);

    Wishlist createWishlist(Wishlist newWishlist);

    List<Wishlist> getAllWishlists(String userId);

    Optional<Wishlist> getWishlistByProduct(String userId, String productId);

    void removeWishlist(Wishlist wishlist);
}

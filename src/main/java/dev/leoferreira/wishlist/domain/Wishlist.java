package dev.leoferreira.wishlist.domain;

import java.time.LocalDateTime;

public record Wishlist(
        String id,
        String productId,
        String createdBy,
        LocalDateTime createdAt
) {
    public static Wishlist createWishlist(
            String productId,
            String createdBy,
            LocalDateTime createdAt
    ) {
        return new Wishlist(null, productId, createdBy, createdAt);
    }
}

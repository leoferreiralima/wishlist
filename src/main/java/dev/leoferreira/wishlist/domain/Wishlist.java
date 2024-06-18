package dev.leoferreira.wishlist.domain;

import java.time.Instant;

public record Wishlist(
        String id,
        String productId,
        String createdBy,
        Instant createdAt
) {
    public static Wishlist createWishlist(
            String productId,
            String createdBy
    ) {
        return new Wishlist(null, productId, createdBy, Instant.now());
    }
}

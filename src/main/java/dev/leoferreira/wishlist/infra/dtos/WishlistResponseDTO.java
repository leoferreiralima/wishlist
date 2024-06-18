package dev.leoferreira.wishlist.infra.dtos;

import java.io.Serializable;
import java.time.Instant;

public record WishlistResponseDTO(
        String id,
        String productId,
        String createdBy,
        Instant createdAt
) implements Serializable {
}

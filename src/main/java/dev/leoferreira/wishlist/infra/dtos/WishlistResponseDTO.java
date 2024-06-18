package dev.leoferreira.wishlist.infra.dtos;

import java.time.LocalDateTime;

public record WishlistResponseDTO(
        String id,
        String productId,
        String createdBy,
        LocalDateTime createdAt
) {
}

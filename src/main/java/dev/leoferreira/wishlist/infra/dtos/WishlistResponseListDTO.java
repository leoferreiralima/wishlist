package dev.leoferreira.wishlist.infra.dtos;

import java.io.Serializable;
import java.util.List;

public record WishlistResponseListDTO(
        List<WishlistResponseDTO> wishlists
) implements Serializable {
}

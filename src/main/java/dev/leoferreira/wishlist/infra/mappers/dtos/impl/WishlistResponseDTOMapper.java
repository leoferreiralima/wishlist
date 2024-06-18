package dev.leoferreira.wishlist.infra.mappers.dtos.impl;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseDTO;
import dev.leoferreira.wishlist.infra.mappers.dtos.ResponseDTOMapper;
import org.springframework.stereotype.Component;

@Component("wishlistResponseDTOMapper")
public class WishlistResponseDTOMapper implements ResponseDTOMapper<WishlistResponseDTO, Wishlist> {
    @Override
    public WishlistResponseDTO toDTO(Wishlist wishlist) {
        return new WishlistResponseDTO(
                wishlist.id(),
                wishlist.productId(),
                wishlist.createdBy(),
                wishlist.createdAt()
        );
    }
}

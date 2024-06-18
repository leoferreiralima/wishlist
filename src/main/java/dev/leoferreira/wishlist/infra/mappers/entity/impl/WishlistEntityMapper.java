package dev.leoferreira.wishlist.infra.mappers.entity.impl;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.mappers.entity.EntityMapper;
import dev.leoferreira.wishlist.infra.persistence.entity.WishlistEntity;
import org.springframework.stereotype.Component;

@Component("wishlistEntityMapper")
public class WishlistEntityMapper implements EntityMapper<WishlistEntity, Wishlist> {

    @Override
    public WishlistEntity toEntity(Wishlist domainWishlist) {
        return new WishlistEntity(
                domainWishlist.id(),
                domainWishlist.productId(),
                domainWishlist.createdBy(),
                domainWishlist.createdAt()
        );
    }

    @Override
    public Wishlist toDomain(WishlistEntity entityWishlist) {
        return new Wishlist(
                entityWishlist.getId(),
                entityWishlist.getProductId(),
                entityWishlist.getCreatedBy(),
                entityWishlist.getCreatedAt()
        );
    }
}

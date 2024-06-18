package dev.leoferreira.wishlist.infra.gateways;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.gateways.WishlistGateway;
import dev.leoferreira.wishlist.infra.mappers.impl.WishlistEntityMapper;
import dev.leoferreira.wishlist.infra.persistence.entity.WishlistEntity;
import dev.leoferreira.wishlist.infra.persistence.respository.WishlistMongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WishlistMongoRepositoryGateway implements WishlistGateway {

    private final WishlistEntityMapper wishlistEntityMapper;
    private final WishlistMongoRepository wishlistMongoRepository;

    public WishlistMongoRepositoryGateway(
            WishlistEntityMapper wishlistEntityMapper,
            WishlistMongoRepository wishlistMongoRepository
    ) {
        this.wishlistEntityMapper = wishlistEntityMapper;
        this.wishlistMongoRepository = wishlistMongoRepository;
    }

    @Override
    public long getWishlistCountByUser(String userId) {
        return wishlistMongoRepository.countByCreatedBy(userId);
    }

    @Override
    public Wishlist createWishlist(Wishlist newWishlist) {
        WishlistEntity wishlistEntity = wishlistEntityMapper.toEntity(newWishlist);

        WishlistEntity savedWishlistEntity = wishlistMongoRepository.save(wishlistEntity);

        return wishlistEntityMapper.toDomain(savedWishlistEntity);
    }

    @Override
    public List<Wishlist> getAllWishlists(String userId) {
        List<WishlistEntity> wishlistEntities = wishlistMongoRepository.findByCreatedBy(userId);

        return wishlistEntityMapper.toDomainList(wishlistEntities);
    }

    @Override
    public Optional<Wishlist> getWishlistByProduct(String userId, String productId) {
        return wishlistMongoRepository.findOneByCreatedByAndProductId(
                userId, productId
        ).map(wishlistEntityMapper::toDomain);
    }

    @Override
    public void removeWishlist(Wishlist wishlist) {
        wishlistMongoRepository.deleteById(wishlist.id());
    }
}

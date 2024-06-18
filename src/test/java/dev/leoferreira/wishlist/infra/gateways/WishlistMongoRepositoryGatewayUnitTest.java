package dev.leoferreira.wishlist.infra.gateways;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.mappers.entity.EntityMapper;
import dev.leoferreira.wishlist.infra.persistence.entity.WishlistEntity;
import dev.leoferreira.wishlist.infra.persistence.respository.WishlistMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class WishlistMongoRepositoryGatewayUnitTest {

    @Mock
    private EntityMapper<WishlistEntity, Wishlist> wishlistEntityMapper;
    @Mock
    private WishlistMongoRepository wishlistMongoRepository;

    @InjectMocks
    private WishlistMongoRepositoryGateway gateway;

    @Test
    public void givenValidUserId_WhenGetWishlistCountByUser_ThenCountWishlistByUser() {
        String userId = "userId";

        given(wishlistMongoRepository.countByCreatedBy(userId))
                .willReturn(10L);

        assertEquals(10, gateway.getWishlistCountByUser(userId));

        then(wishlistMongoRepository)
                .should()
                .countByCreatedBy(userId);
    }

    @Test
    public void givenNewWishlist_WhenCreateWishlistEntity_ThenReturnWishlist() {
        Wishlist newWishlist = mock(Wishlist.class);
        Wishlist savedWishlist = mock(Wishlist.class);

        WishlistEntity wishlistEntity = mock(WishlistEntity.class);
        WishlistEntity savedWishlistEntity = mock(WishlistEntity.class);

        given(wishlistEntityMapper.toEntity(newWishlist))
                .willReturn(wishlistEntity);

        given(wishlistMongoRepository.save(wishlistEntity))
                .willReturn(savedWishlistEntity);

        given(wishlistEntityMapper.toDomain(savedWishlistEntity))
                .willReturn(savedWishlist);

        assertEquals(savedWishlist, gateway.createWishlist(newWishlist));

        then(wishlistEntityMapper)
                .should()
                .toEntity(newWishlist);

        then(wishlistMongoRepository)
                .should()
                .save(wishlistEntity);

        then(wishlistEntityMapper)
                .should()
                .toDomain(savedWishlistEntity);
    }

    @Test
    public void givenValidUserId_WhenGetAllWishlists_ThenReturnWishlists() {
        String userId = "userId";
        List<WishlistEntity> wishlistEntities = List.of(mock(WishlistEntity.class));
        List<Wishlist> wishlists = List.of(mock(Wishlist.class));

        given(wishlistMongoRepository.findByCreatedBy(userId))
                .willReturn(wishlistEntities);

        given(wishlistEntityMapper.toDomainList(wishlistEntities))
                .willReturn(wishlists);

        assertEquals(wishlists, gateway.getAllWishlists(userId));

        then(wishlistMongoRepository)
                .should()
                .findByCreatedBy(userId);

        then(wishlistEntityMapper)
                .should()
                .toDomainList(wishlistEntities);
    }

    @Test
    public void givenValidUserIdAndProductId_WhenGetWishlistByProduct_ThenReturnWishlist() {
        String userId = "userId";
        String productId = "productId";
        WishlistEntity wishlistEntity = mock(WishlistEntity.class);
        Wishlist wishlist = mock(Wishlist.class);

        given(wishlistMongoRepository.findOneByCreatedByAndProductId(userId, productId))
                .willReturn(Optional.of(wishlistEntity));

        given(wishlistEntityMapper.toDomain(wishlistEntity))
                .willReturn(wishlist);

        Optional<Wishlist> result = gateway.getWishlistByProduct(userId, productId);

        assertTrue(result.isPresent());
        assertEquals(wishlist, result.get());

        then(wishlistMongoRepository)
                .should()
                .findOneByCreatedByAndProductId(userId, productId);

        then(wishlistEntityMapper)
                .should()
                .toDomain(wishlistEntity);
    }

    @Test
    public void givenWishlist_WhenRemoveWishlist_ThenDeleteWishlist() {
        Wishlist wishlist = mock(Wishlist.class);
        String wishlistId = "wishlistId";

        given(wishlist.id()).willReturn(wishlistId);

        gateway.removeWishlist(wishlist);

        then(wishlistMongoRepository)
                .should()
                .deleteById(wishlistId);
    }
}
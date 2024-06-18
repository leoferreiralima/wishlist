package dev.leoferreira.wishlist.infra.mappers.entity.impl;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.persistence.entity.WishlistEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WishlistEntityMapperUnitTest {
    private final WishlistEntityMapper mapper = new WishlistEntityMapper();

    @Mock
    private Wishlist domainWishlist;

    @Mock
    private WishlistEntity entityWishlist;

    @Test
    void givenDomainWishlist_WhenToEntity_ThenReturnWishlistEntity() {
        String id = "wishlistId";
        String productId = "productId";
        String createdBy = "userId";
        Instant createdAt = Instant.now();

        given(domainWishlist.id()).willReturn(id);
        given(domainWishlist.productId()).willReturn(productId);
        given(domainWishlist.createdBy()).willReturn(createdBy);
        given(domainWishlist.createdAt()).willReturn(createdAt);

        WishlistEntity entity = mapper.toEntity(domainWishlist);

        assertEquals(id, entity.getId());
        assertEquals(productId, entity.getProductId());
        assertEquals(createdBy, entity.getCreatedBy());
        assertEquals(createdAt, entity.getCreatedAt());
    }

    @Test
    void givenEntityWishlist_WhenToDomain_ThenReturnWishlist() {
        String id = "wishlistId";
        String productId = "productId";
        String createdBy = "userId";
        Instant createdAt = Instant.now();

        given(entityWishlist.getId()).willReturn(id);
        given(entityWishlist.getProductId()).willReturn(productId);
        given(entityWishlist.getCreatedBy()).willReturn(createdBy);
        given(entityWishlist.getCreatedAt()).willReturn(createdAt);

        Wishlist wishlist = mapper.toDomain(entityWishlist);

        assertEquals(id, wishlist.id());
        assertEquals(productId, wishlist.productId());
        assertEquals(createdBy, wishlist.createdBy());
        assertEquals(createdAt, wishlist.createdAt());
    }

    @Test
    void givenEntityList_WhenToDomainList_ThenReturnWishlistList() {
        String id1 = "wishlistId1";
        String productId1 = "productId1";
        String createdBy1 = "userId1";
        Instant createdAt1 = Instant.now();

        String id2 = "wishlistId2";
        String productId2 = "productId2";
        String createdBy2 = "userId2";
        Instant createdAt2 = Instant.now().plus(1, ChronoUnit.DAYS);

        WishlistEntity entity1 = new WishlistEntity(id1, productId1, createdBy1, createdAt1);
        WishlistEntity entity2 = new WishlistEntity(id2, productId2, createdBy2, createdAt2);

        List<WishlistEntity> entityList = List.of(entity1, entity2);

        List<Wishlist> wishlistList = mapper.toDomainList(entityList);

        assertEquals(2, wishlistList.size());

        Wishlist wishlist1 = wishlistList.get(0);
        assertEquals(id1, wishlist1.id());
        assertEquals(productId1, wishlist1.productId());
        assertEquals(createdBy1, wishlist1.createdBy());
        assertEquals(createdAt1, wishlist1.createdAt());

        Wishlist wishlist2 = wishlistList.get(1);
        assertEquals(id2, wishlist2.id());
        assertEquals(productId2, wishlist2.productId());
        assertEquals(createdBy2, wishlist2.createdBy());
        assertEquals(createdAt2, wishlist2.createdAt());
    }
}
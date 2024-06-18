package dev.leoferreira.wishlist.infra.mappers.dtos.impl;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseDTO;
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
class WishlistResponseDTOMapperUnitTest {
    private final WishlistResponseDTOMapper mapper = new WishlistResponseDTOMapper();

    @Mock
    private Wishlist wishlist1;

    @Mock
    private Wishlist wishlist2;

    @Test
    void givenWishlist_WhenToDTO_ThenReturnWishlistResponseDTO() {
        String id = "wishlistId";
        String productId = "productId";
        String createdBy = "userId";
        Instant createdAt = Instant.now();

        given(wishlist1.id()).willReturn(id);
        given(wishlist1.productId()).willReturn(productId);
        given(wishlist1.createdBy()).willReturn(createdBy);
        given(wishlist1.createdAt()).willReturn(createdAt);

        WishlistResponseDTO dto = mapper.toDTO(wishlist1);

        assertEquals(id, dto.id());
        assertEquals(productId, dto.productId());
        assertEquals(createdBy, dto.createdBy());
        assertEquals(createdAt, dto.createdAt());
    }

    @Test
    void givenWishlistList_WhenToDTOList_ThenReturnWishlistResponseDTOList() {
        String id1 = "wishlistId1";
        String productId1 = "productId1";
        String createdBy1 = "userId1";
        Instant createdAt1 = Instant.now();

        String id2 = "wishlistId2";
        String productId2 = "productId2";
        String createdBy2 = "userId2";
        Instant createdAt2 = Instant.now().plus(1, ChronoUnit.DAYS);

        given(wishlist1.id()).willReturn(id1);
        given(wishlist1.productId()).willReturn(productId1);
        given(wishlist1.createdBy()).willReturn(createdBy1);
        given(wishlist1.createdAt()).willReturn(createdAt1);

        given(wishlist2.id()).willReturn(id2);
        given(wishlist2.productId()).willReturn(productId2);
        given(wishlist2.createdBy()).willReturn(createdBy2);
        given(wishlist2.createdAt()).willReturn(createdAt2);

        List<Wishlist> wishlists = List.of(wishlist1, wishlist2);

        List<WishlistResponseDTO> dtos = mapper.toDTOList(wishlists);

        assertEquals(2, dtos.size());

        WishlistResponseDTO dto1 = dtos.get(0);
        assertEquals(id1, dto1.id());
        assertEquals(productId1, dto1.productId());
        assertEquals(createdBy1, dto1.createdBy());
        assertEquals(createdAt1, dto1.createdAt());

        WishlistResponseDTO dto2 = dtos.get(1);
        assertEquals(id2, dto2.id());
        assertEquals(productId2, dto2.productId());
        assertEquals(createdBy2, dto2.createdBy());
        assertEquals(createdAt2, dto2.createdAt());
    }
}
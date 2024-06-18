package dev.leoferreira.wishlist.services.impl;

import dev.leoferreira.wishlist.config.WishlistConfig;
import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.exceptions.WishlistAlreadyExistsException;
import dev.leoferreira.wishlist.exceptions.WishlistCountExceededException;
import dev.leoferreira.wishlist.exceptions.WishlistNotFoundException;
import dev.leoferreira.wishlist.gateways.WishlistGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultWishlistServiceUnitTest {

    @Spy
    private final WishlistConfig wishlistConfig = new WishlistConfig(20);
    @Mock
    private WishlistGateway wishlistGateway;
    @InjectMocks
    private DefaultWishlistService wishlistService;

    @Test
    public void givenValidUserIdAndProductId_WhenWishlistAlreadyExists_ThenThrowWishlistAlreadyExistsException() {
        String userId = "userId";
        String productId = "productId";

        given(wishlistGateway.getWishlistByProduct(userId, productId)).willReturn(
                Optional.of(mock(Wishlist.class))
        );

        assertThrows(
                WishlistAlreadyExistsException.class,
                () -> wishlistService.createWishlist(userId, productId)
        );

        then(wishlistGateway)
                .should()
                .getWishlistByProduct(userId, productId);
    }

    @Test
    public void givenValidUserIdAndProductId_WhenUserHas20Wishlists_ThenThrowWishlistCountExceededException() {
        String userId = "userId";
        String productId = "productId";

        given(wishlistGateway.getWishlistByProduct(userId, productId)).willReturn(
                Optional.empty()
        );

        given(wishlistGateway.getWishlistCountByUser(userId)).willReturn(
                20L
        );

        assertThrows(
                WishlistCountExceededException.class,
                () -> wishlistService.createWishlist(userId, productId)
        );

        then(wishlistGateway)
                .should()
                .getWishlistByProduct(userId, productId);

        then(wishlistGateway)
                .should()
                .getWishlistCountByUser(userId);
    }

    @Test
    public void givenValidUserIdAndProductId_WhenCreateWishlist_ThenCreateNewWishlist() {
        String userId = "userId";
        String productId = "productId";

        given(wishlistGateway.getWishlistByProduct(userId, productId)).willReturn(
                Optional.empty()
        );

        given(wishlistGateway.getWishlistCountByUser(userId)).willReturn(
                1L
        );

        wishlistService.createWishlist(userId, productId);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);

        then(wishlistGateway)
                .should()
                .createWishlist(wishlistCaptor.capture());

        Wishlist newWishlist = wishlistCaptor.getValue();

        assertEquals(userId, newWishlist.createdBy());
        assertEquals(productId, newWishlist.productId());
    }

    @Test
    public void givenValidUserId_WhenGetAllWishlists_ThenReturnWishlists() {
        String userId = "userId";

        List<Wishlist> wishlists = List.of(
                mock(Wishlist.class),
                mock(Wishlist.class),
                mock(Wishlist.class)
        );

        given(wishlistGateway.getAllWishlists(userId)).willReturn(wishlists);

        assertEquals(
                wishlists,
                wishlistService.getAllWishlists(userId)
        );

        then(wishlistGateway)
                .should()
                .getAllWishlists(userId);
    }

    @Test
    public void givenValidUserIdAndProductId_WhenWishlistNotFound_ThenThrowWishlistNotFoundException() {
        String userId = "userId";
        String productId = "productId";

        given(wishlistGateway.getWishlistByProduct(userId, productId)).willReturn(
                Optional.empty()
        );

        assertThrows(
                WishlistNotFoundException.class,
                ()->wishlistService.getWishlistByProduct(userId, productId)
                );


        then(wishlistGateway)
                .should()
                .getWishlistByProduct(userId, productId);
    }

    @Test
    public void givenValidUserIdAndProductId_WhenWishlistFound_ThenReturnWishlist() {
        String userId = "userId";
        String productId = "productId";

        Wishlist wishlist = mock(Wishlist.class);

        given(wishlistGateway.getWishlistByProduct(userId, productId)).willReturn(
                Optional.of(wishlist)
        );

        assertEquals(
                wishlist,
                wishlistService.getWishlistByProduct(userId, productId)
        );

        then(wishlistGateway)
                .should()
                .getWishlistByProduct(userId, productId);
    }

    @Test
    public void givenValidUserIdAndProductId_WhenRemoveWishlist_ThenWishlistIsRemoved() {
        String userId = "userId";
        String productId = "productId";

        Wishlist wishlist = mock(Wishlist.class);

        given(wishlistGateway.getWishlistByProduct(userId, productId)).willReturn(
                Optional.of(wishlist)
        );

        wishlistService.removeWishlist(userId, productId);

        then(wishlistGateway)
                .should()
                .getWishlistByProduct(userId, productId);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);

        then(wishlistGateway)
                .should()
                .removeWishlist(wishlistCaptor.capture());

        assertEquals(wishlist, wishlistCaptor.getValue());
    }
}
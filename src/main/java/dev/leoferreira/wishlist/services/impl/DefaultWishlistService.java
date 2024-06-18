package dev.leoferreira.wishlist.services.impl;

import dev.leoferreira.wishlist.config.WishlistConfig;
import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.exceptions.WishlistAlreadyExistsException;
import dev.leoferreira.wishlist.exceptions.WishlistCountExceededException;
import dev.leoferreira.wishlist.exceptions.WishlistNotFoundException;
import dev.leoferreira.wishlist.gateways.WishlistGateway;
import dev.leoferreira.wishlist.services.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultWishlistService implements WishlistService {

    private final WishlistConfig wishlistConfig;
    private final WishlistGateway wishlistGateway;

    public DefaultWishlistService(
            WishlistConfig wishlistConfig,
            WishlistGateway wishlistGateway
    ) {
        this.wishlistConfig = wishlistConfig;
        this.wishlistGateway = wishlistGateway;
    }

    @Override
    public Wishlist createWishlist(String userId, String productId) {
        validateIfWishlistExists(userId, productId);

        validateMaxLimitOfWishlistByUser(userId);

        Wishlist newWishlist = Wishlist.createWishlist(
                productId,
                userId
        );

        return wishlistGateway.createWishlist(newWishlist);
    }

    @Override
    public List<Wishlist> getAllWishlists(String userId) {
        return wishlistGateway.getAllWishlists(userId);
    }

    @Override
    public Wishlist getWishlistByProduct(String userId, String productId) {
        return wishlistGateway.getWishlistByProduct(userId, productId).orElseThrow(
                () -> new WishlistNotFoundException(
                        String.format(
                                "Cannot find wishlist register for current userId and productId '%s'",
                                productId
                        )
                )
        );
    }

    @Override
    public void removeWishlist(String userId, String productId) {
        Wishlist wishlist = getWishlistByProduct(userId, productId);
        wishlistGateway.removeWishlist(wishlist);
    }

    private void validateIfWishlistExists(String userId, String productId) {
        Optional<Wishlist> storedWishlist = wishlistGateway.getWishlistByProduct(
                userId, productId
        );

        if (storedWishlist.isPresent()) {
            throw new WishlistAlreadyExistsException(
                    String.format(
                            "Wishlist for current userId and productId '%s' already exists",
                            productId
                    )
            );
        }
    }

    private void validateMaxLimitOfWishlistByUser(String userId) {
        long wishlistCount = wishlistGateway.getWishlistCountByUser(userId);

        if (wishlistCount >= wishlistConfig.userMaxLimit()) {
            throw new WishlistCountExceededException(
                    String.format(
                            "You have reached the max limit of %d wishlists. To continue remove one wishlist first.",
                            wishlistConfig.userMaxLimit()
                    )
            );
        }
    }
}

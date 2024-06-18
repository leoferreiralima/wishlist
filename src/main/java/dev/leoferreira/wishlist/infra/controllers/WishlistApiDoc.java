package dev.leoferreira.wishlist.infra.controllers;

import dev.leoferreira.wishlist.infra.dtos.WishlistResponseDTO;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseListDTO;
import dev.leoferreira.wishlist.infra.swagger.ApiProductParam;
import dev.leoferreira.wishlist.infra.swagger.ApiUserParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Wishlist", description = "Wishlist Api")
@ApiUserParam
public interface WishlistApiDoc {
    @Operation(summary = "Get All Wishlists By User", description = "List all user wishlists")
    WishlistResponseListDTO getAllWishlists(String userId);

    @ApiProductParam
    @Operation(summary = "Create Wishlist", description = "Create a wishlist for user")
    WishlistResponseDTO createWishlist(
            String userId,
            String productId
    );

    @ApiProductParam
    @Operation(summary = "Get Wishlist By Product", description = "Get wishlist filtering by productId")
    WishlistResponseDTO getWishlistByProduct(
            String userId,
            String productId
    );

    @ApiProductParam
    @Operation(summary = "Remove User Wishlist", description = "Remove wishlist from user")
    void removeWishlist(
            String userId,
            String productId
    );
}

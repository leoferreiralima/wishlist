package dev.leoferreira.wishlist.infra.controllers;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseDTO;
import dev.leoferreira.wishlist.infra.mappers.dtos.ResponseDTOMapper;
import dev.leoferreira.wishlist.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/users/{userId}",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class WishlistController implements WishlistApiDoc {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    @Qualifier("wishlistResponseDTOMapper")
    private ResponseDTOMapper<WishlistResponseDTO, Wishlist> wishlistResponseDTOMapper;

    @GetMapping
    public List<WishlistResponseDTO> getAllWishlists(@PathVariable String userId) {
        return wishlistResponseDTOMapper.toDTOList(
                wishlistService.getAllWishlists(userId)
        );
    }

    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistResponseDTO createWishlist(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        return wishlistResponseDTOMapper.toDTO(
                wishlistService.createWishlist(
                        userId, productId
                )
        );
    }

    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistResponseDTO getWishlistByProduct(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        return wishlistResponseDTOMapper.toDTO(
                wishlistService.getWishlistByProduct(
                        userId, productId
                )
        );
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeWishlist(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        wishlistService.removeWishlistByProduct(userId, productId);
    }
}

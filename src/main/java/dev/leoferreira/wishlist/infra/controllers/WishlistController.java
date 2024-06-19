package dev.leoferreira.wishlist.infra.controllers;

import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseDTO;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseListDTO;
import dev.leoferreira.wishlist.infra.mappers.dtos.ResponseDTOMapper;
import dev.leoferreira.wishlist.services.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(
        value = "/users/{userId}",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class WishlistController implements WishlistApiDoc {
    private static Logger LOG = LoggerFactory.getLogger(WishlistController.class);
    private final WishlistService wishlistService;

    private final ResponseDTOMapper<WishlistResponseDTO, Wishlist> wishlistResponseDTOMapper;


    public WishlistController(
            WishlistService wishlistService,
            @Qualifier("wishlistResponseDTOMapper")
            ResponseDTOMapper<WishlistResponseDTO, Wishlist> wishlistResponseDTOMapper,
            RedisProperties redisProperties
    ) {
        this.wishlistService = wishlistService;
        this.wishlistResponseDTOMapper = wishlistResponseDTOMapper;

        LOG.info("Redis host: {}", redisProperties.getHost());
    }

    @GetMapping
    @Cacheable(value = "wishlist", key = "#userId")
    public WishlistResponseListDTO getAllWishlists(@PathVariable String userId) {
        return new WishlistResponseListDTO(
                wishlistResponseDTOMapper.toDTOList(
                        wishlistService.getAllWishlists(userId)
                )
        );
    }

    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Caching(
            evict = {
                    @CacheEvict(value = "wishlist", key = "#userId")
            },
            put = {
                    @CachePut(value = "wishlist-product", key = "#userId + ':' + #productId")
            }
    )
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
    @Cacheable(value = "wishlist-product", key = "#userId + ':' + #productId")
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
    @Caching(
            evict = {
                    @CacheEvict(value = "wishlist", key = "#userId"),
                    @CacheEvict(value = "wishlist-product", key = "#userId + ':' + #productId")
            }
    )
    public void removeWishlist(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        wishlistService.removeWishlist(userId, productId);
    }
}

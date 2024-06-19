package dev.leoferreira.wishlist.infra.controllers;

import dev.leoferreira.wishlist.config.WishlistConfig;
import dev.leoferreira.wishlist.domain.Wishlist;
import dev.leoferreira.wishlist.gateways.WishlistGateway;
import dev.leoferreira.wishlist.infra.dtos.WishlistResponseDTO;
import dev.leoferreira.wishlist.infra.mappers.dtos.ResponseDTOMapper;
import dev.leoferreira.wishlist.infra.persistence.entity.WishlistEntity;
import dev.leoferreira.wishlist.infra.persistence.respository.WishlistMongoRepository;
import dev.leoferreira.wishlist.test.intgration.AbstractIntegrationTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WishlistControllerIntegrationTest extends AbstractIntegrationTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("wishlistResponseDTOMapper")
    private ResponseDTOMapper<WishlistResponseDTO, Wishlist> wishlistResponseDTOMapper;

    @Autowired
    private WishlistMongoRepository wishlistRepository;

    @Autowired
    private WishlistGateway wishlistGateway;

    @Autowired
    private WishlistConfig wishlistConfig;

    @BeforeEach
    void setUp() {
        wishlistRepository.deleteAll();
    }

    @Test
    void givenValidUserId_WhenGetAllWishlists_ThenReturnWishlistList() throws Exception {
        String userId = "userId";
        Wishlist wishlist = createWishlist("wishlistId", userId, "productId", Instant.now());

        WishlistResponseDTO wishlistResponseDTO = wishlistResponseDTOMapper.toDTO(wishlist);

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wishlists[0].id").value(wishlistResponseDTO.id()))
                .andExpect(
                        jsonPath("$.wishlists[0].productId").value(
                                wishlistResponseDTO.productId()
                        )
                )
                .andExpect(
                        jsonPath("$.wishlists[0].createdBy").value(
                                wishlistResponseDTO.createdBy()
                        )
                )
                .andExpect(
                        jsonPath("$.wishlists[0].createdAt").value(
                                formatter.format(
                                        wishlistResponseDTO.createdAt().atZone(ZoneOffset.UTC)
                                )
                        )
                );
    }

    @Test
    void givenValidUserIdAndProductId_WhenCreateWishlist_ThenReturnWishlistResponse() throws Exception {
        String userId = "userId";
        String productId = "productId";

        mockMvc.perform(post("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.createdBy").value(userId));
    }

    @Test
    void givenValidUserIdAndProductId_WhenTryCreateDuplicatedProductWishlist_ThenRespondWithBadRequest() throws Exception {
        String userId = "userId";
        String productId = "productId";

        createWishlist(userId, productId);

        mockMvc.perform(post("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidUserIdAndProductId_WhenTryCreateMoreWishlistsThanUserMaxLimit_ThenRespondWithBadRequest() throws Exception {
        String userId = "userId";
        String productId = "productId";

        IntStream.range(0, wishlistConfig.userMaxLimit())
                        .forEach(
                                i -> createWishlist(
                                        String.valueOf(i),
                                        userId,
                                        String.format("%s-%d",productId, i)
                                )
                        );

        mockMvc.perform(post("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidUserIdAndProductId_WhenGetWishlistByProduct_ThenReturnWishlistResponse() throws Exception {
        String userId = "userId";
        String productId = "productId";

        Wishlist wishlist = createWishlist("wishlistId", userId, productId, Instant.now());

        mockMvc.perform(get("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(wishlist.productId()))
                .andExpect(jsonPath("$.createdBy").value(wishlist.createdBy()))
                .andExpect(jsonPath("$.createdAt").value(formatter.format(wishlist.createdAt().atZone(ZoneOffset.UTC))));
    }

    @Test
    void givenValidUserIdAndProductId_WhenTryGetNonexistentWishlistByProduct_ThenRespondWithNotFound() throws Exception {
        String userId = "userId";
        String productId = "productId";

        mockMvc.perform(get("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidUserIdAndProductId_WhenRemoveWishlist_ThenReturnNoContent() throws Exception {
        String userId = "userId";
        String productId = "productId";

        createWishlist(userId, productId);

        mockMvc.perform(delete("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isNoContent());

        List<WishlistEntity> wishlists = wishlistRepository.findByCreatedBy(userId);

        assertTrue(wishlists.isEmpty());
    }

    @Test
    void givenValidUserIdAndProductId_WhenTryRemoveNonexistentWishlist_ThenRespondWithNotFound() throws Exception {
        String userId = "userId";
        String productId = "productId";

        mockMvc.perform(delete("/users/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isNotFound());
    }

    private @NotNull Wishlist createWishlist(String userId, String productId) {
        return createWishlist("wishlistId", userId, productId, Instant.now());
    }

    private @NotNull Wishlist createWishlist(String wishlistId, String userId, String productId) {
        return createWishlist(wishlistId, userId, productId, Instant.now());
    }

    private @NotNull Wishlist createWishlist(String wishlistId, String userId, String productId, Instant createdAt) {
        Wishlist wishlist = new Wishlist(wishlistId, productId, userId, createdAt);

        wishlistGateway.createWishlist(wishlist);
        return wishlist;
    }

}
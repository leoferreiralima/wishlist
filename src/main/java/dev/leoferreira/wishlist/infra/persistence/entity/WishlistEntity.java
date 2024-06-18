package dev.leoferreira.wishlist.infra.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("wishlists")
public class WishlistEntity {

    @Id
    private final String id;
    private final String productId;
    private final String createdBy;
    private final LocalDateTime createdAt;

    public WishlistEntity(String id, String productId, String createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

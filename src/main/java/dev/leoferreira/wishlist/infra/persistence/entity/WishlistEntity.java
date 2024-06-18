package dev.leoferreira.wishlist.infra.persistence.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("wishlists")
@CompoundIndexes({
        @CompoundIndex(name = "createdBy_productId", def = "{'createdBy' : 1, 'productId': 1}"),
})
public class WishlistEntity {

    @Id
    private final String id;
    private final String productId;
    @Indexed
    private final String createdBy;
    @CreatedDate
    private final Instant createdAt;

    public WishlistEntity(String id, String productId, String createdBy, Instant createdAt) {
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}

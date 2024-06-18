package dev.leoferreira.wishlist.infra.persistence.respository;

import dev.leoferreira.wishlist.infra.persistence.entity.WishlistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistMongoRepository extends MongoRepository<WishlistEntity, String> {

    long countByCreatedBy(String createdBy);

    List<WishlistEntity> findByCreatedBy(String createdBy);

    Optional<WishlistEntity> findOneByCreatedByAndProductId(String createdBy, String productId);
}

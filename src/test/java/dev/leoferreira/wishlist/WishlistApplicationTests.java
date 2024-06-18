package dev.leoferreira.wishlist;

import dev.leoferreira.wishlist.test.config.EmbeddedMongoConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(EmbeddedMongoConfig.class)
class WishlistApplicationTests {

    @Test
    void contextLoads() {
    }

}

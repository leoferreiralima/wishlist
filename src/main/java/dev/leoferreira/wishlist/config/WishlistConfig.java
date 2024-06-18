package dev.leoferreira.wishlist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wishlist")
public record WishlistConfig(int userMaxLimit) {
}

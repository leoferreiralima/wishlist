package dev.leoferreira.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WishlistNotFoundException extends ResponseStatusException {
    public WishlistNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

}

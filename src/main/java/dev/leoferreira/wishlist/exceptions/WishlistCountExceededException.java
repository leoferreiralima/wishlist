package dev.leoferreira.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WishlistCountExceededException extends ResponseStatusException {
    public WishlistCountExceededException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

}

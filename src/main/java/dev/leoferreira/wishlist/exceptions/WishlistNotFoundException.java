package dev.leoferreira.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WishlistNotFoundException extends ResponseStatusException {
    public WishlistNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public WishlistNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }

    protected WishlistNotFoundException(String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.NOT_FOUND, reason, cause, messageDetailCode, messageDetailArguments);
    }
}

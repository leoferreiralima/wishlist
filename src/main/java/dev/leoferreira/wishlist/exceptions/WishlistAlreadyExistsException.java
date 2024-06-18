package dev.leoferreira.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WishlistAlreadyExistsException extends ResponseStatusException {
    public WishlistAlreadyExistsException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public WishlistAlreadyExistsException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }

    protected WishlistAlreadyExistsException(String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.BAD_REQUEST, reason, cause, messageDetailCode, messageDetailArguments);
    }
}

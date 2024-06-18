package dev.leoferreira.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WishlistCountExceededException extends ResponseStatusException {
    public WishlistCountExceededException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public WishlistCountExceededException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }

    protected WishlistCountExceededException(String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.BAD_REQUEST, reason, cause, messageDetailCode, messageDetailArguments);
    }
}

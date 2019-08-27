package bookworm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoBookInStockException extends RuntimeException {

    public NoBookInStockException(String message) {
        super(message);
    }

    public NoBookInStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

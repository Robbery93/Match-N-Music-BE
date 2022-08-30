package nl.robbertij.matchnmusic.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

    @Serial
    public static final long serialVersionUID = 1L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}

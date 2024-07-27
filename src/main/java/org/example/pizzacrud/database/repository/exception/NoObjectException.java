package org.example.pizzacrud.database.repository.exception;

public class NoObjectException extends IllegalArgumentException {
    public NoObjectException() {
    }

    public NoObjectException(String message) {
        super(message);
    }

    public NoObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}

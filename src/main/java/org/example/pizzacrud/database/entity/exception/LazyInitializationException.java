package org.example.pizzacrud.database.entity.exception;

public class LazyInitializationException extends RuntimeException {
    public LazyInitializationException() {
    }

    public LazyInitializationException(Throwable cause) {
        super(cause);
    }
}

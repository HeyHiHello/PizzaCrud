package org.example.pizzacrud.database.repository.exception;

public class WrongKeyException extends IllegalArgumentException {
    public WrongKeyException() {
    }

    public WrongKeyException(String message) {
        super(message);
    }
}

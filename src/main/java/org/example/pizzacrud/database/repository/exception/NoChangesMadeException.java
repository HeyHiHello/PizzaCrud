package org.example.pizzacrud.database.repository.exception;

import java.sql.SQLException;

public class NoChangesMadeException extends SQLException {
    public NoChangesMadeException() {
    }

    public NoChangesMadeException(String message) {
        super(message);
    }

    public NoChangesMadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoChangesMadeException(Throwable cause) {
        super(cause);

    }
}

package org.example.pizzacrud.database.repository.exception;

import java.sql.SQLException;

public class InternalDatabaseException extends SQLException {
    public InternalDatabaseException(String reason) {
        super(reason);
    }

    public InternalDatabaseException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public InternalDatabaseException(Throwable cause) {
        super(cause);
    }
}

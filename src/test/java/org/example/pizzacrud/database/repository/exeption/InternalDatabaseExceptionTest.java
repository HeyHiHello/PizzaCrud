package org.example.pizzacrud.database.repository.exeption;

import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InternalDatabaseExceptionTest {

    @Test
    void constructorsTest() {
        InternalDatabaseException internalDatabaseException = new InternalDatabaseException("test");
        Assertions.assertNotNull(internalDatabaseException);
        internalDatabaseException = new InternalDatabaseException(new Exception());
        Assertions.assertNotNull(internalDatabaseException);
        internalDatabaseException = new InternalDatabaseException("test", new Exception());
        Assertions.assertNotNull(internalDatabaseException);
    }
}

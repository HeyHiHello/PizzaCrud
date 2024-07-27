package org.example.pizzacrud.database.repository.exeption;

import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NoObjectExceptionTest {

    @Test
    void constructorTest() {
        NoObjectException exception = new NoObjectException();
        Assertions.assertNotNull(exception);
        exception = new NoObjectException("test");
        Assertions.assertNotNull(exception);
        exception = new NoObjectException("test", new Exception());
        Assertions.assertNotNull(exception);
    }
}

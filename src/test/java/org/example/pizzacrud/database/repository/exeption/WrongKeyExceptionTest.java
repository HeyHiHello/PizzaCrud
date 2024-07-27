package org.example.pizzacrud.database.repository.exeption;

import org.example.pizzacrud.database.repository.exception.WrongKeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WrongKeyExceptionTest {
    @Test
    void constructorTest() {
        WrongKeyException exception = new WrongKeyException();
        Assertions.assertNotNull(exception);
        exception = new WrongKeyException("test");
        Assertions.assertNotNull(exception);
    }
}

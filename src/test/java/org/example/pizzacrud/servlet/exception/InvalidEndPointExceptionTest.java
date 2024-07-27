package org.example.pizzacrud.servlet.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvalidEndPointExceptionTest {
    @Test
    void constructorTest() {
        InvalidEndPointException exception = new InvalidEndPointException();
        Assertions.assertNotNull(exception);
    }
}

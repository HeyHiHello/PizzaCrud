package org.example.pizzacrud.database.repository.exeption;

import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoChangesMadeExceptionTest {
    @Test
    void constructorTest() {
        NoChangesMadeException exception = new NoChangesMadeException();
        Assertions.assertNotNull(exception);
        exception = new NoChangesMadeException("test");
        Assertions.assertNotNull(exception);
        exception = new NoChangesMadeException("test", new Exception());
        Assertions.assertNotNull(exception);
        exception = new NoChangesMadeException(new Exception());
        Assertions.assertNotNull(exception);
    }
}

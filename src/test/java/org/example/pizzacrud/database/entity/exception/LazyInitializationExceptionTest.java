package org.example.pizzacrud.database.entity.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LazyInitializationExceptionTest {
    @Test
    void constructorTest() {
        LazyInitializationException lazyInitializationException = new LazyInitializationException();
        Assertions.assertNotNull(lazyInitializationException);
    }

    @Test
    void constructor2Test() {
        LazyInitializationException lazyInitializationException = new LazyInitializationException(new Exception());
        Assertions.assertNotNull(lazyInitializationException);
    }
}

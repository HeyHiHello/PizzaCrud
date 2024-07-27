package org.example.pizzacrud.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServletActionBuilderTest {
    private static ServletActionBuilder servletActionBuilder = new ServletActionBuilder() {
    };

    @Test
    void buildGetActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> servletActionBuilder.buildGetAction());
    }

    @Test
    void buildPostActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> servletActionBuilder.buildPostAction());
    }

    @Test
    void buildPutActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> servletActionBuilder.buildPutAction());
    }

    @Test
    void buildDeleteActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> servletActionBuilder.buildDeleteAction());
    }

    @Test
    void buildPatchActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> servletActionBuilder.buildPatchAction());
    }

    @Test
    void buildErrorActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> servletActionBuilder.buildErrorAction());
    }
}

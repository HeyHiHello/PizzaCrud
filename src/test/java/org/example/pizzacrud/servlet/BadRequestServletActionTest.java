package org.example.pizzacrud.servlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BadRequestServletActionTest {
    private static HttpServletResponse response;

    @BeforeAll
    public static void setUp() {
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    void executeTest() {
        BadRequestServletAction action = new BadRequestServletAction(response);
        Assertions.assertDoesNotThrow(action::execute);
    }
}

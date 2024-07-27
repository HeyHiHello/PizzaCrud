package org.example.pizzacrud.servlet;

import jakarta.servlet.http.HttpServletResponse;

public class BadRequestServletAction implements ServletAction {
    private final HttpServletResponse httpServletResponse;

    public BadRequestServletAction(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void execute() {
        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

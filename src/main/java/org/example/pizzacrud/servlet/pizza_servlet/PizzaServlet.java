package org.example.pizzacrud.servlet.pizza_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.example.pizzacrud.servlet.pizza_servlet.action.PizzaServletActionBuilder;

import java.io.IOException;

@WebServlet("/api/pizza/*")
public class PizzaServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, res);
            return;
        }

        super.service(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletActionBuilder actionBuilder = getBuilder(req, resp);

        ServletAction action = null;
        try {
            action = actionBuilder.buildGetAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletActionBuilder actionBuilder = getBuilder(req, resp);

        ServletAction action = null;
        try {
            action = actionBuilder.buildPostAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
        ServletActionBuilder actionBuilder = getBuilder(req, resp);

        ServletAction action = null;
        try {
            action = actionBuilder.buildPatchAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletActionBuilder actionBuilder = getBuilder(req, resp);
        ServletAction action = null;
        try {
            action = actionBuilder.buildDeleteAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    public ServletActionBuilder getBuilder(HttpServletRequest req, HttpServletResponse resp) {
        return new PizzaServletActionBuilder(req, resp);
    }
}

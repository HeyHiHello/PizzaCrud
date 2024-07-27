package org.example.pizzacrud.servlet.order_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.example.pizzacrud.servlet.order_servlet.action.OrderServletActionBuilder;

import java.io.IOException;

@WebServlet("/api/order/*")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletActionBuilder actionBuilder = getActionBuilder(req, resp);
        ServletAction action = null;
        try {
            action = actionBuilder.buildGetAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ServletActionBuilder actionBuilder = getActionBuilder(req, resp);
        ServletAction action = null;
        try {
            action = actionBuilder.buildPostAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletActionBuilder actionBuilder = getActionBuilder(req, resp);
        ServletAction action = null;
        try {
            action = actionBuilder.buildDeleteAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    public ServletActionBuilder getActionBuilder(HttpServletRequest req, HttpServletResponse resp) {
        return new OrderServletActionBuilder(req, resp);
    }
}

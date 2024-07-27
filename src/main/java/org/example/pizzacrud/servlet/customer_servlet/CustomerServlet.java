package org.example.pizzacrud.servlet.customer_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.customer_servlet.action.CustomerServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;

import java.io.IOException;

@WebServlet("/api/customer/*")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, resp);
            return;
        }

        super.service(req, resp);
    }

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
        ServletActionBuilder actionBuilder = getActionBuilder(req, resp);
        ServletAction action = null;
        try {
            action = actionBuilder.buildPatchAction();
        } catch (InvalidEndPointException e) {
            action = actionBuilder.buildErrorAction();
        }
        action.execute();
    }

    public ServletActionBuilder getActionBuilder(HttpServletRequest req, HttpServletResponse resp) {
        return new CustomerServletActionBuilder(req, resp);
    }
}

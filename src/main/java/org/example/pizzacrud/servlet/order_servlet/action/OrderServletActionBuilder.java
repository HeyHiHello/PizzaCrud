package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.BadRequestServletAction;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;

public class OrderServletActionBuilder implements ServletActionBuilder {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public OrderServletActionBuilder(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public ServletAction buildGetAction() throws InvalidEndPointException {
        if (request.getPathInfo() == null) {
            throw new InvalidEndPointException();
        }

        String[] pathInfoSplited = request.getPathInfo().split("/");

        if (pathInfoSplited.length > 2) {
            throw new InvalidEndPointException();
        }

        int id;
        try {
            id = Integer.parseInt(pathInfoSplited[1]);
        } catch (NumberFormatException e) {
            throw new InvalidEndPointException();
        }

        return new GetOrderByIdServletAction(id, response);
    }

    @Override
    public ServletAction buildPostAction() throws InvalidEndPointException {
        if (request.getPathInfo() == null) {
            return new PostOrderServletAction(request, response);
        }
        throw new InvalidEndPointException();
    }

    @Override
    public ServletAction buildDeleteAction() throws InvalidEndPointException {
        if (request.getPathInfo() == null) {
            throw new InvalidEndPointException();
        }

        String[] pathInfoSplited = request.getPathInfo().split("/");

        if (pathInfoSplited.length > 2) {
            throw new InvalidEndPointException();
        }

        int id;
        try {
            id = Integer.parseInt(pathInfoSplited[1]);
        } catch (NumberFormatException e) {
            throw new InvalidEndPointException();
        }

        return new DeleteOrderServletAction(id, response);
    }

    @Override
    public ServletAction buildErrorAction() {
        return new BadRequestServletAction(response);
    }
}

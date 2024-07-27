package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.BadRequestServletAction;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;

public class CustomerServletActionBuilder implements ServletActionBuilder {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public CustomerServletActionBuilder(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public ServletAction buildGetAction() throws InvalidEndPointException {
        if (request.getPathInfo() == null) {
            return new GetAllCustomersServletAction(response);
        }
        String[] pathInfoSplited = request.getPathInfo().split("/");

        int id = 0;
        try {
            id = Integer.parseInt(pathInfoSplited[1]);
        } catch (NumberFormatException e) {
            throw new InvalidEndPointException();
        }
        if (pathInfoSplited.length == 2) {
            return new GetCustomerByIdServletAction(id, response);
        }

        if (pathInfoSplited.length == 3) {
            if (pathInfoSplited[2].equals("address")) {
                return new GetCustomerAddressServletAction(id, response);
            } else if (pathInfoSplited[2].equals("orders")) {
                return new GetCustomerOrdersServletAction(id, response);
            }
        }

        throw new InvalidEndPointException();
    }

    @Override
    public ServletAction buildPostAction() throws InvalidEndPointException {
        if (request.getPathInfo() == null) {
            return new PostCustomerServletAction(request, response);
        }
        throw new InvalidEndPointException();
    }

    @Override
    public ServletAction buildPatchAction() throws InvalidEndPointException {
        if (request.getPathInfo() != null) {
            String[] pathInfoSplited = request.getPathInfo().split("/");
            int id;
            try {
                id = Integer.parseInt(pathInfoSplited[1]);
            } catch (NumberFormatException e) {
                throw new InvalidEndPointException();
            }
            return new PatchCustomerServletAction(id, request, response);
        }
        throw new InvalidEndPointException();
    }

    @Override
    public ServletAction buildDeleteAction() throws InvalidEndPointException {
        if (request.getPathInfo() != null) {
            String[] pathInfoSplited = request.getPathInfo().split("/");
            int id;
            try {
                id = Integer.parseInt(pathInfoSplited[1]);
            } catch (NumberFormatException e) {
                throw new InvalidEndPointException();
            }
            return new DeleteCustomerServletAction(id, response);
        }
        throw new InvalidEndPointException();
    }

    @Override
    public ServletAction buildErrorAction() {
        return new BadRequestServletAction(response);
    }
}

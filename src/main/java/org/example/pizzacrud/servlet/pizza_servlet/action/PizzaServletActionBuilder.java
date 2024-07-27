package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;

public class PizzaServletActionBuilder implements ServletActionBuilder {
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public PizzaServletActionBuilder(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public ServletAction buildGetAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            return new GetAllPizzasServletAction(httpServletResponse);
        } else {
            String[] pathInfo = httpServletRequest.getPathInfo().split("/");
            int id;
            try {
                id = Integer.parseInt(pathInfo[1]);

            } catch (NumberFormatException e) {
                throw new InvalidEndPointException();
            }
            if (pathInfo.length > 2 && pathInfo[2].equals("ingredients")) {
                return new GetPizzaIngredientsServletAction(id, httpServletResponse);
            }
            return new GetPizzaByIdServletAction(id, httpServletResponse);
        }
    }

    @Override
    public ServletAction buildPostAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            return new PostPizzaServletAction(httpServletRequest, httpServletResponse);
        } else {
            String[] pathInfo = httpServletRequest.getPathInfo().split("/");
            int id;
            try {
                id = Integer.parseInt(pathInfo[1]);
            } catch (NumberFormatException e) {
                throw new InvalidEndPointException();
            }
            if (pathInfo[2].equals("ingredients")) {
                return new PostPizzaIngredientsServletAction(id, httpServletRequest, httpServletResponse);
            }
            throw new InvalidEndPointException();
        }
    }

    @Override
    public ServletAction buildDeleteAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            throw new InvalidEndPointException();
        }

        String[] pathInfo = httpServletRequest.getPathInfo().split("/");
        try {
            int id = Integer.parseInt(pathInfo[1]);
            return new DeletePizzaServletAction(id, httpServletResponse);
        } catch (NumberFormatException e) {
            throw new InvalidEndPointException();
        }
    }

    @Override
    public ServletAction buildPatchAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            throw new InvalidEndPointException();
        }
        String[] pathInfo = httpServletRequest.getPathInfo().split("/");
        int id;
        try {
            id = Integer.parseInt(pathInfo[1]);
        } catch (NumberFormatException e) {
            throw new InvalidEndPointException();
        }
        return new PatchPizzaServletAction(id, httpServletRequest, httpServletResponse);
    }
}

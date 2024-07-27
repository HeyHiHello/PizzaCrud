package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.BadRequestServletAction;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;

public class IngredientServletActionBuilder implements ServletActionBuilder {
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public IngredientServletActionBuilder(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public ServletAction buildGetAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            return new GetAllIngredientsServletAction(httpServletResponse);
        } else {
            String[] pathInfo = httpServletRequest.getPathInfo().split("/");
            try {
                int id = Integer.parseInt(pathInfo[1]);
                return new GetIngredientByIdServletAction(id, httpServletResponse);
            } catch (NumberFormatException e) {
                throw new InvalidEndPointException();
            }
        }
    }

    @Override
    public ServletAction buildPostAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            return new PostIngredientServletAction(httpServletRequest, httpServletResponse);
        } else {
            throw new InvalidEndPointException();
        }
    }

    @Override
    public ServletAction buildPatchAction() throws InvalidEndPointException {
        if (httpServletRequest.getPathInfo() == null) {
            throw new InvalidEndPointException();
        }

        String[] pathInfo = httpServletRequest.getPathInfo().split("/");
        try {
            int id = Integer.parseInt(pathInfo[1]);
            return new PatchIngredientServletAction(id, httpServletRequest, httpServletResponse);
        } catch (NumberFormatException e) {
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
            return new DeleteIngredientServletAction(id, httpServletResponse);
        } catch (NumberFormatException e) {
            throw new InvalidEndPointException();
        }
    }

    @Override
    public ServletAction buildErrorAction() {
        return new BadRequestServletAction(httpServletResponse);
    }
}

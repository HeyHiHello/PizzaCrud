package org.example.pizzacrud.servlet;

import org.example.pizzacrud.servlet.exception.InvalidEndPointException;

public interface ServletActionBuilder {
    default ServletAction buildGetAction() throws InvalidEndPointException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default ServletAction buildPostAction() throws InvalidEndPointException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default ServletAction buildPutAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default ServletAction buildDeleteAction() throws InvalidEndPointException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default ServletAction buildPatchAction() throws InvalidEndPointException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default ServletAction buildErrorAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

package org.example.pizzacrud.database.repository;

import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Repository<V, K> {
    default List<V> findAll() throws SQLException {
        return new ArrayList<>();
    }

    default Optional<V> findById(K id) throws SQLException {
        return Optional.empty();
    }

    default V save(V entity) throws SQLException, NoChangesMadeException {
        return null;
    }

    default V update(V entity) throws NoChangesMadeException, SQLException {
        return null;
    }

    default V create(V entity) throws SQLException, NoChangesMadeException {
        return null;
    }

    default void delete(K id) throws SQLException, NoChangesMadeException {

    }

    default boolean exists(K id) throws SQLException {
        return false;
    }
}

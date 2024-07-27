package org.example.pizzacrud.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class RepositoryTest {
    private static Repository<String, Integer> testRepository;

    @BeforeAll
    static void setUp() {
        testRepository = new Repository<>() {
        };
    }

    @Test
    void findAllDefaultTest() throws SQLException {

        List<String> result;

        result = testRepository.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void finaByIdDefaultTest() throws SQLException {

        Optional<String> result = testRepository.findById(1);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void saveDefaultTest() throws SQLException {

        String test = "test";

        Assertions.assertNull(testRepository.save(test));
    }

    @Test
    void createDefaultTest() throws SQLException {

        String test = "test";

        Assertions.assertNull(testRepository.create(test));
    }

    @Test
    void updateDefaultTest() throws SQLException {

        String test = "test";

        Assertions.assertNull(testRepository.update(test));
    }

    @Test
    void deleteDefaultTest() {
        Assertions.assertDoesNotThrow(() -> testRepository.delete(1));
    }

    @Test
    void existsDefaultTest() throws SQLException {
        Assertions.assertFalse(testRepository.exists(1));
    }
}

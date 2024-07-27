package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.PizzaRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PizzaService {
    private PizzaRepository pizzaRepository = RepositoryBuilder.buildPizzaRepository();
    private IngredientService ingredientService = ServiceBuilder.buildIngredientService();


    /**
     * Get Pizza by id
     *
     * @param id id of a pizza to be found
     * @return Pizza object
     * @throws InternalDatabaseException Internal database error
     */
    public Pizza getById(int id) throws InternalDatabaseException {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        return pizzaOptional.orElseThrow(NoObjectException::new);
    }

    /**
     * Get all Pizzas in database
     *
     * @return List of all Pizzas
     * @throws InternalDatabaseException Internal database error
     */
    public List<Pizza> getAll() throws InternalDatabaseException {
        return pizzaRepository.findAll();
    }

    /**
     * Delete Pizza by id
     *
     * @param id id of a Pizza to be deleted
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database returned no affected rows
     */
    public void delete(int id) throws InternalDatabaseException, NoChangesMadeException {
        pizzaRepository.delete(id);
    }

    /**
     * Delete Pizza
     *
     * @param pizza Pizza to be deleted
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database returned no affected rows
     */
    public void delete(Pizza pizza) throws InternalDatabaseException, NoChangesMadeException {
        pizzaRepository.delete(pizza.getId());
    }

    /**
     * Create or update Pizza
     *
     * @param pizza Pizza object to be saved
     * @return Saved Pizza. If created new id is set.
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database returned no affected rows
     */
    public Pizza save(Pizza pizza) throws NoChangesMadeException, InternalDatabaseException {
        try {
            pizzaRepository.save(pizza);
        } catch (NoChangesMadeException e) {
            throw e;
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }

        return pizza;
    }

    public void setPizzaIngredient(Pizza pizza) throws InternalDatabaseException {
        try {
            pizzaRepository.savePizzasIngredients(pizza);
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }
    }

    public void setIngredientsByIds(Pizza pizza, List<Integer> ingredientsIds) throws InternalDatabaseException {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Integer id : ingredientsIds) {
            Ingredient ingredient = ingredientService.getById(id);
            ingredients.add(ingredient);
        }

        pizza.setIngredients(ingredients);
        setPizzaIngredient(pizza);
    }

    public void setPizzaRepository(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
}

package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.IngredientRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;

import java.util.List;
import java.util.Optional;

public class IngredientService {
    private IngredientRepository ingredientRepository = RepositoryBuilder.buildIngredientRepository();

    /**
     * Get Ingredient by id
     *
     * @param id id of an Ingredient to be found
     * @return Ingredient
     * @throws InternalDatabaseException Internal database error
     */
    public Ingredient getById(int id) throws InternalDatabaseException {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        return ingredientOptional.orElseThrow(NoObjectException::new);
    }

    /**
     * Get all Ingredients in database
     *
     * @return List of all Ingredients
     * @throws InternalDatabaseException Internal database error
     */
    public List<Ingredient> getAll() throws InternalDatabaseException {
        return ingredientRepository.findAll();
    }

    /**
     * Delete Ingredient by id
     *
     * @param id id of an Ingredient to be deleted
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database return no affected rows
     */
    public void delete(int id) throws InternalDatabaseException, NoChangesMadeException {
        ingredientRepository.delete(id);
    }

    /**
     * Delete Ingredient
     *
     * @param ingredient ingredient to be deleted
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database returned no affected rows
     */
    public void delete(Ingredient ingredient) throws InternalDatabaseException, NoChangesMadeException {
        ingredientRepository.delete(ingredient.getId());
    }

    /**
     * Create or update Ingredient
     *
     * @param ingredient ingredient to be saved
     * @return Same ingredient as in parameters. If Ingredient was inserted, new id is set.
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database returned no affected rows
     */
    public Ingredient save(Ingredient ingredient) throws InternalDatabaseException, NoChangesMadeException {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Save List of Ingredients
     *
     * @param ingredients List of ingredients
     * @throws InternalDatabaseException Internal database error
     * @throws NoChangesMadeException    Database returned no affected rows
     */
    public void saveAll(List<Ingredient> ingredients) throws InternalDatabaseException, NoChangesMadeException {
        boolean result = ingredientRepository.saveAll(ingredients);
        if (!result) {
            throw new NoChangesMadeException();
        }
    }

    public void setIngredientRepository(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
}

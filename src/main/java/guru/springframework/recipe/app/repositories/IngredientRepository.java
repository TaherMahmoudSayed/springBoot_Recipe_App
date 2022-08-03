package guru.springframework.recipe.app.repositories;

import guru.springframework.recipe.app.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {
}



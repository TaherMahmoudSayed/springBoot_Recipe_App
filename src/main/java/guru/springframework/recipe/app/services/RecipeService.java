package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService  {
    Set<Recipe> getRecipes();
    Optional<Recipe> findById(long id);
    RecipeCommand findRecipeCommandById(long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(long id);


}

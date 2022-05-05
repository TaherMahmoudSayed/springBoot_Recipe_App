package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;

import java.util.Set;

public interface RecipeService  {
    Set<Recipe> getRecipes();
}

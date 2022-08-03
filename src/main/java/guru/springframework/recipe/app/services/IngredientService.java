package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(long recipeId,long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteById(Long recipeId, Long ingredientId);
}

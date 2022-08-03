package guru.springframework.recipe.app.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import guru.springframework.recipe.app.services.RecipeService;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    private final RecipeRepository recipeRepository;
    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter, RecipeRepository recipeRepository) {
        this.uomConverter = uomConverter;
        this.recipeRepository = recipeRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setRecipe(recipeRepository.findById(source.getRecipeId()).orElseGet(null));
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        UnitOfMeasure uom=uomConverter.convert(source.getUnitOfMeasure());
        ingredient.setUnitOfMeasure(uom);
        return ingredient;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}

package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.app.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import guru.springframework.recipe.app.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

public class IngredientServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientService ingredientService;

    @BeforeEach
    void setUp()  {
        MockitoAnnotations.openMocks(this);
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository,ingredientToIngredientCommand, unitOfMeasureRepository, ingredientCommandToIngredient);
    }
    @Test
    void  findByRecipeIdAndIngredientId(){
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        //when
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        IngredientCommand ingredientCommand=ingredientService.findByRecipeIdAndIngredientId(1L,2L);
        assertEquals(Long.valueOf(2L),ingredientCommand.getId());
        Mockito.verify(recipeRepository).findById(anyLong());



    }
}

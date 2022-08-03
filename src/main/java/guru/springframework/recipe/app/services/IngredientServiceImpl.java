package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.app.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.Ingredient;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import guru.springframework.recipe.app.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {
        Optional<Recipe> recipeOptional=recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){
            log.error("recipe id not found :"+ recipeId);
        }
        Recipe recipe=recipeOptional.get();
        Optional<Ingredient> ingredientOptional=recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
        Ingredient ingredient=ingredientOptional.orElseGet(null);
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        if(ingredientCommand==null){
            log.error("ingredient id not found :"+ ingredientId);
        }
        return ingredientCommand;

    }

    @Override

    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Recipe recipe=recipeRepository.findById(ingredientCommand.getRecipeId()).orElseGet(null);
        if(recipe==null){
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return ingredientCommand;
        }
        else{
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                UnitOfMeasureCommand unitOfMeasureCommand=new UnitOfMeasureToUnitOfMeasureCommand()
                        .convert(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).orElseGet(null));
                ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            Recipe savedRecipe=recipeRepository.save(recipe);

            Optional<Ingredient> savedOptionalIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

            if(!savedOptionalIngredient.isPresent()){
                savedOptionalIngredient = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }
            return ingredientToIngredientCommand.convert(savedOptionalIngredient.get());
        }

    }




    @Override
    public void deleteById(Long recipeId, Long idToDelete) {

        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
    }


}

package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.converters.RecipeCommandToRecipe;
import guru.springframework.recipe.app.converters.RecipeToRecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;

import guru.springframework.recipe.app.exceptions.NotFoundException;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;


    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("i'm inside recipe service");
        Set<Recipe> recipes=new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> recipes.add(recipe));
        return recipes;
    }
    @Override
    public Optional<Recipe> findById(long id){
        Optional<Recipe> recipe=recipeRepository.findById(id);
        if(recipe.isEmpty())
            throw new NotFoundException("Recipe Not Found For Id Value: "+id);

        return recipe;
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(long id) {
        return recipeToRecipeCommand.convert(recipeRepository.findById(id).orElseGet(null));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe recipe=recipeCommandToRecipe.convert(command);
        Recipe savedRecipe=recipeRepository.save(recipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }
}

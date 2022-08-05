package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.converters.RecipeCommandToRecipe;
import guru.springframework.recipe.app.converters.RecipeToRecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;

import guru.springframework.recipe.app.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith( JUnit4.class)
class RecipeServiceImplTest {
    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Recipe recipe=new Recipe();
        Set<Recipe> recipes=new HashSet<>();
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);
        assertEquals(1,recipeService.getRecipes().size());
        Mockito.verify(recipeRepository,Mockito.times(1)).findAll();
    }
    @Test
    void findRecipeById(){
        Recipe recipe=new Recipe().builder().id(1L).build();
        Optional<Recipe> recipeOptional=Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Optional<Recipe> recipe1 =recipeService.findById(1L);
        assertNotNull(recipe1);
        assertEquals(recipeOptional,recipe1);
        Mockito.verify(recipeRepository,Mockito.times(1)).findById(anyLong());
    }


    @Test
    void getRecipeByIdNotFound() throws Exception{
        Optional<Recipe> recipeOptional=Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

       assertThrows(RuntimeException.class,()->recipeService.findById(1L));
    }
}
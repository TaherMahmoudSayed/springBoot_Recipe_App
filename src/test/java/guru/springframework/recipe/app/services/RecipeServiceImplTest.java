package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceImplTest {
    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {
        Recipe recipe=new Recipe();
        Set<Recipe> recipes=new HashSet<>();
        recipes.add(recipe);
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);
        assertEquals(1,recipeService.getRecipes().size());
        Mockito.verify(recipeRepository,Mockito.times(1)).findAll();
    }
}
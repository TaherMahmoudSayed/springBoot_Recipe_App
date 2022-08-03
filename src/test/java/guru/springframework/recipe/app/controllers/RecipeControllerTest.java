package guru.springframework.recipe.app.controllers;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import guru.springframework.recipe.app.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    Model model;
    RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    void ShowRecipeMVC() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));


        Mockito.verify(recipeService).findById(Mockito.anyLong());

    }
    @Test
    void ShowRecipe()  {
        String pageName=recipeController.ShowRecipe(1L,model);

        Mockito.verify(recipeService, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(model, Mockito.times(1)).addAttribute(eq("recipe"),Mockito.any());
    }
    @Test
    void deleteRecipeById(){
        long resipeId=1L;
        recipeController.DeleteRecipe(resipeId);
        Mockito.verify(recipeService).deleteById(anyLong());
    }
}
package guru.springframework.recipe.app.controllers;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.services.IngredientService;
import guru.springframework.recipe.app.services.RecipeService;
import guru.springframework.recipe.app.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @Mock
    IngredientService ingredientService;
    IngredientController ingredientController;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController=new IngredientController(recipeService,ingredientService,unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }
    @Test
    void listIngredientsOfRecipeTest() throws Exception {
        RecipeCommand recipeCommand=new RecipeCommand();
        recipeCommand.setId(1L);
        recipeCommand.getIngredients().add(new IngredientCommand());
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

    }
    @Test
    void ShowRecipeIngredientMVCTest()throws Exception{
        IngredientCommand ingredientCommand=new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }
}
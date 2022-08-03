package guru.springframework.recipe.app.controllers;

import guru.springframework.recipe.app.commands.IngredientCommand;
import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.services.IngredientService;
import guru.springframework.recipe.app.services.RecipeService;
import guru.springframework.recipe.app.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/recipe")
@Slf4j
public class IngredientController {
private final RecipeService recipeService;
private final IngredientService ingredientService;
private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping
    @RequestMapping("/{id}/ingredients")
    public String GetIngredients(@PathVariable long id, Model model){
        model.addAttribute("recipe",recipeService.findRecipeCommandById(id));
        return "/recipe/ingredient/list";
    }
    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/show")
    public String ShowRecipeIngredient(@PathVariable long recipeId,@PathVariable long id,Model model){
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId,id);
        model.addAttribute("ingredient",ingredientCommand);
        return "recipe/ingredient/show";
    }
    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/update")
    public String UpdateRecipeIngredient(@PathVariable long recipeId,@PathVariable long id,Model model){
        IngredientCommand ingredientCommand=ingredientService.findByRecipeIdAndIngredientId(recipeId,id);
        Set<UnitOfMeasureCommand>unitOfMeasureCommandSet =unitOfMeasureService.listAllUoms();
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",unitOfMeasureCommandSet);
        return "recipe/ingredient/ingredientform";
    }
    @PostMapping
    @RequestMapping("{recipeId}/ingredient")
    public String SaveOrUpdate(@PathVariable long recipeId,@ModelAttribute IngredientCommand ingredientCommand){
       IngredientCommand SavedIngredientCommand=
               ingredientService.saveIngredientCommand(ingredientCommand);
       return "redirect:/recipe/"+recipeId+"/ingredient/"+SavedIngredientCommand.getId()+"/show";

    }
    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/new")
    public String NewRecipe(@PathVariable long recipeId,Model model){
        RecipeCommand recipeCommand=recipeService.findRecipeCommandById(recipeId);
        if(recipeCommand==null)
        {
            log.error("recipe not found");
            return "redirect:/";
        }
        IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient",ingredientCommand);
        Set<UnitOfMeasureCommand>unitOfMeasureCommandSet=unitOfMeasureService.listAllUoms();
        model.addAttribute("uomList",unitOfMeasureCommandSet);
        return "recipe/ingredient/ingredientform";

    }
    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String Delete(@PathVariable long recipeId,@PathVariable long ingredientId,Model model){
        ingredientService.deleteById(recipeId,ingredientId);
        return "redirect:/recipe/"+recipeId+"/ingredients";
    }

}

package guru.springframework.recipe.app.controllers;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.domain.Recipe;

import guru.springframework.recipe.app.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/recipe")
@Slf4j
public class RecipeController {
    final private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping("/{id}/show")
    public String ShowRecipe(@PathVariable Long id,Model model ){

            Optional<Recipe> recipe=recipeService.findById(id);
            model.addAttribute("recipe",recipe.get());

            return "recipe/show";


    }
    @GetMapping
    @RequestMapping("/add")
    public String AddNewRecipeGet(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeForm";

    }
    @PostMapping
    @RequestMapping("/{id}/update")
    public String UpdateRecipe(@PathVariable long id ,Model model){

        model.addAttribute("recipe",recipeService.findRecipeCommandById(id));
        return "recipe/recipeForm";

    }
    @PostMapping
    @RequestMapping("/save")
    public String AddNewRecipePost(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "recipe/recipeForm";
        else {
            command = recipeService.saveRecipeCommand(command);
            return "redirect:/recipe/" + command.getId() + "/show";
        }
    }
    @GetMapping
    @RequestMapping("/{id}/delete")
    public String DeleteRecipe(@PathVariable long id){
        recipeService.deleteById(id);
        return "redirect:/";
    }





}

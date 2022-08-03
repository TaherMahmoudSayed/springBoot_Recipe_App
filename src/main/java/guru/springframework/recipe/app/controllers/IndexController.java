package guru.springframework.recipe.app.controllers;

import guru.springframework.recipe.app.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        System.out.println("live load tester");

        model.addAttribute("recipes",recipeService.getRecipes());


        return "index";
    }
}

package guru.springframework.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class index {


    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        System.out.println("live load tester");

        return "index";
    }
}

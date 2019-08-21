package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeControllers {

    private RecipeService recipeService;

    @Autowired
    public RecipeControllers(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipes/guacamole")
    public String getRecipeGuacamole(Model model){

        model.addAttribute("recipe", recipeService.getRecipe("Guacamole"));
        return "guacamoleRecipe";
    }

    @RequestMapping("/recipes/tacos")
    public String getRecipeTaco(Model model){

        model.addAttribute("recipe", recipeService.getRecipe("Tacos"));
        return "tacosRecipe";
    }
}

package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexControllers {

    private RecipeService recipeService;

    @Autowired
    public IndexControllers(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage(Model model){
        log.debug("I'm in controller");
        model.addAttribute("recipes" ,recipeService.getRecipes());

        return "index";
    }
}

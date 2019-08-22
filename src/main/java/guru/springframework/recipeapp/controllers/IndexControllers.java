package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.domain.Category;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.CategoryRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import guru.springframework.recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexControllers {

    private RecipeService recipeService;

    @Autowired
    public IndexControllers(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes" ,recipeService.getRecipes());

        return "index";
    }
}

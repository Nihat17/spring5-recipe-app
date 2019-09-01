package guru.springframework.recipeapp.services;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    private static final String DESCRIPTION = "NEW DESCRIPTION";

    @Autowired
    RecipeRepository repository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeToRecipeCommand toRecipeCommand;

    @Autowired
    RecipeCommandToRecipe toRecipe;

    @Transactional
    @Test
    public void testSaveOfDescription(){
        //GIVEN
        Iterable<Recipe> recipes = repository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand recipeCommand = toRecipeCommand.convert(testRecipe);

        //WHEN
        recipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //THEN
        assertNotNull(savedRecipeCommand);
        assertEquals(DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
    }
}

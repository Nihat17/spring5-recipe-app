package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;

public class RecipeToRecipeCommandTest {

    private static final Long RECIPE_ID = 1L;
    private static final String DESCRIPTION = "Recipe desc";
    private static final int PREP_TIME = 2;
    private static final int COOK_TIME = 3;
    private static final int SERVINGS = 5;
    private static final Difficulty DIFFICULTY = Difficulty.HARD;
    private static final String SOURCE = "Simple Recipes";
    private static final String URL = "www....com";
    private static final String DIRECTIONS = "DIRECTIONS";
    private static final Long CAT_ID1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long ING_ID1 = 3L;
    private static final Long ING_ID2 = 4L;
    private static final Long NOTES_ID = 9L;

    RecipeToRecipeCommand toRecipeCommand;

    @Before
    public void setUp() throws Exception {
        toRecipeCommand = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new NotesToNotesCommand(), new IngredientToIngredientCommand());
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(toRecipeCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(toRecipeCommand.convert(new Recipe()));
    }

    @Test
    public void convertTest() {
        //GIVEN
        Recipe recipe = new Recipe();

        recipe.setId(RECIPE_ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setSource(SOURCE);
        recipe.setServings(SERVINGS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setUrl(URL);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ING_ID1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ING_ID2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        Category category1 = new Category();
        category1.setId(CAT_ID1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        //WHEN
        RecipeCommand command = toRecipeCommand.convert(recipe);

        //THEN
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
        assertEquals(NOTES_ID, command.getNotes().getId());
    }
}
package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.CategoryCommand;
import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Difficulty;
import guru.springframework.recipeapp.domain.Notes;
import guru.springframework.recipeapp.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.xmlunit.diff.Diff;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    RecipeCommandToRecipe converter;

    private final static Long LONG_VALUE = new Long(1L);
    private final static String DESCRIPTION = "Recipe";
    private final static int PREP_TIME = 1;
    private final static int COOK_TIME = 1;
    private final static int SERVINGS = 2;
    private final static String SOURCE = "Simple Recipe";
    private final static String URL = "www....com";
    private final static String DIRECTIONS = "Directions";
    private final static Difficulty DIFFICULTY = Difficulty.MODERATE;
    private final static Long CAT_ID_1 = new Long(1);
    private final static Long CAT_ID_2 = new Long(2);
    private final static Long ING_ID_1 = new Long(3);
    private final static Long ING_ID_2 = new Long(4);
    private final static Long NOTES_ID = new Long(7);

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(), new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convertTest() {
        //GIVEN
        RecipeCommand command = new RecipeCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);
        command.setCookTime(COOK_TIME);
        command.setDifficulty(DIFFICULTY);
        command.setDirections(DIRECTIONS);
        command.setPrepTime(PREP_TIME);
        command.setServings(SERVINGS);
        command.setSource(SOURCE);
        command.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        command.setNotes(notes);

        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(ING_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(ING_ID_2);

        command.getIngredients().add(ingredient1);
        command.getIngredients().add(ingredient2);

        CategoryCommand category1 = new CategoryCommand();
        category1.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID_2);

        command.getCategories().add(category1);
        command.getCategories().add(category2);

        //WHEN
        Recipe recipe = converter.convert(command);

        //THEN
        assertNotNull(recipe);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
        assertEquals(NOTES_ID, command.getNotes().getId());
    }
}
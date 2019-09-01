package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    private static final Long LONG_VALUE = new Long(1L);
    private static final String DESCRIPTION = "new Description";

    IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient();
    }

    @Test
    public void testNullValue(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convertTest() {
        //GIVEN
        IngredientCommand command = new IngredientCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //WHEN
        Ingredient ingredient = converter.convert(command);

        //THEN
        assertNotNull(ingredient);
        assertEquals(LONG_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}
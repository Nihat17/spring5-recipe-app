package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    private final static Long LONG_VALUE = new Long(1L);
    private final static String DESCRIPTION = "new Description";

    IngredientToIngredientCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void convertTest() {

        //GIVEN
        Ingredient ingredient = new Ingredient();
        ingredient.setId(LONG_VALUE);
        ingredient.setDescription(DESCRIPTION);

        //WHEN
        IngredientCommand returnedValue = converter.convert(ingredient);

        //THEN
        assertNotNull(returnedValue);
        assertEquals(LONG_VALUE, returnedValue.getId());
        assertEquals(DESCRIPTION, returnedValue.getDescription());
    }
}
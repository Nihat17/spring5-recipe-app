package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.CategoryCommand;
import guru.springframework.recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand converter;

    private final static Long LONG_VALUE = new Long(1);
    private final static String DESCRIPTION = "new category";

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convertTest() {
        Category category = new Category();

        category.setId(LONG_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = converter.convert(category);

        assertNotNull(categoryCommand);
        assertEquals(LONG_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}
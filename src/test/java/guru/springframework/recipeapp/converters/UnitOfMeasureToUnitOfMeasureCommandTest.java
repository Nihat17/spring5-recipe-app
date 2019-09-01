package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand converter;

    private static final Long LONG_VALUE = 7L;
    private static final String DESCRIPTION = "NEW description";

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void testConvert() {
        //GIVEN
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        //WHEN
        UnitOfMeasureCommand returnedValue = converter.convert(uom);

        //THEN
        assertNotNull(returnedValue);
        assertEquals(LONG_VALUE, returnedValue.getId());
        assertEquals(DESCRIPTION, returnedValue.getDescription());
    }
}
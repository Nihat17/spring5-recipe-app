package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;

    private static final Long LONG_VALUE = 3L;
    private static final String DESCRIPTION = "new Description";

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void testConvert() {
        //GIVEN
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //WHEN
        UnitOfMeasure uom = converter.convert(command);

        //THEN
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}
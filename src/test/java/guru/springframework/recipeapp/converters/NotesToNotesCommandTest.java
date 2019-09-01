package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    private static final Long LONG_VALUE = new Long(1L);

    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convertTest() {
        //GIVEN
        Notes note = new Notes();
        note.setId(LONG_VALUE);

        //WHEN
        NotesCommand returnObject = converter.convert(note);

        //THEN
        assertNotNull(returnObject);
        assertEquals(LONG_VALUE, returnObject.getId());
    }
}
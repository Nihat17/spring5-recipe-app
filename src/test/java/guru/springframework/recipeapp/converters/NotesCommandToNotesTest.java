package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    private static final Long LONG_VALUE = new Long(1L);

    NotesCommandToNotes converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convertTest() {
        //GIVEN
        NotesCommand command = new NotesCommand();
        command.setId(LONG_VALUE);

        //WHEN
        Notes note = converter.convert(command);

        //THEN
        assertNotNull(note);
        assertEquals(LONG_VALUE, note.getId());
    }
}
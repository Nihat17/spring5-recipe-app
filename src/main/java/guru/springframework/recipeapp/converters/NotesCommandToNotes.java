package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null) {
            return null;
        }

        final Notes note = new Notes();
        note.setId(notesCommand.getId());

        return note;
    }
}

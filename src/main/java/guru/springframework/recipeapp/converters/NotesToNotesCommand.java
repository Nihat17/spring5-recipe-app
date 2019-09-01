package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand  implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes notes) {

        if(notes == null) {
            return null;
        }

        NotesCommand command = new NotesCommand();
        command.setId(notes.getId());

        return command;
    }
}

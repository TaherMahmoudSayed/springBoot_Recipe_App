package guru.springframework.recipe.app.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import guru.springframework.recipe.app.commands.NoteCommand;
import guru.springframework.recipe.app.domain.Note;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {
    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(Note source) {
        if (source == null) {
            return null;
        }

        final NoteCommand notesCommand = new NoteCommand();
        notesCommand.setId(source.getId());
        notesCommand.setDescription(source.getDescription());
        return notesCommand;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}

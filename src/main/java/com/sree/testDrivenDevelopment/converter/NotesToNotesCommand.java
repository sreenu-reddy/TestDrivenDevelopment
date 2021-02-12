package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.NotesCommand;
import com.sree.testDrivenDevelopment.domain.Notes;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
   @Synchronized
   @Nullable
    @Override
    public NotesCommand convert( @NonNull Notes notes) {
       final NotesCommand notesCommand = new NotesCommand();
       notesCommand.setId(notes.getId());
       notesCommand.setRecipeNotes(notes.getRecipeNotes());
       return notesCommand; }
}

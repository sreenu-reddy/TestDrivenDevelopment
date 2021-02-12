package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.NotesCommand;
import com.sree.testDrivenDevelopment.domain.Notes;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Synchronized
    @Override
    public Notes convert( @NonNull NotesCommand notesCommand) {
        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        return notes; }
}

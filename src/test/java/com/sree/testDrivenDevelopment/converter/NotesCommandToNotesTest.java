package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.NotesCommand;
import com.sree.testDrivenDevelopment.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    NotesCommandToNotes toNotes;


    @BeforeEach
    void setUp() {
        toNotes = new NotesCommandToNotes();
    }

    @Test
    void testEmptyObject(){
        assertNotNull(toNotes.convert(new NotesCommand()));
    }

    @Test
    void convert() {
//        Given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(DESCRIPTION);

//        When
        Notes notes = toNotes.convert(notesCommand);

//        Then
        assertNotNull(notes);
        assertEquals(ID_VALUE,notes.getId());
        assertEquals(DESCRIPTION,notes.getRecipeNotes());
    }
}
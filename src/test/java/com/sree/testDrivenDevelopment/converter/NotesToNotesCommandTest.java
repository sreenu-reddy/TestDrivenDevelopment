package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.NotesCommand;
import com.sree.testDrivenDevelopment.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    NotesToNotesCommand toNotesCommand;

    @BeforeEach
    void setUp() {
        toNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void testEmptyObject(){
        assertNotNull(toNotesCommand.convert(new Notes()));
    }

    @Test
    void convert() {
//        given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(DESCRIPTION);

//        When
        NotesCommand command = toNotesCommand.convert(notes);
//        Then
        assertNotNull(command);
        assertEquals(ID_VALUE,command.getId());
        assertEquals(DESCRIPTION,command.getRecipeNotes());
    }
}
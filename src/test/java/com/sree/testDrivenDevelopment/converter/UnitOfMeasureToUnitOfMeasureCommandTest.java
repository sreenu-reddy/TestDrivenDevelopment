package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;
    @BeforeEach
    void setUp() {
        toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void testEmptyObject(){
        assertNotNull(toUnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    void testNullObject(){
        assertNull(toUnitOfMeasureCommand.convert(null));
    }

    @Test
    void convert() {
//        Given
        UnitOfMeasure measure = new UnitOfMeasure();
        measure.setId(ID_VALUE);
        measure.setUom(DESCRIPTION);

//        When
        UnitOfMeasureCommand command = toUnitOfMeasureCommand.convert(measure);

//        Then
        assertNotNull(command);
        assertEquals(ID_VALUE,command.getId());
        assertEquals(DESCRIPTION,command.getUom());
    }
}
package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    @BeforeEach
    void setUp() {
        toUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testEmptyObject(){
        assertNotNull(toUnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void testNullObject(){
        assertNull(toUnitOfMeasure.convert(null));
    }

    @Test
    void convert() {
//        given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(ID_VALUE);
        command.setUom(DESCRIPTION);
//        when
        UnitOfMeasure measure = toUnitOfMeasure.convert(command);
//        then
        assertNotNull(measure);
        assertEquals(ID_VALUE,measure.getId());
        assertEquals(DESCRIPTION,measure.getUom());
    }
}
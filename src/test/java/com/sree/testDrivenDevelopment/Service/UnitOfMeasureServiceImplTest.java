package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import com.sree.testDrivenDevelopment.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository ofMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand =new UnitOfMeasureToUnitOfMeasureCommand();

    UnitOfMeasureServiceImpl ofMeasureService;

    @BeforeEach
    void setUp() {

        ofMeasureService = new UnitOfMeasureServiceImpl(ofMeasureRepository,toUnitOfMeasureCommand);
    }

    @Test
    void listAllUom() {
//        Given
        Set<UnitOfMeasure> measureSet = new HashSet<>();

        UnitOfMeasure measure = new UnitOfMeasure();
        measure.setId(1L);
        measureSet.add(measure);
        UnitOfMeasure measure1 = new UnitOfMeasure();
        measure1.setId(2L);
        measureSet.add(measure1);

        given(ofMeasureRepository.findAll()).willReturn(measureSet);
//        When
        Set<UnitOfMeasureCommand> set= ofMeasureService.listAllUom();

//        then
        assertEquals(2,set.size());
        then(ofMeasureRepository).should().findAll();
        then(ofMeasureRepository).shouldHaveNoMoreInteractions();

    }
}
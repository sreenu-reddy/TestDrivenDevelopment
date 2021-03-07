package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import com.sree.testDrivenDevelopment.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository ofMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository ofMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.ofMeasureRepository = ofMeasureRepository;
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUom() {
        return StreamSupport.stream(ofMeasureRepository.findAll().spliterator(), false)
                .map(toUnitOfMeasureCommand::convert).collect(Collectors.toSet());
    }

}

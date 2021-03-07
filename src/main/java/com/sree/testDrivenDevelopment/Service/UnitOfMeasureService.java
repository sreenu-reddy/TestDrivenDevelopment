package com.sree.testDrivenDevelopment.Service;

import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;

import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUom();

}

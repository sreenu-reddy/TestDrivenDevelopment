package com.sree.testDrivenDevelopment.converter;

import com.sree.testDrivenDevelopment.command.UnitOfMeasureCommand;
import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert( @NonNull UnitOfMeasure unitOfMeasure) {

       final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
          unitOfMeasureCommand.setId(unitOfMeasure.getId());
          unitOfMeasureCommand.setUom(unitOfMeasure.getUom());
          return unitOfMeasureCommand; }
}

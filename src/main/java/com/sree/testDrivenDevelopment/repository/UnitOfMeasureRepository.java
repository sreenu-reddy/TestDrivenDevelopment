package com.sree.testDrivenDevelopment.repository;

import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,Long> {
    Optional<UnitOfMeasure> findByUom(String uom);
}

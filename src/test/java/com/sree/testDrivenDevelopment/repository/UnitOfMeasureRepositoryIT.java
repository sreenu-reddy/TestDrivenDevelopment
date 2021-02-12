package com.sree.testDrivenDevelopment.repository;

import com.sree.testDrivenDevelopment.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void findByUom() {
      Optional<UnitOfMeasure> ofMeasureOptional= unitOfMeasureRepository.findByUom("Teaspoon");
        assertTrue(ofMeasureOptional.isPresent());
        assertEquals("Teaspoon",ofMeasureOptional.get().getUom());
    }
}
package com.sree.testDrivenDevelopment.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingDescription;

    public Ingredient(String ingDescription, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        this.ingDescription = ingDescription;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
        this.recipe = recipe;
    }

    private BigDecimal amount;
    //    UnitOfMeasure
    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    //    Recipe
    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
    }


}

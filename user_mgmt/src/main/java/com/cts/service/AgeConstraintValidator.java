package com.cts.service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeConstraintValidator implements ConstraintValidator<Age, Integer> {

    private int annotationAge;

    @Override
    public void initialize(Age year) {
        this.annotationAge = year.value();
    }

    @Override
    public boolean isValid(Integer target, ConstraintValidatorContext cxt) {
        return target >= annotationAge;
    }

}

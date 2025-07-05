package com.taskmanagement.core.validator.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Enum<?>> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValidator annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values should be handled by @NotNull
        }

        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equals(value.name())) {
                return true;
            }
        }

        return false;
    }
}
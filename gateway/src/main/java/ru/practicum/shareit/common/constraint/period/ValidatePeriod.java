package ru.practicum.shareit.common.constraint.period;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PeriodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidatePeriod {
    String message() default "Start date must be before or equal to end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

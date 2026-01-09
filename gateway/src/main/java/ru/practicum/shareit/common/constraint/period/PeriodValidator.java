package ru.practicum.shareit.common.constraint.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodValidator implements ConstraintValidator<ValidatePeriod, Period> {
    @Override
    public boolean isValid(
            Period period,
            ConstraintValidatorContext context
    ) {
        if (period.getStart() == null || period.getEnd() == null) {
            return true;
        }

        return period.getStart().isBefore(period.getEnd());
    }
}

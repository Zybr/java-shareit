package ru.practicum.shareit.common.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@JsonTest
public class DtoTestCase<D> {
    protected static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    protected static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected JacksonTester<D> json;
    protected Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    protected String formatTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    protected LocalDateTime parseTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    protected String formatTime(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    protected LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    protected List<String> getViolatedProperties(Set<ConstraintViolation<D>> violations) {
        return violations.stream()
                .map(violation -> violation.getPropertyPath().toString())
                .toList();
    }
}

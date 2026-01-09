package ru.practicum.shareit.common.annotations;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
@Target({ElementType.FIELD})
public @interface SerializableDateTime {
}

package ru.practicum.shareit.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.common.exception.AccessException;
import ru.practicum.shareit.common.exception.DuplicationException;
import ru.practicum.shareit.common.exception.InvalidDataException;
import ru.practicum.shareit.common.exception.NotFoundException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(Exception exception) {
        log.warn("Model wasn't found.", exception);
        return makeErrorResponse(exception);
    }

    @ExceptionHandler({
            InvalidDataException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidData(Exception exception) {
        this.logExceptionWarn("Income data is not valid.", exception);
        return makeErrorResponse(exception);
    }

    @ExceptionHandler({
            DuplicationException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(Exception exception) {
        this.logExceptionWarn("Income data conflicts with exited one.", exception);
        return makeErrorResponse(exception);
    }

    @ExceptionHandler({
            AccessException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessException(Exception exception) {
        this.logExceptionWarn("The actions is forbidden.", exception);
        return makeErrorResponse(exception);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeException(RuntimeException exception) {
        logExceptionWarn("Unexpected error.", exception);
        return makeErrorResponse(exception);
    }

    private void logExceptionWarn(
            String description,
            Exception exception
    ) {
        log.warn(
                "{} Error type: {}; Error message: {};",
                description,
                exception.getClass(),
                exception.getMessage()
        );
    }

    private Map<String, String> makeErrorResponse(Exception exception) {
        return Map.of("error", exception.getMessage());
    }
}

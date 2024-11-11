package ru.prokhorov.limitservice.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.prokhorov.limitservice.dto.ErrorResponseDto;
import ru.prokhorov.limitservice.exception.OverLimitException;

/**
 * Глобальный обработчик ошибок.
 *
 * @author Evgeniy_Prokhorov
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(OverLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleEntityNotFoundException(OverLimitException e) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}

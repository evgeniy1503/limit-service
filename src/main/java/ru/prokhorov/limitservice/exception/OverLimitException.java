package ru.prokhorov.limitservice.exception;

/**
 * Исключение при превышении Лимита.
 *
 * @author Evgeniy_Prokhorov
 */
public class OverLimitException extends RuntimeException {

    public OverLimitException(final String message) {
        super(message);
    }
}

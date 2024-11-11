package ru.prokhorov.limitservice.exception;

/**
 * Исключение при отсутствии сущности.
 *
 * @author Evgeniy_Prokhorov
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}

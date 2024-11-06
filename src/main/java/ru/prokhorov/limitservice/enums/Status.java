package ru.prokhorov.limitservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Статусы Операций.
 *
 * @author Evgeniy_Prokhorov
 */
@Getter
@AllArgsConstructor
public enum Status {

    SUCCESS("Успех"),
    FAIL("Ошибка");

    final String description;
}

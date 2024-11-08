package ru.prokhorov.limitservice.service;

import ru.prokhorov.limitservice.dto.LimitRequest;

/**
 * Сервис по обработке операций.
 *
 * @author Evgeniy_Prokhorov
 */
public interface LimitProcessor {

    /**
     * Обработка операций списания
     *
     * @param limitRequest ДТО для списания лимотов
     */
    void processWriteDownEntry(LimitRequest limitRequest);
}

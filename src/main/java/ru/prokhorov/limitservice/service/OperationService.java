package ru.prokhorov.limitservice.service;

import ru.prokhorov.limitservice.dto.LimitRequest;

/**
 * Сервис по обработке операций.
 *
 * @author Evgeniy_Prokhorov
 */
public interface OperationService {

    /**
     * Обработка операций списания
     *
     * @param limitRequest ДТО для списания лимотов
     */
    void processOperation(LimitRequest limitRequest);
}

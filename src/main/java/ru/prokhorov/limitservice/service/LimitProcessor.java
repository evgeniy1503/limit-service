package ru.prokhorov.limitservice.service;

import ru.prokhorov.limitservice.dto.LimitRequest;

/**
 * Сервис по обработке операций.
 *
 * @author Evgeniy_Prokhorov
 */
public interface LimitProcessor {

    /**
     * Снижение лимита
     *
     * @param limitRequest ДТО для изменения лимита
     */
    void lowersLimit(LimitRequest limitRequest);

    /**
     * Увеличение лимита
     *
     * @param limitRequest ДТО для изменения лимита
     */
    void increaseLimit(LimitRequest limitRequest);
}

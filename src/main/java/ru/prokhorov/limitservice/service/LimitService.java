package ru.prokhorov.limitservice.service;

import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitResponse;
import ru.prokhorov.limitservice.entity.Limit;

import java.util.List;

/**
 * Сервис для работы с лимитами.
 *
 * @author Evgeniy_Prokhorov
 */
public interface LimitService {

    /**
     * Создание Лимита
     *
     * @param limitRequest ДТО для создания Лимита
     * @return Лимит
     */
    Limit createLimit(LimitRequest limitRequest);

    /**
     * Создание списка Лимитов
     *
     * @param requestList Список ДТО для создания Лимитов
     */
    void createLimits(List<LimitRequest> requestList);


    /**
     * Получить Лимит по идентификатору пользователя
     *
     * @param userId идентификатор пользователя
     * @return ДТО Лимита
     */
    LimitResponse getLimitByUserId(Long userId);

    /**
     * Сброс всех лимитов
     */
    void refreshAllLimit();

    /**
     * Обновление лимита
     *
     * @param limitRequest ДТО для обновления лимита
     * @return ДТО лимита
     */
    LimitResponse updateLimit(LimitRequest limitRequest);

}

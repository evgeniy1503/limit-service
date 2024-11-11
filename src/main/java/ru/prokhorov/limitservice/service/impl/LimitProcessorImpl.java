package ru.prokhorov.limitservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitUpdateDto;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.exception.EntityNotFoundException;
import ru.prokhorov.limitservice.exception.OverLimitException;
import ru.prokhorov.limitservice.logging.annotations.Loggable;
import ru.prokhorov.limitservice.mapper.LimitMapper;
import ru.prokhorov.limitservice.property.LimitProperties;
import ru.prokhorov.limitservice.repository.LimitRepository;
import ru.prokhorov.limitservice.service.LimitProcessor;
import ru.prokhorov.limitservice.service.LimitService;

import java.math.BigDecimal;

/**
 * Реализация {@link LimitProcessor}.
 *
 * @author Evgeniy_Prokhorov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LimitProcessorImpl implements LimitProcessor {

    private final LimitRepository limitRepository;
    private final LimitService limitService;
    private final LimitProperties limitProperties;
    private final LimitMapper limitMapper;

    @Loggable
    @Override
    public void lowersLimit(LimitRequest limitRequest) {
        Limit limit = limitRepository.getLimitByUserId(limitRequest.getUserId())
                .orElseGet(() -> limitService.createLimit(limitRequest));
        if (limit.getAmount().compareTo(limitRequest.getAmount()) < 0) {
            throw new OverLimitException("Превышен лимит при списании для пользователя с ID = " + limitRequest.getUserId());
        }
        LimitUpdateDto limitUpdate = limitMapper.toLowerUpdate(limitRequest, limit.getAmount());
        limitService.updateLimit(limitUpdate);
    }

    @Loggable
    @Override
    public void increaseLimit(LimitRequest limitRequest) {
        Limit limit = limitRepository.getLimitByUserId(limitRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Лимит не найден для пользователя с ID = " + limitRequest.getUserId()));
        if (limit.getAmount().add(limitRequest.getAmount()).compareTo(limitProperties.getSum()) > 0) {
            throw new OverLimitException("Лимит не может быть больше суточного допустимого для пользователя с ID = " + limitRequest.getUserId());
        }
        LimitUpdateDto limitUpdate = limitMapper.toIncreaseUpdate(limitRequest, limit.getAmount());
        limitService.updateLimit(limitUpdate);
    }
}

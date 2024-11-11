package ru.prokhorov.limitservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitResponse;
import ru.prokhorov.limitservice.dto.LimitUpdateDto;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.exception.EntityNotFoundException;
import ru.prokhorov.limitservice.logging.annotations.Loggable;
import ru.prokhorov.limitservice.mapper.LimitMapper;
import ru.prokhorov.limitservice.property.LimitProperties;
import ru.prokhorov.limitservice.repository.LimitRepository;
import ru.prokhorov.limitservice.service.LimitService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация {@link LimitService}.
 *
 * @author Evgeniy_Prokhorov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitServiceImpl implements LimitService {

    private final LimitMapper limitMapper;
    private final LimitRepository limitRepository;
    private final LimitProperties limitProperties;

    @Loggable
    @Transactional
    @Override
    public Limit createLimit(LimitRequest limitRequest) {
        Limit limit = limitMapper.toEntity(limitRequest);
        limit.setAmount(limitProperties.getSum());
        return limitRepository.save(limit);
    }

    @Loggable
    @Transactional(readOnly = true)
    @Override
    public LimitResponse getLimitByUserId(Long userId) {
        Limit limit = limitRepository.getLimitByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Лимит не найден для пользователя с ID: " + userId));
        return limitMapper.toResponse(limit);
    }

    @Loggable
    @Transactional
    @Override
    public void updateLimit(LimitUpdateDto limitUpdate) {
        Limit limit = limitRepository
                .getLimitByUserId(limitUpdate.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдет лимит для пользователя с ID: " + limitUpdate.getUserId()));
        limitMapper.updateEntity(limitUpdate, limit);
        limitRepository.save(limit);
    }

    @Loggable
    @Scheduled(cron = "${limit.scheduled.cron}")
    @Override
    public void refreshAllLimit() {
        limitRepository.updateLimit(limitProperties.getSum());
    }

}

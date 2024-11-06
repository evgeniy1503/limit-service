package ru.prokhorov.limitservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitResponse;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.exception.EntityNotFoundException;
import ru.prokhorov.limitservice.mapper.LimitMapper;
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
    @Value("${limit.sum}")
    private BigDecimal limitSum;
    @Transactional
    @Override
    public Limit createLimit(LimitRequest limitRequest) {
        log.info("createLimit() - start");
        Limit limit = limitMapper.toEntity(limitRequest);
        limit.setAmount(limitSum);
        Limit save = limitRepository.save(limit);
        log.info("createLimit() - end");
        return save;
    }

    @Transactional
    @Override
    public void createLimits(List<LimitRequest> requestList) {
        log.info("createLimits() - start");
        List<Limit> limit = limitMapper.toEntity(requestList);
        limit.forEach(l -> l.setAmount(limitSum));
        limitRepository.saveAll(limit);
        log.info("createLimits() - end");
    }

    @Transactional(readOnly = true)
    @Override
    public LimitResponse getLimitByUserId(Long userId) {
        log.info("getLimitByUserId() - start");
        Limit limit = limitRepository.getLimitByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Лимит не найден для пользователя с ID: " + userId));
        LimitResponse limitResponse = limitMapper.toResponse(limit);
        log.info("getLimitByUserId() - end");
        return limitResponse;
    }

    @Transactional
    @Override
    public LimitResponse updateLimit(LimitRequest limitRequest) {
        log.info("updateLimit() - start");
        Limit limit = limitRepository
                .getLimitByUserId(limitRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдет лимит для пользователя с ID: " + limitRequest.getUserId()));
        BigDecimal amount = limit.getAmount().subtract(limitRequest.getAmount());
        limit.setAmount(amount);
        limitRepository.save(limit);
        log.info("updateLimit() - end");
        return null;
    }

    @Scheduled(cron = "${limit.scheduled.cron}")
    @Override
    public void refreshAllLimit() {
        log.info("refreshAllLimit() - start: limitSum = {}", limitSum);
        limitRepository.updateLimit(limitSum);
    }

}

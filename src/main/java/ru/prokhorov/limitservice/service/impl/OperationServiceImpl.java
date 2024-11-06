package ru.prokhorov.limitservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.entity.Operation;
import ru.prokhorov.limitservice.enums.Status;
import ru.prokhorov.limitservice.exception.OverLimitException;
import ru.prokhorov.limitservice.mapper.OperationMapper;
import ru.prokhorov.limitservice.repository.OperationRepository;
import ru.prokhorov.limitservice.service.LimitService;
import ru.prokhorov.limitservice.service.OperationService;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Реализация {@link OperationService}.
 *
 * @author Evgeniy_Prokhorov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationMapper operationMapper;
    private final OperationRepository operationRepository;
    private final LimitService limitService;

    @Override
    public void processOperation(LimitRequest limitRequest) {
        log.info("processOperation() - start");
        Operation operation = operationMapper.toOperation(limitRequest);
        Limit limit = operation.getLimit();
        if(Objects.isNull(limit)) {
            limit = limitService.createLimit(limitRequest);
            operation.setLimit(limit);
        }
        validation(operation);
        createOperation(operation);
        if (operation.getStatus().equals(Status.FAIL)) {
            log.error("Возникла ошибка при обработке операции");
            throw new OverLimitException("Превышен суточный лимит для пользователя с ID: " + limitRequest.getUserId());
        } else {
            limitService.updateLimit(limitRequest);
        }
        log.info("processOperation() - end");
    }

    public void createOperation(Operation operation) {
        log.info("createOperation() - start");
        operationRepository.save(operation);
        log.info("createOperation() - end");
    }


    /**
     * Валидация проводимой операции
     *
     * @param operation Операция
     */
    private void validation(Operation operation) {
        log.info("validation() - start: {}", operation);
        BigDecimal amountLimit = operation.getLimit().getAmount();
        BigDecimal amountOperation = operation.getAmount();
        if (amountLimit.compareTo(amountOperation) < 0) {
            operation.setStatus(Status.FAIL);
        }
        log.info("validation() - end: {}", operation);
    }
}

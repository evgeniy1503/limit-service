package ru.prokhorov.limitservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.entity.WriteDownEntry;
import ru.prokhorov.limitservice.enums.Status;
import ru.prokhorov.limitservice.exception.OverLimitException;
import ru.prokhorov.limitservice.logging.annotations.Loggable;
import ru.prokhorov.limitservice.mapper.WriteDownEntryMapper;
import ru.prokhorov.limitservice.repository.LimitRepository;
import ru.prokhorov.limitservice.repository.WriteDownEntryRepository;
import ru.prokhorov.limitservice.service.LimitProcessor;
import ru.prokhorov.limitservice.service.LimitService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Реализация {@link LimitProcessor}.
 *
 * @author Evgeniy_Prokhorov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LimitProcessorImpl implements LimitProcessor {

    private final WriteDownEntryMapper writeDownEntryMapper;
    private final WriteDownEntryRepository writeDownEntryRepository;
    private final LimitRepository limitRepository;
    private final LimitService limitService;

    @Loggable
    @Override
    public void processWriteDownEntry(LimitRequest limitRequest) {
        WriteDownEntry writeDownEntry = writeDownEntryMapper.toWriteDownEntry(limitRequest, limitRepository);
        Limit limit = Optional.ofNullable(writeDownEntry.getLimit())
                .orElseGet(() -> limitService.createLimit(limitRequest));
        writeDownEntry.setLimit(limit);
        validation(writeDownEntry);
        createWriteDownEntry(writeDownEntry);
        if (writeDownEntry.getStatus().equals(Status.FAIL)) {
            log.error("Возникла ошибка при обработке операции");
            throw new OverLimitException("Превышен суточный лимит для пользователя с ID: " + limitRequest.getUserId());
        } else {
            limitService.updateLimit(limitRequest);
        }
    }

    @Loggable
    public void createWriteDownEntry(WriteDownEntry writeDownEntry) {
        writeDownEntryRepository.save(writeDownEntry);
    }


    /**
     * Валидация проводимой операции
     *
     * @param writeDownEntry Операция
     */
    @Loggable
    private void validation(WriteDownEntry writeDownEntry) {
        BigDecimal amountLimit = writeDownEntry.getLimit().getAmount();
        BigDecimal amountWriteDownEntry = writeDownEntry.getAmount();
        if (amountLimit.compareTo(amountWriteDownEntry) < 0) {
            writeDownEntry.setStatus(Status.FAIL);
        }
    }
}

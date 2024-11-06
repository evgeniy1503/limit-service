package ru.prokhorov.limitservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitResponse;
import ru.prokhorov.limitservice.service.LimitService;
import ru.prokhorov.limitservice.service.OperationService;

/**
 * Контроллер для работы с Лимитами.
 *
 * @author Evgeniy_Prokhorov
 */
@Slf4j
@RestController
@RequestMapping("/limits")
@RequiredArgsConstructor
public class LimitController {

    private final OperationService operationService;
    private final LimitService limitService;

    @PostMapping
    public void changeLimit(@Valid @RequestBody LimitRequest request) {
        log.info("changeLimit() - start: request = {}", request);
        operationService.processOperation(request);
        log.info("changeLimit() - end");
    }

    @GetMapping("/user")
    public LimitResponse getLimitByUserId(@RequestParam Long userId) {
        log.info("getLimitByUserId() - start: userId = {}", userId);
        LimitResponse result = limitService.getLimitByUserId(userId);
        log.info("getLimitByUserId() - end: result = {}", result);
        return result;
    }
}

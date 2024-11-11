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
import ru.prokhorov.limitservice.logging.annotations.Loggable;
import ru.prokhorov.limitservice.service.LimitProcessor;
import ru.prokhorov.limitservice.service.LimitService;

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

    private final LimitProcessor limitProcessor;
    private final LimitService limitService;

    @Loggable
    @PostMapping("/lower")
    public void lowerLimit(@Valid @RequestBody LimitRequest request) {
        limitProcessor.lowersLimit(request);
    }

    @Loggable
    @PostMapping("/increase")
    public void changeLimit(@Valid @RequestBody LimitRequest request) {
        limitProcessor.increaseLimit(request);
    }

    @Loggable
    @GetMapping("/user")
    public LimitResponse getLimitByUserId(@RequestParam Long userId) {
        return limitService.getLimitByUserId(userId);
    }
}

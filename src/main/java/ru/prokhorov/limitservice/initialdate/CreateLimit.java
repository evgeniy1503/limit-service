package ru.prokhorov.limitservice.initialdate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.service.LimitService;

import java.util.ArrayList;
import java.util.List;

/**
 * Инициализация базы данных Лимитами.
 *
 * @author Evgeniy_Prokhorov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateLimit implements CommandLineRunner {

    private final LimitService limitService;

    @Override
    public void run(String... args) throws Exception {
        log.info("inti limit - start");
        try {
            List<LimitRequest> limitRequests = createLimits();
            limitService.createLimits(limitRequests);
        } catch (Exception e) {
            log.error("База данных уже заполнена");
        }
        log.info("inti limit - end");
    }

    private List<LimitRequest> createLimits() {
        List<LimitRequest> requestList = new ArrayList<>();
        for(int i = 1; i < 101; i++) {
            LimitRequest limitRequest = new LimitRequest();
            limitRequest.setUserId(Long.valueOf(i));
            requestList.add(limitRequest);
        }
        return requestList;
    }
}

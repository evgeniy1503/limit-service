package ru.prokhorov.limitservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.prokhorov.limitservice.entity.WriteDownEntry;

import java.math.BigDecimal;
import java.util.List;

/**
 * ДТО для отображения Лимитов.
 *
 * @author Evgeniy_Prokhorov
 */
@Getter
@Setter
public class LimitResponse {

    private Long id;

    private BigDecimal amount;

    private List<WriteDownEntry> writeDownEntries;
}

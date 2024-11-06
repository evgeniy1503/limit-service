package ru.prokhorov.limitservice.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.prokhorov.limitservice.entity.Operation;

import java.math.BigDecimal;
import java.util.List;

/**
 * ДТО для отображения Лимитов.
 *
 * @author Evgeniy_Prokhorov
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LimitResponse {

    Long id;

    BigDecimal amount;

    List<Operation> operations;
}

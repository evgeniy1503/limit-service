package ru.prokhorov.limitservice.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * ДТО для изменения Лимита.
 *
 * @author Evgeniy_Prokhorov
 */
@Getter
@Setter
public class LimitRequest {

    @NotNull(message = "Поле не может быть пустым")
    private Long userId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Поле \"Сумма\" не может быть отрицательной")
    private BigDecimal amount;
}

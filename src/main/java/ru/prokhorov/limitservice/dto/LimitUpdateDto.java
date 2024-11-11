package ru.prokhorov.limitservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * ДТО для обновления лимита.
 *
 * @author Evgeniy_Prokhorov
 */
@Getter
@Setter
public class LimitUpdateDto {
    private Long userId;
    private BigDecimal amount;
}

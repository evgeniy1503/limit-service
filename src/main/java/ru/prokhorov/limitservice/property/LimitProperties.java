package ru.prokhorov.limitservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Настройки суммы лимита.
 *
 * @author Evgeniy_Prokhorov
 */

@Setter
@ConfigurationProperties(prefix = "limit")
@Configuration
public class LimitProperties {

    private int sum;

    public BigDecimal getSum() {
        return BigDecimal.valueOf(sum);
    }
}
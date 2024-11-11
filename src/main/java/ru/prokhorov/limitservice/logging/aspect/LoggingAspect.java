package ru.prokhorov.limitservice.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Аспект для обработки Логирования.
 *
 * @author Evgeniy_Prokhorov
 */
@Aspect
@Component
@ConditionalOnProperty(name = "logging.enabled", havingValue = "true")
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(ru.prokhorov.limitservice.logging.annotations.Loggable)")
    public void loggableMethods() {}

    /**
     * Метод, вызываемый перед выполнением метода, помеченного аннотацией @Loggable.
     *
     * @param joinPoint точка соединения, предоставляющая информацию о вызове метода.
     */
    @Before("loggableMethods()")
    public void beforeMethod(JoinPoint joinPoint) {
        LOGGER.debug("Вызов метода: {} {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    /**
     * Метод, вызываемый после успешного выполнения метода, помеченного аннотацией @Loggable.
     *
     * @param joinPoint точка соединения, предоставляющая информацию о вызове метода.
     * @param result  результат выполнения метода.
     */
    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        LOGGER.debug("Метод {} выполнен успешно. Результат: {}", joinPoint.getSignature().getName(), Objects.isNull(result) ? "Метод без результат" : result);
    }

    /**
     * Метод, вызываемый при возникновении исключения в методе, помеченном аннотацией @Loggable.
     *
     * @param joinPoint точка соединения, предоставляющая информацию о вызове метода.
     * @param exception исключение, возникшее во время выполнения метода.
     */
    @AfterThrowing(pointcut = "loggableMethods()", throwing = "exception")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) {
        LOGGER.error("Ошибка при выполнении метода {}: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}

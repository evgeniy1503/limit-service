package ru.prokhorov.limitservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitResponse;
import ru.prokhorov.limitservice.dto.LimitUpdateDto;
import ru.prokhorov.limitservice.entity.Limit;

import java.math.BigDecimal;
import java.util.List;

/**
 * Маппер для Лимитов.
 *
 * @author Evgeniy_Prokhorov
 */
@Mapper(componentModel = "spring")
public interface LimitMapper {

    Limit toEntity(LimitRequest limitRequest);

    void updateEntity(LimitUpdateDto limitUpdateDto, @MappingTarget Limit limit);

    List<Limit> toEntity(List<LimitRequest> requestList);

    LimitResponse toResponse(Limit limit);

    @Mapping(target = "amount", expression = "java(currentAmount.subtract(limitRequest.getAmount()))")
    LimitUpdateDto toLowerUpdate(LimitRequest limitRequest, BigDecimal currentAmount);

    @Mapping(target = "amount", expression = "java(currentAmount.add(limitRequest.getAmount()))")
    LimitUpdateDto toIncreaseUpdate(LimitRequest limitRequest, BigDecimal currentAmount);
}

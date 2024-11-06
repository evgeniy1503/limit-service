package ru.prokhorov.limitservice.mapper;

import org.mapstruct.Mapper;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.dto.LimitResponse;
import ru.prokhorov.limitservice.entity.Limit;

import java.util.List;

/**
 * Маппер для Лимитов.
 *
 * @author Evgeniy_Prokhorov
 */
@Mapper(componentModel = "spring")
public interface LimitMapper {

    Limit toEntity(LimitRequest limitRequest);

    List<Limit> toEntity(List<LimitRequest> requestList);

    LimitResponse toResponse(Limit limit);
}

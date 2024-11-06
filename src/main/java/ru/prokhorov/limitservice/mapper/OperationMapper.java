package ru.prokhorov.limitservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.entity.Operation;
import ru.prokhorov.limitservice.enums.Status;
import ru.prokhorov.limitservice.repository.LimitRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Маппер для Операций.
 *
 * @author Evgeniy_Prokhorov
 */
@Slf4j
@Mapper(componentModel = "spring",
    imports = {
        Date.class,
        Status.class
    }
)
public abstract class OperationMapper {

    @Autowired
    private LimitRepository limitRepository;

    @Mapping(target = "status", expression = "java(Status.SUCCESS)")
    @Mapping(target = "createDate", expression = "java(new Date())")
    public abstract Operation toOperation(LimitRequest request);

    @AfterMapping
    protected void fillLimit(@MappingTarget Operation operation, LimitRequest request){
        log.info("fillLimit() - start");
        Optional<Limit> limit = limitRepository.findById(request.getUserId());
        operation.setLimit(limit.orElse(null));
        log.info("fillLimit() - end");
    }
}

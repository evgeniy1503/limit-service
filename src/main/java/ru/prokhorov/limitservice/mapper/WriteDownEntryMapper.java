package ru.prokhorov.limitservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.prokhorov.limitservice.dto.LimitRequest;
import ru.prokhorov.limitservice.entity.Limit;
import ru.prokhorov.limitservice.entity.WriteDownEntry;
import ru.prokhorov.limitservice.enums.Status;
import ru.prokhorov.limitservice.repository.LimitRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Маппер для Операций.
 *
 * @author Evgeniy_Prokhorov
 */
@Mapper(componentModel = "spring",
        imports = {
            Date.class,
            Status.class,
        }
)
public abstract class WriteDownEntryMapper {

    @Mapping(target = "status", expression = "java(Status.SUCCESS)")
    @Mapping(target = "createDate", expression = "java(new Date())")
    public abstract WriteDownEntry toWriteDownEntry(LimitRequest request,
                                                    @Context LimitRepository limitRepository);

    @AfterMapping
    protected void fillLimit(@MappingTarget WriteDownEntry writeDownEntry,
                             LimitRequest request,
                             @Context LimitRepository limitRepository){
        Optional<Limit> limit = limitRepository.findById(request.getUserId());
        writeDownEntry.setLimit(limit.orElse(null));
    }
}

package vn.com.truyen.usermanagent.service.mapper;


import vn.com.truyen.usermanagent.domain.*;
import vn.com.truyen.usermanagent.service.dto.FunctionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Functions} and its DTO {@link FunctionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FunctionsMapper extends EntityMapper<FunctionsDTO, Functions> {


    @Mapping(target = "names", ignore = true)
    @Mapping(target = "removeName", ignore = true)
    Functions toEntity(FunctionsDTO functionsDTO);

    default Functions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Functions functions = new Functions();
        functions.setId(id);
        return functions;
    }
}

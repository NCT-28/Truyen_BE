package vn.com.truyen.usermanagent.service.mapper;


import vn.com.truyen.usermanagent.domain.*;
import vn.com.truyen.usermanagent.service.dto.RoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {FunctionsMapper.class})
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {


    @Mapping(target = "removeFunction", ignore = true)
    @Mapping(target = "names", ignore = true)
    @Mapping(target = "removeName", ignore = true)
    @Mapping(target = "logins", ignore = true)
    @Mapping(target = "removeLogin", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    default Role fromId(Long id) {
        if (id == null) {
            return null;
        }
        Role role = new Role();
        role.setId(id);
        return role;
    }
}

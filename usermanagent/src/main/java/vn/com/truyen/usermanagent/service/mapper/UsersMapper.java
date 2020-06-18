package vn.com.truyen.usermanagent.service.mapper;


import vn.com.truyen.usermanagent.domain.*;
import vn.com.truyen.usermanagent.service.dto.UsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Users} and its DTO {@link UsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {


    @Mapping(target = "removeRole", ignore = true)
    @Mapping(target = "logins", ignore = true)
    @Mapping(target = "removeLogin", ignore = true)
    Users toEntity(UsersDTO usersDTO);

    default Users fromId(Long id) {
        if (id == null) {
            return null;
        }
        Users users = new Users();
        users.setId(id);
        return users;
    }
}

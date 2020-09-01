package vn.com.truyen.usermanagent.service.mapper;


import vn.com.truyen.usermanagent.domain.*;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupTranslate} and its DTO {@link GroupTranslateDTO}.
 */
@Mapper(componentModel = "spring", uses = {UsersMapper.class, RoleMapper.class})
public interface GroupTranslateMapper extends EntityMapper<GroupTranslateDTO, GroupTranslate> {


    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "removeRole", ignore = true)

    default GroupTranslate fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupTranslate groupTranslate = new GroupTranslate();
        groupTranslate.setId(id);
        return groupTranslate;
    }
}

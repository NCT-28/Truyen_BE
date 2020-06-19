package vn.com.truyen.service.mapper;


import vn.com.truyen.domain.*;
import vn.com.truyen.service.dto.ViewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link View} and its DTO {@link ViewDTO}.
 */
@Mapper(componentModel = "spring", uses = {TruyenMapper.class})
public interface ViewMapper extends EntityMapper<ViewDTO, View> {

//    @Mapping(source = "truyen.id", target = "truyenId")
//    ViewDTO toDto(View view);
//
//    @Mapping(source = "truyenId", target = "truyen")
//    View toEntity(ViewDTO viewDTO);

    default View fromId(Long id) {
        if (id == null) {
            return null;
        }
        View view = new View();
        view.setId(id);
        return view;
    }
}

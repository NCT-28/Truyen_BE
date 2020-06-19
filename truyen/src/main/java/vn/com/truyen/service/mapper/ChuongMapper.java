package vn.com.truyen.service.mapper;


import vn.com.truyen.domain.*;
import vn.com.truyen.service.dto.ChuongDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chuong} and its DTO {@link ChuongDTO}.
 */
@Mapper(componentModel = "spring", uses = {TruyenMapper.class})
public interface ChuongMapper extends EntityMapper<ChuongDTO, Chuong> {

//    @Mapping(source = "truyen.id", target = "truyenId")
//    ChuongDTO toDto(Chuong chuong);
//
//    @Mapping(source = "truyenId", target = "truyen")
//    Chuong toEntity(ChuongDTO chuongDTO);

    default Chuong fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chuong chuong = new Chuong();
        chuong.setId(id);
        return chuong;
    }
}

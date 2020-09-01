package vn.com.truyen.service.mapper;


import vn.com.truyen.domain.*;
import vn.com.truyen.service.dto.TruyenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Truyen} and its DTO {@link TruyenDTO}.
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface TruyenMapper extends EntityMapper<TruyenDTO, Truyen> {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.name", target = "authorName")
    TruyenDTO toDto(Truyen truyen);

    @Mapping(target = "chuongs", ignore = true)
    @Mapping(target = "removeChuong", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "removeView", ignore = true)
    @Mapping(target = "feadbacks", ignore = true)
    @Mapping(target = "removeFeadback", ignore = true)
    @Mapping(source = "authorId", target = "author")
    @Mapping(target = "names", ignore = true)
    @Mapping(target = "removeName", ignore = true)
    Truyen toEntity(TruyenDTO truyenDTO);

    default Truyen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Truyen truyen = new Truyen();
        truyen.setId(id);
        return truyen;
    }
}

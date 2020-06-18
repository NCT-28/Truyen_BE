package vn.com.truyen.service.mapper;


import vn.com.truyen.domain.*;
import vn.com.truyen.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TruyenMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {


    @Mapping(target = "removeTruyen", ignore = true)

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}

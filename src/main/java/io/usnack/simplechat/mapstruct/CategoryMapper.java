package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.CategoryDto;
import io.usnack.simplechat.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}

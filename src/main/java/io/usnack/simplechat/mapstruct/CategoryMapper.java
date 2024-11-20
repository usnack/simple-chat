package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.CategoryDto;
import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "channels", expression ="java(new java.util.ArrayList())" )
    CategoryDto toDto(Category category);
    CategoryDto toDto(Category category, List<ChannelDto> channels);
}

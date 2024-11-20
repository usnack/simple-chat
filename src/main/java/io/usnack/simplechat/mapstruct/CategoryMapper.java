package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.request.CategoryCreateRequest;
import io.usnack.simplechat.dto.response.CategoryDetailResponse;
import io.usnack.simplechat.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDetailResponse toDetailResponse(Category category);
//    @Mapping(target = "createdAt", expression = "java()")
//    Category toEntity(CategoryCreateRequest request);
}

package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.ReadStatusDto;
import io.usnack.simplechat.entity.ReadStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReadStatusMapper {
    ReadStatusDto toDto(ReadStatus readStatus);
}

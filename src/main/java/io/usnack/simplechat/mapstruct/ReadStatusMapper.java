package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.ReadStatusDto;
import io.usnack.simplechat.entity.ReadStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadStatusMapper {
    @Mapping(target = "userId", expression = "java(readStatus.getUser().getId())")
    @Mapping(target = "channelId", expression = "java(readStatus.getChannel().getId())")
    ReadStatusDto toDto(ReadStatus readStatus);
}

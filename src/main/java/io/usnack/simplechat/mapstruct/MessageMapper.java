package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {
    @Mapping(target = "author", source = "author", qualifiedByName = "toDtoWithoutStatus")
    MessageDto toDto(Message message);
}

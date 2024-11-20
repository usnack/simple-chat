package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto toDto(Message message);
}

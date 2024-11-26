package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BinaryContentMapper.class})
public interface MessageMapper {
    MessageDto toDto(Message message);
}

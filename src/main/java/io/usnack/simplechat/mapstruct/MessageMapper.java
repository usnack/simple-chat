package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BinaryContentMapper.class})
public interface MessageMapper {
    @Mapping(target = "channelId", expression = "java(message.getChannel().getId())")
    MessageDto toDto(Message message);
}

package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.entity.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    ChannelDto toDto(Channel channel);
}

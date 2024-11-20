package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.ChannelMemberDto;
import io.usnack.simplechat.entity.ChannelMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMemberMapper {
    ChannelMemberDto toDto(ChannelMember channelMember);
}

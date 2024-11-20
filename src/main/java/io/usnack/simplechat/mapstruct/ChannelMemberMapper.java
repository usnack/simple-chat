package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.response.ChannelMemberDetailResponse;
import io.usnack.simplechat.entity.ChannelMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMemberMapper {
    ChannelMemberDetailResponse toDetailResponse(ChannelMember channelMember);
}

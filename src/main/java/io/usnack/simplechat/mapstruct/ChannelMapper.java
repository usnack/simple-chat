package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.response.CategoryDetailResponse;
import io.usnack.simplechat.dto.response.ChannelDetailResponse;
import io.usnack.simplechat.entity.Category;
import io.usnack.simplechat.entity.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    ChannelDetailResponse toDetailResponse(Channel channel);
}

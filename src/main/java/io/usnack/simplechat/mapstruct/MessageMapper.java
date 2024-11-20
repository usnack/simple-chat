package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.response.MessageDetailResponse;
import io.usnack.simplechat.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDetailResponse toDetailResponse(Message message);
}

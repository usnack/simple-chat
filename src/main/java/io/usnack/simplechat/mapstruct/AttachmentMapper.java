package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.AttachmentDto;
import io.usnack.simplechat.entity.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    @Mapping(target = "messageId", expression = "java(attachment.getMessage().getId())")
    AttachmentDto toDto(Attachment attachment);
}

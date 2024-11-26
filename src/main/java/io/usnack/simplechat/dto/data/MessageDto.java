package io.usnack.simplechat.dto.data;

import java.util.List;
import java.util.UUID;

public record MessageDto(
        UUID id,
        Long createdAt,
        Long updatedAt,
        String content,
        UUID channelId,
        UserDto author,
        List<BinaryContentDto> attachments
) {}

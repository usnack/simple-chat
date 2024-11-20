package io.usnack.simplechat.dto.data;

import java.util.UUID;

public record MessageDto(
        UUID id,
        String content,
        UUID authorId,
        UUID channelId,
        Long createdAt,
        Long updatedAt
) {
}

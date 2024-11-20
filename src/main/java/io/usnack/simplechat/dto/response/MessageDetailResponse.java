package io.usnack.simplechat.dto.response;

import java.util.UUID;

public record MessageDetailResponse(
        UUID id,
        String content,
        UUID authorId,
        UUID channelId,
        Long createdAt,
        Long updatedAt
) {
}

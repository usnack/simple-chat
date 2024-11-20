package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record MessageCreateRequest(
        String content,
        UUID authorId,
        UUID channelId
) {
}

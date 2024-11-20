package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record ReadStatusCreateRequest(
        UUID userId,
        UUID channelId
) {
}

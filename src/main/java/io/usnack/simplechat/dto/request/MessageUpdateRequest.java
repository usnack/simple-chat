package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record MessageUpdateRequest(
        UUID messageId,
        String content
) {
}

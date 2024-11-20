package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record ReadStatusUpdateRequest(
        UUID readStatusId,
        Long readAt
) {
}

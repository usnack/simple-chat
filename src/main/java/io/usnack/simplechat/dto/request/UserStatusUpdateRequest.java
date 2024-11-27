package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record UserStatusUpdateRequest(
        UUID userStatusId,
        Long lastActiveAt
) {
}

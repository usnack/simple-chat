package io.usnack.simplechat.dto.data;

import java.util.UUID;

public record UserStatusDto(
        UUID id,
        UUID userId,
        Boolean online,
        Long lastActiveAt
) {
}

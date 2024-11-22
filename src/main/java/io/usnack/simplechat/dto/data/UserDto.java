package io.usnack.simplechat.dto.data;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        String profileUrl,
        Long createdAt
) {
}

package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record UserUpdateRequest(
        UUID userId,
        String username,
        String email,
        String password,
        String avatarUrl
) {
}

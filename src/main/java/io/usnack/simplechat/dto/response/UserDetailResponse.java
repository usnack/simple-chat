package io.usnack.simplechat.dto.response;

import java.util.UUID;

public record UserDetailResponse(
        UUID id,
        String username,
        String email,
        String password,
        String avatarUrl,
        Long createdAt
) {
}

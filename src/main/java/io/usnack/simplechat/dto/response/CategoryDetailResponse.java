package io.usnack.simplechat.dto.response;

import java.util.UUID;

public record CategoryDetailResponse(
        UUID id,
        String name,
        Long createdAt
) {
}

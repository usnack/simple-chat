package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record CategoryUpdateRequest(
        UUID categoryId,
        String name
) {
}

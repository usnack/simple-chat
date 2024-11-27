package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record PublicChannelUpdateRequest(
        @NotEmpty
        String name,
        @NotEmpty
        String description
) {
}

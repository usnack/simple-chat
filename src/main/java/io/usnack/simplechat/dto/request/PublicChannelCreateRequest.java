package io.usnack.simplechat.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;

public record PublicChannelCreateRequest(
        @NotEmpty
        String name,
        @Nullable
        String description
) {
}

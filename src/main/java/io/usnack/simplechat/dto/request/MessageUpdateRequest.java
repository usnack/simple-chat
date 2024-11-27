package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.NotNull;

public record MessageUpdateRequest(
        @NotNull
        String content
) {
}

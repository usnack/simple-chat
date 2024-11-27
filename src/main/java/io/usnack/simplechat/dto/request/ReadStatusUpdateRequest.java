package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReadStatusUpdateRequest(
        @NotNull
        @Min(0)
        Long readAt
) {
}

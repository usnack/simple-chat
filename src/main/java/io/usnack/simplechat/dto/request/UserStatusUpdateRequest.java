package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateRequest(
        @NotNull
        @Min(0)
        Long lastActiveAt
) {
}

package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record ReadStatusCreateRequest(
        @NotEmpty
        UUID userId,
        @NotEmpty
        UUID channelId
) {
}

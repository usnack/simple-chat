package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record PrivateChannelCreateRequest(
        @NotNull
        @NotEmpty
        @Size(max = 10)
        List<UUID> userIds
) {
}

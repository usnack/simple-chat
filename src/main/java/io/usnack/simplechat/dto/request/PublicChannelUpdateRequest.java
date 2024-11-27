package io.usnack.simplechat.dto.request;

public record PublicChannelUpdateRequest(
        String name,
        String description
) {
}

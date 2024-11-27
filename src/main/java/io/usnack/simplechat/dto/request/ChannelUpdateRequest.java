package io.usnack.simplechat.dto.request;

public record ChannelUpdateRequest(
        String name,
        String description
) {
}

package io.usnack.simplechat.dto.request;

public record PublicChannelCreateRequest(
        String name,
        String description
) {
}

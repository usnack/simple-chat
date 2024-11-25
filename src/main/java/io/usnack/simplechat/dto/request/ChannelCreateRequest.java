package io.usnack.simplechat.dto.request;

import io.usnack.simplechat.entity.ChannelType;

public record ChannelCreateRequest(
        ChannelType type,
        String name,
        String description
) {
}

package io.usnack.simplechat.dto.request;

import io.usnack.simplechat.entity.ChannelType;

import java.util.UUID;

public record ChannelCreateRequest(
        ChannelType type,
        String name,
        String description,
        UUID categoryId,
        UUID ownerId
) {
}

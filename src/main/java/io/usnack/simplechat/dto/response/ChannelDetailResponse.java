package io.usnack.simplechat.dto.response;

import io.usnack.simplechat.entity.ChannelType;

import java.util.UUID;

public record ChannelDetailResponse(
        UUID id,
        ChannelType type,
        String name,
        String description,
        UUID categoryId,
        UUID ownerId,
        Long createdAt,
        Long latestMessageAt
) {
}

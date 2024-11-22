package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record ChannelUpdateRequest(
        UUID channelId,
        String name,
        String description,
        UUID ownerId
) {
}

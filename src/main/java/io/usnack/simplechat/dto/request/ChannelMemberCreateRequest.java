package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record ChannelMemberCreateRequest(
        UUID userId,
        UUID channelId
) {
}

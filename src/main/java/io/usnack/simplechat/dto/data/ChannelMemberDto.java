package io.usnack.simplechat.dto.data;

import java.util.UUID;

public record ChannelMemberDto(
        UUID id,
        UUID userId,
        UUID channelId,
        Long readAt
) {
}

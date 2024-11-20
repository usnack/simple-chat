package io.usnack.simplechat.dto.response;

import java.util.UUID;

public record ChannelMemberDetailResponse(
        UUID id,
        UUID userId,
        UUID channelId,
        Long readAt
) {
}

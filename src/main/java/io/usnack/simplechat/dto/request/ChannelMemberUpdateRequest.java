package io.usnack.simplechat.dto.request;

import java.util.UUID;

public record ChannelMemberUpdateRequest(
        UUID channelMemberId,
        Long readAt
) {
}

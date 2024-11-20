package io.usnack.simplechat.dto.response;

import java.util.List;

public record ChannelListResponse(
        List<ChannelDetailResponse> channels
) {
}

package io.usnack.simplechat.dto.response;

import java.util.List;

public record MessageListResponse(
        List<MessageDetailResponse> messages
) {
}

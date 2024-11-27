package io.usnack.simplechat.dto.request;

import java.util.List;
import java.util.UUID;

public record PrivateChannelCreateRequest(
        List<UUID> userIds
) {
}

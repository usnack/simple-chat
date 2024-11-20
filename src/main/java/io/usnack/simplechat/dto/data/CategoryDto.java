package io.usnack.simplechat.dto.data;

import java.util.List;
import java.util.UUID;

public record CategoryDto(
        UUID id,
        String name,
        Long createdAt,
        List<ChannelDto> channels
) {
}

package io.usnack.simplechat.dto.data;

import java.util.UUID;

public record AttachmentDto(
        UUID id,
        String url,
        UUID messageId
) { }

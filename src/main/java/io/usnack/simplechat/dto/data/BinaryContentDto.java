package io.usnack.simplechat.dto.data;

import java.util.UUID;

public record BinaryContentDto(
        UUID id,
        String fileName,
        Long size,
        String contentType,
        String path
) {
}

package io.usnack.simplechat.dto.data;

import java.io.InputStream;
import java.util.UUID;

public record BinaryContentInputStreamDto(
        UUID id,
        String fileName,
        Long size,
        String contentType,
        String path,
        InputStream inputStream
) {
}

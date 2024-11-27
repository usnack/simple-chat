package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BinaryContentCreateRequest(
        @NotNull
        String fileName,
        @NotNull
        String contentType,
        @NotNull
        @Size(min = 1, max = 10 * 1024 * 1024, message = "파일 크기는 10MB를 초과할 수 없습니다")
        byte[] bytes
) {
}

package io.usnack.simplechat.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record UserUpdateRequest(
        @Nullable
        String username,
        @Nullable
        @Email
        String email,
        @Nullable
        String password,
        @Nullable
        BinaryContentCreateRequest profileImage
) {
}

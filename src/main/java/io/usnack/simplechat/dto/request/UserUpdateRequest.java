package io.usnack.simplechat.dto.request;

public record UserUpdateRequest(
        String username,
        String email,
        String password,
        BinaryContentCreateRequest profileImage
) {
}

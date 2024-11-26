package io.usnack.simplechat.dto.request;

public record UserCreateRequest(
        String username,
        String email,
        String password,
        BinaryContentCreateRequest profileImage
) {
}

package io.usnack.simplechat.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty
        String username,
        @NotEmpty
        @Email(message = "이메일 형식이 아닙니다.")
        String email,
        @NotEmpty
        String password
) {
}

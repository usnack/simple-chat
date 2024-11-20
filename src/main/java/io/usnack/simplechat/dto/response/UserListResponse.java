package io.usnack.simplechat.dto.response;

import java.util.List;

public record UserListResponse(
        List<UserDetailResponse> users
) {
}
